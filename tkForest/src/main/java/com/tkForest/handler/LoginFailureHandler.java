package com.tkForest.handler;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        String errMessage = "";
        String attemptedUsername = request.getParameter("id"); // 로그인 시도한 아이디 가져오기

        log.info("로그인 실패 - 시도한 아이디: {}", attemptedUsername); // 로그인 실패한 아이디 로그

        if (exception instanceof BadCredentialsException) {
            log.error("아이디 또는 비밀번호 오류: {}", exception.getMessage()); // 예외 메시지 로그 추가
            errMessage = exception.getMessage();
            errMessage += " 아이디나 비밀번호가 잘못되었습니다.";
        } else {
            log.error("알 수 없는 로그인 실패 원인: {}", exception.getMessage()); // 기타 예외 로그 추가
            errMessage = exception.getMessage();
            errMessage += " 로그인에 실패했습니다. 관리자에게 문의하세요.";
        }

        log.info("로그인 실패 메시지: {}", errMessage); // 최종 실패 메시지 로그

        errMessage = URLEncoder.encode(errMessage, "UTF-8");

        // GET 방식의 재요청 (loginPage 요청)
        setDefaultFailureUrl("/user/login?error=true&errMessage=" + errMessage); // 에러 메시지 포함한 리다이렉트

        super.onAuthenticationFailure(request, response, exception);
    }

}
