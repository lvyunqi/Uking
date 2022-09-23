package com.example.uking.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserBean {
    private int id;
    private String tel;
    @ApiModelProperty(value = "用户姓名")
    private String name;
    private String password;
    private String aff;
    private int qq;
/*
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAff() {
        return aff;
    }
    public void setAff(String aff) {this.aff = aff;}

    public int getQQ() {
        return qq;
    }

    public void setQQ(int qq) {this.qq=qq;}*/
}
