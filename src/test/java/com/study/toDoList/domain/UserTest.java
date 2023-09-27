package com.study.toDoList.domain;

import com.study.toDoList.domain.enums.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("유저가 생성되는지 확인")
    void createUser(){
        //given
        User user = User.builder().email("test@gmail.com").name("test").role(Role.USER).build();
        //when
        //then
        assertThat(user.getEmail()).isEqualTo("test@gmail.com");
        assertThat(user.getName()).isEqualTo("test");
        assertThat(user.getRole()).isEqualTo(Role.USER);
    }

    @Test
    @DisplayName("유저의 이름이 바뀌는 지 확인")
    void updateUser(){
        //given
        User user = User.builder().email("test@gmail.com").name("test").role(Role.USER).build();
        //when
        user.update("test1");
        //then
        assertThat(user.getEmail()).isEqualTo("test@gmail.com");
        assertThat(user.getName()).isEqualTo("test1");
        assertThat(user.getRole()).isEqualTo(Role.USER);
    }

    @Test
    @DisplayName("role이 제대로 출력되는지 확인")
    void printUserRole(){
        //given
        User user = User.builder().email("test@gmail.com").name("test").role(Role.USER).build();
        //when
        String roleKey = user.getRoleKey();
        //then
        assertThat(roleKey).isEqualTo("ROLE_USER");
    }
}