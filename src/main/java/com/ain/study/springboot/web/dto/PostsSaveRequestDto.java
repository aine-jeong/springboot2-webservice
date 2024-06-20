package com.ain.study.springboot.web.dto;

import com.ain.study.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author).build();
    }

    /*
    Entity 클래스와 거의 유사한 형태지만 Entity 클래스를 Request/Response 클래스로 사용해서는 안된다.
    Entity 클래스는 데이터베이스와 맞닿은 핵심 클래스이며, 수많은 서비스 클래스나 비즈니스 로직들이 Entity 클래스를 기준으로 동작한다.
    Entity 클래스가 변경되면 여러 클래스에 영향을 끼치지만, Request와 Response용 Dto는 View를 위한 클래스라 자주 변경이 필요하다.
    View Layer와 DB Layer의 역할 분리를 철저하게 해야한다.
    또한 Controller에서 결괏값으로 여러 테이블을 조인해서 줘야 할 경우가 빈번하므로 Entity 클래스만으로 표현하기 어려운 경우도 많다.
     */
}
