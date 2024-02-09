package com.veersablog.BlogApp.services;

import com.veersablog.BlogApp.payloads.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto , Integer postId , Integer userId);
    void deleteComment(Integer commentId);
}
