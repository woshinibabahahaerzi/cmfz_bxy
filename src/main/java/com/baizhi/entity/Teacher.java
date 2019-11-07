package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述信息 ()
 *
 * @author bxy
 * @Date 2019/10/31$ 15:37$
 * @version: V_1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget(value = "teacher")
public class Teacher implements Serializable {
    //@Excel(name = "编号")
    @ExcelIgnore
    private String id;

    @Excel(name = "教师姓名",needMerge = true)
    private String name;

    @Excel(name = "性别")
    private String sex;

    @ExcelCollection(name = "168班学员")
    private List<Student> students;
}
