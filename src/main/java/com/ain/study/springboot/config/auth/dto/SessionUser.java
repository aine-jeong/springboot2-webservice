package com.ain.study.springboot.config.auth.dto;

import com.ain.study.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    /*
    SessionUser : 세션에 사용자 정보를 저장하기 위한 Dto 클래스
                  세션에 저장하기 위해 직렬화 기능을 가진 세션 Dto를 추가로 생성하는 것
                  (User 클래스는 엔티티이므로 다른 엔티티와의 관계가 형성되는 등 성능 이슈, 부수 효과가 발생할 확률이 높으므로 이용하지 x)
     */

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

}
