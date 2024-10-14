package com.tkForest.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tkForest.dto.BuyerDTO;
import com.tkForest.dto.LoginBuyerDetails;
import com.tkForest.dto.LoginSellerDetails;
import com.tkForest.dto.SellerDTO;
import com.tkForest.entity.BuyerEntity;
import com.tkForest.entity.SellerEntity;
import com.tkForest.repository.BuyerRepository;
import com.tkForest.repository.SellerRepository; 

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginUserDetailsService implements UserDetailsService {

    final SellerRepository sellerRepository;
    final BuyerRepository buyerRepository;
    
    // UserId 검증 로직 수정 - (DB테이블에서 데이터를 가져옴) picName으로 조회(동명이인문제 추후 해결필요)

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // 우선적으로 Seller에서 조회
        Optional<SellerEntity> seller = sellerRepository.findById(id);
        if (seller.isPresent()) {
            return new LoginSellerDetails(SellerDTO.toDTO(seller.get()));
        }


        // Seller에 없으면 Buyer에서 조회
        Optional<BuyerEntity> buyer = buyerRepository.findById(id);
        if (buyer.isPresent()) {
            return new LoginBuyerDetails(BuyerDTO.toDTO(buyer.get()));
        }


        // 사용자 찾을 수 없을 때 예외 처리
        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: "  + id);
    }
}
