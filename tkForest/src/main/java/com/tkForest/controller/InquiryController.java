package com.tkForest.controller;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
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


@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    final InquiryService inquiryService;
    
    /*
     *  리스트 조회
     */

    @GetMapping("/inquiryList")
    public String inquiryList(
        @AuthenticationPrincipal UserDetails loginUser,
        Model model
    ) {
        List<InquiryDTO> inquiryList;

        // 로그인한 사용자가 셀러일 때
        if (loginUser instanceof LoginSellerDetails) {
            String sellerMemberNo = ((LoginSellerDetails) loginUser).getSellerMemberNo();
            inquiryList = inquiryService.findInquiriesBySeller(sellerMemberNo);
        }
        // 로그인한 사용자가 바이어일 때
        else if (loginUser instanceof LoginBuyerDetails) {
            String buyerMemberNo = ((LoginBuyerDetails) loginUser).getBuyerMemberNo();
            inquiryList = inquiryService.findInquiriesByBuyer(buyerMemberNo);
        } else {
            return "redirect:/user/login";  // 인증되지 않은 사용자는 로그인 페이지로 리다이렉트
        }

        // 모델에 인콰이어리 리스트 추가
        model.addAttribute("list", inquiryList);

        return "inquiry/inquiryList";  // Thymeleaf 템플릿을 통해 뷰로 전달
    }
    
    /*
     * 상세 조회
     */
    @GetMapping("/inquiryDetail")
    public String inquiryDetail(
        @RequestParam("inquiryNo") Integer inquiryNo,
        Model model
    ) {
        // 인콰이어리 정보 조회
        InquiryDTO inquiry = inquiryService.findInquiryByNo(inquiryNo);

        // 인콰이어리 정보가 존재할 때만 모델에 추가
        if (inquiry != null) {
            model.addAttribute("inquiry", inquiry);
            return "inquiry/inquiryDetail";
        }

        // 데이터가 없으면 에러 페이지로 리다이렉트하거나 목록으로 이동
        return "redirect:/inquiry/inquiryList";
    }
    
    /*
     * 작성
     */
	 // 인콰이어리 작성 폼을 GET 요청으로 반환
	    @GetMapping("/inquiryWrite")
	    public String showInquiryWriteForm(Model model) {
	        model.addAttribute("inquiryDTO", new InquiryDTO());
	        return "inquiry/inquiryWrite"; // 폼을 반환할 HTML 파일
	    }

	
	    // 인콰이어리 작성 처리 (POST 요청)
	    @PostMapping("/inquiryWrite")
	    public String inquiryWrite(@ModelAttribute InquiryDTO inquiryDTO, Model model) {
	        // 서비스 호출하여 데이터 저장
	        inquiryService.insertInquiry(inquiryDTO);
	
	        // 저장 후 리스트 페이지로 리다이렉트
	        return "redirect:/inquiry/inquiryList";
	    }
}

//@Slf4j
//@Controller
//@RequestMapping("/inquiry")
//@RequiredArgsConstructor
//public class InquiryController {
//
//    final InquiryService inquiryService;
//    
//    @GetMapping("/inquiryList")
//    public String inquiryList(
//            @AuthenticationPrincipal UserDetails loginUser,
//            @PageableDefault(page = 0, size = 10) Pageable pageable,
//            @RequestParam(name = "searchItem", defaultValue = "subject") String searchItem,
//            @RequestParam(name = "searchWord", defaultValue = "") String searchWord,
//            Model model
//    ) {
//        // 인증되지 않은 사용자는 접근 불가
//        if (loginUser == null) {
//            return "redirect:/user/login";  // 로그인 페이지로 리다이렉트
//        }
//
//        Page<InquiryDTO> list;
//        if (loginUser instanceof LoginSellerDetails) {
//            // 로그인된 사용자가 셀러일 때
//            String sellerMemberNo = ((LoginSellerDetails) loginUser).getSellerMemberNo();
//            list = inquiryService.findInquiriesBySeller(sellerMemberNo, pageable);
//        } else if (loginUser instanceof LoginBuyerDetails) {
//            // 로그인된 사용자가 바이어일 때
//            String buyerMemberNo = ((LoginBuyerDetails) loginUser).getBuyerMemberNo();
//            list = inquiryService.findInquiriesByBuyer(buyerMemberNo, pageable);
//        } else {
//            return "redirect:/user/login";  // 인증되지 않은 경우 로그인 페이지로 리다이렉트
//        }
//
//        // 모델에 값 추가
//        model.addAttribute("list", list);
//        model.addAttribute("searchItem", searchItem);
//        model.addAttribute("searchWord", searchWord);
//
//        return "inquiry/inquiryList";
//    }
//}


