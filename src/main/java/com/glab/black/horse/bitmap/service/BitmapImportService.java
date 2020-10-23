package com.glab.black.horse.bitmap.service;


import com.glab.black.horse.bitmap.pojo.entity.Tag2Bitmap;
import com.glab.black.horse.bitmap.pojo.model.NumberBitmapGroup;
import com.glab.black.horse.bitmap.pojo.model.Tag2RoaringBitmap;
import com.glab.black.horse.bitmap.repo.Tag2BitmapRepo;
import org.roaringbitmap.RoaringBitmap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public NumberBitmapGroup getBitmapGroupById(String tagId){
        // 之后可以先获取meta，再返回合适的bitmap，现在单纯返回int的bitmap
        Tag2Bitmap example = new Tag2Bitmap();
        example.setTagId(tagId);
        List<Tag2Bitmap> tag2Bitmaps = tag2BitmapRepo.findAll(Example.of(example));
        Map<Integer, RoaringBitmap> bitmapMap = new HashMap<>();
        return null;

    }



}
