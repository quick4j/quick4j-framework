package com.github.quick4j.validator.controller;

import com.github.quick4j.core.web.http.JsonResponse;
import com.github.quick4j.validator.User;
import com.github.quick4j.validator.WelcomeService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author zhaojh.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private WelcomeService welcomeService;

    @RequestMapping(
            value = "/new",
            method = RequestMethod.POST,
            produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    public JsonResponse doCreate(@Valid User user){
        return new JsonResponse().success();
    }

    @RequestMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JsonResponse hello(User user){
        welcomeService.hello(user);
        return new JsonResponse().success();
    }
}
