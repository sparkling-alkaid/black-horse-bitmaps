package com.glab.black.horse.bitmap.pojo.entity;


import com.glab.black.horse.bitmap.pojo.model.Tag2RoaringBitmap;
import com.glab.black.horse.bitmap.utils.BitmapUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@IdClass(Tag2Bitmap.Tag2BitmapKey.class)
@Table(name = "tag2bitmap")
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
    public static class Tag2BitmapKey implements Serializable {

        private String tagId;
        private Integer bitIndex;

    }

}
