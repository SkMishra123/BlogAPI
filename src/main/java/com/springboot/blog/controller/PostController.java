package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "CRUD REST APIs for POST Resource")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @Operation(
            summary = "Create POST REST API",
            description =  "Create POST REST API is used to save post in database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize(("hasRole('ADMIN')"))
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @Operation(
            summary = "GetAll POST REST API",
            description =  "GetAll POST REST API is used to retrieve all the posts from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping
    public PostResponse getAllPosts(@RequestParam (value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
                                    @RequestParam (value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                    @RequestParam (value = "sortBy", defaultValue = AppConstants.DEFAULT_SORTBY, required = false) String sortBy,
                                    @RequestParam (value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir){
        return postService.getAllposts(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get POST REST API",
            description =  "Get POST REST API is used to retrieve a single post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize(("hasRole('ADMIN')"))
    @PutMapping("/{id}")
    @Operation(
            summary = "Update POST REST API",
            description =  "Update POST REST API is used to update a particular post in database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("id") long id){
        return ResponseEntity.ok(postService.updatePost(postDto, id));
    }
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize(("hasRole('ADMIN')"))
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete POST REST API",
            description =  "Delete POST REST API is used to delete a single post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePost(id);
        return ResponseEntity.ok("Post is deleted successfully");
     }
     @GetMapping("/category/{id}")
     public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId){
        return ResponseEntity.ok(postService.getPostsByCategory(categoryId));
     }
}
