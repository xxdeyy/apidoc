package com.f2boy.domain.entity;

import java.io.Serializable;

public class ApiModule implements Serializable {
    /**  */
    private Integer id;

    /** 模块名称 */
    private String name;

    /** 排序号 */
    private Integer sortNo;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }
}