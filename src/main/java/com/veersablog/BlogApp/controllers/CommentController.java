package com.veersablog.BlogApp.controllers;

import com.veersablog.BlogApp.payloads.ApiResponse;
import com.veersablog.BlogApp.payloads.CommentDto;
import com.veersablog.BlogApp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @PostMapping("/user/{userId}/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto , @PathVariable Integer postId , @PathVariable Integer userId) {
        CommentDto commentDto1 = this.commentService.createComment(commentDto , postId , userId);
        return new ResponseEntity<CommentDto>(commentDto1 , HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {

        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("comment deleted successfully" , true) , HttpStatus.OK);
    }
}
