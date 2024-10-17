package com.tkForest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tkForest.dto.SellerDTO;
import com.tkForest.service.SellerstoreService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/user")
public class SellerstoreController {

    @Autowired
    private SellerstoreService sellerstoreService;

//    // 기본 셀러 스토어 페이지(sellerMemberNo가 없는 기본 페이지 접근)
//    @GetMapping("/sellerStore")
//    public String showDefaultSellerStore(Model model) {
//        model.addAttribute("message", "No seller selected");
//        return "user/sellerStore";
//    }

    // 특정 셀러 페이지 (sellerMemberNo가 'S99'인 셀러 정보를 표시)
    @GetMapping("/sellerStore")
    public String getSellerStore(Model model) {
    	
//        log.info("컨트롤러 도착");
    	
        String sellerMemberNo = "S250134"; // 고정된 sellerMemberNo
        SellerDTO sellerDTO = sellerstoreService.getSellerByMemberNo(sellerMemberNo);
//        log.info("sellerDTO 조회");
        
        model.addAttribute("seller", sellerDTO);
//        log.info("sellerDTO 모델에 담음");
        
        return "user/sellerStore"; // 페이지 이름은 동일
    }
}


