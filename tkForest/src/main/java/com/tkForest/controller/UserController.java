package com.tkForest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tkForest.dto.BuyerDTO;
import com.tkForest.dto.SellerDTO;
import com.tkForest.service.UserService;

import jakarta.validation.Valid;

//import com.tkForest.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	final UserService userService;
   
   /**
    * 회원가입 전 바이어/셀러 구분 화면을 요청
    * @param model
    * @return
    */
   @GetMapping("/signUp")
   public String signUp() {

      return "user/signUp";
   }
   
   /**
    * 회원가입(셀러) 화면 요청
    * @return
    */
   @GetMapping("/sellerSignUp")
   public String sellerSignUp() {
      
      return "user/sellerSignUp";
   }
    
  	/**
    * 회원가입(셀러) 처리
    * 전달받은 sellerDTO를 sellerEntity로 변경한 후에 DB에 저장
    * @param sellerDTO
	 * @return boolean
    */
   @PostMapping("/sellerSignUp")
   public String sellerSignUp(@ModelAttribute SellerDTO sellerDTO) {
   	
	log.info("SellerDTO: {}", sellerDTO.toString());
   	
	// 기본값으로 설정
	sellerDTO.setSellerStatus(true);
	
   	// UserService를 통해 셀러 회원가입 처리 로직 호출
   	boolean result = userService.sellerSignUp(sellerDTO);
   	log.info("셀러 회원가입 성공여부: {}", result);

   	return "redirect:/";  // 회원가입 완료 후 로그인 페이지로 리다이렉트
//   	return "redirect:/user/login";  // 회원가입 완료 후 로그인 페이지로 리다이렉트
   }

   /**
    * 회원가입(바이어) 화면 요청
    * @return
    */
   @GetMapping("/buyerSignUp")
   public String buyerSignUp() {
       return "user/buyerSignUp";
   }

   /**
    * 회원가입(바이어) 처리
    * 전달받은 buyerDTO를 buyerEntity로 변경한 후에 DB에 저장
    * @param buyerDTO
	 * @return boolean
    */
   @PostMapping("/buyerSignUp")
   public String buyerSignUp(
   		@ModelAttribute BuyerDTO buyerDTO
   		) {
   	 log.info("BuyerDTO: {}", buyerDTO.toString());
   	
   	// UserService를 통해 셀러 회원가입 처리 로직 호출
   	boolean result = userService.buyerSignUp(buyerDTO);
   	log.info("바이어 회원가입 성공여부: {}", result);

   	return "redirect:/user/login";  // 회원가입 완료 후 로그인 페이지로 리다이렉트
   }
   
//   /**
//	 * (셀러) 회원가입시 ID 중복 체크 (비동기 이용해 처리-ResponseBody 필요)
//	 * @return
//	 */
//	@PostMapping("/confirmId")
//	@ResponseBody	// ajax요청이므로
//	public boolean confirmId(@RequestParam(name="userId") String userId) {
//		log.info("회원 가입 아이디: {}", userId);
//		
//		// true일 때 사용가능한 아이디(중복아이디X)		
//		return !userService.existId(userId);	// !(아이디가 이미 존재하면 true, 없으면 false(사용 가능) 반환)
//	}
   
   
   
   
   
   
   /**
    * 로그인(공통) 화면 요청(security 하면 중복되는 내용)
    * @return
    */
   @GetMapping("/login")
   public String login() {
      
      return "user/login";
   }

   

}

