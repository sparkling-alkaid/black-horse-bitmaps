package com.glab.black.horse.bitmap.pojo.entity;


import com.glab.black.horse.bitmap.pojo.model.Tag2RoaringBitmap;
import com.glab.black.horse.bitmap.utils.BitmapUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@IdClass(Tag2Bitmap.Tag2BitmapKey.class)
@Table(name = "tag2bimap")
public class Tag2Bitmap {

    @Id
    private String tagId;
    @Id
    private Integer bitIndex;

    private byte[] bitmap;


    public Tag2Bitmap(Tag2RoaringBitmap tagRb) {
        tagId = tagRb.getTagId();
        bitIndex = tagRb.getBitIndex();
        bitmap = BitmapUtils.serialize(tagRb.getBitmap());
    }

    @Data
    public static class Tag2BitmapKey {

        private String tagId;
        private Integer bitIndex;

    }

}
