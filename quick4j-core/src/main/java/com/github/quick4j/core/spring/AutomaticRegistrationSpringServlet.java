package com.github.quick4j.core.spring;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动注册spring servlet
 *
 * 规则： 1、扫描路径：classpath*:config/ 2、配置文件命名规则：[servlet-name]-servlet.xml 3、servlet-mapping
 * 映射格式：/[servlet-name]/*
 *
 * @author zhaojh
 */
public class AutomaticRegistrationSpringServlet implements WebApplicationInitializer {

  private static final Logger
      logger =
      LoggerFactory.getLogger(AutomaticRegistrationSpringServlet.class);
  private static final String SPRINGMVC_SERVLET_CONFIG_LOCATION_PATH = "classpath*:config/";
  private static final String SPRINGMVC_SERVLET_CONFIG_FILENAME_REGX = "*-servlet.xml";
  private static final String
      SPRINGMVC_SERVLET_CONFIG_MATCHER =
      SPRINGMVC_SERVLET_CONFIG_LOCATION_PATH + SPRINGMVC_SERVLET_CONFIG_FILENAME_REGX;

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    config(servletContext);
    registServlet(servletContext);
  }

  private void config(ServletContext servletContext) {
    StringBuilder sb = new StringBuilder();
    String contextConfigLocation = servletContext.getInitParameter("contextConfigLocation");
    if (StringUtils.isNotBlank(contextConfigLocation)) {
      sb.append(contextConfigLocation);
    }

    sb.append(",classpath*:config/spring-config.xml");
    logger.info("contextConfigLocation: {}", sb.toString());
    servletContext.setInitParameter("contextConfigLocation", sb.toString());
  }

  private void registServlet(ServletContext servletContext) {
    List<ServletConfig> servletConfigList = getServletConfigList();
    if (!servletConfigList.isEmpty()) {
      for (ServletConfig servletConfig : servletConfigList) {
        logger.info(
            "Dynamic registration servlet: {} , mapping: {}",
            servletConfig.getName(),
            servletConfig.getMapping()
        );

        XmlWebApplicationContext appContext = new XmlWebApplicationContext();
        appContext.setConfigLocation(servletConfig.getConfigLocation());

        ServletRegistration.Dynamic registration = servletContext.addServlet(
            servletConfig.getName(),
            new DispatcherServlet(appContext)
        );

        registration.setLoadOnStartup(1);

        if (servletConfig.getName().equalsIgnoreCase("root")) {
          registration.addMapping("/");
        } else {
          registration.addMapping(servletConfig.getMapping());
        }
      }

      logger.info("Dynamic registration finish.");
    }
  }

  private List<ServletConfig> getServletConfigList() {
    List<ServletConfig> configList = new ArrayList<ServletConfig>();
    ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    try {
      Resource[] resources = resourceResolver.getResources(SPRINGMVC_SERVLET_CONFIG_MATCHER);

      for (Resource resource : resources) {
        String configFilename = resource.getFilename();
        String servletName = getServletNameFromConfigFilename(configFilename);
        String servletConfigLocation = SPRINGMVC_SERVLET_CONFIG_LOCATION_PATH + configFilename;
        configList.add(new ServletConfig(servletName, servletConfigLocation));

        logger.info("found the servlet config file: {}", servletConfigLocation);
      }

      if (null == resources || resources.length == 0) {
        logger.info("validated configuration files not found.");
      }
    } catch (Exception e) {
      throw new RuntimeException("Dynamic regist servlet fails. ", e);
    }

    return configList;
  }

  private String getServletNameFromConfigFilename(String servletConfigFilename) {
    if (servletConfigFilename.indexOf('-') == -1) {
      String message = "[%s]命名不符合规范，请按[servlet-name]-servlet.xml格式修改。";
      throw new RuntimeException(String.format(message, servletConfigFilename));
    }
    return servletConfigFilename.split("-")[0];
  }

  private class ServletConfig {

    private String name;
    private String configLocation;
    private String mapping;

    private ServletConfig(String servletName, String servletConfigLocation) {
      this.name = servletName;
      this.configLocation = servletConfigLocation;
      this.mapping = "/" + servletName + "/*";
    }

    public String getName() {
      return name;
    }

    public String getConfigLocation() {
      return configLocation;
    }

    public String getMapping() {
      return mapping;
    }

    @Override
    public String toString() {
      return "ServletConfig{" +
             "name='" + name + '\'' +
             ", configLocation='" + configLocation + '\'' +
             ", mapping='" + mapping + '\'' +
             '}';
    }
  }
}
