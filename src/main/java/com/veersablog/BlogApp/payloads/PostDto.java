package com.veersablog.BlogApp.payloads;

import com.veersablog.BlogApp.entities.Category;
import com.veersablog.BlogApp.entities.Comment;
import com.veersablog.BlogApp.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private Integer postId;
    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto> comments = new HashSet<>();

}
