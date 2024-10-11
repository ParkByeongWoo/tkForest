package com.tkForest.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 로그인 전용 - Security로 login하는 DTO
@ToString
public class LoginBuyerDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String buyerMemberNo;
	private String id;
	private String password;
	private Boolean buyerStatus;
	
	// 생성자
	public LoginBuyerDetails(BuyerDTO buyerDTO) {
		this.buyerMemberNo = buyerDTO.getBuyerMemberNo();
		this.id = buyerDTO.getId();
		this.password = buyerDTO.getPassword();
	}
	
	// 사용자의 Role의 정보 반환
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {	// 관리자
		Collection<GrantedAuthority> collection = new ArrayList<>();	// granted된 사용자인지 반환	// Collection은 list 아부지
		
		collection.add(new GrantedAuthority() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return null;	// 수정해야함
			}
		});
		
		return collection;
	}

	@Override
	public String getPassword() {	// Security에서 비밀번호 확인을 위해 비밀번호 달라는 것
		return this.password;
	}

	// Security가 설정한 메소드
	@Override
	public String getUsername() { 	// Security에서 아이디 확인을 위해 아이디 달라는 것
									// 이름은 Username이지만 여기선 ID를 의미하는 것!
		return this.id;
	}
	
//	// 사용자 정의 메소드(뷰단에서 사용할 사용자의 실명 이름)
//	public String getUserName() {
//		return this.userName;
//	}

}
