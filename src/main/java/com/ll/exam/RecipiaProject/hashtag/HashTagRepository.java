package com.ll.exam.RecipiaProject.hashtag;

import com.ll.exam.RecipiaProject.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface HashTagRepository extends JpaRepository<HashTag, Integer>, BaseRepository {
    HashTag findByTagContent(String tagContent);
    @Query(value = "truncate table hash_tag",nativeQuery = true)
    @Modifying
    @Transactional
    void truncate();

}
