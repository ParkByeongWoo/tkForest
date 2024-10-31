package com.tkForest.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tkForest.dto.LoginBuyerDetails;
import com.tkForest.dto.LoginSellerDetails;
import com.tkForest.dto.PCategoryDTO;
import com.tkForest.dto.ProductCertificateDTO;
import com.tkForest.dto.ProductDTO;

import com.tkForest.repository.SellerRepository;
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
   final SellerRepository sellerRepository;
   
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
    * 상품 상세정보 보기
    * @param productNo
    * @return
    */
   @GetMapping("/productDetail")
   public String productSelectOne(
         @RequestParam(name="productNo") Integer productNo
         , @RequestParam(name="searchType", defaultValue="") String searchType
         , @RequestParam(name="query", defaultValue="") String query
         , @AuthenticationPrincipal LoginBuyerDetails userDetails
         , Model model) {
      
      ProductDTO product = productService.selectOne(productNo);
      String companyName = sellerRepository.findById(product.getSellerMemberNo()).get().getCompanyName();
      List<String> categoryNames = productService.categoryAll(productNo);
      List<Integer> productCertificate = productService.certificateAll(productNo);
      List<ProductDTO> list = productService.findProductsBySellerMemberNo(product.getSellerMemberNo());
      
      log.info("조회된 상품: {}", product.toString());
      productService.incrementHitcount(productNo);
      
      if(product == null) {
         return "redirect:/product/productList"; 
      }
      
      System.out.println(categoryNames);
      model.addAttribute("product", product);
      model.addAttribute("categoryName", categoryNames);
      model.addAttribute("productCertificate", productCertificate);
      model.addAttribute("companyName", companyName);
      // 검색 기능이 추가되면 계속 달고 다녀야 함
      model.addAttribute("searchType", searchType);
      model.addAttribute("query", query);
      model.addAttribute("list", list); // 같은 판매자의 상품 리스트 추가
      
      // 바이어로 로그인한 경우에만 buyerMemberNo 추가
      // 상품 보고있는 바이어의 buyerMemberNo (상품 좋아요 추가하기 위함)
      if (userDetails != null) {
          String buyerMemberNo = userDetails.getBuyerMemberNo();
          model.addAttribute("buyerMemberNo", buyerMemberNo);
      }  
      
      return "product/productDetail";  // 상세페이지로 이동
   }

   
//   /**
//    * index에서 넘어올 경우
//    * + 검색해서 넘어올 경우 searchType(전체or상품or셀러)/query(검색어) + 정렬
//    * 전체카테고리
//    * @param 
//    * @param searchType
//    * @param query
//    * @param model
//    * @return
//    */
//   @GetMapping("/productList")
//   public String productList(
//           @PageableDefault(page=1, size=9) Pageable pageable,
//           @RequestParam(name="searchType", defaultValue="ALL") String searchType,
//           @RequestParam(name="query", defaultValue="") String query,
//           @RequestParam(name = "sortBy", required = false, defaultValue = "registrationDate") String sortBy, // 수정된 부분, 이름명시
//           @AuthenticationPrincipal LoginBuyerDetails userDetails,
//           Model model) {
//	   
//      // 검색기능 + 페이징
//       Page<ProductDTO> list = productService.selectAll(pageable, searchType, query, sortBy);
//
//       int totalPages = list.getTotalPages();
//       int page = pageable.getPageNumber();
//
//       PageNavigator navi = new PageNavigator(pageLimit, page, totalPages);
//
//       
//       model.addAttribute("list", list);
//       model.addAttribute("searchType", searchType);
//       model.addAttribute("query", query);
//       model.addAttribute("navi", navi);
//       
//       // 바이어로 로그인한 경우에만 buyerMemberNo 추가
//       // 상품 보고있는 바이어의 buyerMemberNo (상품 좋아요 추가하기 위함)
//       if (userDetails != null) {
//           String buyerMemberNo = userDetails.getBuyerMemberNo();
//           model.addAttribute("buyerMemberNo", buyerMemberNo);
//       }  
//       
//       return "product/productList";  
//       
//   }
   
