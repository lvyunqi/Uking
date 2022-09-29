package com.example.uking.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.example.uking.dao.UserDao;
import com.example.uking.entity.User;
import com.example.uking.service.UserService;
import com.example.uking.util.EncryptUtil;
import com.example.uking.util.JwtProvider;
import com.example.uking.util.ObjectUtil;
import com.example.uking.util.ShiroUtils;
import com.example.uking.util.exception.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Api(value = "用户授权接口", tags = {"用户授权接口"})
@RestController
public class UserAuthController {
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final UserDao userDao;
    @Autowired
    public UserAuthController(JwtProvider jwtProvider, UserService userService, UserDao userDao) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
        this.userDao = userDao;
    }

    //用户注册
    @ApiOperation(value="用户注册", notes="password传递原始密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "string",dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "tel", value = "手机号", required = true, dataType = "string",dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "原始密码", required = true, dataType = "string",dataTypeClass = String.class, paramType = "query")
    })
    @PostMapping("/api/user/register")
    public ResponseResult<String> register(@RequestParam(value = "username") String username, @RequestParam(value = "tel") String tel,@RequestParam(value = "password") String password, HttpServletResponse response) {
        //根据唯一用户名去查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        QueryWrapper<User> wrapper2 = new QueryWrapper<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isBlank(username)) {
            return ResponseResult.fail("用户名不能为空！");
        }
        if (StringUtils.isBlank(tel)) {
            return ResponseResult.fail("手机号不能为空！");
        }
        if (!tel.matches("^1[3,4,5,7,8,9]\\d{9}$")) {
            return ResponseResult.fail("手机号格式不对！");
        }
        if (StringUtils.isBlank(password)) {
            return ResponseResult.fail("密码不能为空！");
        }
        if (username.length() > 64) {
            return ResponseResult.fail("用户名长度不能超过64位！");
        }
        if (password.length() > 18) {
            return ResponseResult.fail("密码长度不能超过18位！");
        }
        wrapper.eq("username", username);
        User userOne = userService.getOne(wrapper);
        if (!ObjectUtil.isNull(userOne)) {
            return ResponseResult.fail("用户已存在!");
        }
        wrapper2.eq("tel", tel);
        User userTwo = userService.getOne(wrapper2);
        if (!ObjectUtil.isNull(userTwo)) {
            return ResponseResult.fail("手机号已被绑定!");
        }
        queryWrapper.ge("id",11000000);
        int count = Math.toIntExact(userDao.selectCount(queryWrapper));
        int initId = 11000000;
        int UUID = count + initId;
        String encryptPasswd = EncryptUtil.md5(password);
        User user = new User();
        user.setId(UUID);
        user.setUsername(username);
        user.setTel(tel);
        user.setPassword(encryptPasswd);
        userDao.insert(user);
        return ResponseResult.ok("注册成功！");
    }

    //用户登录
    @ApiOperation(value="用户登录", notes="password传递原始密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "手机号", required = true, dataType = "string",dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "原始密码", required = true, dataType = "string",dataTypeClass = String.class, paramType = "query")
    })
    @PostMapping(value = "/api/user/login")
    public ResponseResult<String> login(@RequestParam(value = "tel") String tel,@RequestParam(value = "password") String password){
        //根据唯一用户名去查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("tel",tel);
        User user = userService.getOne(wrapper);
        Assert.notNull(user, "用户不存在");
        if (!user.getPassword().equals(EncryptUtil.md5(password))){
            return ResponseResult.fail("密码不正确");
        }
        //生成Jwt，返回响应头给前端
        String token = jwtProvider.createToken(user.getId());
        return ResponseResult.ok(token);
    }

    /**
     * 获取用户
     */
    @RequiresAuthentication
    @GetMapping("/api/user/getUser")
    public ResponseResult<Long> getUser() {
        // 获取当前登录的用户id
        Long userId = ShiroUtils.getUserId(Long.class);
        return ResponseResult.ok(userId);
    }

    /**
     * 退出登录
     */
    @ApiOperation(value="退出登录", notes="在请求头（headers）放入登录时获取的token")
    @GetMapping("/api/user/logout")
    public ResponseResult<String> logout() {
        SecurityUtils.getSubject().logout();
        return ResponseResult.ok("成功退出登录");
    }
}
