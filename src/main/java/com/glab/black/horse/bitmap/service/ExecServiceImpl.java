package com.glab.black.horse.bitmap.service;

import org.roaringbitmap.RoaringBitmap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExecServiceImpl implements ExecService {

    @Autowired
    ExprService exprService;

    @Override
    public RoaringBitmap exec(String rule) {
        return exprService.exec(rule);
    }

}
