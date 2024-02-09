package com.veersablog.BlogApp.services.impl;

import com.veersablog.BlogApp.entities.Comment;
import com.veersablog.BlogApp.entities.Post;
import com.veersablog.BlogApp.entities.User;
import com.veersablog.BlogApp.exceptions.ResourceNotFoundException;
import com.veersablog.BlogApp.payloads.CommentDto;
import com.veersablog.BlogApp.repositories.CommentRepo;
import com.veersablog.BlogApp.repositories.PostRepo;
import com.veersablog.BlogApp.repositories.UserRepo;
import com.veersablog.BlogApp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
   @Autowired
    private UserRepo userRepo;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId , Integer userId) {

        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post" , "postId" , postId));
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user" , "userId" , userId));
        Comment comment = this.modelMapper.map(commentDto , Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        Comment createComment = this.commentRepo.save(comment);
        return this.modelMapper.map(createComment , CommentDto.class);

    }

    @Override
    public void deleteComment(Integer commentId) {

        Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment" , "commentId" , commentId));
        this.commentRepo.delete(comment);
    }


}
