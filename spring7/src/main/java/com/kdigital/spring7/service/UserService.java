package com.kdigital.spring7.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kdigital.spring7.dto.UserDTO;
import com.kdigital.spring7.entity.UserEntity;
import com.kdigital.spring7.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

	final UserRepository userRepository;
	final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
	 * 전달 받은 userDTO를 userEntity로 변경한 후 DB save();
	 * @param userDTO
	 */
	public boolean join(UserDTO userDTO) {
		// 가입하려는 id가 이미 사용중인지 확인 : 사용중인 아이디이면 true
		boolean isExistUser = userRepository.existsById(userDTO.getUserId());
		if(isExistUser) return false;		// 이미 사용중인 아이디이므로 가입 실패
		
		// 비밀번호 암호화
		userDTO.setUserPwd(bCryptPasswordEncoder.encode(userDTO.getUserPwd()));
		
		UserEntity userEntity = UserEntity.toEntity(userDTO);
		userRepository.save(userEntity);  	// 가입 성공
		
		return true;
	}

	/**
	 * userId에 해당하는 사용자 존재 여부 확인
	 * 회원가입 시 아이디 중복확인 용
	 * @param userId
	 * @return
	 */
	public boolean existId(String userId) {
		boolean result = userRepository.existsById(userId);	// userId가 존재하면 true
		return result;
	}

	/**
	 * 개인정보 수정을 위해 아이디와 비밀번호 확인처리 요청
	 * @param userId
	 * @param userPwd
	 * @return
	 */
	public UserDTO pwdCheck(String userId, String userPwd) {
		Optional <UserEntity> userEntity = userRepository.findById(userId);
		
		if(userEntity.isPresent()) {
			UserEntity temp = userEntity.get();
			String pwd = temp.getUserPwd(); // DB에 저장된 비밀번호

			// 1-암호화되지 않은 비번, 2-DB에 저장된 비번
			boolean result = bCryptPasswordEncoder.matches(userPwd, pwd);
			
			if(result) 
				return UserDTO.toDTO(temp);
		}
		
		return null;
	}

	/**
	 * 개인정보 수정
	 * 수정하려고 하는 정보를 setter를 통해 수정한다.!
	 * JPA의 save() 메소드 : 저장 + 수정도 가능
	 *     저장 : 동일한 PK 없으면 insert
	 *     수정 : 동일한 PK 있으면 update
	 * @param userDTO
	 */
	@Transactional
	public boolean update(UserDTO userDTO) {
		String userId = userDTO.getUserId();
	
		Optional<UserEntity> userEntity = userRepository.findById(userId);
		
		if(userEntity.isPresent()) {
			UserEntity entity = userEntity.get();
			
			// 비밀번호 암호화하여 DB 정보 갱신
			entity.setUserPwd(bCryptPasswordEncoder.encode(userDTO.getUserPwd()));
			entity.setEmail(userDTO.getEmail());
			
			return true;
		}
		
		return false;
	}
}
