package com.bjtu.testbox.controller;

import com.bjtu.testbox.config.api.R;
import io.swagger.annotations.Api;
import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;

@Api(description = "全局异常处理API")
@RestControllerAdvice
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    // 捕捉shiro的异常
    @ExceptionHandler(ShiroException.class)
    public R handle401() {
        logger.info("拦截Shiro异常...");
        return R.fail().code(401).msg("您没有权限访问！");
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    public R globalException(HttpServletRequest request, Throwable ex) {
        logger.info("拦截其他异常...");
        return R.fail().code(getStatus(request).value()).msg("访问出错，无法访问: " + ex.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
