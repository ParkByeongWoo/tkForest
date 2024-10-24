package com.tkForest.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(GlobalMapController.class)
public class GlobalMapControllerTest {

    @Autowired
    private MockMvc mvc;

//    @Test
//    @WithMockUser
//    @DisplayName("trend API 기본 요청 테스트")
    public void testCalltrendApi() throws Exception {
        mvc.perform(get("/api/trend")
                .param("numOfRows", "3")
                .param("pageNo", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }



    @Test
    @WithMockUser
    @DisplayName("trend API 특정 파라미터 요청 테스트")
    public void testCalltrendApiWithSpecificParams() throws Exception {
        mvc.perform(get("/api/trend")
                .param("numOfRows", "3")
                .param("pageNo", "1")
                .param("search1", "")
                .param("search2", "")
                .param("search4", "")
                .param("search5", "I001203"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
