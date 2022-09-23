package com.example.uking.service;

import com.example.uking.bean.UserBean;

import java.util.Map;

public interface UserService {

    Map<String, Object> selectUserByPhone(String tel);

    UserBean selectUserById(int id);

    int selectUserNum();

    UserBean addUserBy(int id, String Tel, String password);
}
