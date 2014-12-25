package com.github.quick4j.core.spring;

import com.github.quick4j.core.web.http.AjaxResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @author zhaojh
 */
@ControllerAdvice
public class GlobalControllerExceptionProcessor {
    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionProcessor.class);

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public AjaxResponse processBindException(HttpServletRequest request, BindException ex){
        String url = request.getRequestURL().toString();

        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = ex.getFieldErrors();
        for (FieldError fieldError : fieldErrors){
            String msg = String.format(
                    "field: [%s], message: %s",
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
            );
            logger.info(msg);
            message.append(",").append(fieldError.getDefaultMessage());
        }

        return new AjaxResponse(false, message.toString(), url, request.getMethod());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public AjaxResponse processConstraintViolationException(HttpServletRequest request,
                                                            ConstraintViolationException ex){
        String url = request.getRequestURL().toString();

        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        for (ConstraintViolation constraintViolation : constraintViolations){
            message.append(",").append(constraintViolation.getMessage());
            logger.info(constraintViolation.getMessage());
        }

        return new AjaxResponse(false, message.toString(), url, request.getMethod());
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AjaxResponse processException(HttpServletRequest request, Exception ex){
        String url = request.getRequestURL().toString();
        String message = ex.getMessage();

        logger.error("error: " + url, ex);
        ex.printStackTrace();

        return new AjaxResponse(AjaxResponse.Status.ERROR, message, url, request.getMethod());
    }
}
