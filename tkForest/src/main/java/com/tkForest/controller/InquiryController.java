//package com.tkForest.controller;
//
//import java.io.FileInputStream;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.FileCopyUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.tkForest.dto.InquiryDTO;
//import com.tkForest.dto.LoginBuyerDetails;
//import com.tkForest.dto.LoginSellerDetails;
//import com.tkForest.service.InquiryService;
//import com.tkForest.util.PageNavigator;
//
//import jakarta.servlet.ServletOutputStream;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Controller
//@RequestMapping("/inquiry")
//@RequiredArgsConstructor
//public class InquiryController {
//
//    final InquiryService inquiryService;
//
//    // 한 페이지의 게시글 수
//    @Value("${user.inquiry.pageLimit}")
//    private int pageLimit;
//
//    @Value("${spring.servlet.multipart.location}")
//    private String uploadPath;
//
//    /**
//     * 게시글 목록 조회를 위해 DB에 요청처리
//     * @param model
//     * @return
//     */
//    @GetMapping("/inquiryList")
//    public String inquiryList(
////            우선적으로 로그인 인증 정보를 안담고 연결만 하기 위해 주석처리
////            @AuthenticationPrincipal UserDetails loginUser,
////            @PageableDefault(page=1) Pageable pageable,
////            @RequestParam(name="searchItem", defaultValue="subject") String searchItem,
////            @RequestParam(name="searchWord", defaultValue="") String searchWord,
////            Model model
//            ) {
//
////        // 인증되지 않은 사용자는 접근 불가
////        if (!(loginUser instanceof LoginSellerDetails) && !(loginUser instanceof LoginBuyerDetails)) {
////            return "redirect:/user/login";  // 로그인 페이지로 리다이렉트
//        }
