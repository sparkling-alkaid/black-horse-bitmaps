package com.glab.black.horse.bitmap.service;


import com.glab.black.horse.bitmap.pojo.entity.Tag2Bitmap;
import com.glab.black.horse.bitmap.pojo.model.Tag2RoaringBitmap;
import com.glab.black.horse.bitmap.repo.Tag2BitmapRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BitmapImportService {


    @Autowired
    private Tag2BitmapRepo tag2BitmapRepo;


    public void importTag2Bitmap(List<Tag2RoaringBitmap> roaringBitmaps){

        if(CollectionUtils.isEmpty(roaringBitmaps)){
            return;
        }

        List<Tag2Bitmap> list = roaringBitmaps.stream().map( s -> new Tag2Bitmap(s) ).collect(Collectors.toList());

        tag2BitmapRepo.saveAll(list);

    }



}
