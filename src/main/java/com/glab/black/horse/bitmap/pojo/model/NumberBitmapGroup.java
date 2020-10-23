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
        this.name = name;
        notNullBitmap = new RoaringBitmap();
        bitmapMap = new HashMap<>((int) ((size + 1) / 0.75) + 1);
        for (int i = 0; i < size; i++) {
            bitmapMap.put(i, new RoaringBitmap());
        }
    }

    public NumberBitmapGroup(String name, RoaringBitmap notNullBitmap, Map<Integer, RoaringBitmap> bitmapMap) {
        this.name = name;
        this.notNullBitmap = notNullBitmap;
        this.bitmapMap = bitmapMap;
    }

    public RoaringBitmap getBitmapAtIndex(int index) {
        RoaringBitmap rb = bitmapMap.get(index);
        return rb == null ? new RoaringBitmap() : rb;
    }


    public void addNumber(int id, int value) {
        notNullBitmap.add(id);
        char[] chars = intValToCharArr(value);
        for (int index = 0; index < 32; index++) {
            if (chars[index] == '0') {
                getBitmapAtIndex(index).add(id);
            }
        }
    }

    private char[] intValToCharArr(int value) {
        String binary = Integer.toBinaryString(value);
        String fullBinary = Strings.padStart(binary, 32, '0');
        char[] chars = new char[fullBinary.length()];
        for (int index = fullBinary.length() - 1; index >= 0; index--) {
            chars[index] = fullBinary.charAt(index);
        }
        return chars;
    }

    public RoaringBitmap gte(int value) {
        return null;
    }

    public RoaringBitmap gt(int value) {
        return null;
    }

    public RoaringBitmap lte(int value) {

        return null;
    }

    public RoaringBitmap lt(int value) {
        RoaringBitmap ret = new RoaringBitmap();
        return null;
    }

    public RoaringBitmap eq(int value) {
        return null;
    }


    public List<Tag2RoaringBitmap> toBitmapList() {
        List<Tag2RoaringBitmap> list = new ArrayList<>(bitmapMap.size() + 1);
        list.add(new Tag2RoaringBitmap(name, -1, notNullBitmap));
        bitmapMap.forEach((k, v) -> list.add(new Tag2RoaringBitmap(name, k, v)));
        return list;
    }


}
