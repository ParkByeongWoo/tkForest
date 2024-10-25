package com.tkForest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkForest.dto.ProductDTO;
import com.tkForest.dto.SellerDTO;
import com.tkForest.entity.ProductEntity;
import com.tkForest.entity.SellerEntity;
import com.tkForest.repository.CategoryRepository;
import com.tkForest.repository.ProductRepository;
import com.tkForest.repository.SCategoryRepository;
import com.tkForest.repository.SellerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SellerstoreService {

    @Autowired
    private SellerRepository sellerRepository;
    private SCategoryRepository sCategoryRepository;
    private CategoryRepository categoryRepository;

    public SellerDTO getSellerByMemberNo(String sellerMemberNo) {
        SellerEntity sellerEntity = sellerRepository.findById(sellerMemberNo)
            .orElseThrow(() -> new RuntimeException("Seller not found"));
        return SellerDTO.toDTO(sellerEntity);
    }
        

     // 테스트 메소드
     // seller 테이블에 존재하는 sellerMemberNo 조회
//     public void testSellerFetch() {
//         SellerEntity seller = sellerRepository.findById("S250134")
//             .orElseThrow(() -> new RuntimeException("Seller not found"));
//            System.out.println(seller);
//        }
    
    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getProductsBySeller(String sellerMemberNo) {
        List<ProductEntity> productEntities = productRepository.findBySellerEntitySellerMemberNo(sellerMemberNo);
        return productEntities.stream()
            .map(product -> ProductDTO.toDTO(product, sellerMemberNo))
            .collect(Collectors.toList());
    }
    
    
    
    // 셀러MemberNo로 카테고리명 리스트 반환
    public List<String> getSellerCategoryNames(String sellerMemberNo) {
    	
    	log.info(sellerMemberNo);
    	
    	List<Integer> sellerCateNos = sCategoryRepository.findCategoryNosBySellerMemberNo(sellerMemberNo);
        log.info("셀러 관심카테고리 categNos 리스트 조회함: {}", sellerCateNos);
    	
        if (sellerCateNos != null) {
        	List<String> sellerCateNames = categoryRepository.findCategoryNameByCategoryNo(sellerCateNos);
        	log.info("셀러 관심카테고리의 카테고리명 리스트 조회함: {}", sellerCateNames);
        	return sellerCateNames;
        }
    	
        return null;
    }
    
    
    
 
}