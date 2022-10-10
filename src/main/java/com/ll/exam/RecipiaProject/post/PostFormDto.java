package com.ll.exam.RecipiaProject.post;

import com.ll.exam.RecipiaProject.post.postImg.PostImgDto;
import com.ll.exam.RecipiaProject.user.SiteUser;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostFormDto {
    private int id;

    private String title;

    private String content;

    private String tagContent;


//작성폼 수정시 사용
private List<PostImgDto> postImgDtoList=new ArrayList<>();

private List<String> postImgDtoIds=new ArrayList<>();
    public PostFormDto(int id,String title,String content){
        this.id=id;
        this.title=title;
        this.content=content;
    }

    public Post createPost(SiteUser siteUser) {
        return Post.builder()
                .title(title)
                .content(content.replace("\r\n","<br/>"))
                .siteUser(siteUser)
                .build();
    }


}
