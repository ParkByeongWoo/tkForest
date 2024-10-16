package com.tkForest.entity;

import com.tkForest.dto.NationDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
@Entity
@Table(name = "NATION")
public class NationEntity {

	@Id
    @Column(name = "NATIONCODE")
    private String nationCode;

    @Column(name = "NATIONNAME")
    private String nationName;

    // DTO -> Entity 변환 메소드
    public static NationEntity toEntity(NationDTO nationDTO) {
        return NationEntity.builder()
                .nationCode(nationDTO.getNationCode())
                .nationName(nationDTO.getNationName())
                .build();
    }
}
