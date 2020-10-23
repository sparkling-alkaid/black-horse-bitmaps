package com.glab.black.horse.bitmap.service;

import org.roaringbitmap.RoaringBitmap;

public interface ExecService {

    RoaringBitmap exec(String rule);

}
