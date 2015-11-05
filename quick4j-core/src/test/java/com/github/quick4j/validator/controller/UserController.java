package com.github.quick4j.validator.controller;

import com.github.quick4j.core.web.http.JsonResponse;
import com.github.quick4j.validator.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author zhaojh.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(
            value = "/new",
            method = RequestMethod.POST,
            produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    public JsonResponse doCreate(@Valid User user){
        System.out.println();
        return new JsonResponse().success();
    }
}
