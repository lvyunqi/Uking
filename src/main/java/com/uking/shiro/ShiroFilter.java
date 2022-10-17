package com.uking.shiro;

import com.alibaba.fastjson2.JSON;
import com.uking.authorization.JwtToken;
import com.uking.util.exception.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 需要认证的url经过该过滤器
 *
 * @author mryunqi
 */
@Slf4j
public class ShiroFilter extends AccessControlFilter {

    /**
     * 跨域支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        log.info("进入预处理器--处理完成进入JwtFilter");
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        httpResponse.setHeader("Access-control-Allow-Origin",httpRequest.getHeader("Origin"));
        httpResponse.setHeader("Access-control-Allow-Methods","GET,POST,OPTIONS,PUT,DELETE");
        httpResponse.setHeader("Access-control-Allow-Headers",httpRequest.getHeader("Access-Control-Request-Headers"));
        // 对跨域OPTIONS请求放行
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            log.debug("收到一个OPTIONS请求--"+httpRequest.getRequestURI());
            httpResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 是否允许通过，因为是无状态所以默认不通过，去自动登陆,返回false，调用onAccessDenied方法
     * 这里getSubject方法实际上就是获得一个subject
     * 与原生shiro不同的地方在于没有对username和password进行封装
     * 直接使用jwt进行认证，login方法实际上就是交给Realm进行认证
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) {
        String token = ((HttpServletRequest) servletRequest).getHeader("token");
        if (token == null) {
            return false;
        }
        try {
            getSubject(servletRequest, servletResponse).login(new JwtToken(token));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 自定义认证失败返回
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        ResponseResult<String> resp = ResponseResult.fail(ResponseResult.RespCode.UNAUTHORIZED);
        httpResponse.getWriter().write(JSON.toJSONString(resp));
        return false;
    }

}
