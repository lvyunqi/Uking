package com.uking.util;

import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;

/**
 * shiro工具类
 * 用于快速获取登录信息
 *
 * @author mryunqi
 */
public class ShiroUtils {

    /**
     * md5盐
     */
    private static final String SALT = "Uking.com";
    private static final String Key = "mryunqi";


    /**
     * 获取登录信息
     */
    public static Subject geSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取用户id
     *
     * @param <T> id类型
     */
    public static <T> T getUserId(Class<T> c) {
        Subject subject = geSubject();
        Claims claims = (Claims) subject.getPrincipal();
        return claims.get("UUID", c);
    }

    /**
     * 密码md5加密
     *
     * @param password 密码
     */
    public static String md5(String password) {
        String encryptPasswd = EncryptUtil.desEncode(password,Key);
        return new Md5Hash(encryptPasswd, SALT, 1024).toString();
    }

    /**
     * 密码比对
     *
     * @param password    未加密的密码
     * @param md5password 加密过的密码
     */
    public static boolean verifyPassword(String password, String md5password) {
        String encryptPasswd = EncryptUtil.desEncode(password,Key);
        return new Md5Hash(encryptPasswd, SALT, 1024).toString().equals(md5password);
    }

    /**
     * 退出登录
     */
    public static void logout() {
        geSubject().logout();
    }

}
