package com.tkForest.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, 
            HttpServletResponse response, 
            Authentication authentication) throws IOException, ServletException {

        // 로그인 성공 메시지 출력
        log.info("로그인 성공");

        // 로그인한 사용자의 정보를 가져옴 (UserDetails 타입으로 캐스팅)
        Object principal = authentication.getPrincipal();
        String username = null;
        
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername(); // 로그인한 사용자 아이디 가져오기
        }

        log.info("로그인한 사용자 아이디: {}", username); // 사용자 아이디 로그 출력

        List<String> roleNames = new ArrayList<>();

        // 사용자의 권한 목록 가져오기
        authentication.getAuthorities().forEach((auth) -> {
            roleNames.add(auth.getAuthority());
        });

        log.info("사용자의 역할: {}", roleNames); // 사용자 권한 로그 출력

        // 각 역할에 따라 로그를 출력하고, 모두 메인 페이지로 리다이렉트
        if (roleNames.contains("ROLE_ADMIN")) {
            log.info("관리자로 로그인하였습니다.");
        } else if (roleNames.contains("ROLE_SELLER")) {
            log.info("셀러로 로그인하였습니다.");
        } else if (roleNames.contains("ROLE_BUYER")) {
            log.info("바이어로 로그인하였습니다.");
        } else {
            log.info("정의되지 않은 역할로 로그인하였습니다.");
        }

        // 로그인 후 메인 페이지로 리다이렉트
        response.sendRedirect("/");
    }

}
