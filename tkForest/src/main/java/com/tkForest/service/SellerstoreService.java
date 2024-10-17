package com.tkForest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkForest.dto.SellerDTO;
import com.tkForest.entity.SellerEntity;
import com.tkForest.repository.SellerRepository;

@Service
public class SellerstoreService {

    @Autowired
    private SellerRepository sellerRepository;

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
    
}