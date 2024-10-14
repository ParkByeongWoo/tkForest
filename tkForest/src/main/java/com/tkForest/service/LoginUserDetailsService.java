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
    
    // UserId 검증 로직 추가 - DB 테이블에서 데이터를 가져옴
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  
        // 1. 셀러 테이블에서 조회 (sellerMemberNo가 'S'로 시작)
        if (username.startsWith("S")) {
            Optional<SellerEntity> seller = sellerRepository.findBySellerMemberNo(username);
            if (seller.isPresent()) {
                return new LoginSellerDetails(SellerDTO.toDTO(seller.get()));
            }
        }

        // 2. 바이어 테이블에서 조회 (buyerMemberNo가 'B'로 시작)
        if (username.startsWith("B")) {
            Optional<BuyerEntity> buyer = buyerRepository.findByBuyerMemberNo(username);
            if (buyer.isPresent()) {
                return new LoginBuyerDetails(BuyerDTO.toDTO(buyer.get()));
            }
        }

        // 3. ID가 없으면 예외 처리
        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
    }
}
