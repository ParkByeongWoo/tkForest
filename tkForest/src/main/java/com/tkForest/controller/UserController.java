package com.tkForest.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tkForest.dto.BuyerDTO;
import com.tkForest.dto.LoginSellerDetails;
import com.tkForest.dto.SellerDTO;
import com.tkForest.service.UserService;

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
	log.info("셀러 Status를 true로 설정함");
	
   	// UserService를 통해 셀러 회원가입 처리 로직 호출
   	boolean result = userService.sellerSignUp(sellerDTO);
   	log.info("셀러 회원가입 성공여부: {}", result);
   	log.info("셀러 회원가입 정보: {}", sellerDTO);

   	return "redirect:/";  // 회원가입 완료 후 메인 페이지로 리다이렉트
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
    * 전달받은 buyerDTO를 BuyerEntity로 변경한 후에 DB에 저장
    * @param buyerDTO
	* @return boolean
    */
   @PostMapping("/buyerSignUp")
   public String buyerSignUp(@ModelAttribute BuyerDTO buyerDTO) {
   	
	log.info("BuyerDTO: {}", buyerDTO.toString());
   	
	// 기본값으로 설정
	buyerDTO.setBuyerStatus(true);
	
   	// UserService를 통해 바이어 회원가입 처리 로직 호출
   	boolean result = userService.buyerSignUp(buyerDTO);
   	log.info("바이어 회원가입 성공여부: {}", result);
   	log.info("바이어 회원가입 정보: {}", buyerDTO);

   	return "redirect:/";  // 회원가입 완료 후 /로 리다이렉트
//   	return "redirect:/user/login";  // 회원가입 완료 후 로그인 페이지로 리다이렉트
   }
   
   /**
	 * (셀/바 공통) 회원가입시 ID 중복, 공백 체크 (비동기 이용해 처리-ResponseBody 필요)
	 * @return
	 */
	@PostMapping("/confirmId")
	@ResponseBody	// ajax요청이므로
	public boolean confirmId(@RequestParam(name="userId") String userId) {
		log.info("회원 가입하려는 아이디: {}", userId);
		
		// userId가 공백인지 확인
	    if (userId == null || userId.trim().isEmpty()) {
	        log.info("아이디가 공백입니다.");
	        return false; // 공백인 경우 false 반환
	    }
		
		boolean exists = userService.existId(userId);
		log.info("아이디 존재 여부 확인 결과(true:존재) {}", exists);
		
		boolean result = !exists;
		log.info("아이디 중복 확인 결과(true:사용 가능 아이디) {}", result);
		
		return result;		// true : 사용가능
	}
   
   /**
    * 로그인(공통) 화면 요청(security 하면 중복되는 내용)
    * @return
    */
   @GetMapping("/login")
   public String login() {
      
      return "user/login";
   }

   /**
    * 셀러 마이페이지 가기 
    * @return
    */
   @GetMapping("/sellerMypage")
   public String sellerMypage() {
       return "user/sellerMypage"; //
   }
   
   /**
    * 바이어 마이페이지 가기 
    * @return
    */
   @GetMapping("/buyerMypage")
   public String buyerMypage() {
       return "user/buyerMypage"; //
   }
   
   /**
    * 수정 처리 요청
    * @param sellerDTO
    * @param buyerDTO
    * @return
    */
   @PostMapping("/update")
   public String update(
           @ModelAttribute SellerDTO sellerDTO,
           @ModelAttribute BuyerDTO buyerDTO) {

       log.info("===== 셀러 DTO 정보: {}", sellerDTO != null ? sellerDTO.toString() : "셀러 DTO가 null입니다.");
       log.info("===== 바이어 DTO 정보: {}", buyerDTO != null ? buyerDTO.toString() : "바이어 DTO가 null입니다.");

       boolean result = userService.update(sellerDTO, buyerDTO);
       log.info("===== 수정 결과: {}", result);

       if(result) {
           log.info("===== 개인정보 수정 성공 - 로그아웃 처리");
           return "redirect:/user/logout"; // 수정 성공 시 로그아웃
       }

       log.info("===== 개인정보 수정 실패");
       return "redirect:/"; // 수정 실패 시 메인 페이지로 리다이렉트
   }

   
   /**
    * 셀러 마이페이지 들어가기 + 
    * 셀러 마이페이지에 로그인한 회원의 정보를 가져오기
    * @param sellerDTO
    * @param buyerDTO
    * @return
    */
   @GetMapping("/sellerMypage")
   public String sellerMyPage(
		   Model model, @AuthenticationPrincipal LoginSellerDetails userDetails) {
       // 로그인한 판매자의 ID를 가져옵니다.
       String sellerId = userDetails.getUsername();
       // 셀러 아이디를 이용해 셀러의 정보를 DB에서 가져옴
       SellerDTO sellerDTO = userService.getSellerById(sellerId); //서비스에서 가져와야함
       // 모델에 sellerDTO를 추가합니다.
       model.addAttribute("userDTO", sellerDTO); // sellerDTO는 sellerMypage.html 때문에 userDTO라고 칭하게 됨
       
       // 템플릿 이름을 반환합니다.
       return "user/sellerMypage"; // 템플릿 이름
   }
   
   
   /**
    * 마이페이지에 로그인한 회원의 정보를 가져오기
    * @param sellerDTO
    * @param buyerDTO
    * @return
    */
   @GetMapping("/sellerProfileUpdate")
   public String sellerProfileUpdate(
		   Model model, @AuthenticationPrincipal LoginSellerDetails userDetails) {
       // 로그인한 판매자의 ID를 가져옵니다.
       String sellerId = userDetails.getUsername();
       // 셀러 아이디를 이용해 셀러의 정보를 DB에서 가져옴
       SellerDTO sellerDTO = userService.getSellerById(sellerId); //서비스에서 가져와야함
       // 모델에 sellerDTO를 추가합니다.
       model.addAttribute("userDTO", sellerDTO); // sellerDTO는 sellerMypage.html 때문에 userDTO라고 칭하게 됨
       
       // 템플릿 이름을 반환합니다.
       return "user/sellerProfileUpdate"; // 템플릿 이름
   }
// 하나의 경로에 대해 한 컨트롤러 메서드만 매핑되도록 수정하기
//   /**
//    * 셀러 셀러스토어 가기
//    * @return
//    */
//   @GetMapping("/sellerStore")
//   public String sellerStore() {
//       return "user/sellerStore"; //
//   }


   
   

   


}

