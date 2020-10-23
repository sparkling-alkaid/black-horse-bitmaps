package com.glab.black.horse.bitmap.pojo.model;


import com.google.common.base.Strings;
import lombok.Data;
import org.roaringbitmap.RoaringBitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class NumberBitmapGroup {

    private String name;

    private RoaringBitmap notNullBitmap;

    private Map<Integer, RoaringBitmap> bitmapMap;


    public NumberBitmapGroup(String name) {
        this(name, 32);
    }

    public NumberBitmapGroup(String name, int size) {
        notNullBitmap = new RoaringBitmap();
        bitmapMap = new HashMap<>((int) ((size + 1) / 0.75) + 1);
        for (int i = 0; i < size; i++) {
            bitmapMap.put(i, new RoaringBitmap());
        }
    }

    public RoaringBitmap getBitmapAtIndex(int index) {
        return bitmapMap.get(index);
    }


    public void addNumber(int value) {
        notNullBitmap.add(value);
        String binary = Integer.toBinaryString(value);
        String fullBinary = Strings.padStart(binary, 32, '0');
        char[] chars = fullBinary.toCharArray();

    }

    public RoaringBitmap gte(int value){
        return null;
    }

    public RoaringBitmap gt(int value){
        return null;
    }

    public RoaringBitmap lte(int value){
        return null;
    }

    public RoaringBitmap lt(int value){
        return null;
    }

    public RoaringBitmap eq(int value){
        return null;
    }


    public List<Tag2RoaringBitmap> toBitmapList(){
        List<Tag2RoaringBitmap> list = new ArrayList<>(bitmapMap.size()+1);
        list.add(new Tag2RoaringBitmap(name, -1, notNullBitmap));
        bitmapMap.forEach((k, v) -> list.add(new Tag2RoaringBitmap(name, k, v)));
        return list;
    }


}
