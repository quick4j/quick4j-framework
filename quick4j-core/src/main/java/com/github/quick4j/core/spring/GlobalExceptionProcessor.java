package com.github.quick4j.core.spring;

import com.github.quick4j.core.exception.BizException;
import com.github.quick4j.core.web.http.JsonMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import java.util.Set;

/**
 * @author zhaojh
 */
@ControllerAdvice
public class GlobalExceptionProcessor{
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionProcessor.class);

    @Resource
    private MessageSource messageSource;

//    @ExceptionHandler(BindException.class)
//    @ResponseBody
//    public JsonResponse processBindException(HttpServletRequest request, BindException ex){
//        StringBuilder message = new StringBuilder();
//        List<FieldError> fieldErrors = ex.getFieldErrors();
//        for (FieldError fieldError : fieldErrors){
//            String msg = String.format(
//                    "field: [%s], message: %s",
//                    fieldError.getField(),
//                    fieldError.getDefaultMessage()
//            );
//            logger.info(msg);
//            message.append(",").append(fieldError.getDefaultMessage());
//        }
//
//        return new JsonResponse().failure(message.toString());
//    }


    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public JsonMessage processConstraintViolationException(HttpServletRequest request,
                                                            ConstraintViolationException ex){
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        for (ConstraintViolation constraintViolation : constraintViolations){
            message.append(",")
                    .append(constraintViolation.getPropertyPath())
                    .append(':')
                    .append(constraintViolation.getMessage());
            logger.info(constraintViolation.getMessage());
        }

        return new JsonMessage().failure(message.toString());
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public JsonMessage processBizException(HttpServletRequest request, BizException e){
        String message = messageSource.getMessage(e.getCode(), e.getArgs(), request.getLocale());
        return new JsonMessage().failure(message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonMessage processException(HttpServletRequest request, Exception ex){
        String url = request.getRequestURL().toString();
        String message = ex.getMessage();

        logger.error("error: " + url, ex);
        ex.printStackTrace();

        return new JsonMessage().failure(message);
    }
}
