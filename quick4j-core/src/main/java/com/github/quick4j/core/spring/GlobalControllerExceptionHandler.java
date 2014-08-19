package com.github.quick4j.core.spring;

import com.github.quick4j.core.web.http.AjaxResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhaojh
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AjaxResponse processException(HttpServletRequest request, Exception ex){
        String url = request.getRequestURL().toString();
        String message = ex.getMessage();
        return new AjaxResponse(AjaxResponse.Status.ERROR, message, url);
    }
}
