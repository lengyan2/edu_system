package com.example.edu.entity.comment;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class oneComment {
    private String id;
    private String nickname;
    private String avatar;
    private String content;
    private List<twoComment> list = new ArrayList<>();
}
