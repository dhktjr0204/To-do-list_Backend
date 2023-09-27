package com.study.toDoList.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ContentTest {
    @Test
    @DisplayName("content 잘 생성되는지 확인")
    void createContent(){
        //given
        Content content = Content.builder().content("test").status('N').schedule_id(1L).build();
        //when,then
        assertThat(content.getContent()).isEqualTo("test");
        assertThat(content.getStatus()).isEqualTo('N');
        assertThat(content.getSchedule_id()).isEqualTo(1L);
    }

    @Test
    @DisplayName("status 업데이트 확인")
    void updateContent(){
        //given
        Content content = Content.builder().content("test").status('N').schedule_id(1L).build();
        //when
        content.update('Y');
        // then
        assertThat(content.getContent()).isEqualTo("test");
        assertThat(content.getStatus()).isEqualTo('Y');
        assertThat(content.getSchedule_id()).isEqualTo(1L);
    }

}