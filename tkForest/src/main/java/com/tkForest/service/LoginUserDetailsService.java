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

@Service
@RequiredArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {

    final SellerRepository sellerRepository;
    final BuyerRepository buyerRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // Seller에서 조회
        Optional<SellerEntity> sellerOpt = sellerRepository.findById(id);
        if (sellerOpt.isPresent() && sellerOpt.get().getSellerMemberNo().startsWith("S")) {
            SellerEntity seller = sellerOpt.get();
            return new LoginSellerDetails(SellerDTO.toDTO(seller));
        }

        // Buyer에서 조회
        Optional<BuyerEntity> buyerOpt = buyerRepository.findById(id);
        if (buyerOpt.isPresent() && buyerOpt.get().getBuyerMemberNo().startsWith("B")) {
            BuyerEntity buyer = buyerOpt.get();
            return new LoginBuyerDetails(BuyerDTO.toDTO(buyer));
        }

        // 사용자 찾을 수 없을 때 예외 처리
        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + id);
    }
}
