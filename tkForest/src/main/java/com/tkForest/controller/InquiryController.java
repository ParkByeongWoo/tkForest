package com.tkForest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tkForest.dto.InquiryDTO;
import com.tkForest.dto.LoginBuyerDetails;
import com.tkForest.dto.LoginSellerDetails;
import com.tkForest.dto.ProductDTO;
import com.tkForest.repository.BuyerRepository;
import com.tkForest.service.InquiryService;
import com.tkForest.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
@Slf4j
public class InquiryController {

 
    private final InquiryService inquiryService;
    private final ProductService productService;
	private BuyerRepository buyerService;

    
    /*
     *  리스트 조회
     */
	@GetMapping("/inquiryList")
	public String inquiryList(@AuthenticationPrincipal UserDetails loginUser, Model model) {
	    List<InquiryDTO> inquiryList;
	    Map<String, String> buyerNames = new HashMap<>();

	    if (loginUser instanceof LoginSellerDetails) {
	        String sellerMemberNo = ((LoginSellerDetails) loginUser).getSellerMemberNo();
	        inquiryList = inquiryService.findInquiriesBySeller(sellerMemberNo);
	    } else if (loginUser instanceof LoginBuyerDetails) {
	        String buyerMemberNo = ((LoginBuyerDetails) loginUser).getBuyerMemberNo();
	        inquiryList = inquiryService.findInquiriesByBuyer(buyerMemberNo);

	        // 바이어 이름을 조회하여 맵에 추가
	        for (InquiryDTO inquiry : inquiryList) {
	            String buyerName = inquiryService.getBuyerNameByMemberNo(inquiry.getBuyerMemberNo());
	            buyerNames.put(inquiry.getBuyerMemberNo(), buyerName);
	        }
	    } else {
	        return "redirect:/user/login";  // 인증되지 않은 사용자는 로그인 페이지로 리다이렉트
	    }

	    model.addAttribute("list", inquiryList);
	    model.addAttribute("buyerNames", buyerNames);

	    return "inquiry/inquiryList";
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
     * 작성 (기본 인콰 write 페이지)
     */
	    // 인콰이어리 작성 처리 (POST 요청)
	    @PostMapping("/inquiryWrite")
	    public String inquiryWrite(@ModelAttribute InquiryDTO inquiryDTO, Model model) {

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
	     * 상품 리스트 화면에서 인콰이어리 작성(상품)
	     */
	    @PostMapping("/inquiryWriteProduct")
	    public String inquiryWriteProduct(@ModelAttribute InquiryDTO inquiryDTO, Model model) {
	        // 서비스 호출하여 데이터 저장
	        inquiryService.insertInquiry(inquiryDTO);

	        // 저장 후 리스트 페이지로 리다이렉트
	        return "redirect:/inquiry/inquiryList";
	    }

	    @GetMapping("/inquiryWriteProduct")
	    public String showInquiryWriteProductForm(
	            @RequestParam("sellerMemberNo") String sellerMemberNo,
	            @RequestParam("productNo") Integer productNo,
	            @AuthenticationPrincipal UserDetails loginUser,
	            Model model
	        ) {
	        InquiryDTO inquiryDTO = new InquiryDTO();
	        
	        // 로그인한 사용자가 바이어일 경우
	        if (loginUser instanceof LoginBuyerDetails) {
	            String buyerMemberNo = ((LoginBuyerDetails) loginUser).getBuyerMemberNo();
	            String userName = ((LoginBuyerDetails) loginUser).getUsername();  // 바이어의 이름 가져오기

	            // DTO에 필요한 값 설정
	            inquiryDTO.setBuyerMemberNo(buyerMemberNo);  // 로그인된 바이어의 buyerMemberNo 설정
	            inquiryDTO.setSellerMemberNo(sellerMemberNo);  // 상품의 셀러 번호 설정
	            inquiryDTO.setProductNo(productNo);  // 상품 번호 설정

	            // 상품명 조회
	            String productName = productService.findProductNameById(productNo);  // 상품명 가져오기

	            // 셀러의 picName 조회
	            String sellerPicName = inquiryService.getSellerNameByMemberNo(sellerMemberNo);  // 셀러의 picName 가져오기

	            // 모델에 필요한 값 추가
	            model.addAttribute("inquiry", inquiryDTO);  // "inquiry"로 inquiryDTO 전달
	            model.addAttribute("buyerMemberNo", buyerMemberNo);  // buyerMemberNo 전달
	            model.addAttribute("picName", userName);  // 바이어의 이름 전달
	            model.addAttribute("sellerPicName", sellerPicName);  // 셀러 이름 전달
	            model.addAttribute("sellerMemberNo", sellerMemberNo);  // 셀러 번호 전달
	            model.addAttribute("productNo", productNo);  // 상품 번호 전달
	            model.addAttribute("productName", productName);  // 상품명 전달
	        } else {
	            return "redirect:/user/login";  // 비로그인 또는 바이어가 아닐 경우 로그인 페이지로 리다이렉트
	        }

	        return "inquiry/inquiryWriteProduct";  // inquiryWriteProduct 템플릿 반환
	    }

 
	    /*
	     * 상품 상세 화면에서 인콰이어리 작성(셀러)
	     */
	    @PostMapping("/inquiryWriteSeller")
	    public String submitInquiryWriteSeller(@ModelAttribute InquiryDTO inquiryDTO, Model model) {
	        // 서비스 호출하여 인콰이어리 저장
	        inquiryService.insertInquiry(inquiryDTO);

	        // 저장 후 리스트 페이지로 리다이렉트
	        return "redirect:/inquiry/inquiryList";
	    }

	    @GetMapping("/inquiryWriteSeller")
	    public String showInquiryWriteSellerForm(
	            @RequestParam("sellerMemberNo") String sellerMemberNo,
	            @RequestParam("productNo") Integer productNo,
	            @AuthenticationPrincipal UserDetails loginUser,
	            Model model
	        ) {
	        InquiryDTO inquiryDTO = new InquiryDTO();
	        
	        // 셀러의 상품 목록 조회 -- ProductService 밑단에 임시로 생성함
	        List<ProductDTO> productList = productService.findProductsBySellerMemberNo(sellerMemberNo);
	        
	        // 로그인한 사용자가 바이어일 경우
	        if (loginUser instanceof LoginBuyerDetails) {
	            String buyerMemberNo = ((LoginBuyerDetails) loginUser).getBuyerMemberNo();
	            String userName = ((LoginBuyerDetails) loginUser).getUsername();
	            
	            // DTO에 필요한 값 설정
	            inquiryDTO.setBuyerMemberNo(buyerMemberNo);
	            inquiryDTO.setSellerMemberNo(sellerMemberNo);
	            inquiryDTO.setProductNo(productNo);
	            
	            // 상품명 조회
	            String productName = productService.findProductNameById(productNo);  // 상품명 가져오기

	            // 셀러의 picName 조회
	            String sellerPicName = inquiryService.getSellerNameByMemberNo(sellerMemberNo);  // 셀러의 picName 가져오기
        
	            // 모델에 추가
	            model.addAttribute("inquiry", inquiryDTO);
	            model.addAttribute("productList", productList);  // 셀러의 상품 목록 전달
	            model.addAttribute("buyerMemberNo", buyerMemberNo);
	            model.addAttribute("picName", userName);
	            model.addAttribute("sellerPicName", sellerPicName);  // 셀러 이름 전달
	            model.addAttribute("sellerMemberNo", sellerMemberNo);
	            model.addAttribute("productNo", productNo);
	        } else {
	            return "redirect:/user/login";  // 비로그인 사용자 리다이렉트
	        }

	        return "inquiry/inquiryWriteSeller";
	    }


//	    @PostMapping("/inquiryWriteSeller")
//	    public String inquiryWriteSeller(@ModelAttribute InquiryDTO inquiryDTO, Model model) {
//	        // 서비스 호출하여 데이터 저장
//	        inquiryService.insertInquiry(inquiryDTO);
//
//	        // 저장 후 리스트 페이지로 리다이렉트
//	        return "redirect:/inquiry/inquiryList";
//	    }
//
//	    @GetMapping("/inquiryWriteSeller")
//	    public String showInquiryWriteSellerForm(
//	            @RequestParam("sellerMemberNo") String sellerMemberNo,
//	            @RequestParam("productNo") Integer productNo,
//	            @AuthenticationPrincipal UserDetails loginUser,
//	            Model model
//	        ) {
//	        InquiryDTO inquiryDTO = new InquiryDTO();
//	        
//	        // 로그인한 사용자가 바이어일 경우
//	        if (loginUser instanceof LoginBuyerDetails) {
//	            String buyerMemberNo = ((LoginBuyerDetails) loginUser).getBuyerMemberNo();
//	            String userName = ((LoginBuyerDetails) loginUser).getUsername();  // 바이어의 이름 가져오기
//
//	            // DTO에 필요한 값 설정
//	            inquiryDTO.setBuyerMemberNo(buyerMemberNo);  // 로그인된 바이어의 buyerMemberNo 설정
//	            inquiryDTO.setSellerMemberNo(sellerMemberNo);  // 상품의 셀러 번호 설정
//	            inquiryDTO.setProductNo(productNo);  // 상품 번호 설정
//
//	            // 상품명 조회
//	            String productName = productService.findProductNameById(productNo);  // 상품명 가져오기
//
//	            // 모델에 필요한 값 추가
//	            model.addAttribute("inquiry", inquiryDTO);  // "inquiry"로 inquiryDTO 전달
//	            model.addAttribute("buyerMemberNo", buyerMemberNo);  // buyerMemberNo 전달
//	            model.addAttribute("picName", userName);  // 바이어의 이름 전달
//	            model.addAttribute("sellerMemberNo", sellerMemberNo);  // 셀러 번호 전달
//	            model.addAttribute("productNo", productNo);  // 상품 번호 전달
//	            model.addAttribute("productName", productName);  // 상품명 전달
//	        } else {
//	            return "redirect:/user/login";  // 비로그인 또는 바이어가 아닐 경우 로그인 페이지로 리다이렉트
//	        }
//
//	        return "inquiry/inquiryWriteProduct";  // inquiryWriteProduct 템플릿 반환
//	    }

}
