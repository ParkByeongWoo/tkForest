package com.tkForest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class chartTestController {
	@GetMapping("/chart/chartTest")
    public String showChartTest() {
        return "chart/chartTest"; // src/main/resources/templates/chart/chartTest.html 파일을 반환
    }
}
