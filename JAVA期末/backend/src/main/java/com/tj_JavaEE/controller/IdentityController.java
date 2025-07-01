package com.tj_JavaEE.controller;


import com.tj_JavaEE.entity.Result;
import com.tj_JavaEE.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin
public class IdentityController {

    @GetMapping("/identity")
    public Result getIdentity(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            // 可以返回错误信息或者继续处理其他业务
           return Result.success("visitor");

        }

        String jwt = authorizationHeader.substring(7);
        log.info("请求头中的令牌是:{}",jwt);
        Claims claims= JwtUtils.parseJwt(jwt);
        log.info("结果为{}",claims);
        return Result.success(claims);


    }

    @GetMapping("/identity/view")
    public Result getIdentityView(HttpServletRequest request)
    {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            // 可以返回错误信息或者继续处理其他业务
            return Result.success("visitor");
        }
        String jwt = authorizationHeader.substring(7);
        Claims claims= JwtUtils.parseJwt(jwt);
        String idt = claims.get("identity", String.class);
        return Result.success(idt);
    }
}
