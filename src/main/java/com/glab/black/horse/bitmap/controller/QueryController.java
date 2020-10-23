package com.glab.black.horse.bitmap.controller;

import com.glab.black.horse.bitmap.pojo.model.NumberBitmapGroup;
import com.glab.black.horse.bitmap.service.BitmapImportService;
import com.glab.black.horse.bitmap.service.ExecService;
import com.glab.black.horse.bitmap.vo.QueryReq;
import com.glab.black.horse.bitmap.vo.TestReq;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
public class QueryController {

    @Autowired
    ExecService execService;

    /**
     * {"action":"query","rule":"prop1<10 && prop2<=20 && prop3>30.5 && prop4>=40.9"}
     *
     * @param
     * @return
     */
    @RequestMapping("/query")
    public int[] query(QueryReq req) {
        String rule = req.getRule();
        return execService.exec(rule);
    }



    @Autowired
    public BitmapImportService aaa;

    @RequestMapping("/queryByTag")
    public Object queryAA(@RequestBody TestReq req) throws Exception{
        NumberBitmapGroup bg = aaa.getBitmapGroupById(req.getTag());

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

        Map<String, Object> ret = new HashMap<>();
        boolean equals = bg.equals(test);
        ret.put("list", bg.lt(req.getLt()).toString());
        return ret;
    }



}
