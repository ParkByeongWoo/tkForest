package com.tkForest.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tkForest.dto.BCategoryDTO;
import com.tkForest.dto.BuyerDTO;
import com.tkForest.dto.SCategoryDTO;
import com.tkForest.dto.SellerCertificateDTO;
import com.tkForest.dto.SellerDTO;
import com.tkForest.entity.BCategoryEntity;
import com.tkForest.entity.BuyerEntity;
import com.tkForest.entity.CategoryEntity;
import com.tkForest.entity.CertificateEntity;
import com.tkForest.entity.SCategoryEntity;
import com.tkForest.entity.SellerCertificateEntity;
import com.tkForest.entity.SellerEntity;
import com.tkForest.repository.BCategoryRepository;
import com.tkForest.repository.BuyerRepository;
import com.tkForest.repository.CategoryRepository;
import com.tkForest.repository.CertificateRepository;
import com.tkForest.repository.SCategoryRepository;
import com.tkForest.repository.SellerCertificateRepository;
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
    
    final SCategoryRepository sCategoryRepository;
    final BCategoryRepository bCategoryRepository;
    final CategoryRepository categoryRepository;
  
    final CertificateRepository certificateRepository;
    final SellerCertificateRepository sellerCertificateRepository;
    

    /**
     * 전달 받은 sellerDTO를 sellerEntity로 변경한 후 DB에 저장.
     * @param sellerDTO
     * @return boolean
     */
	@Transactional
    public boolean sellerSignUp(SellerDTO sellerDTO) {
    	
    	// 가입하려는 ID가 이미 있으면 같은 ID로 가입 불가
		
		if (isUserIdExists(sellerDTO.getSellerId())) {
	        return false; // 이미 존재하는 ID라면 회원가입 불가 처리
	    }
		log.info("isExistId 확인함");

         // 비밀번호 암호화
         // 사용자가 입력한 비밀번호를 get => 암호화 encode => 다시 set 
         sellerDTO.setPassword(bCryptPasswordEncoder.encode(sellerDTO.getPassword()));
         log.info("암호화된 비밀번호 set 함");
    	
         // SellerMemberNo는 중복되면 안되므로 기존 SellerMemberNo의 최대값 +1로 set
    	 sellerDTO.setSellerMemberNo(generateUniqueSellerMemberNo());
    	
        // 존재하지 않는 ID일 경우 회원가입 처리
        SellerEntity sellerEntity = SellerEntity.toEntity(sellerDTO);
        sellerRepository.save(sellerEntity);   // 가입 성공
        return true;
    }
	
    /**
     * (셀/바 공통) 이미 존재하는 id인지 확인
     * @param sellerId
     * @return
     */
    private boolean isUserIdExists(String userId) {

    	boolean isExistId = sellerRepository.findBySellerId(userId).isPresent() 
                || buyerRepository.findByBuyerId(userId).isPresent();  // 셀러 또는 바이어 테이블에 이미 존재하면 true
    	
    	return isExistId;
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
     * 가입중인 Seller의 카테고리 추가하기
     * @param SellerDTO, SCategoryDTO
     */
    public boolean SellerCategoryInsert(SellerDTO sellerDTO, SCategoryDTO sCategoryDTO) {
    	
    	// sellerId로 셀러 엔티티 찾아오기
    	Optional<SellerEntity> sellerEntity = sellerRepository.findBySellerMemberNo(sellerDTO.getSellerMemberNo());
    	
    	// 셀러가 있는 경우 실행
    	if (sellerEntity.isPresent()) {
    		SellerEntity entity1 = sellerEntity.get();
    		
	    	// 카테고리 리스트가 null일 경우 해당 로직을 실행하지 않음
	        if (sellerDTO.getCategoryNames() != null) {
    	
	        	// CategoryDTO에서 선택된 카테고리 코드 목록을 가져와서 반복 처리
	        	for (String cateName : sellerDTO.getCategoryNames()) { 
	    	
	        		// cateName으로 카테고리 엔티티 찾아오기
	        		Optional<CategoryEntity> categoryEntity = categoryRepository.findByCategoryName(cateName);
		    	
		        	if (categoryEntity.isPresent()) {
		        		
		        		CategoryEntity entity2 = categoryEntity.get();
		        		
		        		SCategoryEntity sCategoryEntity = SCategoryEntity.toEntity(sCategoryDTO, entity1, entity2);
		      	      	sCategoryRepository.save(sCategoryEntity);
		        	
		      	      	log.info("sCategoryEntity 저장 성공");
		        	}
	        } // for문 끝
	        	return true;
	        	
        	} else {
                System.out.println("입력된 카테고리명이 없습니다.");
                return false;
    	} 
	        }
    	return false;
    }

    /**
     * 가입중인 Seller의 인증서 추가하기
     * @param SellerDTO, SellerCertificateDTO
     */
    public boolean SellerCertificateInsert(SellerDTO sellerDTO, SellerCertificateDTO sellerCertificateDTO) {
    	
    	// sellerId로 셀러 엔티티 찾아오기
    	Optional<SellerEntity> sellerEntity = sellerRepository.findBySellerMemberNo(sellerDTO.getSellerMemberNo());
    	
    	// 셀러가 있는 경우 실행
    	if (sellerEntity.isPresent()) {
    		SellerEntity entity1 = sellerEntity.get();
    		
	    	// 인증서 리스트가 null일 경우 해당 로직을 실행하지 않음
	        if (sellerDTO.getSellerCertificateTypeCodes() != null) {
	        
	        	// sellerCertificateDTO에서 선택된 인증서 코드 목록을 가져와서 반복 처리
	        	for (Integer certCode : sellerDTO.getSellerCertificateTypeCodes()) { 
	    	
	        		// certificateCode로 인증서 엔티티 찾아오기
	        		Optional<CertificateEntity> certificateEntity = certificateRepository.findById(certCode);
		    	
		        	if (certificateEntity.isPresent()) {
		        		
		        		CertificateEntity entity2 = certificateEntity.get();
		        		
		        		SellerCertificateEntity sellerCertificateEntity = SellerCertificateEntity.toEntity(sellerCertificateDTO, entity1, entity2);
		      	      	sellerCertificateRepository.save(sellerCertificateEntity);
		        	
		      	      	log.info("sellerCertificateEntity 저장 성공");
		      	      	
		        	}
	  	      
	        	} // for문 끝
	        	return true;
	        	
	        } else {
	                System.out.println("인증서 타입 코드가 없습니다.");
	                return true;
	        }
    	}
        return false;
    }
    
   /**
     * 전달 받은 buyerDTO를 buyerEntity로 변경한 후 DB에 저장.
     * @param buyerDTO
     * @return boolean
     */
	@Transactional
    public boolean buyerSignUp(BuyerDTO buyerDTO) {
    	
    	// 가입하려는 ID가 이미 있으면 같은 ID로 가입 불가
		
		if (isUserIdExists(buyerDTO.getBuyerId())) {
	        return false; // 이미 존재하는 ID라면 회원가입 불가 처리
	    }
		log.info("isExistId 확인함");

         // 비밀번호 암호화
         // 사용자가 입력한 비밀번호를 get => 암호화 encode => 다시 set 
		buyerDTO.setPassword(bCryptPasswordEncoder.encode(buyerDTO.getPassword()));
         log.info("암호화된 비밀번호 set 함");
    	
         // BuyerMemberNo는 중복되면 안되므로 기존 BuyerMemberNo의 최대값 +1로 set
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
            newMemberNoSuffix = Integer.parseInt(lastMemberNo.substring(1)) + 1; // B 다음 숫자 증가
        } else {
            newMemberNoSuffix = 1; // 초기값 설정
        }

        return "B" + newMemberNoSuffix; // 새로운 BuyerMemberNo 생성
   }
    
    /**
     * 가입중인 Buyer의 카테고리 추가하기
     * @param BuyerDTO, BCategoryDTO
     */
    public boolean BuyerCategoryInsert(BuyerDTO buyerDTO, BCategoryDTO bCategoryDTO) {
    	
    	// buyerId로 셀러 엔티티 찾아오기
    	Optional<BuyerEntity> buyerEntity = buyerRepository.findByBuyerMemberNo(buyerDTO.getBuyerMemberNo());
    	
    	// 바이어가 있는 경우 실행
    	if (buyerEntity.isPresent()) {
    		BuyerEntity entity1 = buyerEntity.get();
    		
	    	// 카테고리 리스트가 null일 경우 해당 로직을 실행하지 않음
	        if (buyerDTO.getCategoryNames() != null) {
    	
	        	// CategoryDTO에서 선택된 카테고리 코드 목록을 가져와서 반복 처리
	        	for (String cateName : buyerDTO.getCategoryNames()) { 
	    	
	        		// cateName으로 카테고리 엔티티 찾아오기
	        		Optional<CategoryEntity> categoryEntity = categoryRepository.findByCategoryName(cateName);
		    	
		        	if (categoryEntity.isPresent()) {
		        		
		        		CategoryEntity entity2 = categoryEntity.get();
		        		
		        		BCategoryEntity bCategoryEntity = BCategoryEntity.toEntity(bCategoryDTO, entity1, entity2);
		      	      	bCategoryRepository.save(bCategoryEntity);
		        	
		      	      	log.info("bCategoryEntity 저장 성공");
		        	}
	        } // for문 끝
	        	return true;
	        	
        	} else {
                System.out.println("입력된 카테고리명이 없습니다.");
                return false;
    	} 
	        }
    	return false;
    }
    
    /**
	 * (셀/바 공통) 이미 존재하는 ID인지 확인
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
	 * (셀러) 이미 존재하는 사업자등록번호인지 확인
	 * 회원가입 시 사업자등록번호 중복확인용
	 * @param userId
	 * @return
	 */
	public boolean existBizregNo(String bizregNo) {
		
		boolean result = sellerRepository.findByBizregNo(bizregNo).isPresent();  // 셀러 테이블에 이미 존재하면 true(사용 불가)
		
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
	
	/**
	 * 셀러 id로 셀러 정보 조회
	 * @param sellerId
	 * @return
	 */
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
	
	/**
	 * 바이어 id로 바이어 정보 조회
	 * @param buyerId
	 * @return
	 */
	public BuyerDTO getBuyerById(String buyerId) {
	    // 바이어 엔티티를 데이터베이스에서 조회
	    Optional<BuyerEntity> optionalBuyerEntity = buyerRepository.findByBuyerId(buyerId);

	    // 바이어 엔티티가 존재하는 경우
	    if (optionalBuyerEntity.isPresent()) {
	        BuyerEntity buyerEntity = optionalBuyerEntity.get();
	        System.out.println("----- 데이터 존재?? " + buyerEntity );
	        // 바이어 엔티티를 BuyerDTO로 변환하여 반환
	        return BuyerDTO.toDTO(buyerEntity);
	    } else {
	        // 바이어가 존재하지 않는 경우 null 반환 또는 예외 처리
	        return null; // 또는 예외를 던질 수 있습니다
	    }
	
	}


//    /**
//     * 개인정보 수정
//     * 수정하려는 정보를 setter를 통해 수정
//     * JPA의 save() 메소드 : 저장 + 수정 가능
//     * @param sellerDTO
//     * @param buyerDTO
//     */
//    @Transactional
//    public boolean update(SellerDTO sellerDTO, BuyerDTO buyerDTO) {
//        if (sellerDTO != null) {
//            Optional<SellerEntity> sellerEntity = sellerRepository.findById(sellerDTO.getId());
//            if (sellerEntity.isPresent()) {
//                SellerEntity entity = sellerEntity.get();
//                
//                // 비밀번호 암호화하여 DB 정보 갱신
//                entity.setPassword(bCryptPasswordEncoder.encode(sellerDTO.getPassword()));
//                entity.setEmail(sellerDTO.getEmail());
//                sellerRepository.save(entity); // 수정 후 저장
//                return true;
//            }
//        }
//
//        if (buyerDTO != null) {
//            Optional<BuyerEntity> buyerEntity = buyerRepository.findById(buyerDTO.getId());
//            if (buyerEntity.isPresent()) {
//                BuyerEntity entity = buyerEntity.get();
//                
//                // 비밀번호 암호화하여 DB 정보 갱신
//                entity.setPassword(bCryptPasswordEncoder.encode(buyerDTO.getPassword()));
//                entity.setEmail(buyerDTO.getEmail());
//                buyerRepository.save(entity); // 수정 후 저장
//                return true;
//            }
//        }
//
//        return false;
//    }
    
	

}
