package com.tkForest.service;

import java.util.Optional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
     * 전달 받은 sellerDTO를 sellerEntity로 변경한 후 DB에 저장.
     * @param sellerDTO
     * @return boolean
     */
    public boolean sellerSignUp(SellerDTO sellerDTO) {
    	
    	// 가입하려는 ID가 이미 있으면 같은 ID로 가입 불가
        boolean isExistId = sellerRepository.findBySellerId(sellerDTO.getSellerId()).isPresent() 
                            || buyerRepository.findByBuyerId(sellerDTO.getSellerId()).isPresent();  // 셀러 또는 바이어 테이블에 이미 존재하면 true
        
        if (isExistId) {
            // 이미 존재하는 ID라면 회원가입 불가 처리
            return false;
        }

         // 비밀번호 암호화
         // 사용자가 입력한 비밀번호를 get => 암호화 encode => 다시 set 
         sellerDTO.setPassword(bCryptPasswordEncoder.encode(sellerDTO.getPassword()));
        
    	// sellerDTO.setSellerMemberNo("S2");
         // SellerMemberNo는 중복되면 안되므로 기존 SellerMemberNo의 최대값 +1로 set
    	 sellerDTO.setSellerMemberNo(generateUniqueSellerMemberNo());
    	
        // 존재하지 않는 ID일 경우 회원가입 처리
        SellerEntity sellerEntity = SellerEntity.toEntity(sellerDTO);
        sellerRepository.save(sellerEntity);   // 가입 성공
        return true;
 
    }
    
    /**
     * SellerMemberNo 중복되지 않도록 하기 위함(기존 최대값 +1)
     * @return
     */
    private String generateUniqueSellerMemberNo() {
       String lastMemberNo = sellerRepository.findLastMemberNo(); // 최대 memberNo 조회
        int newMemberNoSuffix;

        if (lastMemberNo != null) {
            newMemberNoSuffix = Integer.parseInt(lastMemberNo.substring(1)) + 1; // S 다음 숫자 증가
        } else {
            newMemberNoSuffix = 1; // 초기값 설정
        }

        return "S" + newMemberNoSuffix; // 새로운 SellerMemberNo 생성
   }

   /**
     * 전달 받은 buyerDTO를 buyerEntity로 변경한 후 DB에 저장.
     * @param buyerDTO
     * @return boolean
     */
    public boolean buyerSignUp(BuyerDTO buyerDTO) {
        
//       // 가입하려는 ID가 이미 있으면 같은 ID로 가입 불가
//      boolean isExistId = sellerRepository.findById(buyerDTO.getId()).isPresent() 
//                          || buyerRepository.findById(buyerDTO.getId()).isPresent();  // 셀러 또는 바이어 테이블에 이미 존재하면 true
//      
//      if (isExistId) {
//          // 이미 존재하는 ID라면 회원가입 불가 처리
//          return false;
//      }

      // 비밀번호 암호화
      // 사용자가 입력한 비밀번호를 get => 암호화 encode => 다시 set 
      buyerDTO.setPassword(bCryptPasswordEncoder.encode(buyerDTO.getPassword()));
      
       buyerDTO.setBuyerMemberNo("B1");
       buyerDTO.setBuyerMemberNo(generateUniqueBuyerMemberNo());
     
      // 존재하지 않는 ID일 경우 회원가입 처리
      BuyerEntity buyerEntity = BuyerEntity.toEntity(buyerDTO);
      buyerRepository.save(buyerEntity);   // 가입 성공
      return true;
 
    }
    
    /**
     * BuyerMemberNo 중복되지 않도록 하기 위함(기존 최대값 +1)
     * @return
     */
    private String generateUniqueBuyerMemberNo() {
       String lastMemberNo = buyerRepository.findLastMemberNo(); // 최대 memberNo 조회
        int newMemberNoSuffix;

        if (lastMemberNo != null) {
            newMemberNoSuffix = Integer.parseInt(lastMemberNo.substring(1)) + 1; // S 다음 숫자 증가
        } else {
            newMemberNoSuffix = 1; // 초기값 설정
        }

        return "B" + newMemberNoSuffix; // 새로운 BuyerMemberNo 생성
   }
    
    /**
	 * (셀러) 이미 존재하는 ID인지 확인
	 * 회원가입 시 아이디 중복확인용
	 * @param userId
	 * @return
	 */
	public boolean existId(String userId) {
		
		boolean result = sellerRepository.findBySellerId(userId).isPresent() 
              || buyerRepository.findByBuyerId(userId).isPresent();  // 셀러 또는 바이어 테이블에 이미 존재하면 true(사용 불가)
		
		return result;
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
            Optional<SellerEntity> sellerEntity = sellerRepository.findBySellerId(id);
            if (sellerEntity.isPresent()) {
                SellerEntity temp = sellerEntity.get();
                String pwd = temp.getPassword(); // DB에 저장된 비밀번호

                boolean result = bCryptPasswordEncoder.matches(password, pwd);
                if (result) return SellerDTO.toDTO(temp); // 비밀번호 일치 시 DTO로 반환
            }
        } else {
            Optional<BuyerEntity> buyerEntity = buyerRepository.findByBuyerId(id);
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
            Optional<SellerEntity> sellerEntity = sellerRepository.findBySellerId(sellerDTO.getSellerId());
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
            Optional<BuyerEntity> buyerEntity = buyerRepository.findByBuyerId(buyerDTO.getBuyerId());
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

	public static void processSellerSignUp() {
		// TODO Auto-generated method stub
		
	}

	public static void processBuyerSignUp() {
		// TODO Auto-generated method stub
		
	}

	public SellerDTO getSellerById(String sellerId) {
	    // 셀러 엔티티를 데이터베이스에서 조회
	    Optional<SellerEntity> optionalSellerEntity = sellerRepository.findBySellerId(sellerId);

	    // 셀러 엔티티가 존재하는 경우
	    if (optionalSellerEntity.isPresent()) {
	        SellerEntity sellerEntity = optionalSellerEntity.get();
	        System.out.println("----- 데이터 존재?? " +sellerEntity );
	        // 셀러 엔티티를 SellerDTO로 변환하여 반환
	        return SellerDTO.toDTO(sellerEntity);
	    } else {
	        // 셀러가 존재하지 않는 경우 null 반환 또는 예외 처리
	        return null; // 또는 예외를 던질 수 있습니다
	    }
	}
	
	

}
