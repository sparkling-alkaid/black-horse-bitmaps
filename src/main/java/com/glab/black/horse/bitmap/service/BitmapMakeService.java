package com.glab.black.horse.bitmap.service;


import com.glab.black.horse.bitmap.pojo.entity.TagMeta;
import com.glab.black.horse.bitmap.pojo.model.NumberBitmapGroup;
import com.glab.black.horse.bitmap.pojo.model.Tag2RoaringBitmap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BitmapMakeService {


    @Autowired
    private BitmapImportService bitmapImportService;

    String dataFile = "";


    public void makeBitmap(String filePath, List<TagMeta> metaList) throws IOException {

        File dataFile = new File(filePath);

        List<NumberBitmapGroup> numberBitmapGroups = new ArrayList<NumberBitmapGroup>();

        metaList.forEach(tagMeta -> {
            numberBitmapGroups.add(new NumberBitmapGroup(tagMeta.getTagId()));
        });


        Files.lines(dataFile.toPath()).forEach(line -> {
            //0,3420,1010,2263.61,5663.63
            //1,2120,441,8334.31,466.74
            //2,1436,3475,4418.21,2474.56
            //3,685,2770,2861.16,7302.55
            //4,9113,5624,5317.58,4217.87
            //5,183,8496,8676.36,5515.76
            //6,1483,5666,4927.33,7573.11


            if ("".equals(line)) {
                return;
            }
            String[] strings = line.split(",", -1);
            if (strings.length != metaList.size() + 1) {
                return;
            }

            int id = Integer.parseInt(strings[0]);

            for (int i = 0; i < metaList.size(); i++) {
                int tagValue = new BigDecimal(strings[i + 1]).multiply(new BigDecimal(10).pow(metaList.get(i).getPrecision())).intValue();
                numberBitmapGroups.get(i).addNumber(id, tagValue);
            }
        });
        List<Tag2RoaringBitmap> list = numberBitmapGroups.stream().flatMap(numberBitmapGroup -> numberBitmapGroup.toBitmapList().stream()).collect(Collectors.toList());

        bitmapImportService.importTag2Bitmap(list);

    }


}
