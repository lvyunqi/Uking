package com.uking.util.code;

// 此类实际开发中是否使用有待考究
// 可以使用在JWT中学习到的返还前端数据的方法来替代

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前后端分离数据封装 DataResult
 * 前后端分离情况下，反馈数据（状态码、提示语、数据）格式的统一，数据封装到DataResult
 * 这里使用泛型T是为了使不同的反馈数据均可传入
 *
 * 目前市面上公司开发模式普遍采用了前后端分离，而前后端交互一般会以 json 的形式交互，既然涉及到多方交互那就需要一些约定好
 * 的交互格式，然而每个人的想法有可能是不一样的，所以定义的格式字段就可能不一样，如果我们后端不统一前端会不知道应该怎么操作，
 * 所以我们需要封装一个统一的返回格式
 */
@Data
public class DataResult<T> {
    //状态码
    @ApiModelProperty(value = "状态码，0成功，其他失败")
    private int code;
    // 响应
    @ApiModelProperty(value = "提示语")
    private String msg;
    // 反馈数据
    @ApiModelProperty(value = "反馈数据")
    private T data;

    public DataResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public DataResult(int code, T data) {
        this.code = code;
        this.data = data;
        this.msg = null;
    }

    public DataResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public DataResult(){
        this.code = BaseResponseCode.SUCCESS.getCode();
        this.msg=BaseResponseCode.SUCCESS.getMsg();
    }

    public DataResult(T data){
        this.code = BaseResponseCode.SUCCESS.getCode();
        this.msg=BaseResponseCode.SUCCESS.getMsg();
        this.data = data;
    }

    public static DataResult success(){
        return new DataResult();
    }

    public static <T> DataResult success(T data){
        return new DataResult(data);
    }


    // 传入接口对象取得code和msg的构造方法
    public DataResult(ResponseCodeInterface responseCodeInterface, T data){
        this.code = responseCodeInterface.getCode();
        this.msg = responseCodeInterface.getMsg();
        this.data = data;
    }

    public DataResult(ResponseCodeInterface responseCodeInterface){
        this.code = responseCodeInterface.getCode();
        this.msg = responseCodeInterface.getMsg();
        this.data = null;
    }

    //不同参数个数情况下的构造方法生成的对象通过不同的静态方法返回
    public static <T> DataResult getResult(int code,String msg,T data){
        return new DataResult(code,msg,data);
    }

    public static DataResult getResult(int code,String msg){
        return new DataResult(code,msg);
    }

    public static <T> DataResult getResult(int code,T data){
        return new DataResult(code,data);
    }

    // 静态方法
    public static <T> DataResult getResult(ResponseCodeInterface responseCodeInterface,T data){
        return new DataResult(responseCodeInterface,data);
    }

    public static DataResult getResult(ResponseCodeInterface responseCodeInterface){
        return new DataResult(responseCodeInterface);
    }

}