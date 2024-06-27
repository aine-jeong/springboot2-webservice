package com.ain.study.springboot.config.auth;

import com.ain.study.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /*
    @EnableWebSecurity : Spring Security 설정들을 활성화시켜준다.
    .csrf().disable().headers().frameOptions().disable() : h2-console 화면을 사용하기 위해 해당 옵션들을 disable 한다.
    authorizeRequest : URL별 권한 관리를 설정하는 옵션의 시작점. authorizeRequest가 선언되어야만 antMatchers 옵션 사용 가능
    antMatchers : 권한 관리 대상을 지정하는 옵션. URL, HTTP 메소드별로 관리 가능
                    "/" 등 지정된 URL 들은 permitAll() 옵션을 통해 전체 열람 권한을 부여함
                    "/api/v1/**" 주소를 가진 API 는 USER 권한을 가진 사람만 열람 가능하도록 권한을 부여함.
    anyRequest : 설정된 값 이외 나머지 URL
                 여기서는 authenticated()을 추가하여 나머지 URL들은 모두 인증된 사용자(로그인 유저)들에게만 허용하도록 함.
    logout().logoutSuccessUrl("/") : 로그아웃 기능에 대한 여러 설정의 진입점. 로그아웃 성공시 / 주소로 이동한다.
    oauth2Login : OAuth2 로그인 기능에 대한 여러 설정의 진입점
    userInfoEndpoint : OAuth 2 로그인 성공 이후 사용자 정보를 가져올 떄의 설정 담당
    userService : 소셜 로그인 성공시 후속 조지를 시행할 UserService 인터페이스의 구현체를 등록
                  리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시 가능
     */

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}