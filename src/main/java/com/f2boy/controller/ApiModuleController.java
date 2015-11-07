package com.f2boy.controller;

import com.f2boy.domain.entity.ApiModule;
import com.f2boy.service.ApiModuleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 接口模块
 */
@Controller
@RequestMapping("/admin/api_module")
public class ApiModuleController extends BaseController {

    @Autowired
    private ApiModuleService apiModuleService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {

        ModelAndView mav = new ModelAndView();
        mav.addObject("list", apiModuleService.allModule());

        mav.setViewName("api_module/list");
        return mav;
    }

    /**
     * 添加\编辑模块页面
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam(required = false, defaultValue = "0") int id) {
        ModelAndView mav = new ModelAndView();

        ApiModule module = null;

        if (id > 0) {
            module = apiModuleService.find(id);
        }

        if (module == null) {
            module = new ApiModule();
            module.setSortNo(apiModuleService.calculateMaxSortNo() + 1);
        }

        mav.addObject("module", module);

        mav.setViewName("api_module/edit");
        return mav;
    }

    /**
     * 提交编辑接口模块
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(ApiModule apiModule) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("info", "保存失败");

        if (StringUtils.isBlank(apiModule.getName())) {
            map.put("info", "名称不能为空");
            return map;
        }

        apiModuleService.save(apiModule);

        map.put("code", 1);
        return map;
    }

    /**
     * 删除接口模块
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("info", "删除失败");

        apiModuleService.delete(id);

        map.put("code", 1);
        return map;
    }
}
