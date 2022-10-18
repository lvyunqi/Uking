package com.uking.util.exception;

import com.uking.util.code.BaseResponseCode;
import com.uking.util.code.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    /**
     * 系统繁忙，请稍候再试
     */
    @ExceptionHandler(Exception.class)
    public <T> DataResult handleException(Exception e){
        log.error("Exception,exception:{}", e);
        return DataResult.getResult(BaseResponseCode.SYSTEM_BUSY);
    }


}
