//package com.tkForest.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.tkForest.dto.SellerDTO;
//import com.tkForest.service.SellerstoreService;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Controller
//@Slf4j
//@RequestMapping("/user")
//public class SellerstoreController {
//
//    @Autowired
//    private SellerstoreService sellerstoreService;
//
////    // 기본 셀러 스토어 페이지(sellerMemberNo가 없는 기본 페이지 접근)
////    @GetMapping("/sellerStore")
////    public String showDefaultSellerStore(Model model) {
////        model.addAttribute("message", "No seller selected");
////        return "user/sellerStore";
////    }
//
//    // 특정 셀러 페이지 (sellerMemberNo가 'S99'인 셀러 정보를 표시)
//    @GetMapping("/sellerStore")
//    public String getSellerStore(Model model) {
//    	
////        log.info("컨트롤러 도착");
//    	
//        String sellerMemberNo = "S250134"; // 고정된 sellerMemberNo
//        SellerDTO sellerDTO = sellerstoreService.getSellerByMemberNo(sellerMemberNo);
////        log.info("sellerDTO 조회");
//        
//        model.addAttribute("seller", sellerDTO);
////        log.info("sellerDTO 모델에 담음");
//        
//        return "user/sellerStore"; // 페이지 이름은 동일
//    }
//}
package com.tkForest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tkForest.dto.SellerDTO;
import com.tkForest.dto.LoginSellerDetails;
import com.tkForest.dto.ProductDTO;
import com.tkForest.service.SellerstoreService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/product")
public class SellerstoreController {

    @Autowired
    private SellerstoreService sellerstoreService;

//    // 로그인된 셀러의 페이지를 표시
//    @GetMapping("/productSellerStore")
//    public String getSellerStore(Model model) {
//        // 현재 로그인된 사용자의 정보 가져오기
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        
//        if (principal instanceof LoginSellerDetails) {
//            // 로그인된 셀러의 sellerMemberNo 가져오기
//            String sellerMemberNo = ((LoginSellerDetails) principal).getSellerMemberNo();
//
//            // 셀러 정보를 가져오기
//            SellerDTO sellerDTO = sellerstoreService.getSellerByMemberNo(sellerMemberNo);
//            model.addAttribute("seller", sellerDTO);
//        } else {
//            // 셀러가 아닌 경우 처리
//            model.addAttribute("message", "Seller not found or not logged in as seller.");
//        }
//
//        return "product/productSellerStoree"; // 셀러 스토어 페이지로 이동
//    }
//    @GetMapping("/productSellerStore")
//    public String getSellerStore(Model model) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        
//        if (principal instanceof LoginSellerDetails) {
//            String sellerMemberNo = ((LoginSellerDetails) principal).getSellerMemberNo();
//            
//            // 셀러 정보 가져오기
//            SellerDTO sellerDTO = sellerstoreService.getSellerByMemberNo(sellerMemberNo);
//            model.addAttribute("seller", sellerDTO);
//            
//            // 셀러의 상품 리스트 가져오기
//            List<ProductDTO> productDTOs = sellerstoreService.getProductsBySeller(sellerMemberNo);
//            model.addAttribute("products", productDTOs);
//        } else {
//            model.addAttribute("message", "Seller not found or not logged in as seller.");
//        }
//
//        return "product/productSellerStore"; // 페이지 이름은 적절하게 수정
//    }
    
    @GetMapping("/productSellerStore/{sellerMemberNo}")
    public String getSellerStore(@PathVariable("sellerMemberNo") String sellerMemberNo, Model model) {
        // 셀러 정보 가져오기
        SellerDTO sellerDTO = sellerstoreService.getSellerByMemberNo(sellerMemberNo);
        model.addAttribute("seller", sellerDTO);
        
        // 셀러의 상품 리스트 가져오기
        List<ProductDTO> productDTOs = sellerstoreService.getProductsBySeller(sellerMemberNo);
        model.addAttribute("products", productDTOs);

        return "product/productSellerStore"; // 셀러 스토어 페이지로 이동
    }


}

