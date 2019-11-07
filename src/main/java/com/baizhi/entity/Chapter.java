package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 类描述信息 ()
 *
 * @author bxy
 * @Date 2019/10/28$ 14:24$
 * @version: V_1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cmfz_chapter")
public class Chapter implements Serializable {
    @Id
    private String id;
    private String title;
    private String size;
    private String duration;
    @Column(name = "chapter_url")
    private String url;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JSONField(format ="yyyy-MM-dd HH:mm:ss" )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JSONField(format ="yyyy-MM-dd HH:mm:ss" )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Transient
    private Date lastUpdateDate;
    @Transient
    private Integer status;
    private String albumId;

}
