package com.f2boy.interceptor;

import com.f2boy.AdminUtils;
import com.f2boy.CommonStatic;
import com.f2boy.WebUtils;
import com.f2boy.domain.entity.Admin;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(AdminInterceptor.class);

    /**
     * 登录URL
     */
    private static final String loginUrl = "/login";

    // 排除的url
    private List<String> excludeUrls;

    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String requestUri = request.getRequestURI();
        String apiRoute = requestUri.substring(request.getContextPath().length());

        for (String s : excludeUrls) {
            if (apiRoute.startsWith(s)) {
                return true;
            }
        }

        Admin admin = AdminUtils.getCurrentAdmin(request, response);

        if (admin == null) {

            String header = request.getHeader("X-Requested-With");
            if (header != null && "xmlhttprequest".equals(StringUtils.lowerCase(header))) {
                logger.debug("没登陆的ajax请求 : " + request.getRequestURI());

                Map<String, Object> map = new HashMap<>();
                map.put("status", 8);
                map.put("redirect_url", loginUrl);

                WebUtils.writeJsonResponse(response, map);
            } else {

                String lastUrl = (String) WebUtils.getSession(request, CommonStatic.LAST_URL_SESSION_KEY);
                WebUtils.setSession(request, CommonStatic.REDIRECT_URL_SESSION_KEY, lastUrl);
                response.sendRedirect(request.getContextPath() + loginUrl);
            }

            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }
}