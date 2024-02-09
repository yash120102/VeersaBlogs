package com.veersablog.BlogApp.services.impl;

import com.veersablog.BlogApp.entities.Category;
import com.veersablog.BlogApp.entities.Post;
import com.veersablog.BlogApp.entities.User;
import com.veersablog.BlogApp.exceptions.ResourceNotFoundException;
import com.veersablog.BlogApp.payloads.PostDto;
import com.veersablog.BlogApp.payloads.PostResponse;
import com.veersablog.BlogApp.repositories.CategoryRepo;
import com.veersablog.BlogApp.repositories.PostRepo;
import com.veersablog.BlogApp.repositories.UserRepo;
import com.veersablog.BlogApp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User" , "userId" , userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category" , "categoryId" , categoryId));
        Post post = this.postDtoToPost(postDto);
        post.setImageName("default.png");
        post.setCategory(category);
        post.setUser(user);
        post.setAddedDate(new Date());
        Post savedPost = this.postRepo.save(post);
        return this.postToPostDto(savedPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post" , "postId" , postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());


        Post updatedPost = this.postRepo.save(post);
        return this.postToPostDto(updatedPost);
    }

    @Override
    public PostDto getPost(Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post" , "postId" , postId));
        return this.postToPostDto(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber , Integer pageSize , String sortBy , String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber , pageSize , sort);
        Page<Post> posts = this.postRepo.findAll(p);

        List<Post> allPost = posts.getContent();

        List<PostDto> allPostDtos = allPost.stream().map(this::postToPostDto).toList();

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(allPostDtos);
        postResponse.setLastPage(posts.isLast());
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());

        return postResponse;
    }

    @Override
    public void deletePost(Integer postId) {
       Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post" , "postId" , postId));
       this.postRepo.delete(post);
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User" , "User" , userId));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream().map(this::postToPostDto).toList();
        return postDtos;
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category" , "categoryId" , categoryId));
        List<Post> posts = this.postRepo.findByCategory(category);

        List<PostDto> allPostDtos = posts.stream().map((post)-> this.postToPostDto(post)).collect(Collectors.toList());
        return allPostDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keywords) {
     List<Post> posts = this.postRepo.findByTitleContaining(keywords);
      List<PostDto> postDtos = posts.stream().map((post)-> this.postToPostDto(post)).collect(Collectors.toList());
      return postDtos;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post" , "postId" , postId));
        return postToPostDto(post);
    }

    public Post postDtoToPost(PostDto postDto) {
        Post post = this.modelMapper.map(postDto , Post.class);
        return post;
    }
    public PostDto postToPostDto(Post post) {
        PostDto postDto = this.modelMapper.map(post , PostDto.class);
        return postDto;
    }
}
