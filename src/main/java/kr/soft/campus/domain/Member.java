package kr.soft.campus.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Data

public class Member {
    private int idx;
    private String userId;
    private String name;
    private String userPw;
    private String email;
    private LocalDateTime createdAt;


}