package com.f2boy.controller;

import com.f2boy.CommonStatic;
import com.f2boy.domain.entity.Api;
import com.f2boy.domain.jsondo.ApiParams;
import com.f2boy.service.ApiModuleService;
import com.f2boy.service.ApiService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/api")
public class ApiController extends BaseController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private ApiModuleService apiModuleService;

    /**
     * 接口列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(required = false) Integer moduleId) {

        ModelAndView mav = new ModelAndView();

        List<Api> list;
        if (moduleId == null || moduleId < 0) {
            list = apiService.allApi();
        } else {
            list = apiService.findByModule(moduleId);
        }
        mav.addObject("list", list);
        mav.addObject("modules", apiModuleService.allModule());
        mav.addObject("moduleId", moduleId);

        mav.addObject(CommonStatic.PAGE_TITLE_VAR, "接口列表");
        mav.setViewName("api/list");
        return mav;
    }

    /**
     * 接口详情
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView info(int id) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("api", apiService.find(id));
        mav.addObject("modules", apiModuleService.allModule());

        mav.addObject(CommonStatic.PAGE_TITLE_VAR, "接口详情");
        mav.setViewName("api/detail");
        return mav;
    }

    /**
     * 添加\编辑接口页面
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam(required = false, defaultValue = "0") int id, Integer moduleId) {
        ModelAndView mav = new ModelAndView();

        Api api = null;

        if (id > 0) {
            api = apiService.find(id);
        }

        if (api == null) {
            api = new Api();
            moduleId = moduleId == null ? 0 : moduleId;
            api.setModuleId(moduleId);
            api.setSortNo(apiService.calculateMaxSortNo(moduleId) + 1);
        }

        mav.addObject("api", api);
        mav.addObject("modules", apiModuleService.allModule());

        mav.addObject(CommonStatic.PAGE_TITLE_VAR, "新增\\编辑接口");
        mav.setViewName("api/edit");
        return mav;
    }

    /**
     * 计算新增加的接口的排序号
     */
    @RequestMapping(value = "/calculateSortNo", method = RequestMethod.GET)
    @ResponseBody
    public int calculateSortNo(Integer moduleId) {
        moduleId = moduleId == null ? 0 : moduleId;

        return apiService.calculateMaxSortNo(moduleId) + 1;
    }

    /**
     * 提交编辑接口
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(Api api) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("info", "添加失败");

        if (StringUtils.isBlank(api.getName())) {
            map.put("info", "名称不能为空");
            return map;
        }

        if (StringUtils.isBlank(api.getUri())) {
            map.put("info", "uri不能为空");
            return map;
        }

        if (StringUtils.isBlank(api.getMethod())) {
            map.put("info", "http method不能为空");
            return map;
        }

        if (StringUtils.isBlank(api.getContentType())) {
            api.setContentType("form");
        }

        if (StringUtils.isBlank(api.getResponse())) {
            map.put("info", "响应参数不能为空");
            return map;
        }

        if (api.getModuleId() == null || api.getModuleId() < 0) {
            api.setModuleId(0);
        }

        if (api.getParams() != null) {
            ApiParams apiParams = api.getParams();
            for (int i = apiParams.size() - 1; i >= 0; --i) {
                ApiParams.SinglePara para = apiParams.get(i);
                if (StringUtils.isBlank(para.getKey())) {
                    apiParams.remove(i);
                }
            }
            Collections.sort(apiParams);
            for (int i = 0; i < apiParams.size(); ++i) {
                ApiParams.SinglePara para = apiParams.get(i);
                para.setSortNo(i + 1);
            }
        }

        apiService.save(api);

        map.put("code", 1);
        return map;
    }

    /**
     * 删除接口
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("info", "删除失败");

        apiService.delete(id);

        map.put("code", 1);
        return map;
    }
}
