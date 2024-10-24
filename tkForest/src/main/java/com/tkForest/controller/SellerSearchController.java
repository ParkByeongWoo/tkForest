package com.tkForest.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tkForest.dto.ProductDTO;
import com.tkForest.service.ProductService;

//@RestController
//@RequestMapping("/user")
//public class SellerSearchController {
//	
//
//	@Autowired
//    private ProductService productService;
//
//	/**
//     * 셀러의 상품 목록 조회 API (sellerMemberNo로 검색)
//     * @param sellerMemberNo
//     * @return ResponseEntity<List<ProductDTO>>
//     */
//    @GetMapping("/{sellerMemberNo}/products")
//    public ResponseEntity<?> getSellerProducts(@PathVariable("sellerMemberNo") String sellerMemberNo) {
//        // 입력값 검증
//        if (sellerMemberNo == null || sellerMemberNo.trim().isEmpty()) {
//            return ResponseEntity.badRequest().body("Seller Member No cannot be null or empty");
//        }
//
//        // 셀러의 상품 목록 조회
//        List<ProductDTO> productList = productService.findProductsBySellerMemberNo(sellerMemberNo);
//
//        // 조회된 상품 목록이 없을 때
//        if (productList.isEmpty()) {
//           // return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found for the given sellerMemberNo.");
//        	return ResponseEntity.ok(Collections.emptyList());
//        }
//
//        // 성공적으로 조회된 경우
//        return ResponseEntity.ok(productList);
//    }
//}

@RestController
@RequestMapping("/user")
public class SellerSearchController {
    
    @Autowired
    private ProductService productService;

    // 셀러의 상품 목록 조회 API (sellerMemberNo로 검색)
    @GetMapping("/{sellerMemberNo}/products")
    public ResponseEntity<List<ProductDTO>> getSellerProducts(@PathVariable("sellerMemberNo") String sellerMemberNo) {
        List<ProductDTO> productList = productService.findProductsBySellerMemberNo(sellerMemberNo);
        
        // 만약 productList가 비어 있으면 빈 배열을 JSON으로 반환
        if (productList.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(productList);  // JSON 응답 반환
    }
}
