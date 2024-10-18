//package com.tkForest.controller;
//
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.tkForest.dto.ProductDTO;
//import com.tkForest.service.ProductService;
//import com.tkForest.util.PageNavigator;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Controller
//@RequestMapping("/product")
//@RequiredArgsConstructor
//public class ProductController {
//
//	// 컨트롤러   
//   
//   final ProductService productService;
//   
//   // 한 페이지의 게시글 수
//   @Value("${user.inquiry.pageLimit}")
//   private int pageLimit;
//   
//   /**
//    * 상품 등록 화면을 요청
//    * @param model
//    * @return
//    */
//   @GetMapping("/productCreate")
//   public String productCreate(Model model) {
//	   
//	 log.info("상품 생성 페이지로 넘어감");
//      
//      return "product/productCreate";
//   }
//   
//   @PostMapping("/productCreate")
//   public String productCreate(@ModelAttribute ProductDTO productDTO) {
//	   
//	 log.info("클라이언트에서 전송된 데이터 : {}", productDTO.toString());
//
//	 productService.ProductCreate(productDTO);
//      
//     return "product/productCreate"; //마이페이지-상품관리페이지로 넘어갈 것
//   }
//   
//   /**
//    * 
//    * @param productNo
//    * @return
//    */
//   @GetMapping("/productDetail")
//   public String productOne(
//		   @RequestParam(name="productNo") Integer productNo
//		   , @RequestParam(name="searchItem", defaultValue="") String searchItem
//		   , @RequestParam(name="searchWord", defaultValue="") String searchWord
//		   , Model model) {
//      
//      ProductDTO product = productService.selectOne(productNo);
//      log.info("조회된 상품: {}", product.toString());
//      productService.incrementHitcount(productNo);
//      
//      if(product == null) {
//			return "redirect:/product/productList"; 
//      }
//      
//      model.addAttribute("product", product);
//		// 검색 기능이 추가되면 계속 달고 다녀야 함
//      model.addAttribute("searchItem", searchItem);
//      model.addAttribute("searchWord", searchWord);
//      
//      return "product/productDetail";
//   }
//   
//   /**
//    * 
//    * @param pageable
//    * @param searchItem
//    * @param searchWord
//    * @param model
//    * @return
//    */
//   @GetMapping("/productList")
//   public String productList(
//		   @PageableDefault(page=1) Pageable pageable,
//           @RequestParam(name="searchItem", defaultValue="") String searchItem,
//           @RequestParam(name="searchWord", defaultValue="") String searchWord,
//           Model model) {
//	   // 검색기능 + 페이징
//       Page<ProductDTO> list = productService.selectAll(pageable, searchItem, searchWord);
//
//       int totalPages = list.getTotalPages();
//       int page = pageable.getPageNumber();
//
//       PageNavigator navi = new PageNavigator(pageLimit, page, totalPages);
//
//       model.addAttribute("list", list);
//       model.addAttribute("searchItem", searchItem);
//       model.addAttribute("searchWord", searchWord);
//       model.addAttribute("navi", navi);
//       return "product/productList";  
//       
//   }
//   
//   /**
//    * 
//    * @param productNo
//    * @param model
//    * @return
//    */
//   @GetMapping("/productUpdate")
//   public String productUpdate(
//		   @RequestParam(name="productNo") Integer productNo
//		   , Model model
//		   )	{
//	   
//	   ProductDTO product = productService.selectOne(productNo);
//	   model.addAttribute("product", product);
//	   
//	   return "product/productUpdate";
//   }
//   
//   @PostMapping("/productUpdate")
//   public String productUpdate(
//		   @ModelAttribute ProductDTO product
//		   , RedirectAttributes rttr) {
//	   log.info("수정할 글: {}", product.toString());
//	   
//	   productService.updateProduct(product);
//	   
//	   return "redirect:/product/productDetail";
//   }
//   
//   
//   /**
//    * 
//    * @param productNo
//    * @param searchItem
//    * @param searchWord
//    * @param rttr
//    * @return
//    */
//   @GetMapping("/productDelete")
//   public String productDelete(
//           @RequestParam(name="productNo") Integer productNo,
//           @RequestParam(name="searchItem", defaultValue="") String searchItem,
//           @RequestParam(name="searchWord", defaultValue="") String searchWord,
//           RedirectAttributes rttr) {
//
//       productService.deleteOne(productNo);
//
//       rttr.addAttribute("searchItem", searchItem);
//       rttr.addAttribute("searchWord", searchWord);
//
//       return "redirect:/product/productList";
//   }
//   
//   /**
//	 * 게시판 수정화면에서 파일만 삭제하도록 요청
//	 */
//	@GetMapping("/deleteFile")
//	public String deleteFile(
//			@RequestParam(name="productNo") int productNo
//			, RedirectAttributes rttr
//			) {
//
//		// boardService에 파일삭제 요청 (Update와 동일)
//		productService.deleteFile(productNo);
//
//		rttr.addAttribute("boardNum", productNo);
//		return "redirect:/product/productDetail";
//	}
//
//}
