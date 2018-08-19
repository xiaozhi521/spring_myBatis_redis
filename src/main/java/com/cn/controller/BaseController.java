package com.cn.controller;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {
    protected final String errorPage="forward:/common/error.jsp";

    /**
     *  获取requeset
     * @return
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     *  获取response
     * @return
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }
    /**
     * 获取session
     * @return
     */
    public HttpSession getSession() {
        return getRequest().getSession();
    }

    protected String redirectPath(String path){
        return new StringBuilder("redirect:").append(path).append(".jsp").toString();
    }

    protected String forwardPath(String path){
        return new StringBuilder("forward:/WEB-INF/jsp").append(path).append(".jsp").toString();
    }

}
