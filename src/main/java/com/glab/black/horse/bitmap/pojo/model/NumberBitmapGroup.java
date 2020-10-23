package com.glab.black.horse.bitmap.pojo.model;


import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.roaringbitmap.RoaringBitmap;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class NumberBitmapGroup {

    private String name;

    private RoaringBitmap notNullBitmap;

    private Map<Integer, RoaringBitmap> bitmapMap;

    private int size;


    public NumberBitmapGroup(String name) {
        this(name, 32);
    }

    public NumberBitmapGroup(String name, int size) {
        this.name = name;
        notNullBitmap = new RoaringBitmap();
        this.size = size;
        bitmapMap = new HashMap<>((int) ((size + 1) / 0.75) + 1);
        for (int i = 0; i < size; i++) {
            bitmapMap.put(i, new RoaringBitmap());
        }
    }

    public NumberBitmapGroup(String name, RoaringBitmap notNullBitmap, Map<Integer, RoaringBitmap> bitmapMap) {
        this.name = name;
        this.notNullBitmap = notNullBitmap;
        this.bitmapMap = bitmapMap;
        this.size = bitmapMap.size();
    }

    public RoaringBitmap getBitmapAtIndex(int index) {
        RoaringBitmap rb = bitmapMap.get(index);
        return rb == null ? new RoaringBitmap() : rb;
    }


    public void addNumber(int id, int value) {
        notNullBitmap.add(id);
        char[] chars = intValToCharArr(value);
        for (int index = 0; index < size; index++) {
            if (chars[index] == '0') {
                getBitmapAtIndex(index).add(id);
            }
        }
    }

    public static void main(String[] args) throws Exception{
        NumberBitmapGroup test = new NumberBitmapGroup("test");
//        for (int i = 1; i < 100000; i++) {
//            if(i==200 || i==100){
//                continue;
//            }
//            test.addNumber(i+1, i);
//        }

        String filePath="/Users/ranwd/Desktop/shupan1.txt";

        File dataFile = new File(filePath);
        Files.lines(dataFile.toPath()).forEach(line -> {


            if ("".equals(line)) {
                return;
            }
            String[] strings = line.split(",", -1);


            int id = Integer.parseInt(strings[0].trim());

            int value = Integer.parseInt(strings[1].trim());
            test.addNumber(id, value);

        });
        System.out.println(test.getBitmapAtIndex(0));

//        System.out.println(test.lt(1000));
    }

    private char[] intValToCharArr(int value) {
        String binary = Integer.toBinaryString(value);
        String fullBinary = Strings.padStart(binary, size, '0');
        char[] chars = new char[fullBinary.length()];
        for (int index = fullBinary.length() - 1; index >= 0; index--) {
            chars[fullBinary.length() - 1-index] = fullBinary.charAt(index);
        }
        return chars;
    }

    private char[] intValToCharArrReverse(int value) {
        String binary = Integer.toBinaryString(value);
        String fullBinary = Strings.padStart(binary, size, '0');
        return fullBinary.toCharArray();
    }

    public RoaringBitmap gte(int value) {
        return RoaringBitmap.andNot(notNullBitmap, lt(value));
    }

    public RoaringBitmap gt(int value) {
        return RoaringBitmap.andNot(notNullBitmap, lte(value));
    }

    public RoaringBitmap lte(int value) {
        return lt(value + 1);
    }

    public RoaringBitmap lt(int value) {
        char[] chars = intValToCharArrReverse(value);
        List<BitmapCalcUnit> list = new LinkedList<>();
        List<RoaringBitmap> collects = new LinkedList<>();
        Character charPrevChar = null;
        for (int i = 0; i < chars.length; i++) {
            if (charPrevChar == null) {
                charPrevChar = chars[i];
                // 此处有反转
                collects.add(getBitmapAtIndex(31 - i));
            } else {
                if (chars[i] == charPrevChar) {
                    collects.add(getBitmapAtIndex(31 - i));
                } else if (chars[i] != charPrevChar) {
                    // do merge
                    BitmapCalcUnit calc = makeRaw(charPrevChar, collects);
                    list.add(calc);
                    collects = new LinkedList<>();
                    charPrevChar = chars[i];
                    collects.add(getBitmapAtIndex(31 - i));
                }
                if (i == chars.length - 1) {
                    // do merge
                    list.add(makeRaw(charPrevChar, collects));
                }
            }

        }
        return ltMergeList(list).getLtBitmap();
    }

    private BitmapCalcUnit makeRaw(char flag, List<RoaringBitmap> collects) {
        BitmapCalcUnit ret = new BitmapCalcUnit();
        RoaringBitmap ltBitmap = null;
        RoaringBitmap eqBitmap = null;
        if (flag == '0') {
            ltBitmap = new RoaringBitmap();;
            eqBitmap = collects.get(0).clone();
            for (int i = 1; i < collects.size(); i++) {
                eqBitmap.and(collects.get(i));
            }
        } else {
            ltBitmap = collects.get(0).clone();
            for (int i = 1; i < collects.size(); i++) {
                ltBitmap.or(collects.get(i).clone());
            }
            eqBitmap = RoaringBitmap.andNot(notNullBitmap, ltBitmap);
        }
        ret.setLtBitmap(ltBitmap);
        ret.setEqBitmap(eqBitmap);
        return ret;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class BitmapCalcUnit {

        private RoaringBitmap ltBitmap;
        private RoaringBitmap eqBitmap;

    }

    private BitmapCalcUnit ltMergeList(List<BitmapCalcUnit> bitmapList) {
        if (bitmapList.size() <= 0) {
            return null;
        } else if (bitmapList.size() == 1) {
            return bitmapList.get(0);
        } else {
            List<BitmapCalcUnit> merge = new ArrayList<>();
            BitmapCalcUnit odd = null;
            BitmapCalcUnit even = null;
            boolean isEven = ((bitmapList.size() & 1) == 0);
            for (int i = 0; isEven ? i < bitmapList.size() : i < bitmapList.size() - 1; i++) {

                boolean indexIsOdd = ((i & 1) == 0);
                if (indexIsOdd) {
                    odd = bitmapList.get(i);
                } else {
                    even = bitmapList.get(i);
                }
                if (odd != null && even != null) {
                    merge.add(ltMerge(odd, even, notNullBitmap));
                    odd = null;
                    even = null;
                }
            }
            if (!isEven) {
                merge.add(bitmapList.get(bitmapList.size() - 1));
            }
            return ltMergeList(merge);
        }
    }

    private BitmapCalcUnit ltMerge(BitmapCalcUnit unit1, BitmapCalcUnit unit2, RoaringBitmap allBitmap) {
        BitmapCalcUnit unit = new BitmapCalcUnit();
        unit.setEqBitmap(RoaringBitmap.and(unit1.getEqBitmap(), unit2.getEqBitmap()));
        unit.setLtBitmap(RoaringBitmap.or(unit1.getLtBitmap(), RoaringBitmap.and(unit1.getEqBitmap(), unit2.getLtBitmap())));
        return unit;
    }


    // from 是高位， to是地位
    private static int getValueAtBitRange(int value, int from, int to) {
        int ret = value;
        char[] chars = new char[32];
        for (int i = 0; i < 32; i++) {
            if (i >= from && i < to) {
                chars[i] = '1';
            } else {
                chars[i] = '0';
            }
        }
        int mask = Integer.parseInt(new String(chars), 2);
        return (value & mask) >> (32 - to);
    }


    public RoaringBitmap eq(int value) {
        return RoaringBitmap.andNot(lte(value), lt(value));
    }


    public List<Tag2RoaringBitmap> toBitmapList() {
        List<Tag2RoaringBitmap> list = new ArrayList<>(bitmapMap.size() + 1);
        list.add(new Tag2RoaringBitmap(name, -1, notNullBitmap));
        bitmapMap.forEach((k, v) -> list.add(new Tag2RoaringBitmap(name, k, v)));
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberBitmapGroup that = (NumberBitmapGroup) o;
        for(int i=0;i<32;i++){
            RoaringBitmap source = getBitmapAtIndex(i);
            RoaringBitmap target = that.getBitmapAtIndex(i);
            boolean equals = Arrays.equals(source.toArray(), target.toArray());
            if(!equals){
                return false;
            };
//
//            if(!getBitmapAtIndex(i).equals(that.getBitmapAtIndex(i))){
//
//                return false;
//            }
        }
        return Arrays.equals(notNullBitmap.toArray(), that.notNullBitmap.toArray());
    }


}
