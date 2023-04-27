package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFound;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper mapper;
    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public PostDto createPost(PostDto postDto){
        Post post = dtoToENTITY(postDto);
        Category category = categoryRepository.findById(postDto.getCategoryId()).
                orElseThrow(()-> new ResourceNotFound("category","id",postDto.getCategoryId()));
        post.setCategory(category);
        Post newpost = postRepository.save(post);
        PostDto postResponse = mapToDTO(newpost);

        return postResponse;
    }
    @Override
    public PostResponse getAllposts(int pageNo, int pageSize, String sortBy, String sortDir){

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll((org.springframework.data.domain.Pageable) pageable);
        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content = posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post;
        post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post;
        Category category = categoryRepository.findById(postDto.getCategoryId()).
                orElseThrow(()-> new ResourceNotFound("category","id",postDto.getCategoryId()));
        post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Post", "id", id));
        post.setCategory(category);
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post;
        post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Post", "id", id));
        postRepository.deleteById(id);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(()-> new ResourceNotFound("category","id",categoryId));

        List<Post> posts = postRepository.findByCategoryId(categoryId);

        return posts.stream().map((post -> mapToDTO(post))).collect(Collectors.toList());
    }

    private PostDto mapToDTO (Post post){
        PostDto postDto = mapper.map(post, PostDto.class);
        return postDto;
    }

    private Post dtoToENTITY (PostDto postdto){
        Post post = mapper.map(postdto, Post.class);
        return post;
    }
}
