package com.veersablog.BlogApp.controllers;

import com.veersablog.BlogApp.config.AppConstants;
import com.veersablog.BlogApp.payloads.ApiResponse;
import com.veersablog.BlogApp.payloads.PostDto;
import com.veersablog.BlogApp.payloads.PostResponse;
import com.veersablog.BlogApp.services.FileService;
import com.veersablog.BlogApp.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/* This is a PostController*/
@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;


    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto
            , @PathVariable Integer userId , @PathVariable Integer categoryId) {

        PostDto createPost = this.postService.createPost(postDto , userId , categoryId);
        return new ResponseEntity<PostDto>(createPost , HttpStatus.CREATED);
    }
     @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto , @PathVariable Integer postId) {
        PostDto updatedPost = this.postService.updatePost(postDto , postId);
        return new ResponseEntity<PostDto>(updatedPost , HttpStatus.OK);
    }

    @GetMapping("posts/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Integer postId) {
        PostDto getPost = this.postService.getPost(postId);
        return new ResponseEntity<PostDto>(getPost , HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
           @RequestParam (value = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber ,
           @RequestParam (value = "pageSize" , defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize ,
           @RequestParam (value = "sortBy" , defaultValue = AppConstants.SORTBY , required = false) String sortBy ,
           @RequestParam(value = "sortDir" , defaultValue = AppConstants.SORTDIR , required = false) String sortDir
           ) {
        PostResponse allPosts = this.postService.getAllPost(pageNumber , pageSize , sortBy , sortDir);
        return new ResponseEntity<PostResponse>(allPosts , HttpStatus.OK);
    }

    @DeleteMapping("posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("post deleted successfully" , true) , HttpStatus.OK );
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
      List<PostDto> postDtos = this.postService.getPostByUser(userId);
      return new ResponseEntity<List<PostDto>>(postDtos , HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
        List<PostDto> postDtos = this.postService.getPostByUser(categoryId);
        return new ResponseEntity<List<PostDto>>(postDtos , HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> getPostsBySearch(@PathVariable String keywords) {
        List<PostDto> postDtos = this.postService.searchPosts(keywords);
        return new ResponseEntity<List<PostDto>>(postDtos , HttpStatus.OK);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image")MultipartFile image ,
            @PathVariable Integer postId
            ) throws IOException {
        PostDto postDto = this.postService.getPostById(postId);

        String fileName = this.fileService.uploadImage(path , image);
        postDto.setImageName(fileName);
       PostDto updatePost = this.postService.updatePost(postDto , postId);
       return new ResponseEntity<PostDto>(updatePost , HttpStatus.OK);

    }

    @GetMapping(value = "/post/image/{imageName}" , produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName ,
            HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path , imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource , response.getOutputStream());

    }



}
