package com.glab.black.horse.bitmap.controller;

import com.glab.black.horse.bitmap.vo.QueryReq;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {

    /**
     * {"action":"query","rule":"prop1<10 && prop2<=20 && prop3>30.5 && prop4>=40.9"}
     *
     * @param
     * @return
     */
    @RequestMapping("/query")
    public String home(QueryReq req) {
        String rule = req.getRule();

        return "Hello World!";

    }

}
