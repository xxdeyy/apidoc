package com.f2boy.controller;

import com.f2boy.AdminUtils;
import com.f2boy.domain.entity.Admin;
import com.f2boy.service.AdminService;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    private AdminService adminService;

    /**
     * 登录页面
     */
    @RequestMapping(name = "", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) {

        Admin admin = AdminUtils.getCurrentAdmin(request, response);

        if (admin != null) {
            return "redirect:/admin";
        }

        return "login";
    }

    /**
     * 执行登录
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doLogin(String username, String password,
                                       @RequestParam(required = false, defaultValue = "true") boolean rememberMe,
                                       HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("info", "登录失败");

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            map.put("info", "帐号或密码不能为空");
            return map;
        }

        Admin admin = adminService.get(username);
        if (admin == null) {
            map.put("info", "帐号不存在");
            return map;
        }

        String salt = admin.getPasswordSalt();
        String pwd = admin.getPassword();
        String loginPwd = DigestUtils.md5Hex(password + salt);
        if (!pwd.equals(loginPwd)) {
            map.put("info", "密码错误");
            return map;
        }

        // 用户存入session
        AdminUtils.setAdminToSession(request, response, admin);
        // 写入当前用户到Cookie
        if (rememberMe) {
            AdminUtils.setAdminToCookie(request, response, admin);
        }

        map.put("code", 1);
        return map;
    }

}
