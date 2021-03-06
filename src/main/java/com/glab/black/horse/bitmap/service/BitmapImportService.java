package com.glab.black.horse.bitmap.service;


import com.glab.black.horse.bitmap.pojo.entity.Tag2Bitmap;
import com.glab.black.horse.bitmap.pojo.model.NumberBitmapGroup;
import com.glab.black.horse.bitmap.pojo.model.Tag2RoaringBitmap;
import com.glab.black.horse.bitmap.repo.Tag2BitmapRepo;
import com.glab.black.horse.bitmap.utils.BitmapUtils;
import org.roaringbitmap.RoaringBitmap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@EnableCaching
public class BitmapImportService {


    @Autowired
    private Tag2BitmapRepo tag2BitmapRepo;


    public void importTag2Bitmap(List<Tag2RoaringBitmap> roaringBitmaps) {

        if (CollectionUtils.isEmpty(roaringBitmaps)) {
            return;
        }
        List<Tag2Bitmap> list = roaringBitmaps.stream().map(s -> new Tag2Bitmap(s)).collect(Collectors.toList());

        tag2BitmapRepo.saveAll(list);

    }

    @Cacheable(value = "bitmapGroup", key = "#tagId")
    public NumberBitmapGroup getBitmapGroupById(String tagId) {
        // 之后可以先获取meta，再返回合适的bitmap，现在单纯返回int的bitmap
        Tag2Bitmap example = new Tag2Bitmap();
        example.setTagId(tagId);
        List<Tag2Bitmap> tag2Bitmaps = tag2BitmapRepo.findAll(Example.of(example));
        RoaringBitmap notNullBitmap = null;
        Map<Integer, RoaringBitmap> bitmapMap = new HashMap<>();
        for (Tag2Bitmap tb : tag2Bitmaps) {
            if (tb.getBitIndex() < 0) {
                notNullBitmap = BitmapUtils.deserialize(tb.getBitmap());
            } else {
                bitmapMap.put(tb.getBitIndex(), BitmapUtils.deserialize(tb.getBitmap()));
            }
        }
        return new NumberBitmapGroup(tagId, notNullBitmap, bitmapMap);
    }


}
