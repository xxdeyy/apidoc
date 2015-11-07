package com.f2boy;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Utils - Web
 */
public final class WebUtils {

    /**
     * 不可实例化
     */
    private WebUtils() {
    }

    /**
     * 设置Session
     */
    public static void setSession(HttpServletRequest request, String key, Object value) {

        HttpSession session = request.getSession();
        session.setAttribute(key, value);
    }

    /**
     * 获取Session
     */
    public static Object getSession(HttpServletRequest request, String key) {

        return request.getSession().getAttribute(key);
    }

    /**
     * 删除Session
     */
    public static void removeSession(HttpServletRequest request, String key) {

        HttpSession session = request.getSession();
        session.removeAttribute(key);
    }

    /**
     * 设置Cookie
     */
    public static void setCookie(HttpServletResponse response, String key, String value, int expiry) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(expiry);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     */
    public static String getCookie(HttpServletRequest request, String key) {

        Cookie[] cookies = request.getCookies();

        if (key == null || cookies == null) {
            return null;
        }

        for (Cookie ele : cookies) {
            if (key.equalsIgnoreCase(ele.getName())) {
                return ele.getValue();
            }
        }

        return null;
    }

    /**
     * 删除Cookie
     */
    public static void removeCookie(HttpServletResponse response, String key) {

        setCookie(response, key, null, 0);
    }

    /**
     * 返回响应内容 -- json格式
     *
     * @throws IOException response.getWriter()抛出的
     */
    public static void writeJsonResponse(HttpServletResponse response, Map<String, Object> map) throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(JsonUtils.object2Json(map));

    }

}