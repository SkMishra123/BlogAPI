package com.springboot.blog.service;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto  createPost(PostDto postDto);
    PostResponse getAllposts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(long id);
    PostDto  updatePost(PostDto postDto, long id);
    void deletePost(long id);
    List<PostDto> getPostsByCategory(Long categoryId);
}
