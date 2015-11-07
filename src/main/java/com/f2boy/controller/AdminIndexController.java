package com.f2boy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminIndexController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AdminIndexController.class);

    @RequestMapping("")
    public String index() {

        return "forward:/admin/api/list";
    }

}
