package com.uking.util.code;

/**
 * 自定义异常错误状态码及反馈信息的枚举类
 * 如果还有其他需要提示的异常，再继续在枚举中添加即可
 */
// 枚举类
public enum BaseResponseCode implements ResponseCodeInterface {
    /**
     * 这个要和前段约定好
     *code=0：服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。
     *code=401 0001：（授权异常） 请求要求身份验证。 客户端需要跳转到登录页面重新登录
     *code=401 0002：(凭证过期) 客户端请求刷新凭证接口
     *code=403 0001：没有权限禁止访问
     *code=400 xxxx：系统主动抛出的业务异常
     *code=500 0001：系统异常
     */
    // 操作成功的操作
    SUCCESS(0,"操作成功"),
    // 服务器内部异常操作
    SYSTEM_ERROR(5000001,"系统异常，500。"),
    DATA_ERROR(4000001,"参数异常"),
    VALID_DATA_ERROR(4000002,"参数校验异常"),
    USER_ERROR(4000003,"账号异常，请重新注册。"),
    USER_LOCK_ERROR(4000004,"该账号涉嫌违规，已被封禁，用户已被强制登出。"),
    USER_PASSWORD_ERROR(4000005,"账号密码错误。"),
    TOKEN_NOT_NULL_ERROR(4010001,"Token凭证不能为空，请重新登录获取。"),
    TOKEN_LOSE_ERROR(4010002,"Token认证失败，请重新登录获取"),
    Authorization_invalid(401003,"Authorization token is invalid"),
    ACCOUNT_LOCK(4010001,"该账号被锁定,请联系系统管理员"),
    ACCOUNT_HAS_DELETED_ERROR(4010001,"该账号已被删除，请联系系统管理员"),
    TOKEN_PAST_DUE(4010002,"token失效,请刷新token"),
    NOT_PERMISSION(4030001,"没有权限访问该资源"),
    SYSTEM_BUSY(4000006, "系统繁忙");


    // 状态码
    private final int code;

    // 提示语
    private final String msg;

    BaseResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}