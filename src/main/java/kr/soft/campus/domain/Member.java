package kr.soft.campus.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Member {
    private int idx;
    private String id;
    private String name;
    private String password;
    private String email;
    private LocalDateTime createdAt;


}