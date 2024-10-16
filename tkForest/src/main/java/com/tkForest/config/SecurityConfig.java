package com.tkForest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.tkForest.handler.LoginFailureHandler;
import com.tkForest.handler.LoginSuccessHandler;
import com.tkForest.service.LoginUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration  // 이 파일이 설정파일임을 나타내는 annotation
@EnableWebSecurity  // 웹 보안은 모두 이 설정파일을 따른다.
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final LoginUserDetailsService loginUserDetailService;
    
    private final LoginFailureHandler failureHandler;  // 로그인 실패시 처리할 객체 - 필터같은 역할을 함
    private final LoginSuccessHandler successHandler;  // 로그인 성공시 처리할 객체
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        // 1) 웹 요청에 대한 접근 권한 설정
        http
            .authorizeHttpRequests((auth) -> auth
                // 로그인 안해도 모든 사람이 접근 가능한 목록 (비회원 포함)
                .requestMatchers(
                        "/",  // 홈 페이지
                        "/aboutUs",  // 어바웃 어스 페이지
//                        "/user/*",  // 유저 관련 페이지 (회원가입, 로그인 등)
                        "/user/signUp",  // 회원가입
                        "/user/sellerSignUp",  // 셀러 회원가입
                        "/user/buyerSignUp",  // 바이어 회원가입
                        "/user/confirmId",  // 아이디 중복 확인
               		    "/user/logout",  // 세션 만료 시를 위해 모두 접근 허용해야 함
                        "/product/productList",  // 상품 목록
                        "/rec/recList",  // 추천 페이지
                        "/inquiry/inquiryList",  // 인콰이어리 목록
                        "/inquiry/inquiryWrite"  // 인콰이어리 작성
	                    , "/assets/**"           // 정적 자원 경로
//						, "/images/**"
//						, "/css/**"
//						, "/script/**"

                ).permitAll()  // 인증절차 없이 접근 가능한 요청 정보
                
                // 로그인 페이지는 비회원(anonymous)만 접근 가능
                .requestMatchers("/user/login").anonymous()
                
                // 관리자만 접근 가능한 경로
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // 인증된 사용자 (회원)만 접근 가능한 경로 (BUYER, SELLER, ADMIN 모두 허용)
                .requestMatchers(
                		"/my/**"
	                    , "/inquiry/inquiryList" // 인콰이어리 페이지
                		
                		).hasAnyRole("ADMIN", "SELLER", "BUYER")
                
                // SELLER만 접근 가능한 경로
                .requestMatchers(
                	      "/seller/**"
	                    , "/user/sellerMypage"
                        , "/product/productCreate"
                		
                		
                		).hasRole("SELLER")
                
               
                
                // BUYER만 접근 가능한 경로
                .requestMatchers(
                		
                	      "/buyer/**"
	                    , "/inquiry/inquiryWrite"  // 인콰이어리 작성 
	                    , "/user/buyerMypage"
	                    
                      ).hasRole("BUYER")
                
                // 그 외 다른 경로는 인증된 사용자만 접근 가능
                .anyRequest().authenticated()  
            );
        
        // 2) Custom Login 설정 (로그인 관련 요청은 Controller에서 처리하지 않음)
        http
            .formLogin((auth) -> auth
                .loginPage("/user/login")  // 커스텀 로그인 페이지 경로
                //.loginProcessingUrl("/user/loginProc")  // 로그인 처리 경로(Proc안써서 우선 박탈)
                .usernameParameter("userId")  // 로그인 폼에서 아이디 필드명
                .passwordParameter("userPwd")  // 로그인 폼에서 비밀번호 필드명
                .failureHandler(failureHandler)  // 로그인 실패 핸들러 등록
                .successHandler(successHandler)  // 로그인 성공 핸들러 등록
                .permitAll()  // 로그인 페이지는 인증 없이 접근 가능
            );
        
        // 3) 로그아웃 설정
        http
            .logout((auth) -> auth
                .logoutUrl("/user/logout")  // 로그아웃 처리 URL
                .logoutSuccessUrl("/")  // 로그아웃 성공시 리다이렉트 URL
                .invalidateHttpSession(true)  // 세션 무효화
                .permitAll()  // 로그아웃 요청은 인증 없이 처리 가능
            );
        
        // 4) POST 요청시 CSRF (Cross-Site Request Forgery) 방지
        // 개발시에는 비활성화, 나중엔 활성화하여 보안 위협 방지
        http
            .csrf((auth) -> auth.disable());  // CSRF 비활성화 (개발환경용)
        
        return http.build();
    }
    
    // 비밀번호 암호화에 사용할 BCryptPasswordEncoder Bean
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

