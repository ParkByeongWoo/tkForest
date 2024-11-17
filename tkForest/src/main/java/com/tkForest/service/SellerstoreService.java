package com.tkForest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tkForest.dto.ProductDTO;
import com.tkForest.dto.SellerDTO;
import com.tkForest.entity.ProductEntity;
import com.tkForest.entity.SellerEntity;
import com.tkForest.repository.CategoryRepository;
import com.tkForest.repository.PCategoryRepository;
import com.tkForest.repository.ProductRepository;
import com.tkForest.repository.SCategoryRepository;
import com.tkForest.repository.SellerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SellerstoreService {

    final SellerRepository sellerRepository;
    final SCategoryRepository sCategoryRepository;
    final CategoryRepository categoryRepository;
    final PCategoryRepository pCategoryRepository;
    final ProductRepository productRepository;

    public SellerDTO getSellerByMemberNo(String sellerMemberNo) {
        SellerEntity sellerEntity = sellerRepository.findById(sellerMemberNo)
            .orElseThrow(() -> new RuntimeException("Seller not found"));
        return SellerDTO.toDTO(sellerEntity);
    }
    

    public List<ProductDTO> getProductsBySeller(String sellerMemberNo) {
        List<ProductEntity> productEntities = productRepository.findBySellerEntitySellerMemberNo(sellerMemberNo);
        return productEntities.stream()
            .map(product -> ProductDTO.toDTO(product, sellerMemberNo))
            .collect(Collectors.toList());
    }
    
    
    
    /**
     * 셀러MemberNo -> cateNos -> cateNames 리스트 반환
     * @param sellerMemberNo
     * @return
     */
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
    
    
    
    

//    public SellerstoreService(ProductRepository productRepository, PCategoryRepository pCategoryRepository, CategoryRepository categoryRepository) {
//        this.productRepository = productRepository;
//        this.pCategoryRepository = pCategoryRepository;
//        this.categoryRepository = categoryRepository;
//    }

//    /**
//     * 특정 셀러가 등록한 모든 상품의 카테고리명을 조회하기
//     * @param sellerMemberNo 셀러 고유 번호
//     * @return 셀러가 가진 모든 카테고리 이름 리스트
//     */
//    public List<String> getSellerProductCategories(String sellerMemberNo) {
//        // 셀러의 모든 상품 번호를 조회
//        List<Integer> productNos = productRepository.findProductNosBySellerMemberNo(sellerMemberNo);
//
//        // 각 상품 번호에 대해 카테고리 번호를 조회하고, 카테고리 이름으로 변환
//        Set<String> categoryNames = new HashSet<>();
//        for (Integer productNo : productNos) {
//            // 상품 번호에 해당하는 카테고리 번호 목록 조회
//            List<Integer> categoryNos = pCategoryRepository.findCategoryNosByProductNo(productNo);
//            
//            // 각 카테고리 번호에 대해 카테고리 이름 조회 후 Set에 추가
//            for (Integer categoryNo : categoryNos) {
//                categoryRepository.findById(categoryNo).ifPresent(category -> categoryNames.add(category.getCategoryName()));
//            }
//        }
//        
//        return new ArrayList<>(categoryNames); // 중복 제거를 위해 Set을 사용
//    }

   
 
}