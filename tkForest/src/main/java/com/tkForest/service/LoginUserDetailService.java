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
public class LoginUserDetailService implements UserDetailsService {

	final SellerRepository sellerRepository;
	final BuyerRepository buyerRepository;
	
	// UserId 검증 로직 추가 - DB 테이블에서 데이터를 가져옴
	// 사용자가 login을 하면 SecurityConfig 가로챈 후 이쪽으로 데이터를 전달
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	// username이면 헷갈리니까 userId로 변경함
		
		// 1. 셀러 테이블에서 조회 (ID가 UQ인 컬럼 사용)
	    Optional<SellerEntity> seller = sellerRepository.findByUsername(username);
	    if (seller.isPresent()) {
	        return new LoginSellerDetails(SellerDTO.toDTO(seller.get()));
	    }

	    // 2. 바이어 테이블에서 조회 (ID가 UQ인 컬럼 사용)
	    Optional<BuyerEntity> buyer = buyerRepository.findByUsername(username);
	    if (buyer.isPresent()) {
	        return new LoginBuyerDetails(BuyerDTO.toDTO(buyer.get()));
	    }

	    // 3. ID가 없으면 예외 처리
	    throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
	    
	}
}
