package kr.soft.campus.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Board {
    private int idx;                   // 게시판 idx
    private String title;               // 제목
    private String content;             // 내용
    private String deleteYn = "N";      // 삭제 여부
    private int good;                   // 좋아요
    private LocalDateTime created;      // 생성 날짜
    private LocalDateTime modified;     // 수정 날짜
    private String createdBy;           // 생성자
    private String modifiedBy;          // 수정자
}