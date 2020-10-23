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

    public static final int MIN_LENGTH = 32;
    public static final char PAD_CHAR = '0';
    private String name;

    private RoaringBitmap notNullBitmap;

    //0是低位
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
        //int -> binary
        String s = Integer.toBinaryString(value);
        //原始binary的长度
        int firstIndex = s.length();

        String binStr = Strings.padStart(s, MIN_LENGTH, PAD_CHAR);
        char[] chars = binStr.toCharArray();
        int loopSize = 32 - firstIndex;
        for (int i = 0; i < loopSize; i++) {
            String substring = binStr.substring(0, loopSize);
            substring.toCharArray();

        }

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
