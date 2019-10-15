package com.handwrit.manage.config.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 甘银涛 on 2019/5/5 20:54
 */
@Component
@WebFilter(urlPatterns = {"/*"}, filterName = "accessFilter")
public class AccessFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "HEAD, POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Access-Control-Allow-Credentials", "true");

//        HttpSession session = request.getSession();
//        User user = UserUtil.getUser(session);
//        log.info("request url : " + request.getRequestURI());
//
//        if (user == null)
//        {
//            String currentURL = request.getRequestURI();
//            if (!currentURL.contains("/login"))
//            {
//                RequestUtil.commonOut(response,new Message(MessageCode.NOT_LOGIN));
//                return;
//            }
//        }
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
