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
 * 规则：
 *  1、扫描路径：classpath*:config/
 *  2、配置文件命名规则：[servlet-name]-servlet.xml
 *  3、servlet-mapping 映射格式：/[servlet-name]/*
 *
 * @author zhaojh
 */
public class AutomaticRegistrationSpringServlet implements WebApplicationInitializer {
    private static final Logger logger = LoggerFactory.getLogger(AutomaticRegistrationSpringServlet.class);
    private static final String SPRINGMVC_SERVLET_CONFIG_LOCATION_PATH = "classpath*:config/";
    private static final String SPRINGMVC_SERVLET_CONFIG_FILENAME_REGX = "*-servlet.xml";
    private static final String SPRINGMVC_SERVLET_CONFIG_MATCHER = SPRINGMVC_SERVLET_CONFIG_LOCATION_PATH + SPRINGMVC_SERVLET_CONFIG_FILENAME_REGX;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        config(servletContext);
        registServlet(servletContext);
    }

    private List<ServletConfig> getServletConfigList(){
        List<ServletConfig> configList = new ArrayList<ServletConfig>();
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

        try{
            Resource[] resources = resourceResolver.getResources(SPRINGMVC_SERVLET_CONFIG_MATCHER);

            for(Resource resource : resources){
                String configFilename = resource.getFilename();
                String servletName = getServletNameFromConfigFilename(configFilename);
                String servletConfigLocation = SPRINGMVC_SERVLET_CONFIG_LOCATION_PATH + configFilename;
                configList.add(new ServletConfig(servletName, servletConfigLocation));

                logger.info("found the servlet config file: {}", servletConfigLocation);
            }

            if(null == resources || resources.length == 0) logger.info("validated configuration files not found.");
        }catch(Exception e){
            throw new RuntimeException("Dynamic regist servlet fails. ", e);
        }

        return configList;
    }

    private String getServletNameFromConfigFilename(String servletConfigFilename){
        return servletConfigFilename.split("-")[0];
    }

    private class ServletConfig{
        private String servletName;
        private String servletConfigLocation;
        private String servletMapping;

        private ServletConfig(String servletName, String servletConfigLocation) {
            this.servletName = servletName;
            this.servletConfigLocation = servletConfigLocation;
            this.servletMapping = "/" + servletName + "/*";
        }

        public String getServletName() {
            return servletName;
        }

        public String getServletConfigLocation() {
            return servletConfigLocation;
        }

        public String getServletMapping() {
            return servletMapping;
        }

        @Override
        public String toString() {
            return "ServletConfig{" +
                    "servletName='" + servletName + '\'' +
                    ", servletConfigLocation='" + servletConfigLocation + '\'' +
                    ", servletMapping='" + servletMapping + '\'' +
                    '}';
        }
    }

    private void config(ServletContext servletContext){
        StringBuilder sb = new StringBuilder();
        String contextConfigLocation = servletContext.getInitParameter("contextConfigLocation");
        if(StringUtils.isNotBlank(contextConfigLocation)){
            sb.append(contextConfigLocation);
        }

        sb.append(",classpath*:config/spring-config.xml");
        logger.info("contextConfigLocation: {}", sb.toString());
        servletContext.setInitParameter("contextConfigLocation", sb.toString());
    }

    private void registServlet(ServletContext servletContext){
        List<ServletConfig> servletConfigList = getServletConfigList();
        if(!servletConfigList.isEmpty()){
            for(ServletConfig servletConfig : servletConfigList){
                logger.info(
                        "Dynamic registration servlet: {} , mapping: {}",
                        servletConfig.getServletName(),
                        servletConfig.getServletMapping()
                );

                XmlWebApplicationContext appContext = new XmlWebApplicationContext();
                appContext.setConfigLocation(servletConfig.getServletConfigLocation());

                ServletRegistration.Dynamic registration = servletContext.addServlet(
                        servletConfig.getServletName(),
                        new DispatcherServlet(appContext)
                );

                registration.setLoadOnStartup(1);
                registration.addMapping(servletConfig.getServletMapping());
            }

            logger.info("Dynamic registration finish.");
        }
    }
}