//    /**
//     * 게시글 목록 조회를 위해 DB에 요청처리
//     * @param model
//     * @return
//     */
//    @GetMapping("/inquiryList")
//    public String inquiryList(
////            우선적으로 로그인 인증 정보를 안담고 연결만 하기 위해 주석처리
////            @AuthenticationPrincipal UserDetails loginUser,
////            @PageableDefault(page=1) Pageable pageable,
////            @RequestParam(name="searchItem", defaultValue="subject") String searchItem,
////            @RequestParam(name="searchWord", defaultValue="") String searchWord,
////            Model model
//            ) {
//
////        // 인증되지 않은 사용자는 접근 불가
////        if (!(loginUser instanceof LoginSellerDetails) && !(loginUser instanceof LoginBuyerDetails)) {
////            return "redirect:/user/login";  // 로그인 페이지로 리다이렉트
//    		  return "inquiry/inquiryList";
//        }
//
//
//    /**
//     * 글 자세히 보기
//     * @param inquiryNo
//     * @param model
//     * @return
//     */
//    @GetMapping("/inquiryDetail")
//    public String inquiryDetail(
////            @AuthenticationPrincipal UserDetails loginUser,
////            @RequestParam(name="inquiryNo") Integer inquiryNo,
////            @RequestParam(name="searchItem", defaultValue="subject") String searchItem,
////            @RequestParam(name="searchWord", defaultValue="") String searchWord,
////            Model model
//    		) {
////
//////        // 인증되지 않은 사용자는 접근 불가
//////        if (!(loginUser instanceof LoginSellerDetails) && !(loginUser instanceof LoginBuyerDetails)) {
//////            return "redirect:/user/login";  // 로그인 페이지로 리다이렉트
//////        }
//////
//////        // 검색기능 + 페이징
//////        Page<InquiryDTO> list = inquiryService.selectAll(pageable, searchItem, searchWord);
//////
//////        int totalPages = list.getTotalPages();
//////        int page = pageable.getPageNumber();
//////
//////        PageNavigator navi = new PageNavigator(pageLimit, page, totalPages);
//////
//////        model.addAttribute("list", list);
//////        model.addAttribute("searchItem", searchItem);
//////        model.addAttribute("searchWord", searchWord);
//////        model.addAttribute("navi", navi);
//////
//////        if (loginUser instanceof LoginSellerDetails) {
//////            LoginSellerDetails sellerDetails = (LoginSellerDetails) loginUser;
//////            model.addAttribute("loginName", sellerDetails.getUsername());
//////        } else if (loginUser instanceof LoginBuyerDetails) {
//////            LoginBuyerDetails buyerDetails = (LoginBuyerDetails) loginUser;
//////            model.addAttribute("loginName", buyerDetails.getUsername());
//////        }
////
//
////        InquiryDTO inquiry = inquiryService.selectOne(inquiryNo);
////
////        if (inquiry == null) {
////            return "redirect:/inquiry/inquiryList";
////        }
////
////        model.addAttribute("inquiry", inquiry);
////        model.addAttribute("searchItem", searchItem);
////        model.addAttribute("searchWord", searchWord);
////
////        // 로그인 사용자 이름 추가
////        if (loginUser != null) {
////            model.addAttribute("loginName", loginUser.getUsername());
////        }
////
//        return "inquiry/inquiryDetail";
//    }
//
//
//
//    /**
//     * 인콰 작성 화면 요청 (바이어만 접근 가능)
//     * @return
//     */
//    @GetMapping("/inquiryWrite")
//    public String inquiryWrite(
////  		  우선적으로 로그인 인증 정보를 안담고 연결만 하기 위해 주석처리    		
////            @AuthenticationPrincipal UserDetails loginUser,
////            Model model
//    		) {
////
////    final InquiryService inquiryService;
////
////        // 인증된 바이어의 이름 추가
////        if (loginUser instanceof LoginBuyerDetails) {
////            model.addAttribute("loginName", ((LoginBuyerDetails) loginUser).getUsername());
////        }
//
//        return "inquiry/inquiryWrite";
//    }
//    
//
//
////
////    /**
////     * DB에 글을 등록 처리하는 요청
////     * 첨부 파일도 포함
////     * @return
////     */
////    @PostMapping("/inquiryWrite")
//////    public String inquiryWrite(@AuthenticationPrincipal UserDetails loginUser,
//////                                   @ModelAttribute InquiryDTO inquiryDTO) {
//////
//////        // 인증되지 않은 사용자는 접근 불가
//////        if (!(loginUser instanceof LoginSellerDetails) && !(loginUser instanceof LoginBuyerDetails)) {
//////            return "redirect:/user/login";
//////        }
//////
//////        log.info("클라이언트에서 전송된 데이터 : {}", inquiryDTO.toString());
//////
//////        inquiryService.insertInquiry(inquiryDTO);
//////
//////        return "redirect:/inquiry/inquiryList";
//////    }
////
//////    /**
//////     * 글 자세히 보기
//////     * @param inquiryNo
//////     * @param model
//////     * @return
//////     */
//////    @GetMapping("/inquiryDetail")
//////    public String inquiryDetail(
//////            @AuthenticationPrincipal UserDetails loginUser,
//////            @RequestParam(name="inquiryNo") Integer inquiryNo,
//////            @RequestParam(name="searchItem", defaultValue="subject") String searchItem,
//////            @RequestParam(name="searchWord", defaultValue="") String searchWord,
//////            Model model) {
//////
//////        // 인증되지 않은 사용자는 접근 불가
//////        if (!(loginUser instanceof LoginSellerDetails) && !(loginUser instanceof LoginBuyerDetails)) {
//////            return "redirect:/user/login";
//////        }
//////
//////        InquiryDTO inquiry = inquiryService.selectOne(inquiryNo);
//////
//////        if (inquiry == null) {
//////            return "redirect:/inquiry/inquiryList";
//////        }
//////
//////        model.addAttribute("inquiry", inquiry);
//////        model.addAttribute("searchItem", searchItem);
//////        model.addAttribute("searchWord", searchWord);
//////
//////        // 로그인 사용자 이름 추가
//////        if (loginUser != null) {
//////            model.addAttribute("loginName", loginUser.getUsername());
//////        }
//////
//////        return "inquiry/inquiryDetail";
//////    }
//////
//
//////
//////    /**
//////     * 파일 다운로드
//////     * @param inquiryNo
//////     * @param response
//////     * @return
//////     */
//////    @GetMapping("/download")
//////    public String download(
//////            @RequestParam(name="inquiryNo") Integer inquiryNo,
//////            HttpServletResponse response) {
//////
//////        InquiryDTO inquiryDTO = inquiryService.selectOne(inquiryNo);
//////
//////        String originalFileName = inquiryDTO.getOriginalFileName();
//////        String savedFileName = inquiryDTO.getSavedFileName();
//////
//////        log.info("원본 파일명 : {}", originalFileName);
//////        log.info("저장 파일명 : {}", savedFileName);
//////        log.info("저장 디렉토리 : {}", uploadPath);
//////
//////        try {
//////            String tempName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8.toString());
//////            response.setHeader("Content-Disposition", "attachment;filename=" + tempName);
//////        } catch (UnsupportedEncodingException e) {
//////            e.printStackTrace();
//////        }
//////
//////        String fullPath = uploadPath + "/" + savedFileName;
//////
//////        try (FileInputStream filein = new FileInputStream(fullPath);
//////             ServletOutputStream fileout = response.getOutputStream()) {
//////
//////            FileCopyUtils.copy(filein, fileout);
//////        } catch (Exception e) {
//////            e.printStackTrace();
//////        }
//////
//////        return null;
//////    }
//}

