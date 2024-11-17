package com.tkForest.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.tkForest.repository.InquiryRepository;
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
	final InquiryRepository inquiryRepository;
	final PCategoryRepository pCategoryRepository;
	final CategoryRepository categoryRepository;
	final CertificateRepository certificateRepository;
	final ProductCertificateRepository productCertificateRepository;
	final BLikeRepository bLikeRepository;
   
   // 페이징할 때 한 페이지 출력할 글 개수
   @Value("${user.inquiry.pageLimit}")
	private int pageLimit;	
	
   // 업로드된 파일이 저장될 디렉토리 경로를 읽어옴
   @Value("${download.image.path}") // 확인 필요
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
       log.info("셀러엔티티 불러옴: {}", sellerEntity.orElse(null));

       // 셀러가 있으면
       if (sellerEntity.isPresent()) {
           log.info("셀러의 Id: {}", sellerEntity.get().getSellerId());

           // 첨부 파일 처리
           String tempFilePath = null;

           // 파일 첨부여부 확인
           if (!productDTO.getUploadFile().isEmpty()) {
               // 임시 파일을 먼저 저장
               tempFilePath = FileService.saveFile(productDTO.getUploadFile(), uploadPath);

               // 임시 저장된 파일 이름 설정
               String originalFilename = productDTO.getUploadFile().getOriginalFilename();
               productDTO.setProductImagePath2(originalFilename);
               productDTO.setProductImagePath1(tempFilePath);
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

           // 2) save()로 데이터 저장
           ProductEntity savedEntity = productRepository.save(productEntity);
           productDTO.setProductNo(savedEntity.getProductNo());

           // 3) 파일 이름을 productNo.jpg로 변경 후 이동
           if (tempFilePath != null && savedEntity.getProductNo() != null) {
               String newFilename = savedEntity.getProductNo() + ".jpg";
               String newFilePath = uploadPath + newFilename;
               FileService.renameFile(tempFilePath, newFilePath);

               // 새로운 파일 경로를 productDTO에 설정
               productDTO.setProductImagePath1(newFilePath);
               log.info("파일이 저장되었습니다: {}", newFilePath);
           }

           // 저장된 엔티티 확인
           if (savedEntity != null && savedEntity.getProductNo() != null) {
               log.info("상품(기본데이터) 저장 완료, 저장된 상품 정보: {}", savedEntity);
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

>>>>>>> 245c0971e27f9f1798e3459c5a6adef45c326510
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
	public List<String> categoryAll(Integer productNo) {
		List<Integer> categoryNos = pCategoryRepository.findCategoryNosByProductNo(productNo);
		List<String> categoryNames = categoryRepository.findCategoryNameByCategoryNo(categoryNos);
		
	    System.out.println(categoryNames);
	    
	    return categoryNames;
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
	 * (상품리스트 최종)
	 * 카테고리필터링 + 검색 + 정렬 All
	 * (검색기능 포함) 상품 리스트 불러오기 (상품 검색, 상품 조회)
	 * @param pageable
	 * @param searchItem 아니고 searchType
	 * @param searchWord 아니고 query
	 * @return
	 */
	public Page<ProductDTO> selectAll(Pageable pageable, String searchType, String query, String sortBy, Integer CategoryNo) {
	    
	    log.info("서비스 CategoryNo: {}", CategoryNo);
	    
	    int page = pageable.getPageNumber() - 1;
	    int pageLimit = pageable.getPageSize();
	    
	    // 기본 정렬 기준 (없으면 registrationDate)
	    if (sortBy == null || sortBy.isEmpty()) {
	        sortBy = "registrationDate";
	    }
	    
	   // 동적으로 정렬 기준을 설정
	    Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
	    
	    Page<ProductEntity> entityList = null;

	    if (CategoryNo != null) {
	        // 카테고리가 지정된 경우, 해당 카테고리의 상품만 조회
	        List<Integer> uniqueProductNos = pCategoryRepository.findProductNosByCategoryNoStartsWith(CategoryNo).stream()
	                .distinct()
	                .toList();  // 중복제거된 productNos 리스트
	        
	        log.info("카테고리가 지정됨: {}", CategoryNo);
	        log.info("중복제거 ProductNos: {}", uniqueProductNos);
	        
	        if (query == null || query.isEmpty()) {
	            // 검색어 없이 단순히 카테고리로만 필터링
	            entityList = productRepository.findByProductNoIn(
	                uniqueProductNos, PageRequest.of(page, pageLimit, sort));
	        } else {
	            // 상품 이름, 브랜드 검색 및 카테고리 필터링을 함께 적용
	            switch (searchType) {
	                case "ALL":
	                    entityList = productRepository.findByProductNameContainsOrBrandContainsAndProductNoIn(
	                        query, query, uniqueProductNos, PageRequest.of(page, pageLimit, sort));
	                    break;
	                case "Products":
	                    entityList = productRepository.findByProductNameContainsAndProductNoIn(
	                        query, uniqueProductNos, PageRequest.of(page, pageLimit, sort));
	                    break;
	                case "Brand":
	                    entityList = productRepository.findByBrandContainsAndProductNoIn(
	                        query, uniqueProductNos, PageRequest.of(page, pageLimit, sort));
	                    break;
	                default:
	                    entityList = productRepository.findByProductNameContainsOrBrandContainsAndProductNoIn(
	                        query, query, uniqueProductNos, PageRequest.of(page, pageLimit, sort));
	                    break;
	            }
	        }
	    } else {
	        // 카테고리가 지정되지 않은 경우, 전체 검색
	        if (query == null || query.isEmpty()) {
	            // 검색어가 없을 때는 전체 상품 목록 반환
	            entityList = productRepository.findAll(PageRequest.of(page, pageLimit, sort));
	        } else {
	            // 검색어가 있을 때는 검색어로 필터링
	            switch (searchType) {
	                case "ALL":
	                    entityList = productRepository.findByProductNameContainsOrBrandContains(
	                        query, query, PageRequest.of(page, pageLimit, sort));
	                    break;
	                case "Products":
	                    entityList = productRepository.findByProductNameContains(
	                        query, PageRequest.of(page, pageLimit, sort));
	                    break;
	                case "Brand":
	                    entityList = productRepository.findByBrandContains(
	                        query, PageRequest.of(page, pageLimit, sort));
	                    break;
	                default:
	                    entityList = productRepository.findByProductNameContainsOrBrandContains(
	                        query, query, PageRequest.of(page, pageLimit, sort));
	                    break;
	            }
	        }
	    }

	    // ProductEntity를 ProductDTO로 매핑
	    Page<ProductDTO> list = entityList.map(
	        (product) -> new ProductDTO(
	            product.getProductNo(),
	            product.getSellerEntity().getSellerMemberNo(), 
	            product.getRegistrationDate(),
	            product.getProductName(),
	            product.getBrand(),
	            product.getSellerEntity().getCompanyName())
	    );

	    return list;
	}

	
	
	
	/**
	 * 특정 카테고리에 속한 상품들 가져오기
	 * @param query 
	 * @param searchType 
	 * @param pageable 
	 * @param categoryId
	 * @return
	 */
	public Page<ProductDTO> getProductsByCategory(Pageable pageable, String searchType, String query, Integer categoryId) {
		log.info("categoryId: {}", categoryId);
		
		int page = pageable.getPageNumber() - 1;
		int pageLimit = pageable.getPageSize();
		
		Page<ProductEntity> entityList = null;
		
		// 특정 카테고리에 해당하는 상품No 조회함
		// List<Integer> productNosByCategory = pCategoryRepository.findProductNosByCategoryNo(categoryId);
		
//		// 특정 카테고리에 해당하는 상품No 조회함 - categoryId로 시작하는 카테고리(즉, 대분류)
//		List<Integer> productNosByCategory = pCategoryRepository.findProductNosByCategoryNoStartsWith(categoryId);
//		// log.info("카테고리 필터링된 상품의 productNos 리스트 조회함: {}", productNosByCategory);
//		
//		// 위에서 불러온 List<Integer> productNosByCategory에는 productNo가 중복되어 들어가있기 때문에 중복제거 작업
//		List<Integer> uniqueProductNos = productNosByCategory.stream()
//	            .distinct()  // 중복 제거
//	            .collect(Collectors.toList());
//		log.info("중복 제거된 카테고리 필터링된 상품의 productNos 리스트 조회함: {}", uniqueProductNos);
		
		
		// 특정 카테고리에 해당하는 상품No 조회 후 중복 제거 작업 - categoryId로 시작하는 카테고리(즉, 대분류)
		List<Integer> uniqueProductNos = pCategoryRepository.findProductNosByCategoryNoStartsWith(categoryId).stream()
		    .distinct()
		    .toList();  // Java 16 이상에서 사용 가능

		log.info("중복 제거된 카테고리 필터링된 상품의 productNos 리스트 조회함: {}", uniqueProductNos);
		
		 // 해당 productNo에 해당하는 ProductEntity 리스트 조회
        Page<ProductEntity> cateProductEntityList = productRepository.findPageByProductNoIn(uniqueProductNos, PageRequest.of(page, pageLimit));
        log.info("좋아요 한 상품엔티티 리스트: {}", cateProductEntityList.get());

        Page<ProductDTO> list = null;

		// 페이징 형태의 list로 변환
		// 목록에서 사용할 필요한 데이터만 간추림(생성자 만듦)
		list = cateProductEntityList.map(
				(product) -> new ProductDTO(
						product.getProductNo(),
						product.getSellerEntity().getSellerMemberNo(), // ******혹시 나중에 오류나면 확인해보시길******
						product.getRegistrationDate(),
						product.getProductName(),
						product.getBrand(),
						product.getSellerEntity().getCompanyName())
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
					product.getProductNo(),
					product.getSellerEntity().getSellerMemberNo(), // ******혹시 나중에 오류나면 확인해보시길******
					product.getRegistrationDate(),
					product.getProductName(),
					product.getBrand(),
					product.getSellerEntity().getCompanyName());
            list.add(dto);
        }
        log.info("productDTO list: {}", list);
        
        return list;
		
    }
	
	public List<ProductDTO> selectAllInquiry(String buyerMemberNo){
		List<ProductEntity> productEntityList = inquiryRepository.findProductEntitiesByBuyerMemberNoContains(buyerMemberNo);
        List<ProductDTO> list = new ArrayList<>();
        // for 루프를 사용하여 ProductEntity -> ProductDTO 변환
        for (ProductEntity product : productEntityList) {
            ProductDTO dto = new ProductDTO(
					product.getProductNo(),
					product.getSellerEntity().getSellerMemberNo(), // ******혹시 나중에 오류나면 확인해보시길******
					product.getRegistrationDate(),
					product.getProductName(),
					product.getBrand(),
					product.getSellerEntity().getCompanyName());
            list.add(dto);
        }
        log.info("productDTO list: {}", list);
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
		
		// buyer정보가 없으면 null 반환
		BuyerEntity buyer = buyerRepository.findByBuyerMemberNo(buyerMemberNo)
				.orElse(null);
		
		if (buyer == null) {
			log.info("buyer회원 정보를 찾을 수 없습니다: {}", buyerMemberNo);
	        return false;  // 메소드가 실패했음을 나타내는 값 반환
		}
		
//		Optional<ProductEntity> productEntityOpt = productRepository.findByProductNo(productNo);
//		Optional<BLikeEntity> BLikeEntityOpt = BLikeRepository.(productNo);
//		
//		if (productEntityOpt.isPresent()) {
//			ProductEntity productEntity = productEntityOpt.get();
//		}
		
//		BuyerEntity buyer = buyerRepository.findByBuyerMemberNo(buyerMemberNo)
//	                .orElseThrow(() -> new IllegalArgumentException("Buyer not found"));

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
	/**
     * sellerMemberNo로 셀러가 등록한 상품 목록 조회
     * @param sellerMemberNo
     * @return List<ProductDTO>
     */

	public List<ProductDTO> findProductsBySellerMemberNo(String sellerMemberNo) {
	    // sellerMemberNo로 셀러의 상품 목록을 조회하는 로직
	    List<ProductEntity> productEntityList = productRepository.findBySellerEntitySellerMemberNo(sellerMemberNo);
	    
	    List<ProductDTO> list = new ArrayList<>();
	    // ProductEntity를 ProductDTO로 변환하여 반환
	    for (ProductEntity product : productEntityList) {
	    	ProductDTO dto = new ProductDTO(
	    			product.getProductNo(),
	    			product.getSellerEntity().getSellerMemberNo(), // ******혹시 나중에 오류나면 확인해보시길******
	    			product.getRegistrationDate(),
	    			product.getProductName(),
	    			product.getBrand(),
	    			product.getSellerEntity().getCompanyName());
	    	list.add(dto);}
	    return list;
	}
    // 상품 번호로 상품명 조회
    public String findProductNameById(Integer productNo) {
        Optional<ProductEntity> productEntity = productRepository.findById(productNo);
        
        if (productEntity.isPresent()) {
            return productEntity.get().getProductName();  // 상품 이름 반환
        } else {
            throw new RuntimeException("Product not found with productNo: " + productNo);
        }
    }
    

}

