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

  private MockMvc mockMvc;
  @Resource
  private WebApplicationContext context;
  @Resource
  private WelcomeService welcomeService;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  public void testNameNotEmptyOnController() throws Exception {
    mockMvc.perform(
        post("/user/new")
            .param("password", "123456")
            .accept(MediaType.parseMediaType("application/json;charset=utf-8"))
    )
//        .andExpect(jsonPath("$.meta.success").value(Boolean.FALSE))
        .andDo(print());
  }

  @Test
  public void testPasswordLengthOnController() throws Exception {
    mockMvc.perform(
        post("/user/new")
            .param("name", "loafer")
            .param("password", "123")
            .accept(MediaType.parseMediaType("application/json;charset=utf-8"))
    )
//        .andExpect(jsonPath("$.meta.success").value(Boolean.FALSE))
        .andDo(print());
  }

  @Test
  public void testMultiFieldValidateOnController() throws Exception {
    mockMvc.perform(
        post("/user/new")
            .param("password", "123")
            .accept(MediaType.parseMediaType("application/json;charset=utf-8"))
    )
//        .andExpect(jsonPath("$.meta.success").value(Boolean.FALSE))
        .andDo(print());
  }

  @Test
  public void testRequestMethod() throws Exception {
    mockMvc.perform(
        get("/user/new")
            .param("name", "loafer")
            .param("password", "123")
            .accept(MediaType.parseMediaType("application/json;charset=utf-8"))
    ).andDo(print());
  }

  @Test
  public void testNameNotEmptyOnService() throws Exception {
    mockMvc.perform(
        post("/user/hello")
            .param("password", "123456")
            .accept(MediaType.parseMediaType("application/json;charset=utf-8"))
    ).andDo(print());
  }
}
