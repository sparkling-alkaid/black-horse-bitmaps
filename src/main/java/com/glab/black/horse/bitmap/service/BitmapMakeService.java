package com.glab.black.horse.bitmap.service;


import com.glab.black.horse.bitmap.pojo.entity.TagMeta;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.List;

@Service
public class BitmapMakeService {


    String dataFile = "";


    public void makeBitmap(String filePath, List<TagMeta> metaList) throws IOException {

        File dataFile = new File(filePath);

        Files.lines(dataFile.toPath()).map(line -> {
            //0,3420,1010,2263.61,5663.63
            //1,2120,441,8334.31,466.74
            //2,1436,3475,4418.21,2474.56
            //3,685,2770,2861.16,7302.55
            //4,9113,5624,5317.58,4217.87
            //5,183,8496,8676.36,5515.76
            //6,1483,5666,4927.33,7573.11

            if ("".equals(line)) {
                return null;
            }
            String[] strings = line.split(",", -1);
            if (strings.length != 6) {
                return null;
            }
            int offset = 0;
            int id = Integer.parseInt(strings[offset++]);
            int pro1 = Integer.parseInt(strings[offset++]);
            int pro2 = Integer.parseInt(strings[offset++]);
            BigDecimal pro3 = new BigDecimal(strings[offset++]);
            BigDecimal pro4 = new BigDecimal(strings[offset++]);


            return null;
        });


    }


}
