package com.tkForest.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tkForest.dto.PCategoryDTO;
import com.tkForest.dto.ProductCertificateDTO;
import com.tkForest.dto.ProductDTO;
import com.tkForest.entity.B_LikeEntity;
import com.tkForest.entity.BuyerEntity;
import com.tkForest.entity.CategoryEntity;
import com.tkForest.entity.CertificateEntity;
import com.tkForest.entity.PCategoryEntity;
import com.tkForest.entity.ProductCertificateEntity;
import com.tkForest.entity.ProductEntity;
import com.tkForest.entity.SellerEntity;
import com.tkForest.repository.BLikeRepository;
import com.tkForest.repository.BuyerRepository;
import com.tkForest.repository.CategoryRepository;
import com.tkForest.repository.CertificateRepository;
import com.tkForest.repository.PCategoryRepository;
import com.tkForest.repository.ProductCertificateRepository;
import com.tkForest.repository.ProductRepository;
import com.tkForest.repository.SellerRepository;
import com.tkForest.util.FileService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductService {
   
	final ProductRepository productRepository;
	final SellerRepository sellerRepository;
	final BuyerRepository buyerRepository;
	
	final PCategoryRepository pCategoryRepository;
	final CategoryRepository categoryRepository;
	final CertificateRepository certificateRepository;
	final ProductCertificateRepository productCertificateRepository;
	
	final BLikeRepository bLikeRepository;
   
   // 페이징할 때 한 페이지 출력할 글 개수
   @Value("${user.inquiry.pageLimit}")
	private int pageLimit;	
	
   // 업로드된 파일이 저장될 디렉토리 경로를 읽어옴
   @Value("${spring.servlet.multipart.location}") // 확인 필요
   private String uploadPath;
   
   /**
    * DB에 상품 저장
    * @param productDTO : 저장해야 하는 상품
    */
   @Transactional
   public boolean productCreate(ProductDTO productDTO, String sellerMemberNo) {
      
	  log.info("저장 경로: {}", uploadPath);
      
	  // 셀러 존재여부 확인
      Optional<SellerEntity> sellerEntity = sellerRepository.findBySellerMemberNo(sellerMemberNo);
      log.info("셀러엔티티 불러옴: {}", sellerEntity.get());
      
      // 셀러가 있으면
      if (sellerEntity.isPresent()) {
    	  log.info("셀러의 Id: {}", sellerEntity.get().getSellerId());
          
          // 첨부 파일 처리
          String productImagePath1 = null;
          String productImagePath2 = null;

          // 파일 첨부여부 확인
          if(!productDTO.getUploadFile().isEmpty()) {
             productImagePath1 = FileService.saveFile(productDTO.getUploadFile(), uploadPath);
             productImagePath2 = productDTO.getUploadFile().getOriginalFilename();

             productDTO.setProductImagePath2(productImagePath2);
             productDTO.setProductImagePath1(productImagePath1);
          }

          SellerEntity entity = sellerEntity.get();
          log.info("셀러 엔티티: {}", entity);
          
          // 기본값 설정 - sellerMemberNo 설정, viewCnt를 디폴트 0으로 설정
          productDTO.setSellerMemberNo(sellerMemberNo);
          productDTO.setViewCnt(0);
          log.info("셀러번호, viewCnt 기본값 설정 완료");
          
          // 1) Entity로 변환
          ProductEntity productEntity = ProductEntity.toEntity(productDTO, entity);
          log.info("변환한 productEntity: {}", productEntity);
          log.info("entity 변환함");
          
          // 2) save()로 데이터 저장
          ProductEntity savedEntity = productRepository.save(productEntity);
          
          // 카테고리, 키워드 추가 위해 productDTO에 productNo set하기
          productDTO.setProductNo(savedEntity.getProductNo());
          
          // 저장된 엔티티 확인
          if (savedEntity != null && savedEntity.getProductNo() != null) {
              log.info("상품(기본데이터) 저장 완료, 저장된 상품 정보: {}", savedEntity.toString());
              return true;
          } else {
              log.warn("상품 저장 실패 또는 저장된 상품 번호를 확인할 수 없습니다.");
              return false;
          }
      }
      return false;
   }
   
   /**
    * 등록중인 상품에 카테고리 추가하기
    * 정인 ver
    * @param pCategoryDTO
    */
   public boolean ProductCategoryInsert(ProductDTO productDTO, PCategoryDTO pCategoryDTO) {
	
	// productNo로 상품 엔티티 찾아오기
   	Optional<ProductEntity> productEntity = productRepository.findByProductNo(productDTO.getProductNo());
	
   	
   	// 상품이 있는 경우 실행
	if (productEntity.isPresent()) {
		log.info("등록된 상품이 있음: {}", productEntity.get());
		ProductEntity entity1 = productEntity.get();
	   
	   // 카테고리 리스트가 null일 경우 해당 로직을 실행하지 않음
       if (productDTO.getCategoryNames() != null) {
	
       	// CategoryDTO에서 선택된 카테고리 코드 목록을 가져와서 반복 처리
       	for (String cateName : productDTO.getCategoryNames()) { 
   	
       		// cateName으로 카테고리 엔티티 찾아오기
       		Optional<CategoryEntity> categoryEntity = categoryRepository.findByCategoryName(cateName);
	    	
	        	if (categoryEntity.isPresent()) {
	        		
	        		CategoryEntity entity2 = categoryEntity.get();
	        		
	        		PCategoryEntity pCategoryEntity = PCategoryEntity.toEntity(pCategoryDTO, entity1, entity2);
	      	      	pCategoryRepository.save(pCategoryEntity);
	        	
	      	      	log.info("pCategoryEntity 저장");
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
    * 병우 ver
    * 등록중인 상품에 카테고리 추가하기
    * @param pCategoryDTOList

   public void categoryInsert(List<PCategoryDTO> pCategoryDTOList) {
      for (PCategoryDTO pCategoryDTO : pCategoryDTOList) {
		  Optional<ProductEntity> productEntity = productRepository.findById(pCategoryDTO.getProductNo());
	      Optional<CategoryEntity> categoryEntity = categoryRepository.findById(pCategoryDTO.getCategoryNo());
	      if(productEntity.isPresent() && categoryEntity.isPresent()) {
		    	  
		      ProductEntity entity1 = productEntity.get();
		      CategoryEntity entity2 = categoryEntity.get();
		      
		      PCategoryEntity pCategoryEntity = PCategoryEntity.toEntity(pCategoryDTO, entity1, entity2);
		
		      System.out.println(pCategoryEntity);
		      
		      pCategoryRepository.save(pCategoryEntity);
	      }
      }
      return ;
   }
   */
   
   /**
    * 등록중인 상품에 인증서 추가하기
    * @param ProductDTO, ProductCertificateDTO
    */
   public boolean ProductCertificateInsert(ProductDTO productDTO, ProductCertificateDTO productCertificateDTO) {
   	
	// productNo로 상품 엔티티 찾아오기
	Optional<ProductEntity> productEntity = productRepository.findByProductNo(productDTO.getProductNo());
   	
	// 상품이 있는 경우 실행
		if (productEntity.isPresent()) {
			ProductEntity entity1 = productEntity.get();
		   
		   // 인증서 리스트가 null일 경우 해당 로직을 실행하지 않음
	       if (productDTO.getProductCertificateTypeCodes() != null) {
		
	    	// productCertificateDTO에서 선택된 인증서 코드 목록을 가져와서 반복 처리
	        	for (Integer certCode : productDTO.getProductCertificateTypeCodes()) { 
	   	
	        	// certificateCode로 인증서 엔티티 찾아오기
	        	Optional<CertificateEntity> certificateEntity = certificateRepository.findById(certCode);
		    	
	        	if (certificateEntity.isPresent()) {
	        		
	        		CertificateEntity entity2 = certificateEntity.get();
		        		
	        		ProductCertificateEntity productCertificateEntity = ProductCertificateEntity.toEntity(productCertificateDTO, entity1, entity2);
	      	      	productCertificateRepository.save(productCertificateEntity);
	        	
	      	      	log.info("productCertificateEntity 저장");
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
    * 등록된 상품DB에 인증서 추가하기
    * 병우 ver
    * @param productCertificateDTO
   
   public void certificateInsert(List<ProductCertificateDTO> productCertificateDTOList) {
	   for(ProductCertificateDTO productCertificateDTO : productCertificateDTOList) {
		   Optional<ProductEntity> productEntity = productRepository.findById(productCertificateDTO.getProductNo());
		   Optional<CertificateEntity> certificateEntity = certificateRepository.findById(productCertificateDTO.getCertificateTypeCode());
		   if (productEntity.isPresent() && certificateEntity.isPresent()){   
			   ProductEntity entity1 = productEntity.get();
			   CertificateEntity entity2 = certificateEntity.get();
			   
			   ProductCertificateEntity productCertificateEntity = ProductCertificateEntity.toEntity(productCertificateDTO, entity1, entity2);
			      
			   System.out.println(productCertificateEntity);
	
			   productCertificateRepository.save(productCertificateEntity);
		   }
	   }
	   return ;
   }
    */
   
   /**
    * 상품 1개 조회해오기
    * @param productNo
    * @return
    */
	public ProductDTO selectOne(int productNo) {
		Optional<ProductEntity> entity = productRepository.findById(productNo);
		
		if(entity.isPresent()) {
			ProductEntity temp = entity.get();
			return ProductDTO.toDTO(temp, temp.getSellerEntity().getSellerMemberNo());
		}
		return null;
	}
	
	/**
	 * 상품의 모든 카테고리 조회하기
	 * @param productNo
	 * @return
	 */
	public List<Integer> categoryAll(Integer productNo) {
		List<Integer> categoryNos = pCategoryRepository.findCategoryNosByProductNo(productNo);

	    System.out.println(categoryNos);
	    
	    return categoryNos;
	} 
	
	/**
	 * 상품의 인증서 조회하기
	 * @param productNo
	 * @return
	 */
	public List<Integer> certificateAll(Integer productNo){
	
		List<Integer> CertificateTypeCodes = productCertificateRepository.findCertificateTypeCodesByProductNo(productNo);
	
		System.out.println(CertificateTypeCodes);
	    
	    return CertificateTypeCodes;
	}
	
	/**
	 * (검색기능 포함) 상품 리스트 불러오기 (상품 검색, 상품 조회)
	 * @param pageable
	 * @param searchItem 아니고 searchType
	 * @param searchWord 아니고 query
	 * @return
	 */
	public Page<ProductDTO> selectAll(Pageable pageable, String searchType, String query) {
		int page = pageable.getPageNumber() - 1;
		int pageLimit = pageable.getPageSize();
		
		Page<ProductEntity> entityList = null;

	    switch (searchType) {
        case "ALL":
            // ProductName 또는 Brand에 포함된 항목 모두 검색
            entityList = productRepository.findByProductNameContainsOrBrandContains(query, query, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "productName")));
            break;
        case "Products":
            // ProductName으로 검색
            entityList = productRepository.findByProductNameContains(query, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "productName")));
            break;
        case "Brand":
            // Brand로 검색
            entityList = productRepository.findByBrandContains(query, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "brand")));
            break;
        default:
            // 기본 전체 검색 (ProductName 또는 Brand로 정렬)
            entityList = productRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "productName")));
            break;
    }
	    
	    Page<ProductDTO> list = null;

		// 페이징 형태의 list로 변환
		// 목록에서 사용할 필요한 데이터만 간추림(생성자 만듦)
		list = entityList.map(
				(product) -> new ProductDTO(
						product.getProductNo(),
						product.getSellerEntity().getSellerMemberNo(), // ******혹시 나중에 오류나면 확인해보시길******
						product.getRegistrationDate(),
						product.getProductName(),
						product.getBrand(),
						product.getProductImagePath1())
				);

		return list;
	}
	
	
	/**
	 * (B_마이페이지) 좋아요 한 상품 리스트 불러오기
	 * @return
	 */
	public List<ProductDTO> selectAllLike(String buyerMemberNo) {
		
		// 좋아요한 productNo 리스트 조회
        // List<Integer> likedProductNos = bLikeRepository.findByLikefromBuyerEntity_BuyerMemberNoAndLikeUseYn(buyerMemberNo, "Y");
        
		log.info(buyerMemberNo);
		
		// 되는 코드들
		// List<Integer> likedProductNos = bLikeRepository.findProductNosByBuyerMemberNoAndLikeUseYn();
        // log.info("좋아요 한 상품의 productNos 리스트 조회함: {}", likedProductNos);
		// 되는 코드 끝

		
		List<Integer> likedProductNos = bLikeRepository.findLikedProductsByBuyerMemberNo(buyerMemberNo, "Y");
        log.info("좋아요 한 상품의 productNos 리스트 조회함: {}", likedProductNos);
		
		
        // 해당 productNo에 해당하는 ProductEntity 리스트 조회
        List<ProductEntity> likedProductEntityList = productRepository.findByProductNoIn(likedProductNos); 
        log.info("좋아요 한 상품엔티티 리스트: {}", likedProductEntityList);

        
        // ProductDTO 리스트 생성
        List<ProductDTO> list = new ArrayList<>();
        

        // for 루프를 사용하여 ProductEntity -> ProductDTO 변환
        for (ProductEntity product : likedProductEntityList) {
            ProductDTO dto = new ProductDTO(
                    product.getProductNo(),  // productNo 추가
                    product.getProductName(),
                    product.getBrand()
            );
            list.add(dto);
            log.info("productDTO list: {}", list);
        }
        
        return list;
		
    }
	    
	/**
	 * 상품 좋아요(B_Like 추가)
	 * @param buyerMemberNo
	 * @param productNo
	 * @param likeUseYn
	 * @return
	 */
	public boolean productLikeCreate(String buyerMemberNo, Integer productNo, String likeUseYn) {
		
//		Optional<ProductEntity> productEntityOpt = productRepository.findByProductNo(productNo);
//		Optional<BLikeEntity> BLikeEntityOpt = BLikeRepository.(productNo);
//		
//		if (productEntityOpt.isPresent()) {
//			ProductEntity productEntity = productEntityOpt.get();
//		}
		
		BuyerEntity buyer = buyerRepository.findByBuyerMemberNo(buyerMemberNo)
	                .orElseThrow(() -> new IllegalArgumentException("Buyer not found"));
		
		ProductEntity product = productRepository.findByProductNo(productNo)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
		
		 B_LikeEntity blike = B_LikeEntity.builder()
	                .likefromBuyerEntity(buyer)         // Set the buyer entity
	                .likedProductEntity(product)        // Set the product entity
	                .likeCreatedDate(LocalDateTime.now())  // Automatically set the current date/time
	                .likeUseYn(likeUseYn)               // Set the like status (e.g., "Y")
	                .build();
		
		bLikeRepository.save(blike);
		log.info("B_like 추가함:{}", blike);
		
		return true;
	}
//		
//		B_LikeEntity bLikeEntity = new B_LikeEntity();
//		bLikeEntity.setLikefromBuyerEntity(buyerMemberNo);
//		blikeEntity.set
//		
//		public LikeEntity saveLike(String fromBuyerMemberNo, String toSellerMemberNo, Long toProductNo) {
//	        LikeEntity like = new LikeEntity();
//	        like.setFromBuyerMemberNoLike(fromBuyerMemberNo);
//	        like.setToSellerMemberNoLike(toSellerMemberNo);
//	        like.setToProductNoLike(toProductNo);
//	        like.setLikeCreateDate(LocalDateTime.now());
//	        like.setLikeUseYn("Y");  // Assuming "Y" means active
//
//	        return likeRepository.save(like);
//	    }
		
		
		
	
	
	
	/**
	 * 상품 1개 정보 수정하기
	 * @param product
	 */
	@Transactional
	public void updateProduct(ProductDTO product) {
		MultipartFile uploadFile = product.getUploadFile();
		
		String productImagePath1 = null;
		String productImagePath2 = null;
		String oldProductImagePaht2 = null;
		
		if(!uploadFile.isEmpty()) {
			productImagePath1 = uploadFile.getOriginalFilename();
			productImagePath2 = FileService.saveFile(uploadFile, uploadPath);
		}
		
		Optional<ProductEntity> entity = productRepository.findById(product.getProductNo());
		
		if(entity.isPresent()) {
			ProductEntity temp = entity.get();
			oldProductImagePaht2 = temp.getProductImagePath2();
			
			if(oldProductImagePaht2 != null && !uploadFile.isEmpty()) {
				String fullPath = uploadPath + "/" + oldProductImagePaht2;
				FileService.deleteFile(fullPath);
				
				temp.setProductImagePath1(productImagePath1);
				temp.setProductImagePath2(productImagePath2);
			}
			
			if(oldProductImagePaht2 == null && !uploadFile.isEmpty()) {
				temp.setProductImagePath1(productImagePath1);
				temp.setProductImagePath2(productImagePath2);
			}
			
			temp.setProductName(product.getProductName());
			temp.setBrand(product.getBrand());
			temp.setProductDescription(product.getProductDescription());
			temp.setKeyword(product.getKeyword());
			
			productRepository.save(temp);
		}
	}
	
	/**
	 * 상품 1개 삭제하기
	 * @param productNo
	 */
	@Transactional
	public void deleteOne(int productNo) {
		Optional<ProductEntity> entity = productRepository.findById(productNo);
		
		if(entity.isPresent()) {
			ProductEntity product = entity.get();
			String productImagePath2 = product.getProductImagePath2();
			
			if(productImagePath2 != null) {
				String fullPath = uploadPath + "/" + productImagePath2;
				boolean result  = FileService.deleteFile(fullPath);
				
				log.info("파일 삭제 여부 : {}", result);
			}
			
			productRepository.deleteById(productNo);
		}
		
	}
	/**
	 * 조회수 증가
	 * @param boardNum
	 */
	@Transactional
	public void incrementHitcount(int productNo) {
		Optional<ProductEntity> entity = productRepository.findById(productNo);

		if(entity.isPresent()) {
			ProductEntity temp = entity.get();
			temp.setViewCnt(temp.getViewCnt() + 1);
		}
	}
	/**
	 * 게시글은 그대로, 파일만 삭제
	 * @param boardNum : 파일이 저장된 게시글번호
	 */
	@Transactional
	public void deleteFile(int productNo) {
		// 1) 데이터 조회
		Optional<ProductEntity> entity = productRepository.findById(productNo);

		String oldSavedFileName = null;

		if(entity.isPresent()) {
			ProductEntity temp = entity.get();
			oldSavedFileName = temp.getProductImagePath2();

			// 2) 예전 파일은 삭제
			String fullPath = uploadPath + "/" + oldSavedFileName; 
			FileService.deleteFile(fullPath);

			// 3) 파일명을 null로
			temp.setProductImagePath1(null);
			temp.setProductImagePath2(null);
		}
	}
}
