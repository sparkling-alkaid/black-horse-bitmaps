package com.glab.black.horse.bitmap.repo;


import com.glab.black.horse.bitmap.pojo.entity.TagMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagMetaRepo extends JpaRepository<TagMeta, String> {

}
