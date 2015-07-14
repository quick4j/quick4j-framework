package com.github.quick4j.validator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * @author zhaojh.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
        "/spring-config.xml",
        "/spring-config-mybatis.xml",
        "/spring-config-validator.xml",
        "/config/user-servlet.xml"
})
public class ValidatorTest {
    @Resource
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testValidator() throws Exception {
        mockMvc.perform(
                post("/user/new")
                .param("name", "loafer1111")
                        .param("password", "123456")
                .accept(MediaType.parseMediaType("application/json;charset=utf-8"))
        )
//        .andExpect(jsonPath("$.success").value(Boolean.FALSE))
        .andDo(print());
    }
}
