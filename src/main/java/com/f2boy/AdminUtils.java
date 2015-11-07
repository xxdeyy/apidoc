package com.f2boy;

import com.f2boy.domain.entity.Admin;
import com.f2boy.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 与管理员操作相关的方法
 */
public final class AdminUtils {

    private static AdminService adminService = (AdminService) SpringContextHelper.getApplicationContext().getBean(
            "adminService");

    /**
     * 不可实例化
     */
    private AdminUtils() {
    }

    /**
     * 获取当前登录管理员，若未登录则返回null
     */
    public static Admin getCurrentAdmin(HttpServletRequest request, HttpServletResponse response) {

        Admin admin = getAdminInSession(request);
        if (admin == null) {
            admin = getAdminInCookie(request, response);
            if (admin != null) {
                setAdminToSession(request, response, admin);
            }
        }

        return admin;
    }

    /**
     * 刷新session中当前管理员的信息
     */
    public static void refreshCurrentAdmin(HttpServletRequest request, HttpServletResponse response) {

        Admin admin = getAdminInSession(request);
        if (admin != null) {
            admin = adminService.get(admin.getId());
            setAdminToSession(request, response, admin);
        }
    }

    /**
     * 把管理员的信息存入session
     */
    public static void setAdminToSession(HttpServletRequest request, HttpServletResponse response, Admin admin) {

        WebUtils.setSession(request, CommonStatic.CURRENT_ADMIN_SESSION_KEY, admin);
    }

    /**
     * 把管理员信息存入cookie
     */
    public static void setAdminToCookie(HttpServletRequest request, HttpServletResponse response, Admin admin) {

        int expire = 3600 * 24;
        WebUtils.setCookie(response, CommonStatic.CURRENT_ADMIN_ID_COOKIE_KEY, admin.getId().toString(), expire);
    }

    /**
     * 从session中删除当前管理员信息
     */
    public static void removeAdminInSession(HttpServletRequest request, HttpServletResponse response) {

        WebUtils.removeSession(request, CommonStatic.CURRENT_ADMIN_SESSION_KEY);
    }

    /**
     * 从cookie中删除当前管理员信息
     */
    public static void removeAdminInCookie(HttpServletRequest request, HttpServletResponse response) {

        WebUtils.removeCookie(response, CommonStatic.CURRENT_ADMIN_ID_COOKIE_KEY);
    }

    // 从session中取得管理员
    private static Admin getAdminInSession(HttpServletRequest request) {

        return (Admin) WebUtils.getSession(request, CommonStatic.CURRENT_ADMIN_SESSION_KEY);
    }

    // 从cookie中取得管理员
    private static Admin getAdminInCookie(HttpServletRequest request, HttpServletResponse response) {

        String adminId = WebUtils.getCookie(request, CommonStatic.CURRENT_ADMIN_ID_COOKIE_KEY);
        if (adminId != null) {
            Integer mid = Integer.parseInt(adminId);
            Admin admin = adminService.get(mid);
            if (admin == null) {
                WebUtils.removeCookie(response, CommonStatic.CURRENT_ADMIN_ID_COOKIE_KEY);
            }

            return admin;
        } else {
            return null;
        }
    }

}
