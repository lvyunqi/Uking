package com.uking.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (User)表实体类
 *
 * @author mryunqi
 * @since 2022-09-27 09:20:50
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "User")
public class User extends Model<User> {
    
    private Integer id;
    
    private String name;

    private String username;
    
    private String tel;
    
    private String email;
    
    private String password;
    
    private Integer qq;
    
    private String aff;
    
    private Integer verifyEmail;
    
    private String industry;
    
    private String business;
    
    private String website;
    
    private String country;
    
    private String province;
    
    private String city;
    
    private String address;
    
    private String fax;
    
    private Float money;
    
    private Float creditAmount;
    
    private String alternateTel;
    
    private Integer membershipGroup;
    
    private Integer sale;
    
    private Integer loginLock;
    
    private Integer adminLock;
    
    private String appId;
    
    private String apiKey;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
    }

