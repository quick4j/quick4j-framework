package com.github.quick4j.core.web.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Locale;

/**
 * @author zhaojh
 */
public final class RequestContext {
    private static final Logger logger = LoggerFactory.getLogger(RequestContext.class);

    private static final ThreadLocal<RequestContext> requestContextContainer = new ThreadLocal<RequestContext>();

    private HttpServletRequest  request;
    private HttpServletResponse response;

    /**
     * 初始化
     */
    public static void init(HttpServletRequest request, HttpServletResponse response){
        RequestContext requestContext = new RequestContext();
        requestContext.request = request;
        requestContext.response = response;
        requestContextContainer.set(requestContext);
    }

    /**
     * 销毁
     */
    public static void destroy(){
        requestContextContainer.remove();
    }

    public static HttpServletRequest getHttpRequest(){
        return getInstance().request;
    }

    public static HttpSession getHttpSession(){
        return getHttpRequest().getSession();
    }

    public static String getRemoteUser(){
        return getHttpRequest().getRemoteUser();
    }

    public static Principal getUserPrincipal(){
        return getHttpRequest().getUserPrincipal();
    }

    public static Locale getLocal(){
        return getHttpRequest().getLocale();
    }

    private static RequestContext getInstance(){
        return requestContextContainer.get();
    }
}
