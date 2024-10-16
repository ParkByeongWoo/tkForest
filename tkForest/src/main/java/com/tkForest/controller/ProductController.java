package com.tkForest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
<<<<<<< HEAD
=======
import org.springframework.ui.Model;
>>>>>>> 3d282b4cb27750b3190389f57667bb38c94f1a42
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tkForest.dto.ProductDTO;
import com.tkForest.service.ProductService;
import com.tkForest.util.PageNavigator;

import com.tkForest.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

<<<<<<< HEAD
// 컨트롤러	
	
	final ProductService productService;
//	
//	/**
//	 * 상품 등록 화면을 요청
//	 * @param model
//	 * @return
//	 */
//	@GetMapping("/productCreate")
//	public String productCreate(Model model) {
//		
//		log.info("상품 등록 화면 요청 받음");
//		return "product/productCreate";
//	}
//	
//	
//	public String productOne() {
//		Long productNum = 1L;
//		
//		ProductDTO product = productService.selectOne(productNum);
//		log.info("조회된 상품: {}", product.toString());
//		
//		return null;
//	}
//	
	/**
	 * 대분류 카테고리
	 * @return
	 */
	@GetMapping("/productList")
	public String productList() {
		
	    return "product/productList";  
	    
	}
	
	/**
	 * 상품 등록 화면
	 * @return
	 */
	@GetMapping("/productCreate")
	public String productCreate() {
		
	    return "product/productCreate";  
	    
	}
	
	
=======
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
   public String productCreate(Model model) {
      
      log.info("상품 등록 화면 요청 받음");
      return "product/productCreate";
   }
   
   /**
    * 
    * @param productNo
    * @return
    */
   @GetMapping("/productDetail")
   public String productOne(int productNo) {
      
      ProductDTO product = productService.selectOne(productNo);
      log.info("조회된 상품: {}", product.toString());
      
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
   
   @GetMapping("/productDelete")
   public String productDelete(
           @RequestParam(name="productNo") int productNo,
           @RequestParam(name="searchItem", defaultValue="") String searchItem,
           @RequestParam(name="searchWord", defaultValue="") String searchWord,
           RedirectAttributes rttr) {

       productService.deleteOne(productNo);

       rttr.addAttribute("searchItem", searchItem);
       rttr.addAttribute("searchWord", searchWord);

       return "redirect:/product/productList";
   }
   
>>>>>>> 3d282b4cb27750b3190389f57667bb38c94f1a42

}
