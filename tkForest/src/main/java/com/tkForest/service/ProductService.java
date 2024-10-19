package com.tkForest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tkForest.dto.PCategoryDTO;
import com.tkForest.dto.ProductCertificateDTO;
import com.tkForest.dto.ProductDTO;
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

    @Value("${user.inquiry.pageLimit}")
    private int pageLimit;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    /**
     * DB에 상품 저장
     * @param productDTO : 저장해야 하는 상품
     */
    public void productCreate(ProductDTO productDTO) {
        log.info("저장 경로: {}", uploadPath);
        Optional<SellerEntity> sellerEntity = sellerRepository.findById(productDTO.getSellerMemberNo());
        String productImagePath1 = null;
        String productImagePath2 = null;

        if(!productDTO.getUploadFile().isEmpty()) {
            productImagePath1 = FileService.saveFile(productDTO.getUploadFile(), uploadPath);
            productImagePath2 = productDTO.getUploadFile().getOriginalFilename();
            productDTO.setProductImagePath2(productImagePath2);
            productDTO.setProductImagePath1(productImagePath1);
        }

        SellerEntity entity = sellerEntity.get();
        ProductEntity productEntity = ProductEntity.toEntity(productDTO, entity);
        productRepository.save(productEntity);
    }

    public void categoryInsert(PCategoryDTO pCategoryDTO) {
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

    public void certificateInsert(ProductCertificateDTO productCertificateDTO) {
        Optional<ProductEntity> productEntity = productRepository.findById(productCertificateDTO.getProductNo());
        Optional<CertificateEntity> certificateEntity = certificateRepository.findById(productCertificateDTO.getCertificateTypeCode());

        if (productEntity.isPresent() && certificateEntity.isPresent()) {
            ProductEntity entity1 = productEntity.get();
            CertificateEntity entity2 = certificateEntity.get();
            ProductCertificateEntity productCertificateEntity = ProductCertificateEntity.toEntity(productCertificateDTO, entity1, entity2);
            System.out.println(productCertificateEntity);
            productCertificateRepository.save(productCertificateEntity);
        }
    }

    // 회사명을 위한 수정 부분 -->
    public ProductDTO selectOne(int productNo) {
        Optional<ProductEntity> entity = productRepository.findByIdWithSeller(productNo); // SellerEntity와 함께 페치 조인으로 조회

        if (entity.isPresent()) {
            ProductEntity productEntity = entity.get();
            SellerEntity sellerEntity = productEntity.getSellerEntity();
            if (sellerEntity != null) {
                log.info("조회된 회사명: {}", sellerEntity.getCompanyName());  // 회사명 출력
            }

            log.info("조회된 상품 정보: {}", productEntity);  // 상품 정보 출력
            return ProductDTO.toDTO(productEntity); // DTO로 변환하여 반환
        } else {
            log.warn("ProductEntity not found with productNo: {}", productNo);
        }
        return null;
    }
    // <-- 여기까지 회사명을 위한 수정 부분

    public List<Integer> categoryAll(Integer productNo) {
        return pCategoryRepository.findByProductEntityProductNo(productNo);
    }

    public List<Integer> certificateAll(Integer productNo){
        return productCertificateRepository.findByProductEntityProductNo(productNo);
    }

    // 회사명을 포함한 페이징 처리된 상품 목록 반환 -->
    public Page<ProductDTO> selectAll(Pageable pageable, String searchItem, String searchWord) {
        int page = pageable.getPageNumber() - 1;
        Page<ProductEntity> entityList = productRepository.findAll(pageable);

        // 페이징 형태의 list로 변환, 회사명 추가된 부분
        Page<ProductDTO> list = entityList.map(
            (product) -> {
                SellerEntity seller = product.getSellerEntity();  // Lazy 로딩된 seller 불러오기
                return new ProductDTO(
                    product.getProductNo(),
                    product.getSellerEntity().getSellerMemberNo(),
                    product.getRegistrationDate(),
                    product.getProductName(),
                    product.getBrand(),
                    product.getProductImagePath1()
                );
            }
        );
        return list;
    }
    // <-- 여기까지 회사명 페이징 처리

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

    @Transactional
    public void deleteOne(int productNo) {
        Optional<ProductEntity> entity = productRepository.findById(productNo);

        if(entity.isPresent()) {
            ProductEntity product = entity.get();
            String productImagePath2 = product.getProductImagePath2();

            if(productImagePath2 != null) {
                String fullPath = uploadPath + "/" + productImagePath2;
                boolean result = FileService.deleteFile(fullPath);
                log.info("파일 삭제 여부 : {}", result);
            }

            productRepository.deleteById(productNo);
        }
    }

    @Transactional
    public void incrementHitcount(int productNo) {
        Optional<ProductEntity> entity = productRepository.findById(productNo);

        if(entity.isPresent()) {
            ProductEntity temp = entity.get();
            temp.setViewCnt(temp.getViewCnt() + 1);
        }
    }

    @Transactional
    public void deleteFile(int productNo) {
        Optional<ProductEntity> entity = productRepository.findById(productNo);
        String oldSavedFileName = null;

        if(entity.isPresent()) {
            ProductEntity temp = entity.get();
            oldSavedFileName = temp.getProductImagePath2();
            String fullPath = uploadPath + "/" + oldSavedFileName;
            FileService.deleteFile(fullPath);

            temp.setProductImagePath1(null);
            temp.setProductImagePath2(null);
        }
    }
}
