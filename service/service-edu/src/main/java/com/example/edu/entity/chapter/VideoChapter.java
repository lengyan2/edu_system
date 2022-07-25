package com.example.edu.entity.chapter;

import lombok.Data;

@Data
public class VideoChapter {
    private String id;
    private String title;
    private String videoSourceId;
    private Integer isFree;
}
