package com.tkForest.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tkForest.dto.ProductDTO;
import com.tkForest.entity.ProductEntity;
import com.tkForest.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

	final ProductRepository productRepository;
	
	/**
	 * PK에 해당하는 productNum 값을 이용해 상품 한 개 조회
	 * @param productNum
	 * @return
	 */
	public ProductDTO selectOne(Long productNum) {
		Optional<ProductEntity> entity = productRepository.findById(productNum);
		
		// Entity => DTO
		if (entity.isPresent()) {
			ProductEntity temp = entity.get();
			log.info("entity: {}", temp);
			return ProductDTO.toDTO(temp);
		}

		return null;
	}
	
}
