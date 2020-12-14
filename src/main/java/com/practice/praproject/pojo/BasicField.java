package com.practice.praproject.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.practice.praproject.mybatis.CreateTime;
import com.practice.praproject.mybatis.UpdateTime;

public class BasicField {
    @TableId(type = IdType.ASSIGN_UUID)
    String id;
    @CreateTime
    String createTime;
    @UpdateTime
    String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
