package com.example.uking.controller;

import com.example.uking.bean.UserBean;
import com.example.uking.service.UserService;
import com.example.uking.util.JwtProvider;
import com.example.uking.util.ShiroUtils;
import com.example.uking.util.exception.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(value = "用户接口", tags = {"用户接口"})
@RestController
@RequiredArgsConstructor
//@RequestMapping("/sysapi/user")
public class UserController {
    private final JwtProvider jwtProvider;
    @Autowired
    private UserService userService;

    //用户注册
    @ApiOperation(value="用户注册", notes="password传递原始密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "手机号", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "原始密码", required = true, dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/api/user/register",method = RequestMethod.POST)
    public ResponseResult<String> addUser(@RequestParam(value = "tel") String tel,@RequestParam(value = "password") String password) {
        int initId = 11000000;
        String userData = String.valueOf(userService.selectUserByPhone(tel));
        if(!(userData.equals("null"))){
            return ResponseResult.fail("用户已存在!");
        }
        int id = initId+userService.selectUserNum();
        String encryptPasswd = ShiroUtils.md5(password);
        UserBean userBean = userService.addUserBy(id,tel,encryptPasswd);
        if (userBean == null){
            return ResponseResult.ok("注册成功!");
        }
        return ResponseResult.fail(String.valueOf(userBean));
    }

    //用户登录
    @ApiOperation(value="用户登录", notes="password传递原始密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "手机号", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "原始密码", required = true, dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/api/user/login",method = RequestMethod.POST)
    public ResponseResult<String> login(@RequestParam(value = "tel") String tel,@RequestParam(value = "password") String password){
        String userData = String.valueOf(userService.selectUserByPhone(tel));
        if(userData.equals("null")){
            return ResponseResult.fail("用户不存在!");
        }
        Map<String, Object> map = userService.selectUserByPhone(tel);
        String SqlPassword = String.valueOf(map.get("password"));
        Object UUID = map.get("id");
        if(ShiroUtils.verifyPassword(password,SqlPassword)){
            String token = jwtProvider.createToken(UUID);
            return ResponseResult.ok(token);
        }
        return ResponseResult.fail("账号或密码错误");
    }

    /**
     * 获取用户
     */
    /*@GetMapping("/api/user/getUser")
    public ResponseResult<Long> getUser() {
        // 获取当前登录的用户id
        Long userId = ShiroUtils.getUserId(Long.class);
        return ResponseResult.ok(userId);
    }*/

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
