package com.tkForest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tkForest.dto.SellerDTO;
import com.tkForest.service.SellerstoreService;

@Controller
@RequestMapping("/user")
public class SellerstoreController {

    @Autowired
    private SellerstoreService sellerstoreService;

    // 기본 셀러 스토어 페이지(sellerMemberNo가 없는 기본 페이지 접근)
    @GetMapping("/sellerStore")
    public String showDefaultSellerStore(Model model) {
        // 필요시 기본 셀러 데이터를 설정
    	model.addAttribute("message", "No seller selected");
        return "user/sellerStore";
    }

//    // 특정 셀러 페이지
//    @GetMapping("/sellerStore/{sellerMemberNo}")
//    public String getSellerStore(@PathVariable String sellerMemberNo, Model model) {
//        SellerDTO sellerDTO = sellerstoreService.getSellerByMemberNo(sellerMemberNo);
//        model.addAttribute("seller", sellerDTO);
//        return "user/sellerStore";
//    }
}

