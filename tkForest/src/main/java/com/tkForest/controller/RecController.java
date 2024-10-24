package com.tkForest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tkForest.dto.BCategoryDTO;
import com.tkForest.dto.LoginBuyerDetails;
import com.tkForest.dto.PCategoryDTO;
import com.tkForest.dto.ProductDTO;
import com.tkForest.entity.ProductEntity;
import com.tkForest.service.RecService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/rec")
@RequiredArgsConstructor
public class RecController {
	
	final RecService recService;
	
	@GetMapping("/recList")
	public String recPage() {
		log.info("상품 추천 페이지로 넘어감");
		
		return "rec/recommendationmember";
	}
	
	@PostMapping("/recCategory")
	public String recCategory(@AuthenticationPrincipal LoginBuyerDetails userDetails
			, Model model) {
	String buyerMemberNo = userDetails.getBuyerMemberNo();
		List<BCategoryDTO> bCategoryDTO = recService.recCategory(buyerMemberNo);
		
		model.addAttribute("bCategory", bCategoryDTO);
		return "rec/recommendationmember";
	}
		
		
	@PostMapping("/recList")
	@ResponseBody
	public List<ProductDTO> recList(@ModelAttribute String buyerMember,
			@AuthenticationPrincipal LoginBuyerDetails userDetails) {
		String buyerMemberNo = userDetails.getBuyerMemberNo();
		List<ProductDTO> productEntityList = recService.recList(buyerMemberNo);
		
		
		return productEntityList;
	}
}
