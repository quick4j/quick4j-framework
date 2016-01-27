package com.github.quick4j.core.web.http.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhaojh. 基于HTML5处理跨域请求，要求浏览器支持。IE9+
 *
 *         Access-Control-Allow-Origin：允许访问的客户端域名。如:http://www.baidu.com　或者'*'。生产环境不建议使用'*'
 *         Access-Control-Allow-Methods：允许访问的方法名，多个方法名用逗号分割，例如：GET,POST,PUT,DELETE,OPTIONS。
 *         Access-Control-Allow-Credentials：是否允许请求带有验证信息，若要获取客户端域下的 cookie 时，需要将其设置为 true
 *         Access-Control-Allow-Headers：允许服务端访问的客户端请求头，多个请求头用逗号分割，例如：Content-Type, Authorization,
 *         Accept, X-Requested-With Access-Control-Expose-Headers：允许客户端访问的服务端响应头，多个响应头用逗号分割。
 */
public class Html5CorsFilter implements Filter {

  private String allowOrigin;
  private String allowMethods;
  private String allowCredentials;
  private String allowHeaders;
  private String exposeHeaders;

  @Override
  public void init(FilterConfig config) throws ServletException {
    this.allowOrigin = config.getInitParameter("allowOrigin");
    this.allowMethods = config.getInitParameter("allowMethods");
    this.allowCredentials = config.getInitParameter("allowCredentials");
    this.allowHeaders = config.getInitParameter("allowHeaders");
    this.exposeHeaders = config.getInitParameter("exposeHeaders");
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                       FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    if (StringUtils.isNotEmpty(allowOrigin)) {
      List<String> allowOriginList = Arrays.asList(allowOrigin.split(","));
      String currentOrigin = request.getHeader("Origin");
      if (allowOriginList != null) {
        if (allowOriginList.contains(currentOrigin)) {
          response.setHeader("Access-Control-Allow-Origin", currentOrigin);
        }
      }
    }

    if (StringUtils.isNotEmpty(allowMethods)) {
      response.setHeader("Access-Control-Allow-Methods", allowMethods);
    }

    if (StringUtils.isNotEmpty(allowCredentials)) {
      response.setHeader("Access-Control-Allow-Credentials", allowCredentials);
    }

    if (StringUtils.isNotEmpty(allowHeaders)) {
      response.setHeader("Access-Control-Allow-Headers", allowHeaders);
    }

    if (StringUtils.isNotEmpty(exposeHeaders)) {
      response.setHeader("Access-Control-Expose-Headers", exposeHeaders);
    }

    chain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {
  }
}
