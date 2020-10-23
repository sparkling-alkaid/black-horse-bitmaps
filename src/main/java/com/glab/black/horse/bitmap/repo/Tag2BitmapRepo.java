package com.glab.black.horse.bitmap.repo;


import com.glab.black.horse.bitmap.pojo.entity.Tag2Bitmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Tag2BitmapRepo extends JpaRepository<Tag2Bitmap, Tag2Bitmap.Tag2BitmapKey> {

}
