package com.tkForest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tkForest.service.ProductService;
import com.tkForest.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

   // final ProductService productService;
   final UserService userService;

   /**
    * 회원가입 전 바이어/셀러 구분 화면을 요청
    * 
    * @param model
    * @return
    */
   @GetMapping("/signUp")
   public String signUp() {

      return "user/signUp";
   }

   /**
    * 회원가입(셀러) 화면 요청
    * 
    * @return
    */
   @GetMapping("/sellerSignUp")
   public String sellerSignUp() {

      return "user/sellerSignUp";
   }

   /**
    * 회원가입(셀러)
    */

   /**
    * 회원가입(바이어) 화면 요청
    * 
    * @return
    */
   @GetMapping("/buyerSignUp")
   public String buyerSignUp() {

      return "user/buyerSignUp";
   }

   /**
    * 회원가입(바이어)
    */

   /**
    * 로그인(공통) 화면 요청
    * 
    * @return
    */
   @GetMapping("/login")
   public String login() {

      return "user/login";
   }

   /**
    * 바이어 마이페이지 화면 요청
    * 
    * @return 바이어 마이페이지
    */
   @GetMapping("/buyerMypage")
   public String buyerMypage() {
      log.info("바이어 마이페이지 화면 요청");
      return "user/buyerMypage";
   }

   /**
    * 셀러 마이페이지 화면 요청
    * 
    * @return
    */
   @GetMapping("/sellerMypage")
   public String sellerMypage() {
      return "user/sellerMypage";
   }

   /**
    * 바이어 프로필 수정 요청
    * 
    * @return 바이어 프로필 수정 화면
    */
   @GetMapping("/buyerProfileUpdate")
   public String buyerProfileUpdate() {
      log.info("바이어 프로필 수정 페이지 요청");
      return "user/buyerProfileUpdate";
   }

   /**
    * 셀러 프로필 수정 요청
    * 
    * @return 바이어 프로필 수정 화면
    */
   @GetMapping("/sellerProfileUpdate")
   public String sellerProfileUpdate() {
      log.info("바이어 프로필 수정 페이지 요청");
      return "user/sellerProfileUpdate";
   }

   /**
    * 바이어 계정 삭제
    * 
    * @return 바이어 프로필 수정 화면
    */

   // /**
   // * 바이어 회원 탈퇴
   // * @param buyerMemberNo
   // */
   // @DeleteMapping("/buyerAccountDelete/{buyerMemberNo}")
   // public ResponseEntity<String> deleteAccount(@PathVariable Long userId) {
   // userService.deleteUser(userId);
   // return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
   // }
   // }
   // /**
   // * 셀러 회원 탈퇴
   // * @param sellerMemberNo
   // */
   // @DeleteMapping("/sellerAccountDelete/{sellerMemberNo}")
   // public ResponseEntity<String> deleteAccount(@PathVariable Long userId) {
   // userService.deleteUser(userId);
   // return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
   // }
   // }

}
