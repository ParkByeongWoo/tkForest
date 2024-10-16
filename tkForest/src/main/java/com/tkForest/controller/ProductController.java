package com.tkForest.controller;

import org.springframework.stereotype.Controller;
<<<<<<< HEAD
import org.springframework.ui.Model;

=======
>>>>>>> origin/hyun0_new
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
=======
// 컨트롤러   
>>>>>>> origin/hyun0_new
   
   final ProductService productService;
//   
//   /**
//    * 상품 등록 화면을 요청
//    * @param model
//    * @return
//    */
//   @GetMapping("/productCreate")
//   public String productCreate(Model model) {
//      
//      log.info("상품 등록 화면 요청 받음");
//      return "product/productCreate";
//   }
//   
//   
//   public String productOne() {
//      Long productNum = 1L;
//      
//      ProductDTO product = productService.selectOne(productNum);
//      log.info("조회된 상품: {}", product.toString());
//      
//      return null;
//   }
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
<<<<<<< HEAD
  
=======
   
   
>>>>>>> origin/hyun0_new

}
