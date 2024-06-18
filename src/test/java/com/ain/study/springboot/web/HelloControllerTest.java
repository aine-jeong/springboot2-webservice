package com.ain.study.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    /*
    RunWith(SpringRunner.class)
        - 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킴
        - 여기서는 SpringRunner라는 스프링 실행자를 사용함 -> 스프링 부트 테스트와 JUnit사이에 연결자 역할

    WebMvcTest
        - 여러 스프링 테스트 어노테이션 중, Web에 집중할 수 있는 어노테이션

    MockMvc
        - 웹 API 테스트시 사용하며 스프링 MVC 테스트의 시작점
        - 이 클래스로 HTTP GET, POST등에 대한 API 테스트 가능

    mvc.perform(get"/hello")) : MockMvc를 통해 /hello 주소로 HTTP GET dycjd
    .andExpect(staatus().isOk()) : mvc.perform의 결과 검증. HTTP Header의 Status를 검증한다.
    .andExpect(content().string(hello)) : mvc.perform의 결과 검증. 응답 본문의 내용을 검증한다.
     */

    @Autowired
    private MockMvc mvc;

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                        get("/hello/dto")
                                .param("name", name)
                                .param("amount", String.valueOf(amount))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
