package com.glab.black.horse.bitmap.pojo.entity;


import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "tag_meta")
public class TagMeta {

    private String tagId;
    private String tagType;
    private Integer precision;

}
