package com.tkForest.controller;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tkForest.dto.InquiryDTO;
import com.tkForest.dto.LoginUserDetails;
import com.tkForest.service.InquiryService;
import com.tkForest.util.PageNavigator;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {

	final InquiryService inquiryService;
	
	// 한 페이지의 게시글 수
	@Value("${user.inquiry.pageLimit}")
	private int pageLimit;	

	@Value("${spring.servlet.multipart.location}")
	private String uploadPath;	
	
	/**
	 * 게시글 목록 조회를 위해 DB에 요청처리
	 * 1) index에서 넘어올 경우에는 searchItem/searchWord가 없는 상태이므로 기본값 세팅
	 * 2) list에서 직접 입력해서 넘어올 경우 searchItem/searchWord가 있으므로 그 값을 사용
	 * @param model
	 * @return
	 */
	@GetMapping("/inquiryList")
	public String inquiryList(
			@AuthenticationPrincipal LoginUserDetails loginUser
			, @PageableDefault(page=1) Pageable pageable
			, @RequestParam(name="searchItem", defaultValue="subject") String searchItem
			, @RequestParam(name="searchWord", defaultValue="") String searchWord
			, Model model) {

		// 검색기능 + 페이징
		Page<InquiryDTO> list = inquiryService.selectAll(pageable, searchItem, searchWord); 


		int totalPages = list.getTotalPages();	// DB에서 받아온 정보에서 추출
		int page = pageable.getPageNumber();	// 사용자가 요청한 정보에서 추출

		PageNavigator navi = new PageNavigator(pageLimit, page, totalPages);


		model.addAttribute("list", list);
		model.addAttribute("searchItem", searchItem);
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("navi", navi);

		// 인증을 받은 사용자
		if(loginUser != null) {
			model.addAttribute("loginName",loginUser.getUserName());
		}
		return "inquiry/inquiryList";
	}


	/**
	 * 인콰 작성 화면 요청
	 * @return
	 */
	@GetMapping("/inquiryWrite")
	public String buyerMemberNo(
			@AuthenticationPrincipal LoginUserDetails loginUser
			, Model model
			) {
		
		// 인증을 받은 사용자
		if(loginUser != null) {
			model.addAttribute("loginName",loginUser.getUserName());
		}
		return "inquiry/inquiryWrite";
	}

//	// 셀러와 상품 정보를 연결하는 코드 (상품 기능 구현 후 수정하기) & 인콰write에서 수신자 th:value 같이 수정하기
//	/**
//	 * 인콰이어리 작성 화면 요청
//	 * @param productNo 상품 고유 번호
//	 * @param loginUser 현재 로그인한 사용자 정보
//	 * @param model 모델 객체
//	 * @return 인콰이어리 작성 화면
//	 */
//	@GetMapping("/inquiryWrite/{productNo}")
//	public String inquiryWrite(
//	        @PathVariable Integer productNo, 
//	        @AuthenticationPrincipal LoginUserDetails loginUser, 
//	        Model model) {
//
//	    // 인증된 사용자 정보 추가
//	    if (loginUser != null) {
//	        model.addAttribute("loginName", loginUser.getUserName()); // 로그인한 사용자 이름
//	        model.addAttribute("buyerId", loginUser.getUserName()); // 바이어 ID
//	    }
//
//	    // 상품 정보 조회
//	    ProductEntity product = productRepository.findById(productNo)
//	            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
//	    
//	    // 셀러 ID를 모델에 추가
//	    String sellerId = product.getSellerMemberNo(); // 상품의 셀러 ID
//	    model.addAttribute("sellerId", sellerId); // 셀러 ID 추가
//	    model.addAttribute("productNo", productNo); // 상품 고유 번호 추가
//	    model.addAttribute("subject", "No Title"); // 제목 기본값 추가
//
//	    return "inquiry/inquiryWrite";
//	}

	
	/**
	 * DB에 글을 등록 처리하는 요청
	 * 첨부 파일도 포함
	 * @return
	 */
	@PostMapping("/inquiryWrite")
	public String buyerMemberNo(@ModelAttribute InquiryDTO inquiryDTO) {
		log.info("클라이언트에서 전송된 데이터 : {}", inquiryDTO.toString());

		inquiryService.insertInquiry(inquiryDTO);

		return "redirect:/inquiry/inquiryList";
	}

	/**
	 * 글 자세히 보기
	 * 검색 후의 정보를 전달받도록 함 
	 * @param inquiryNo
	 * @param model
	 * @return
	 */
	@GetMapping("/inquiryDetail")
	public String InquiryDetail(
			@AuthenticationPrincipal LoginUserDetails loginUser
			, @RequestParam(name="inquiryNo") Integer inquiryNo
			, @RequestParam(name="searchItem", defaultValue="subject") String searchItem
			, @RequestParam(name="searchWord", defaultValue="") String searchWord
			, Model model) {

		InquiryDTO inquiry = inquiryService.selectOne(inquiryNo);
		System.out.println(inquiryNo);

		if(inquiry == null) {
			return "redirect:/inquiry/inquiryList"; 
		}

		model.addAttribute("inquiry", inquiry);
		// 검색 기능이 추가되면 계속 달고 다녀야 함
		model.addAttribute("searchItem", searchItem);
		model.addAttribute("searchWord", searchWord);

		// 로그인이 된 사용자의 경우 로그인 아이디를 가져감
		if(loginUser != null) {
			model.addAttribute("loginName", loginUser.getUserName());  // 실명
		}

		return "inquiry/inquiryDetail"; 
	}

	/**
	 * 전달받은 글번호(inquiryNo)을 받아 service로 전달
	 * @param inquiryNo
	 * @return
	 */
	@GetMapping("/inquiryDelete")
	public String inquiryDelete(
			@RequestParam(name="inquiryNo") Integer inquiryNo
			, @RequestParam(name="searchItem", defaultValue="subject") String searchItem
			, @RequestParam(name="searchWord", defaultValue="") String searchWord
			, RedirectAttributes rttr
			) {

		inquiryService.deleteOne(inquiryNo);

		rttr.addAttribute("searchItem", searchItem);
		rttr.addAttribute("searchWord", searchWord);

		return "redirect:/inquiry/inquiryList";
	}

	
	/**
	 * 전달받은 게시글 번호에 파일을 다운로드
	 * @return
	 */
	@GetMapping("/download")
	public String download(
			@RequestParam(name="inquiryNo") Integer inquiryNo
			, HttpServletResponse response
			) {

		InquiryDTO inquiryDTO = inquiryService.selectOne(inquiryNo);

		String originalFileName= inquiryDTO.getOriginalFileName(); 
		String savedFileName= inquiryDTO.getSavedFileName(); 

		log.info("원본 파일명 : {}", originalFileName);
		log.info("저장 파일명 : {}", savedFileName);
		log.info("저장 디렉토리 : {}", uploadPath);

		try {
			String tempName = URLEncoder.encode(
					originalFileName, 
					StandardCharsets.UTF_8.toString());

			response.setHeader("Content-Disposition", "attachment;filename="+tempName);

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		String fullPath = uploadPath + "/" + savedFileName;

		FileInputStream filein = null;
		ServletOutputStream fileout = null;

		try {
			filein = new FileInputStream(fullPath);
			fileout = response.getOutputStream();

			FileCopyUtils.copy(filein, fileout);

			fileout.close();
			filein.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
}
