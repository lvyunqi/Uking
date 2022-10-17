package com.uking.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.uking.dao.UserDao;
import com.uking.entity.User;
import com.uking.service.UserService;
import com.uking.util.EncryptUtil;
import com.uking.util.JwtProvider;
import com.uking.util.exception.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Authorization", tags = {"用户鉴权接口"})
@RestController
public class Authorization {
    private final JwtProvider jwtProvider;
    private final UserService userService;
    @Autowired
    public Authorization(JwtProvider jwtProvider, UserService userService, UserDao userDao) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    //用户登录
    @ApiOperation(value="用户登录", notes="password传递原始密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "手机号", required = true, dataType = "string",dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "原始密码", required = true, dataType = "string",dataTypeClass = String.class, paramType = "query")
    })
    @PostMapping(value = "/api/user/login")
    public ResponseResult<String> login(@RequestParam(value = "tel") String tel, @RequestParam(value = "password") String password){
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
     * 退出登录
     */
    @ApiOperation(value="退出登录", notes="在请求头（headers）放入登录时获取的token")
    @GetMapping("/api/user/logout")
    public ResponseResult<String> logout() {
        SecurityUtils.getSubject().logout();
        return ResponseResult.ok("成功退出登录");
    }
}
