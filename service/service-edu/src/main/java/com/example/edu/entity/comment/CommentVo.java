package com.example.edu.entity.comment;

import lombok.Data;

@Data
public class CommentVo {
    private String id;
    private String courseId;
    private String teacherId;
    private String content;
    private String memberId;
    private String avatar;
    private String nickname;
    private String parentId;
}
