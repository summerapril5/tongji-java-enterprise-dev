package com.tj_JavaEE.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.tj_JavaEE.entity.Result;
import com.tj_JavaEE.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Enumeration;

@Slf4j
@Component
public class UserLoginCheckInterceptor implements HandlerInterceptor {


    @Override//为true放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取请求对象的url
        String url = request.getRequestURI().toString();
        log.info("请求的url是:{}",url);

        //2.判断url是否有login，如果有就放行
        if(url.contains("login")){
            log.info("登录请求，放行");
            return true;
        }

        // 放行 OPTIONS 请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        //打印所有请求头

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            log.info("Header: {} = {}", headerName, headerValue);
        }

        //3.获取请求头中的令牌
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            // 可以返回错误信息或者继续处理其他业务
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.info("请求头中无authorizationHeader，返回未登录信息");
            response.getWriter().write("Authorization header is missing");
            return false;
        }

        String jwt = authorizationHeader.substring(7);
        log.info("请求头中的令牌是:{}",jwt);

        //4.判断是否存在，如果不存在就返回错误结果（未登录）
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头中无token，返回未登录信息");
            Result error=Result.error("Not_Login");
            String notLogin= JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }

        //5.解析token，如果解析失败，返回错误结果（未登录）
        try {
            JwtUtils.parseJwt(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("解析令牌失败，返回未登录信息");
            Result error=Result.error("Not_Login");
            String notLogin= JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }

        //6.放行
        log.info("令牌合法，放行");
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
    }


}
