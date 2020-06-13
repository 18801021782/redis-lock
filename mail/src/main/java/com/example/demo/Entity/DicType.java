package com.example.demo.Entity;


import javax.persistence.Table;

@Table(name = "t_sys_dic_type")
public class DicType extends BaseEntity{

    private String code;

    private String name;

    private Integer status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
