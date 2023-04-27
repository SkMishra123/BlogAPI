package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "POSTDto  model information")
public class PostDto {
    private long id;
    @Schema(description = "Blog post title")
    @NotEmpty
    @Size(min=2, message= "Post title should have at least 2 characters")
    private String title;
    @Schema(description = "Blog post description")
    @NotEmpty
    @Size(min=10, message= "Description title should have at least 10 characters" )
    private String description;
    @Schema(description = "Blog post content")
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
    @Schema(description = "Blog post category")
    private Long categoryId;
}
