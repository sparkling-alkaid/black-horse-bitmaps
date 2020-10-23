package com.glab.black.horse.bitmap.pojo.entity;

import lombok.Data;

import java.util.List;

@Data
public class QueryResponse {
    private List<Long> hits;
}
