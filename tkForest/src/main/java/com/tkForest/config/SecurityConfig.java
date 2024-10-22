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

@Configuration   // 이 파일이 설정파일임을 나타내는 annotation
@EnableWebSecurity   // 웹 보안은 모두 이 설정파일을 따른다.
@RequiredArgsConstructor
public class SecurityConfig {
   
   private final LoginUserDetailsService loginUserDetailService;
   
   private final LoginFailureHandler failureHandler;   // 로그인 실패시 처리할 객체 - 필터같은 역할을 함
   private final LoginSuccessHandler successHandler;   // 로그인 성공시 처리할 객체
    
   @Bean
   SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      
      // 1) 웹 요청에 대한 접근 권한 설정
      http
         .authorizeHttpRequests((auth) -> auth
               // 로그인 안해도 모든 사람이 접근 가능한 목록
               .requestMatchers(
                     "/"
                     , "/user/signUp"
                     , "/user/login"         // 에러 발생시 경로
                     , "/user/sellerSignUp"
                     , "/user/buyerSignUp"
//                     , "/user/confirmId"
                          , "/user/signUp"        // join에서 변경
                          , "/user/sellerSignUp"  // 셀러가입창
                          , "/user/buyerSignUp"   // 바이어가입창
                          , "/user/login"         // 에러 발생시 경로
                          , "/user/logout"         // 로그아웃
                          , "/user/confirmId"
                          , "/aboutUs"            // 어바웃 어스 경로
                          , "/user/buyerMypage"
                          , "/user/sellerMypage"
                          , "/user/sellerStore" // 셀러스토어 
                          , "/product/productList" // 대분류 카테고리
                          , "/rec/recList"         // 추천 페이지
                          , "/inquiry/inquiryList" // 인콰이어리 페이지
                          , "/inquiry/inquiryWrite" // 인콰이어리 작성
                          , "/inquiry/inquiryDetail" // 인콰이어리 상세 보기 및 답변 작성
                          , "/product/productCreate" // 상품 등록 화면
                          , "/product/productList"
                          , "/product/productDetail"
                          , "/chart/chartTest.html"
                          , "/assets/**"           // 정적 자원 경로
                     , "/images/**"
                     , "/css/**"
                     , "/script/**"
//                     , "/img/**"
                     , "/**"
                     ,"/fragment/**"
                     
                     ).permitAll()   // permitAll() 인증절차 없이도 접근가능한 요청 정보      
               .requestMatchers("/admin/**").hasRole("ADMIN")
               .requestMatchers("/my/**").hasAnyRole("ADMIN", "SELLER", "BUYER")
               .anyRequest().authenticated()   // 기타 다른 경로는 인증된 사용자만 접근 가능(가장 마지막에 와야함)
               );
      
      // Custom Login 설정 (로그인 관련 요청은 Controller에서 처리하지 않음)
      http
         .formLogin((auth) -> auth
               .loginPage("/user/login")
               .failureHandler(failureHandler)    // 로그인 실패시 처리할 핸들러 등록
               .successHandler(successHandler)    // 로그인 성공시 처리할 핸들러 등록
               .usernameParameter("id")
               .passwordParameter("password")
               .loginProcessingUrl("/user/loginProc").permitAll()
               // .defaultSuccessUrl("/").permitAll()      // 로그인 성공하면   // successHandler가 등록돼서 필요없어짐;
               );
      
      // logout 설정
      http
         .logout((auth) -> auth
               .logoutUrl("/user/logout")      // 로그아웃 처리 URL
               .logoutSuccessUrl("/")         // 로그아웃 성공시 URL 
               .invalidateHttpSession(true)   // 세션 메모리공간을 무효화시킴 (중요!) (session.invalidate())
               );
      
      // POST 요청시 CSRF (Cross-Site Request Forgery)
      // 개발시에는 비활성화 나중엔 활성화해야 보안위험 방지
      http
         .csrf((auth) -> auth.disable());
      
      return http.build();
   }
   
   // 비밀번호 암호화 
   @Bean
   BCryptPasswordEncoder bCryptPasswordEncoder() {
      return new BCryptPasswordEncoder();
   }
   
}
