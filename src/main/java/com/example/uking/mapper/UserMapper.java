package com.example.uking.mapper;

import com.example.uking.bean.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface  UserMapper {
    UserBean getInfo_id(int id);
    void addUser(@Param("id") int id,@Param("tel") String Tel,@Param("password") String Password);
    int getUserNum();
    Map<String, Object> getInfo_tel(@Param("tel") String tel);

    UserBean selectById(int parseInt);

    String selectUserPasswordByPhone(@Param("tel") String Tel);
}
