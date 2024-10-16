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

<<<<<<< HEAD
   final ProductService productService;
   
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
    * 회원가입(셀러)
    */
   
   
   
   /**
    * 회원가입(바이어) 화면 요청
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
    * @return
    */
   @GetMapping("/login")
   public String login() {
      
      return "user/login";
   }
=======
    final UserService userService;
>>>>>>> 3d282b4cb27750b3190389f57667bb38c94f1a42

    /**
     * 바이어 마이페이지 화면 요청
     * @return
     */
    @GetMapping("/buyerMypage")
    public String buyerMypage() {
        return "user/buyerMypage"; 
    }

    /**
     * 셀러 마이페이지 화면 요청
     * @return
     */
    @GetMapping("/sellerMypage")
    public String sellerMypage() {
        return "user/sellerMypage"; 
    }

<<<<<<< HEAD
   
   
   
   
   
   
   
=======
    /**
     * 회원가입(셀러) 처리
     */
    @PostMapping("/sellerSignUp")
    public String processSellerSignUp() {
        // ProductService를 통해 셀러 회원가입 처리 로직 호출
        userService.processSellerSignUp();
        log.info("Seller Sign Up Processed");
        return "redirect:/user/login";  // 회원가입 완료 후 로그인 페이지로 리다이렉트
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
     */
    @PostMapping("/buyerSignUp")
    public String processBuyerSignUp() {
        // ProductService를 통해 바이어 회원가입 처리 로직 호출
        userService.processBuyerSignUp();
        log.info("Buyer Sign Up Processed");
        return "redirect:/user/login";  // 회원가입 완료 후 로그인 페이지로 리다이렉트
    }

    /**
     * 로그인(공통) 화면 요청
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }
>>>>>>> 3d282b4cb27750b3190389f57667bb38c94f1a42
}
