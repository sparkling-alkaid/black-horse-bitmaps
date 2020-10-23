package com.glab.black.horse.bitmap.controller;

import com.glab.black.horse.bitmap.service.ExecService;
import com.glab.black.horse.bitmap.vo.QueryReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
