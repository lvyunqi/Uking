package com.example.uking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.uking.dao.UserDao;
import com.example.uking.entity.User;
import com.example.uking.service.UserService;
import org.springframework.stereotype.Service;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-09-28 17:22:35
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}

