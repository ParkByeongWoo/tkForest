package com.tkForest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tkForest.dto.LoginBuyerDetails;
import com.tkForest.dto.LoginSellerDetails;
import com.tkForest.dto.ProductDTO;
import com.tkForest.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MainController {

	final ProductService productService;

	
	//    /**
//     * 첫 화면 요청
//     * @return
//     */
//    @GetMapping({"/", ""})
//    public String index(Model model) {
//        return "index";
//    }

	@GetMapping({"/", ""})
	public String index(
	        @PageableDefault(page=1, size=3) Pageable pageable,
	        @RequestParam(name="searchType", defaultValue="ALL") String searchType,
	        @RequestParam(name="query", defaultValue="") String query,
	        @AuthenticationPrincipal LoginBuyerDetails userDetails,
	        @AuthenticationPrincipal LoginSellerDetails sellerDetails,
	        Model model) {

	    // registrationDate 기준으로 정렬된 리스트
	    Page<ProductDTO> listByRegistrationDate = productService.selectAll(pageable, searchType, query, "registrationDate", null);

	    // viewCount 기준으로 정렬된 리스트
	    Page<ProductDTO> listByViewCnt = productService.selectAll(pageable, searchType, query, "viewCnt", null);
	    // 모델에 각 리스트 추가
	    model.addAttribute("listByRegistrationDate", listByRegistrationDate);
	    model.addAttribute("listByViewCnt", listByViewCnt);
	    model.addAttribute("searchType", searchType);
	    model.addAttribute("query", query);
	    
	    // 바이어로 로그인한 경우에만 buyerMemberNo 추가
	    if (userDetails != null) {
	        String buyerMemberNo = userDetails.getBuyerMemberNo();
	        model.addAttribute("buyerMemberNo", buyerMemberNo);
	    }
	    
	    if (sellerDetails != null) {
	        String sellerMemberNo = sellerDetails.getSellerMemberNo();
	        model.addAttribute("sellerMemberNo", sellerMemberNo);
	    }
	    

	    return "index";
	}
	
   /**
    * 어바웃 어스
    * @return
    */
   @GetMapping("/aboutUs")
   public String aboutUs() {
       return "aboutUs";  
   }
   
   /**
    * (비회원) 메인 홈
    */
   @GetMapping("/home/userHome")
   public String userHome() {
	   return "home/userHome";  
   }
   
   /**
    * (회원) 메인 홈
    */
   @GetMapping("/home/memberHome")
   public String memberHome() {
       return "home/memberHome";  
   }
   
   
   /**
    * 보따리 추천
    * @return
    */
   @GetMapping("/recList")
   public String recList() {
       return "rec/recList";  
   }

}