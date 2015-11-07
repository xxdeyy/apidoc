package com.f2boy.filter;

import com.f2boy.AdminUtils;
import com.f2boy.CommonStatic;
import com.f2boy.WebUtils;
import com.f2boy.domain.entity.Admin;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter - 记录用户最近一次访问的Url
 */
public class UrlRecordFilter extends OncePerRequestFilter {

    /**
     * 忽略URL
     */
    private List<String> ignoreUrls;

    public void setIgnoreUrls(List<String> ignoreUrlPatterns) {
        this.ignoreUrls = ignoreUrlPatterns;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestUri = request.getRequestURI();

        boolean isIgnoreUrl = false;
        for (String url : ignoreUrls) {
            if (requestUri.contains(url)) {
                isIgnoreUrl = true;
                break;
            }
        }

        if (!isIgnoreUrl) {

            if ("GET".equals(request.getMethod())) {

                Admin admin = AdminUtils.getCurrentAdmin(request, response);

                if (admin != null) {
                    // 如果用户已登录，首先判断用户之前是否有重定向的url，如果有，则跳转过去
                    String redirectUrl = (String) WebUtils.getSession(request, CommonStatic.REDIRECT_URL_SESSION_KEY);
                    if (StringUtils.isNotBlank(redirectUrl)) {
                        WebUtils.removeSession(request, CommonStatic.REDIRECT_URL_SESSION_KEY);
                        response.sendRedirect(redirectUrl);
                        return;
                    }
                } else {
                    // 记录用户最后一次有返回页面的的请求(get请求)url
                    String queryString = request.getQueryString();
                    if (queryString != null) {
                        WebUtils.setSession(request, CommonStatic.LAST_URL_SESSION_KEY,
                                request.getRequestURL().append("?").append(queryString).toString());
                    } else {
                        WebUtils.setSession(request, CommonStatic.LAST_URL_SESSION_KEY,
                                request.getRequestURL().toString());
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}
