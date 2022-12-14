package com.ll.exam.RecipiaProject.post.postImg;

import com.ll.exam.RecipiaProject.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class PostImgService {
    @Value("${postImgLocation}")
    String postImgLocation;
    private final PostImgRepository postImgRepository;
    private final PostImgFileService postImgFileService;
    public void createPostImg(PostImg postImg, MultipartFile file) {
        String originImgName=file.getOriginalFilename();
        String imgName="";
        String imgUrl="";
        if(!StringUtils.isEmpty(originImgName)){
            //이미지 파일 저장
            try {
                imgName=postImgFileService.uploadFile(originImgName,file,postImgLocation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            imgUrl="/images/img/"+imgName;
        }
        //DB에 이미지 정보 저장
        postImg.updatePostImg(originImgName,imgName,imgUrl);
        postImgRepository.save(postImg);
    }

    public void deletePostImgList(Post post) {
        List<PostImg> postImgList= post.getPostImgList();
        for(int i=0;i<postImgList.size();i++){
            PostImg postImg=postImgList.get(i);
            postImgFileService.deleteFile(postImgLocation+"/"+postImg.getImgName());
            postImgList.remove(postImg);
            postImgRepository.deleteById(postImg.getId());
        }
    }

    public void deletePostImg(int postImgId) {
        postImgRepository.deleteById(postImgId);
    }
}