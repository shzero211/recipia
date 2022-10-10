package com.ll.exam.RecipiaProject.post;

import com.ll.exam.RecipiaProject.comment.entity.Comment;
import com.ll.exam.RecipiaProject.post.postImg.PostImgDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailDto {

    private int id;

    private String title;

    private String content;

    private int score;

    private int views ;

    private int likes;

    private String username;

    private List<String> hashTagContentList;

    private List<PostImgDto>postImgDtoList=new ArrayList<>();

    private List<Comment> commentList=new ArrayList<>();
}
