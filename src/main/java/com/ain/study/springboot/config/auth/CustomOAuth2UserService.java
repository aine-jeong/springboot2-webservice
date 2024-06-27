package com.ain.study.springboot.config.auth;

import com.ain.study.springboot.config.auth.dto.OAuthAttributes;
import com.ain.study.springboot.config.auth.dto.SessionUser;
import com.ain.study.springboot.domain.user.User;
import com.ain.study.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    /*
    registrationId : 현재 로그인 진행중인 서비스를 구분하는 코드
    userNameAttributeName : OAuth2 로그인 진행시 키가 되는 필드값. Primary Key와 같은의미
                            구글의 경우 기본적으로 코드를 지원하지만 네이버, 카카오 등은 기본지원하지 않음
    OAuthAttributes : OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
    SessionUser : 세션에 사용자 정보를 저장하기 위한 Dto 클래스
                  세션에 저장하기 위해 직렬화 기능을 가진 세션 Dto를 추가로 생성하는 것
                  (User 클래스는 엔티티이므로 다른 엔티티와의 관계가 형성되는 등 성능 이슈, 부수 효과가 발생할 확률이 높으므로 이용하지 x)
     */

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }


    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}