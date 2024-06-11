package com.it.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import com.it.common.Result;
import com.it.context.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录校验
 * @author 
 * @date 2024/4/15 14:34
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("url is {}", requestURI);
        if(requestURI.contains("/login") || requestURI.contains("/createUser") || requestURI.contains("static") || requestURI.contains("upload")){
            return true;
        }

        String method = request.getMethod();
        if("OPTIONS".equals(method.toUpperCase())){
            return true;
        }

        try{
            StpUtil.checkLogin();
            log.info("denglu {}", StpUtil.getLoginId());
            UserContext.setUserId(Long.valueOf(StpUtil.getLoginId().toString()));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            handError(response);
            // TODO
            return false;
        }
    }

    void handError(HttpServletResponse response){
        Result error = Result.ok(null, 304, "未登录");// 错误信息

        response.setStatus(HttpServletResponse.SC_OK); // 设置响应状态为500
        response.setContentType("text/plain"); // 设置响应内容类型
        response.setCharacterEncoding("UTF-8"); // 设置响应字符编码

        try {
            response.getWriter().write(JSONObject.toJSONString(error)); // 将错误信息写入响应流
        } catch (IOException e) {

        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.clear();
    }
}
