package com.glab.black.horse.bitmap.service;


import com.glab.black.horse.bitmap.pojo.entity.TagMeta;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class BitmapMakeService {


    String dataFile = "";


    public void makeBitmap(String filePath, List<TagMeta> metaList) throws IOException {

        File dataFile = new File(filePath);




        Files.lines(dataFile.toPath()).map(line ->{
            return null;
        });


    }



}
