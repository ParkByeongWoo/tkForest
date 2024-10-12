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
@Component	// 클래스를 Bean으로 관리하게 하는 Annotaion
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {	// 이 외에도 핸들러 많은데 이건 심플버전
	
	// 로그인 실패했을 때 자동 캐치함. 요청 가로채고, response 응답하고, 예외처리하는 메소드
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		String errMessage = "";
		
		if (exception instanceof BadCredentialsException) {	// 아이디 or 비밀번호 오류
			errMessage = exception.getMessage();	// 어떤 오류인지 메시지 확인용(필수는 X)
			errMessage += " 아이디나 비밀번호가 잘못되었습니다.";
		} else {
			errMessage = exception.getMessage();
			errMessage += " 로그인에 실패했습니다. 관리자에게 문의하세요.";
		}
		
		log.info("로그인 실패: {} ", errMessage);
		
		errMessage = URLEncoder.encode(errMessage, "UTF-8");
		
		// GET 방식의 재요청 (loginPage 요청)
		setDefaultFailureUrl("/user/login?error=true&errMessage=" + errMessage);		// 에러났다고 알려주기
		
		super.onAuthenticationFailure(request, response, exception);
	}

}
