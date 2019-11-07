package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cmfz_admin")
public class Admin implements Serializable {
    @Id
    private String id;
    private String adminName;
    private String adminPassword;
    private String adminNickname;
}
