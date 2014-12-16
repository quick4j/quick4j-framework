package com.github.quick4j.core.spring;

import com.github.quick4j.core.web.http.AjaxResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhaojh
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AjaxResponse processException(HttpServletRequest request, Exception ex){
        String url = request.getRequestURL().toString();
        String message = ex.getMessage();

        logger.error("error: " + url, ex);
        ex.printStackTrace();

        return new AjaxResponse(AjaxResponse.Status.ERROR, message, url, request.getMethod());
    }
}
