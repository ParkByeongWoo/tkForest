package com.tkForest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.tkForest.dto.SellerDTO;
import com.tkForest.entity.CategoryEntity;
import com.tkForest.entity.CertificateEntity;
import com.tkForest.entity.PCategoryEntity;
import com.tkForest.entity.ProductCertificateEntity;
import com.tkForest.entity.ProductEntity;
import com.tkForest.entity.SellerEntity;
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
	final PCategoryRepository pCategoryRepository;
	final CategoryRepository categoryRepository;
	final CertificateRepository certificateRepository;
	final ProductCertificateRepository productCertificateRepository;
   
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
   public void productCreate(ProductDTO productDTO) {
      log.info("저장 경로: {}", uploadPath);
      Optional<SellerEntity> sellerEntity = sellerRepository.findById(productDTO.getSellerMemberNo());
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
   
      
      // 1) Entity로 변환
      ProductEntity productEntity = ProductEntity.toEntity(productDTO, entity);
      // 2) save()로 데이터 저장
      productRepository.save(productEntity);
   }
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
   
	public ProductDTO selectOne(int productNo) {
		Optional<ProductEntity> entity = productRepository.findById(productNo);
		
		if(entity.isPresent()) {
			ProductEntity temp = entity.get();
			return ProductDTO.toDTO(temp, temp.getSellerEntity().getSellerMemberNo());
		}
		return null;
	}
	
	public List<Integer> categoryAll(Integer productNo) {
		List<Integer> categoryNos = pCategoryRepository.findByProductEntityProductNo(productNo);
	    System.out.println(categoryNos);
	    
	    return categoryNos;
	}
	
	public List<Integer> certificateAll(Integer productNo){
	
		List<Integer> certificateNos = productCertificateRepository.findByProductEntityProductNo(productNo);
	
		System.out.println(certificateNos);
	    
	    return certificateNos;
	}

	public Page<ProductDTO> selectAll(Pageable pageable, String searchItem, String searchWord) {
		int page = pageable.getPageNumber() - 1;
		
		Page<ProductEntity> entityList = productRepository.findAll(pageable);;

//		switch(searchItem) {
//		case "brand"   :
//			entityList = productRepository.findByBrandContains(
//					searchWord, 
//					PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "productNo") ));
//			break;
//		case "productName"  :
//			entityList = productRepository.findByProductNoContains(
//					searchWord, 
//					PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "productNo") ));
//			break;
//		case "productDescription" :
//			entityList = productRepository.findByProductDescriptionContains(
//					searchWord, 
//					PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "productNo") ));
//			break;
//		}
		
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
	 * 
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
