package com.veersablog.BlogApp.services;

import com.veersablog.BlogApp.entities.Post;
import com.veersablog.BlogApp.payloads.PostDto;
import com.veersablog.BlogApp.payloads.PostResponse;

import java.util.List;

public interface PostService {

       PostDto createPost(PostDto postDto , Integer userId , Integer categoryId);

       PostDto updatePost(PostDto postDto , Integer postId);

       PostDto getPost(Integer postId);

       PostResponse getAllPost(Integer pageNumber , Integer pageSize , String sortBy , String sortDir);

       void deletePost(Integer postId);

       List<PostDto> getPostByUser(Integer userId);

       List<PostDto> getPostByCategory(Integer categoryId);

      List<PostDto> searchPosts(String keywords);

       PostDto getPostById(Integer postId);
}
