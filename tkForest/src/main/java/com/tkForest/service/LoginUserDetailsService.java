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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {

    private final SellerRepository sellerRepository;
    private final BuyerRepository buyerRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        log.info("로그인 시도한 아이디: {}", id);

        // Seller에서 조회
        Optional<SellerEntity> sellerOpt = sellerRepository.findBySellerId(id);
        if (sellerOpt.isPresent()) {
            log.info("조회된 셀러 ID: {}", sellerOpt.get().getSellerId());
            SellerEntity seller = sellerOpt.get();
            return new LoginSellerDetails(SellerDTO.toDTO(seller));
        }

        // Buyer에서 조회
        Optional<BuyerEntity> buyerOpt = buyerRepository.findByBuyerId(id);
        if (buyerOpt.isPresent()) {
            log.info("조회된 바이어 ID: {}", buyerOpt.get().getBuyerId());
            BuyerEntity buyer = buyerOpt.get();
            return new LoginBuyerDetails(BuyerDTO.toDTO(buyer));
        }

        log.warn("사용자를 찾을 수 없습니다: {}", id);
        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + id);
    }
}
