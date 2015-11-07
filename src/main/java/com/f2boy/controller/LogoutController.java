package com.f2boy.controller;

import com.f2boy.AdminUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    /**
     * 登出
     */
    @RequestMapping("")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        AdminUtils.removeAdminInCookie(request, response);
        AdminUtils.removeAdminInSession(request, response);

        return "redirect:/login";
    }

}
