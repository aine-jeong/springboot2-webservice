package com.ain.study.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloResponseDto {

    /*
    @RequiredArgsConstructor : 선언된 모든 필드의 get 메소드 생성, final이 없는 필드는 생성자에 포함되지 않음
     */

    private final String name;
    private final int amount;
}
