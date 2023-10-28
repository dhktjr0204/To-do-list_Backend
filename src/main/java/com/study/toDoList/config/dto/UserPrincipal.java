package com.study.toDoList.config.dto;

import com.study.toDoList.domain.User;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class UserPrincipal implements OAuth2User {
    private User user;
    private Map<String, Object> attributes;

    private UserPrincipal(User member) {
        this.user = member;
    }

    public static UserPrincipal from(User user) {
        return new UserPrincipal(user);
    }

    public static UserPrincipal of(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.from(user);
        userPrincipal.attributes = attributes;
        return userPrincipal;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRoleKey()));
    }

    @Override
    public String getName() {
        return user.getName();
    }


    public String getEmail() {
        return user.getEmail();
    }

}