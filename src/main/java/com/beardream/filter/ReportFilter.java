package com.beardream.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bearDream on 2017/5/17.
 * 打印报告的拦截器
 */
@Component
public class ReportFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        System.out.println("********************梦袋熊后端框架访问报告********************");
        System.out.println("********************访问时间："+ "  "+ new SimpleDateFormat("yyyyMMdd hh:mm:ss").format(new Date()) +"********************");
        System.out.println("*************访问的Controller："+request.getRequestURI()+"*************");
        System.out.println("*************Method："+request.getMethod()+"*************");
        System.out.println("*************Parameter参数："+request.getQueryString()+"*************");
        System.out.println("-------------------------------------------------------------");
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
