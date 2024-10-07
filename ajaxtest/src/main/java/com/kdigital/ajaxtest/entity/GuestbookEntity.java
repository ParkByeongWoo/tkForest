package com.kdigital.ajaxtest.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.kdigital.ajaxtest.dto.GuestbookDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity
@Table(name="guestbook")
public class GuestbookEntity { 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="seq")
	private Long seq;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="pwd", nullable=false)
	private String pwd;
	
	@Column(name="content")
	private String content;
	
	@Column(name="create_date")
	@CreationTimestamp
	private LocalDate createDate;
	
	public static GuestbookEntity toEntity(GuestbookDTO dto) {
		return GuestbookEntity.builder()
				.seq(dto.getSeq())
				.name(dto.getName())
				.pwd(dto.getPwd())
				.content(dto.getContent())
				.createDate(dto.getCreateDate())
				.build();
	}
}
