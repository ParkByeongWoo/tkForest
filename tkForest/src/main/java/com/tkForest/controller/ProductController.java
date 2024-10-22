package com.tkForest.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tkForest.dto.PCategoryDTO;
import com.tkForest.dto.ProductCertificateDTO;
import com.tkForest.dto.ProductDTO;
import com.tkForest.entity.ProductEntity;
import com.tkForest.dto.LoginSellerDetails;
import com.tkForest.service.ProductService;
import com.tkForest.util.PageNavigator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

	// 컨트롤러   
   
   final ProductService productService;
   
   // 한 페이지의 게시글 수
   @Value("${user.inquiry.pageLimit}")
   private int pageLimit;
   
   /**
    * 상품 등록 화면을 요청
    * @param model
    * @return
    */
   @GetMapping("/productCreate")
   public String productCreate()		   
		  {
	   
	 log.info("상품 생성 페이지로 넘어감");
     
	 // 로그인한 셀러의 ID
	 // model.addAttribute("loginId", loginSeller.getUsername());
	 
      return "product/productCreate";
   }
   
   /**
    * 상품 등록 처리
    * @param productDTO
    * @param pCategoryDTOList
    * @param productCertificateDTOList
    * @return
    */
   @PostMapping("/productCreate")
   public String productCreate(
		   @ModelAttribute ProductDTO productDTO
		   , @ModelAttribute PCategoryDTO pCategoryDTO
		   , @ModelAttribute ProductCertificateDTO productCertificateDTO
		   , @AuthenticationPrincipal LoginSellerDetails loginSeller
		   ) {
	 log.info("클라이언트에서 전송된 데이터 : {}", productDTO.toString());
	 
	 // 상품 등록(상품DTO, 로그인한 셀러의 Id)
	 boolean result = productService.productCreate(productDTO, loginSeller.getSellerMemberNo());
     log.info("상품(기본) 등록 성공 여부: {}", result);
	 
	 // 카테고리 추가
	 boolean resultCategory = productService.ProductCategoryInsert(productDTO, pCategoryDTO);
     log.info("상품 카테고리 추가: {}", resultCategory);
     
     // 인증서 추가
     boolean resultCert = productService.ProductCertificateInsert(productDTO, productCertificateDTO);
     log.info("상품 인증서 추가: {}", resultCert);
     
     return "redirect:/"; //마이페이지-상품관리페이지로 넘어갈 것
   }
   
   /**
    * 
    * @param productNo
    * @return
    */
   @GetMapping("/productDetail")
   public String productSelectOne(
		   @RequestParam(name="productNo") Integer productNo
		   , @RequestParam(name="searchItem", defaultValue="") String searchItem
		   , @RequestParam(name="searchWord", defaultValue="") String searchWord
		   , Model model) {
      
      ProductDTO product = productService.selectOne(productNo);
      List<Integer> categoryNos = productService.categoryAll(productNo);
      List<Integer> productCertificate = productService.certificateAll(productNo);
      
      log.info("조회된 상품: {}", product.toString());
      productService.incrementHitcount(productNo);
      
      if(product == null) {
			return "redirect:/product/productList"; 
      }
      
      model.addAttribute("product", product);
      model.addAttribute("categoryNos", categoryNos);
      model.addAttribute("productCertificate", productCertificate);
		// 검색 기능이 추가되면 계속 달고 다녀야 함
      model.addAttribute("searchItem", searchItem);
      model.addAttribute("searchWord", searchWord);
      
      return "product/productDetail";
   }
   
   /**
    * 
    * @param pageable
    * @param searchItem
    * @param searchWord
    * @param model
    * @return
    */
   @GetMapping("/productList")
   public String productList(
		   @PageableDefault(page=1) Pageable pageable,
           @RequestParam(name="searchItem", defaultValue="") String searchItem,
           @RequestParam(name="searchWord", defaultValue="") String searchWord,
           Model model) {
	   // 검색기능 + 페이징
       Page<ProductDTO> list = productService.selectAll(pageable, searchItem, searchWord);

       int totalPages = list.getTotalPages();
       int page = pageable.getPageNumber();

       PageNavigator navi = new PageNavigator(pageLimit, page, totalPages);

       model.addAttribute("list", list);
       model.addAttribute("searchItem", searchItem);
       model.addAttribute("searchWord", searchWord);
       model.addAttribute("navi", navi);
       return "product/productList";  
       
   }
   
   /**
    * 
    * @param productNo
    * @param model
    * @return
    */
   @GetMapping("/productUpdate")
   public String productUpdate(
		   @RequestParam(name="productNo") Integer productNo
		   , Model model
		   )	{
	   
	   ProductDTO product = productService.selectOne(productNo);
	   model.addAttribute("product", product);
	   
	   return "product/productUpdate";
   }
   
   @PostMapping("/productUpdate")
   public String productUpdate(
		   @ModelAttribute ProductDTO product
		   , RedirectAttributes rttr) {
	   log.info("수정할 글: {}", product.toString());
	   
	   productService.updateProduct(product);
	   
	   return "redirect:/product/productDetail";
   }
   
   
   /**
    * 
    * @param productNo
    * @param searchItem
    * @param searchWord
    * @param rttr
    * @return
    */
   @GetMapping("/productDelete")
   public String productDelete(
           @RequestParam(name="productNo") Integer productNo,
           @RequestParam(name="searchItem", defaultValue="") String searchItem,
           @RequestParam(name="searchWord", defaultValue="") String searchWord,
           RedirectAttributes rttr) {

       productService.deleteOne(productNo);

       rttr.addAttribute("searchItem", searchItem);
       rttr.addAttribute("searchWord", searchWord);

       return "redirect:/product/productList";
   }
   
   /**
	 * 게시판 수정화면에서 파일만 삭제하도록 요청
	 */
	@GetMapping("/deleteFile")
	public String deleteFile(
			@RequestParam(name="productNo") int productNo
			, RedirectAttributes rttr
			) {

		// boardService에 파일삭제 요청 (Update와 동일)
		productService.deleteFile(productNo);

		rttr.addAttribute("boardNum", productNo);
		return "redirect:/product/productDetail";
	}
	/*
	 * 상품 상세 화면(임시)
	 */
	 @GetMapping("/productDetail-Temp")
	    public String productDetailTemp() {
	        return "product/productDetail-Temp";  // "productDetail-Temp.html" 템플릿 파일을 반환
	    }
    
}
