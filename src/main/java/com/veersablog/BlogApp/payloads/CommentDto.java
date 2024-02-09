package com.veersablog.BlogApp.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private int id;
    private String content;
    private int postId;
    private int userId;
}
