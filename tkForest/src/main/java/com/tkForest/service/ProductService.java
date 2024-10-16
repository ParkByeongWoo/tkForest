package com.tkForest.service;

import org.springframework.stereotype.Service;

import com.tkForest.dto.ProductDTO;
import com.tkForest.entity.ProductEntity;
import com.tkForest.repository.ProductRepository;
import com.tkForest.util.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductService {
   
   final ProductRepository productRepository;
   
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
   public void ProductCreate(ProductDTO productDTO) {
      log.info("저장 경로: {}", uploadPath);

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

      // 1) Entity로 변환
      ProductEntity productEntity = ProductEntity.toEntity(productDTO);

      // 2) save()로 데이터 저장
      productRepository.save(productEntity);
   }

	public ProductDTO selectOne(int productNo) {
		Optional<ProductEntity> entity = productRepository.findById(productNo);
		
		// 데이터를 꺼내 InquiryDTO로 변환
		if(entity.isPresent()) {
			ProductEntity temp = entity.get();
			return ProductDTO.toDTO(temp);
		}
		return null;
	}

	public Page<ProductDTO> selectAll(Pageable pageable, String searchItem, String searchWord) {
		int page = pageable.getPageNumber() - 1;
		
		Page<ProductEntity> entityList = null;

		switch(searchItem) {
		case "brand"   :
			entityList = productRepository.findByBrandContains(
					searchWord, 
					PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "productNo") ));
			break;
		case "productName"  :
			entityList = productRepository.findByProductNoContains(
					searchWord, 
					PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "productNo") ));
			break;
		case "productDescription" :
			entityList = productRepository.findByProductDescriptionContains(
					searchWord, 
					PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "productNo") ));
			break;
		}
		
		Page<ProductDTO> list = null;

		// 페이징 형태의 list로 변환
		// 목록에서 사용할 필요한 데이터만 간추림(생성자 만듦)
		list = entityList.map(
				(product) -> new ProductDTO(
						product.getProductNo(),
						product.getSellerMemberNo(),
						product.getRegistrationDate(),
						product.getProductName(),
						product.getBrand(),
						product.getProductImagePath1(),
						product.getProductDescription(),
						product.getKeyword(),
						product.getViewCnt()
						)
				);

		return list;
	}

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

}
