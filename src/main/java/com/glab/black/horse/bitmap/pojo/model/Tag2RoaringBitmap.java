package com.glab.black.horse.bitmap.pojo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.roaringbitmap.RoaringBitmap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag2RoaringBitmap {

    private String tagId;
    private Integer bitIndex;
    private RoaringBitmap bitmap;

}
