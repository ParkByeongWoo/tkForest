package com.tkForest.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class MainController {

    /**
     * 첫 화면 요청
     * @return
     */
    @GetMapping({"/", ""})
    public String index(Model model) {
        return "index";
    }
    
   /**
    * 어바웃 어스
    * @return
    */
   @GetMapping("/aboutUs")
   public String aboutUs() {
       return "aboutUs";  
   }
   
   /**
    * 보따리 추천
    * @return
    */
   @GetMapping("/recList")
   public String recList() {
       return "rec/recList";  
   }

}