package com.glab.black.horse.bitmap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExecServiceImpl implements ExecService {

    @Autowired
    ExprService exprService;

    @Override
    public int[] exec(String rule) {
        int[] exec = exprService.exec(rule);
        return null;
    }

}
