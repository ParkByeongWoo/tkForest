package com.tkForest.controller;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tkForest.dto.InquiryDTO;
import com.tkForest.dto.LoginBuyerDetails;
import com.tkForest.dto.LoginSellerDetails;
import com.tkForest.service.InquiryService;
import com.tkForest.util.PageNavigator;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    final InquiryService inquiryService;

    // 한 페이지의 게시글 수
    @Value("${user.inquiry.pageLimit}")
    private int pageLimit;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    /**
     * 게시글 목록 조회를 위해 DB에 요청처리
     * @param model
     * @return
     */
    @GetMapping("/inquiryList")
    public String inquiryList(
            @AuthenticationPrincipal UserDetails loginUser,
            @PageableDefault(page=1) Pageable pageable,
            @RequestParam(name="searchItem", defaultValue="subject") String searchItem,
            @RequestParam(name="searchWord", defaultValue="") String searchWord,
            Model model) {

        // 인증되지 않은 사용자는 접근 불가
        if (!(loginUser instanceof LoginSellerDetails) && !(loginUser instanceof LoginBuyerDetails)) {
            return "redirect:/user/login";  // 로그인 페이지로 리다이렉트
        }

        // 검색기능 + 페이징
        Page<InquiryDTO> list = inquiryService.selectAll(pageable, searchItem, searchWord);

        int totalPages = list.getTotalPages();
        int page = pageable.getPageNumber();

        PageNavigator navi = new PageNavigator(pageLimit, page, totalPages);

        model.addAttribute("list", list);
        model.addAttribute("searchItem", searchItem);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("navi", navi);

        if (loginUser instanceof LoginSellerDetails) {
            LoginSellerDetails sellerDetails = (LoginSellerDetails) loginUser;
            model.addAttribute("loginName", sellerDetails.getUsername());
        } else if (loginUser instanceof LoginBuyerDetails) {
            LoginBuyerDetails buyerDetails = (LoginBuyerDetails) loginUser;
            model.addAttribute("loginName", buyerDetails.getUsername());
        }

        return "inquiry/inquiryList";
    }

    /**
     * 인콰 작성 화면 요청 (바이어만 접근 가능)
     * @return
     */
    @GetMapping("/inquiryWrite")
    public String inquiryWrite(
            @AuthenticationPrincipal UserDetails loginUser,
            Model model) {

        // 인증되지 않은 사용자는 접근 불가
        if (!(loginUser instanceof LoginBuyerDetails)) {
            return "redirect:/user/login";  // 바이어가 아니면 로그인 페이지로 리다이렉트
        }

        // 인증된 바이어의 이름 추가
        if (loginUser instanceof LoginBuyerDetails) {
            model.addAttribute("loginName", ((LoginBuyerDetails) loginUser).getUsername());
        }

        return "inquiry/inquiryWrite";
    }


    /**
     * DB에 글을 등록 처리하는 요청
     * 첨부 파일도 포함
     * @return
     */
    @PostMapping("/inquiryWrite")
    public String inquiryWrite(@AuthenticationPrincipal UserDetails loginUser,
                                   @ModelAttribute InquiryDTO inquiryDTO) {

        // 인증되지 않은 사용자는 접근 불가
        if (!(loginUser instanceof LoginSellerDetails) && !(loginUser instanceof LoginBuyerDetails)) {
            return "redirect:/user/login";
        }

        log.info("클라이언트에서 전송된 데이터 : {}", inquiryDTO.toString());

        inquiryService.insertInquiry(inquiryDTO);

        return "redirect:/inquiry/inquiryList";
    }

    /**
     * 글 자세히 보기
     * @param inquiryNo
     * @param model
     * @return
     */
    @GetMapping("/inquiryDetail")
    public String inquiryDetail(
            @AuthenticationPrincipal UserDetails loginUser,
            @RequestParam(name="inquiryNo") Integer inquiryNo,
            @RequestParam(name="searchItem", defaultValue="subject") String searchItem,
            @RequestParam(name="searchWord", defaultValue="") String searchWord,
            Model model) {

        // 인증되지 않은 사용자는 접근 불가
        if (!(loginUser instanceof LoginSellerDetails) && !(loginUser instanceof LoginBuyerDetails)) {
            return "redirect:/user/login";
        }

        InquiryDTO inquiry = inquiryService.selectOne(inquiryNo);

        if (inquiry == null) {
            return "redirect:/inquiry/inquiryList";
        }

        model.addAttribute("inquiry", inquiry);
        model.addAttribute("searchItem", searchItem);
        model.addAttribute("searchWord", searchWord);

        // 로그인 사용자 이름 추가
        if (loginUser != null) {
            model.addAttribute("loginName", loginUser.getUsername());
        }

        return "inquiry/inquiryDetail";
    }

    /**
     * 글 삭제 처리
     * @param inquiryNo
     * @return
     */
    @GetMapping("/inquiryDelete")
    public String inquiryDelete(
            @RequestParam(name="inquiryNo") Integer inquiryNo,
            @RequestParam(name="searchItem", defaultValue="subject") String searchItem,
            @RequestParam(name="searchWord", defaultValue="") String searchWord,
            RedirectAttributes rttr) {

        inquiryService.deleteOne(inquiryNo);

        rttr.addAttribute("searchItem", searchItem);
        rttr.addAttribute("searchWord", searchWord);

        return "redirect:/inquiry/inquiryList";
    }

    /**
     * 파일 다운로드
     * @param inquiryNo
     * @param response
     * @return
     */
    @GetMapping("/download")
    public String download(
            @RequestParam(name="inquiryNo") Integer inquiryNo,
            HttpServletResponse response) {

        InquiryDTO inquiryDTO = inquiryService.selectOne(inquiryNo);

        String originalFileName = inquiryDTO.getOriginalFileName();
        String savedFileName = inquiryDTO.getSavedFileName();

        log.info("원본 파일명 : {}", originalFileName);
        log.info("저장 파일명 : {}", savedFileName);
        log.info("저장 디렉토리 : {}", uploadPath);

        try {
            String tempName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment;filename=" + tempName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String fullPath = uploadPath + "/" + savedFileName;

        try (FileInputStream filein = new FileInputStream(fullPath);
             ServletOutputStream fileout = response.getOutputStream()) {

            FileCopyUtils.copy(filein, fileout);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

