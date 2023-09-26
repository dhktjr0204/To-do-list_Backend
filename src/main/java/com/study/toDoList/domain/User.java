package com.study.toDoList.domain;

import com.study.toDoList.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(long id, String name, String email,Role role){
        this.id=id;
        this.name=name;
        this.email=email;
        this.role=role;
    }

    public User update(String name){
        this.name=name;
        return this;
    }

    public String getRoleKey(){return this.role.getKey();}
}
