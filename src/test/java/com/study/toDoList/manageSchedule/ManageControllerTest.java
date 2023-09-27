package com.study.toDoList.manageSchedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.toDoList.domain.Content;
import com.study.toDoList.manageSchedule.dto.CreateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class ManageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ManageController manageController;
    @MockBean
    private ManageService manageService;


    @Test
    @DisplayName("스케줄 등록 성공")
    public void createScheduleTest() throws Exception{
        //given
        List<String> content= Arrays.asList("테스트1","테스트2");
        CreateRequestDto dto = CreateRequestDto.builder()
                .email("dhktjr0204@naver.com")
                .contents(content)
                .type("day")
                .day("")
                .closingDate("").build();

        //when, then
        mockMvc.perform(post("/api/create")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print());
    }
    //스프링 시큐리티 아예 비활성화하는 설정
    @Configuration
    @EnableWebSecurity
    public static class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/**").permitAll()
                    .and()
                    .csrf().disable();
        }
    }
}