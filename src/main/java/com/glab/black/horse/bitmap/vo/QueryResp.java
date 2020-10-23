package com.glab.black.horse.bitmap.vo;

import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
public class QueryResp {

    private int count;

    private List<Integer> hits;

}
