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
import com.tkForest.entity.BuyerEntity;
import com.tkForest.entity.InquiryEntity;
import com.tkForest.repository.InquiryRepository;
import com.tkForest.service.InquiryService;
import com.tkForest.util.PageNavigator;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
@Slf4j
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
//	    @GetMapping("/inquiryWrite")
//	    public String showInquiryWriteForm(Model model) {
//	        model.addAttribute("inquiryDTO", new InquiryDTO());
//	        return "inquiry/inquiryWrite"; // 폼을 반환할 HTML 파일
//	    }

	
	    // 인콰이어리 작성 처리 (POST 요청)
	    @PostMapping("/inquiryWrite")
	    public String inquiryWrite(@ModelAttribute InquiryDTO inquiryDTO, Model model) {
	    	log.info("Received Inquiry: {}", inquiryDTO);
	        log.info("SellerMemberNo: {}", inquiryDTO.getSellerMemberNo());
	        log.info("ProductNo: {}", inquiryDTO.getProductNo());
	        log.info("BuyerMemberNo: {}", inquiryDTO.getBuyerMemberNo());
	        // 서비스 호출하여 데이터 저장
	        inquiryService.insertInquiry(inquiryDTO);
	
	        // 저장 후 리스트 페이지로 리다이렉트
	        return "redirect:/inquiry/inquiryList";
	    }
	    
	    @GetMapping("/inquiryWrite")
	    public String showInquiryWriteForm(
	        @AuthenticationPrincipal UserDetails loginUser,  
	        Model model
	    ) {
	        // 로그인한 사용자가 바이어일 경우
	        if (loginUser instanceof LoginBuyerDetails) {
	            // 로그인한 바이어의 buyerMemberNo와 이름 가져오기
	            String buyerMemberNo = ((LoginBuyerDetails) loginUser).getBuyerMemberNo();
	            String userName = ((LoginBuyerDetails) loginUser).getUsername();  // 바이어의 이름 가져오기
	            
	            // DTO에 buyerMemberNo 설정 및 모델에 추가
	            InquiryDTO inquiryDTO = new InquiryDTO();
	            inquiryDTO.setBuyerMemberNo(buyerMemberNo);  // 로그인된 바이어의 buyerMemberNo 설정
	            model.addAttribute("inquiryDTO", inquiryDTO);
	            model.addAttribute("buyerMemberNo", buyerMemberNo);  // buyerMemberNo를 따로 전달
	            model.addAttribute("picName", userName);  // 바이어의 이름을 모델에 추가
	        } 
	        // 로그인한 사용자가 셀러일 경우
	        else if (loginUser instanceof LoginSellerDetails) {
	            // 로그인한 셀러의 sellerMemberNo와 이름 가져오기
	            String sellerMemberNo = ((LoginSellerDetails) loginUser).getSellerMemberNo();
	            String userName = ((LoginSellerDetails) loginUser).getUsername();  // 셀러의 이름 가져오기
	            
	            // DTO에 sellerMemberNo 설정 및 모델에 추가
	            InquiryDTO inquiryDTO = new InquiryDTO();
	            inquiryDTO.setSellerMemberNo(sellerMemberNo);  // 로그인된 셀러의 sellerMemberNo 설정
	            model.addAttribute("inquiryDTO", inquiryDTO);
	            model.addAttribute("sellerMemberNo", sellerMemberNo);  // sellerMemberNo를 따로 전달
	            model.addAttribute("picName", userName);  // 셀러의 이름을 모델에 추가
	        } 
	        // 비로그인 사용자 처리
	        else {
	            return "redirect:/user/login";
	        }

	        return "inquiry/inquiryWrite";  // 폼을 반환할 HTML 파일
	    }

	    /*
	     * 상품 상세 화면에서 인콰이어리 작성(상품)
	     */
	    @GetMapping("/inquiryWrite-product")
	    public String showInquiryWriteForm(
	            @RequestParam("sellerMemberNo") String sellerMemberNo,
	            @RequestParam("productNo") Integer productNo,
	            Model model
	        ) {
	        // InquiryDTO에 셀러 번호와 상품 번호를 설정
	        InquiryDTO inquiryDTO = new InquiryDTO();
	        inquiryDTO.setSellerMemberNo(sellerMemberNo);
	        inquiryDTO.setProductNo(productNo);

	        // 모델에 inquiryDTO를 추가
	        model.addAttribute("inquiry", inquiryDTO);
	        model.addAttribute("sellerMemberNo", sellerMemberNo);
	        model.addAttribute("productNo", productNo);

	        return "inquiryWrite-product";  // 해당 페이지로 이동
	    }

}
