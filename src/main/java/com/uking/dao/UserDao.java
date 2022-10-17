package com.uking.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uking.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * (User)表数据库访问层
 *
 * @author mryunqi
 * @since 2022-09-28 17:22:24
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

}

