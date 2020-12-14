package com.practice.praproject.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
public class User extends BasicField{

    @Size(min=6, max=20)
    private String username;
    @Size(min=8, max=20)
    private String password;

    public String getUsername() {
        return username;
    }
    public User(){}
    public User(@Size(min = 6, max = 20) String username, @Size(min = 8, max = 20) String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
