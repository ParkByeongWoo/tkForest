package com.tkForest.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component 		// Bean으로 관리해야하니
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request
			, HttpServletResponse response
			, Authentication authentication)	// cf) failureHandler => exception 
					throws IOException, ServletException {
	
		log.info("로그인 성공");
		
		List<String> roleNames = new ArrayList<>();
		
		authentication.getAuthorities().forEach((auth) -> { 			// Collection이라 forEach 가능
			roleNames.add(auth.getAuthority());
		});
	
		log.info("role names ===> {}", roleNames);		// admin이면 ROLE_ADMIN, 일반이면 ROLE_USER
		
		// ADMIN / USER
		if (roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect("/admin");
		} else if (roleNames.contains("ROLE_USER"))  {
			response.sendRedirect("/");
			return;
		}		
	}

}
