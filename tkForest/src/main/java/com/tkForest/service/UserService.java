package com.tkForest.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tkForest.dto.BuyerDTO;
import com.tkForest.dto.SellerDTO;
import com.tkForest.entity.BuyerEntity;
import com.tkForest.entity.SellerEntity;
import com.tkForest.repository.BuyerRepository;
import com.tkForest.repository.SellerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {


    final SellerRepository sellerRepository;
    final BuyerRepository buyerRepository;
    final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 전달 받은 sellerDTO 또는 buyerDTO를 엔티티로 변경한 후 DB에 저장.
     * @param sellerDTO
     * @param buyerDTO
     */
    public boolean join(SellerDTO sellerDTO, BuyerDTO buyerDTO) {
        // 셀러인지 바이어인지 구분
        if (sellerDTO != null) {
            // 셀러의 ID가 이미 사용 중인지 확인
            boolean isExistSeller = sellerRepository.existsById(sellerDTO.getId());
            if (isExistSeller) return false; // 이미 사용 중인 아이디이므로 가입 실패

            // 비밀번호 암호화
            sellerDTO.setPassword(bCryptPasswordEncoder.encode(sellerDTO.getPassword()));

            // 엔티티로 변환 후 저장
            SellerEntity sellerEntity = SellerEntity.toEntity(sellerDTO);
            sellerRepository.save(sellerEntity);
            return true;
        }

        if (buyerDTO != null) {
            // 바이어의 ID가 이미 사용 중인지 확인
            boolean isExistBuyer = buyerRepository.existsById(buyerDTO.getId());
            if (isExistBuyer) return false; // 이미 사용 중인 아이디이므로 가입 실패

            // 비밀번호 암호화
            buyerDTO.setPassword(bCryptPasswordEncoder.encode(buyerDTO.getPassword()));

            // 엔티티로 변환 후 저장
            BuyerEntity buyerEntity = BuyerEntity.toEntity(buyerDTO);
            buyerRepository.save(buyerEntity);
            return true;
        }

        return false;
    }

    /**
     * sellerId 또는 buyerId에 해당하는 사용자 존재 여부 확인
     * 회원가입 시 아이디 중복 확인 용
     * @param id
     * @return
     */
    public boolean existId(String id, boolean isSeller) {
        if (isSeller) {
            return sellerRepository.existsById(id); // sellerId가 존재하면 true
        } else {
            return buyerRepository.existsById(id);  // buyerId가 존재하면 true
        }
    }

    /**
     * 개인정보 수정을 위해 아이디와 비밀번호 확인처리 요청
     * @param id
     * @param password
     * @param isSeller
     * @return
     */
    public Object pwdCheck(String id, String password, boolean isSeller) {
        if (isSeller) {
            Optional<SellerEntity> sellerEntity = sellerRepository.findById(id);
            if (sellerEntity.isPresent()) {
                SellerEntity temp = sellerEntity.get();
                String pwd = temp.getPassword(); // DB에 저장된 비밀번호

                boolean result = bCryptPasswordEncoder.matches(password, pwd);
                if (result) return SellerDTO.toDTO(temp); // 비밀번호 일치 시 DTO로 반환
            }
        } else {
            Optional<BuyerEntity> buyerEntity = buyerRepository.findById(id);
            if (buyerEntity.isPresent()) {
                BuyerEntity temp = buyerEntity.get();
                String pwd = temp.getPassword(); // DB에 저장된 비밀번호

                boolean result = bCryptPasswordEncoder.matches(password, pwd);
                if (result) return BuyerDTO.toDTO(temp); // 비밀번호 일치 시 DTO로 반환
            }
        }

        return null; // 일치하는 사용자 없음
    }

    /**
     * 개인정보 수정
     * 수정하려는 정보를 setter를 통해 수정
     * JPA의 save() 메소드 : 저장 + 수정 가능
     * @param sellerDTO
     * @param buyerDTO
     */
    @Transactional
    public boolean update(SellerDTO sellerDTO, BuyerDTO buyerDTO) {
        if (sellerDTO != null) {
            Optional<SellerEntity> sellerEntity = sellerRepository.findById(sellerDTO.getId());
            if (sellerEntity.isPresent()) {
                SellerEntity entity = sellerEntity.get();
                
                // 비밀번호 암호화하여 DB 정보 갱신
                entity.setPassword(bCryptPasswordEncoder.encode(sellerDTO.getPassword()));
                entity.setEmail(sellerDTO.getEmail());
                sellerRepository.save(entity); // 수정 후 저장
                return true;
            }
        }

        if (buyerDTO != null) {
            Optional<BuyerEntity> buyerEntity = buyerRepository.findById(buyerDTO.getId());
            if (buyerEntity.isPresent()) {
                BuyerEntity entity = buyerEntity.get();
                
                // 비밀번호 암호화하여 DB 정보 갱신
                entity.setPassword(bCryptPasswordEncoder.encode(buyerDTO.getPassword()));
                entity.setEmail(buyerDTO.getEmail());
                buyerRepository.save(entity); // 수정 후 저장
                return true;
            }
        }

        return false;
    }
    
    
}
