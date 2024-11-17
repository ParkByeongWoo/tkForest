package com.tkForest.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tkForest.dto.BCategoryDTO;
import com.tkForest.dto.CategoryDTO;
import com.tkForest.dto.LoginBuyerDetails;
import com.tkForest.dto.ProductDTO;
import com.tkForest.service.ProductService;
import com.tkForest.service.RecService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/rec")
@RequiredArgsConstructor
public class RecController {
	
	final RecService recService;
	final ProductService productService;
	
    @GetMapping("/recList")
    public String recPage(@AuthenticationPrincipal LoginBuyerDetails userDetails, Model model) {
        log.info("상품 추천 페이지로 넘어감");
        if(userDetails != null)
        	{
        	// GET 요청 시 카테고리 데이터를 미리 로드
        	String buyerMemberNo = userDetails.getBuyerMemberNo();

        	List<BCategoryDTO> bCategoryDTOList = recService.recCategory(buyerMemberNo);
        	List<CategoryDTO> categoryList = recService.category(bCategoryDTOList);
        	List<String> keywordList = recService.keyword(buyerMemberNo);
     	   	List<ProductDTO> likeList = productService.selectAllLike(buyerMemberNo);
     	   	List<ProductDTO> inquiryList = productService.selectAllInquiry(buyerMemberNo);
        	model.addAttribute("categoryList", categoryList);
        	model.addAttribute("keywordList", keywordList);
        	model.addAttribute("likeList", likeList);
        	model.addAttribute("inquiryList",inquiryList);
        	}

        return "rec/recommendationmember";  // 카테고리 데이터가 포함된 페이지 렌더링
    }
	
		
		
	@PostMapping("/recList")
	@ResponseBody
	public List<ProductDTO> recList(@ModelAttribute String buyerMember,
			@AuthenticationPrincipal LoginBuyerDetails userDetails) {
		String buyerMemberNo = userDetails.getBuyerMemberNo();
		List<ProductDTO> productDTOList = recService.recList(buyerMemberNo);
		
		
		return productDTOList;
	}
}
