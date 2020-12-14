package com.practice.praproject.pojo;
public class Role extends BasicField{

    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", createdTime='" + createTime + '\'' +
                ", updatedTime='" + updateTime + '\'' +
                '}';
    }
}


