package com.example.uking.serviceImpl;

import com.example.uking.bean.UserBean;
import com.example.uking.mapper.UserMapper;
import com.example.uking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> selectUserByPhone(String tel) {
        return userMapper.getInfo_tel(tel);
    }

    @Override
    public UserBean selectUserById(int id) {
        return null;
    }

    @Override
    public UserBean addUserBy(int id, String tel, String password) {
        userMapper.addUser(id,tel,password);
        return null;
    }

    @Override
    public int selectUserNum() {
        return userMapper.getUserNum();
    }

}