//   // 검색 및 정렬된 상품 목록 제공
//   @GetMapping("/productListOrderBy")
//   @ResponseBody
//   public Page<ProductEntity> productListOrderBy(
//           @RequestParam(name = "searchType", defaultValue = "ALL") String searchType,
//           @RequestParam(name = "query", defaultValue = "") String query,
//           @RequestParam(name = "orderBy", defaultValue = "latest") String orderBy,
//           @RequestParam(name = "page", defaultValue = "1") int page) {
//
//       return productService.getProducts(searchType, query, page, orderBy);
//   }
   
   
	/** (카테고리 필터링 테스트 위한 이전 코드)
	 * 카테고리 필터링 된 상품 목록 조회
	 * @param pageable
	 * @param searchType
	 * @param query
	 * @param userDetails
	 * @param categoryNo
	 * @param model
	 * @return
	 */
	@GetMapping("/user/{categoryNo}")
	public String productListByCategory(
			@PageableDefault(page=1) Pageable pageable,
	        @RequestParam(name="searchType", defaultValue="ALL") String searchType,
	        @RequestParam(name="query", defaultValue="") String query,
	        @AuthenticationPrincipal LoginBuyerDetails userDetails,
			@PathVariable("categoryNo") Integer categoryNo, 
			Model model
			) {
      
		log.info("컨트롤러 도착함");
		
		// 검색기능 + 페이징 + 카테고리
	    // Page<ProductDTO> list = productService.selectAll(pageable, searchType, query);
		Page<ProductDTO> list = productService.getProductsByCategory(pageable, searchType, query, categoryNo);
		log.info("Page<ProductDTO> list: {}", list);
		
			int totalPages = list.getTotalPages();
			int page = pageable.getPageNumber();

	       PageNavigator navi = new PageNavigator(pageLimit, page, totalPages);

	       model.addAttribute("list", list);
	       model.addAttribute("searchType", searchType);
	       model.addAttribute("query", query);
	       model.addAttribute("navi", navi);
	       
	        // 바이어로 로그인한 경우에만 buyerMemberNo 추가
	        // 상품 보고있는 바이어의 buyerMemberNo (상품 좋아요 추가하기 위함)
	        if (userDetails != null) {
	            String buyerMemberNo = userDetails.getBuyerMemberNo();
	            model.addAttribute("buyerMemberNo", buyerMemberNo);
	        }  
	        
	       return "product/productList";  
  
	}

	/** (최종)
    * index에서 넘어올 경우
    * + 검색해서 넘어올 경우 searchType(전체or상품or셀러)/query(검색어) + 정렬
    * + 카테고리 필터링
    **/
	@GetMapping("/productList")
	public String productList(
	           @PageableDefault(page=1, size=9) Pageable pageable,
	           @RequestParam(name="searchType", defaultValue="ALL") String searchType,
	           @RequestParam(name="query", defaultValue="") String query,
	           @RequestParam(name = "sortBy", required = false, defaultValue = "registrationDate") String sortBy, // 수정된 부분, 이름명시
	           @AuthenticationPrincipal LoginBuyerDetails userDetails,
//	           @PathVariable("categoryNo") Integer categoryNo, 
	           @RequestParam(name = "categoryNo", required = false) Integer categoryNo,  // categoryNo 추가
			Model model
			) {
      
		log.info("컨트롤러 도착함 categoryNo: {}", categoryNo);
		
		// 검색기능 + 페이징 + 카테고리
	    // Page<ProductDTO> list = productService.selectAll(pageable, searchType, query);
		// Page<ProductDTO> list = productService.getProductsByCategory(pageable, searchType, query, categoryNo);
		Page<ProductDTO> list = productService.selectAll(pageable, searchType, query, sortBy, categoryNo);
		log.info("Page<ProductDTO> list: {}", list.toString());
		
			int totalPages = list.getTotalPages();
			int page = pageable.getPageNumber();

	       PageNavigator navi = new PageNavigator(pageLimit, page, totalPages);

	       model.addAttribute("list", list);
	       model.addAttribute("searchType", searchType);
	       model.addAttribute("query", query);
	       model.addAttribute("navi", navi);
	       model.addAttribute("categoryNo", categoryNo);
	       
	        // 바이어로 로그인한 경우에만 buyerMemberNo 추가
	        // 상품 보고있는 바이어의 buyerMemberNo (상품 좋아요 추가하기 위함)
	        if (userDetails != null) {
	            String buyerMemberNo = userDetails.getBuyerMemberNo();
	            model.addAttribute("buyerMemberNo", buyerMemberNo);
	        }  
	        
	       return "product/productList";  
  
	}
	
	
	
	
   
   /**
    * (바이어) 상품 좋아요 : Like 버튼 누르면 B_Like에 추가
    * @param buyerMemberNo
    * @param productNo
    * @param likeUseYn
    */
   @PostMapping("/productLike")
   public ResponseEntity<String> productLike(
		   @RequestParam(name="buyerMemberNo") String buyerMemberNo
		   , @RequestParam(name="productNo") Integer productNo
		   , @RequestParam(name="likeUseYn") String likeUseYn
		   ) {
	   log.info("서비스 도착 {}", productNo);
	   
	   boolean result = productService.productLikeCreate(buyerMemberNo, productNo, likeUseYn);
	   log.info("blike 저장 성공여부:{}", result);
	   
	    if (result) {
	        return ResponseEntity.ok("좋아요 처리 완료"); // 200 OK 반환
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("처리 실패"); // 500 에러 반환
	    }
	   
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
         )   {
      
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
   
   
   
}
   

