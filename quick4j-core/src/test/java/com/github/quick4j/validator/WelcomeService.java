package com.github.quick4j.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * @author zhaojh.
 */
@Service
@Validated
public class WelcomeService {
    public void hello(@Valid User user){}
}
