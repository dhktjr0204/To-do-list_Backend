package com.study.toDoList.config.oauth.dto;

import com.study.toDoList.domain.User;
import com.study.toDoList.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class LoginApiAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String provider;

    public static LoginApiAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(registrationId,userNameAttributeName, attributes);
    }

    private static LoginApiAttributes ofGoogle(String registrationId,String userNameAttributeName, Map<String, Object> attributes) {
        return LoginApiAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .provider((String) registrationId)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
//User 엔티티 생성 OAuthAttribute에서 엔티티를 생성하는 시점은 처음 가입할 때
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .build();
    }
}
