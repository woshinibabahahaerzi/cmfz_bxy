package com.baizhi.entity;

import com.sun.tracing.dtrace.ModuleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cmfz_user")
public class User implements Serializable {
    @Id
    //@KeySql(useGeneratedKeys = true)
    private String id;


    private String username;

    private String password;

    @Transient
    private Integer age;

/*
    @Transient
    private List<Admin> list;*/

}