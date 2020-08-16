package com.bjtu.testbox.controller;

import com.bjtu.testbox.config.api.R;
import com.bjtu.testbox.config.api.ResultMap;
import io.swagger.annotations.Api;
import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;

@Api(description = "全局异常处理API")
@RestControllerAdvice
@RequestMapping(produces = "application/json;charset=UTF-8")
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);
    @Autowired
    private ResultMap resultMap;

    // 捕捉shiro的异常
    @ExceptionHandler(ShiroException.class)
    public ResultMap handle401() {
        logger.info("拦截Shiro异常...");
        return resultMap.fail().code(401).msg("Have no permission to Access!");
    }

    // 捕捉其他所有异常
//    @ExceptionHandler(Exception.class)
//    public ResultMap globalException(HttpServletRequest request, Throwable ex) {
//        logger.info("拦截其他异常...");
//        return resultMap.fail().code(getStatus(request).value()).msg("can't access!: " + ex.getMessage());
//    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
