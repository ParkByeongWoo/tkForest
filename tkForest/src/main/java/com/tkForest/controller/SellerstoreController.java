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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tkForest.dto.ProductDTO;
import com.tkForest.dto.SellerDTO;
import com.tkForest.service.SellerstoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/product")
@RequiredArgsConstructor
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
    
    /**
     * 
     * @param sellerMemberNo
     * @param model
     * @return
     */
    @GetMapping("/productSellerStore/{sellerMemberNo}")
    public String getSellerStore(@PathVariable("sellerMemberNo") String sellerMemberNo, Model model) {
        // 셀러 정보 가져오기
        SellerDTO sellerDTO = sellerstoreService.getSellerByMemberNo(sellerMemberNo);
        model.addAttribute("seller", sellerDTO);
        
        // 셀러의 상품 리스트 가져오기
        List<ProductDTO> productDTOs = sellerstoreService.getProductsBySeller(sellerMemberNo);
        model.addAttribute("products", productDTOs);
        
        // 셀러의 카테고리명 리스트 가져오기
        List<String> sellerCateNames = sellerstoreService.getSellerCategoryNames(sellerMemberNo);
        
        log.info("셀러의 카테고리명 리스트: {}", sellerCateNames);
        
         model.addAttribute("cateNames", sellerCateNames);

         
     // 임시로 cateNames를 테스트용 문자열로 설정
//        List<String> testCateNames = List.of("테스트1", "테스트2");
//        model.addAttribute("cateNames", testCateNames);

        
     // 기존 코드에서 cateNames 변수 전달 후 임시 확인용 변수 추가
        model.addAttribute("cateNamesCheck", sellerCateNames.isEmpty() ? "카테고리 없음" : "카테고리 있음");
        
        
        return "product/productSellerStore"; // 셀러 스토어 페이지로 이동
    }

    
//    @GetMapping("/productSellerStore/{sellerMemberNo}")
//    @ResponseBody // JSON 형식으로 리턴하게 설정
//    public Map<String, Object> getSellerStore(@PathVariable("sellerMemberNo") String sellerMemberNo) {
//        Map<String, Object> response = new HashMap<>();
//        
//        // 셀러 정보 가져오기
//        SellerDTO sellerDTO = sellerstoreService.getSellerByMemberNo(sellerMemberNo);
//        response.put("seller", sellerDTO);
//        
//        // 셀러의 상품 리스트 가져오기
//        List<ProductDTO> productDTOs = sellerstoreService.getProductsBySeller(sellerMemberNo);
//        response.put("products", productDTOs);
//        
//        // 셀러의 카테고리명 리스트 가져오기
//        List<String> sellerCateNames = sellerstoreService.getSellerCategoryNames(sellerMemberNo);
//        response.put("cateNames", sellerCateNames);
//
//        return response;
//    }

}

