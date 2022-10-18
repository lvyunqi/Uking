package com.uking.util.exception;

import com.uking.util.code.ResponseCodeInterface;

/**
 * 自定义的业务逻辑异常类
 在现实实战中，往往我们会在复杂的带有数据库事务的业务中，经常会遇到一些不规则的信息，这个就需要我们后端根据相应的业务
 抛出相应的运行时异常，进行数据库事务回滚，并希望该异常信息能被返回显示给用户。
 */
public class BusinessException extends RuntimeException {

    /**
     * 提示的编码
     */
    private final int code;

    /**
     * 提示语
     */
    private final String msg;

    public BusinessException(int code, String msg) {
        super(msg); // 将msg传入父类构造方法
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(ResponseCodeInterface responseCodeInterface){
        this(responseCodeInterface.getCode(),responseCodeInterface.getMsg());// 此处this的作用是调用本类中其他构造方法 // 此处是调用上面的参数为code和msg的构造方法
    }

/*    public BusinessException(ResponseCodeInterface responseCodeInterface) {
        this(responseCodeInterface.getCode(),responseCodeIntejavarface.getMsg());
    }*/

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
