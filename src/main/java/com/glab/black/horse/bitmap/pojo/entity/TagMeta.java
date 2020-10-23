package com.glab.black.horse.bitmap.pojo.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tag_meta")
public class TagMeta {

    @Id
    private String tagId;
    private String tagType;
    private Integer precision;

}
