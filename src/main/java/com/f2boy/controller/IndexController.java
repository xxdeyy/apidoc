package com.f2boy.controller;

import com.f2boy.domain.entity.Api;
import com.f2boy.domain.entity.ApiModule;
import com.f2boy.service.ApiModuleService;
import com.f2boy.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
public class IndexController extends BaseController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private ApiModuleService apiModuleService;

    @RequestMapping("")
    public ModelAndView index() {

        ModelAndView mav = new ModelAndView();

        List<Api> apis = apiService.allApi();
        List<ApiModule> modules = apiModuleService.allModule();

        // 模块与接口的映射map
        Map<Integer, List<Api>> moduleApiMap = new HashMap<>(modules.size());
        for (ApiModule module : modules) {
            int key = module.getId();
            if (!moduleApiMap.containsKey(key)) {
                moduleApiMap.put(key, new ArrayList<>());
            }

            List<Api> list = moduleApiMap.get(key);
            for (Api api : apis) {
                if (api.getModuleId().equals(key)) {
                    list.add(api);
                }
            }
        }

        // 没有指定模块的接口集合
        List<Api> noModuleApis = new ArrayList<>();
        for (Api api : apis) {
            boolean noModule = true;
            for (ApiModule module : modules) {
                if (module.getId().equals(api.getModuleId())) {
                    noModule = false;
                    break;
                }
            }
            if (noModule) {
                noModuleApis.add(api);
            }
        }

        // 对没有指定模块对接口集合做数据较正
        for (Api api : noModuleApis) {
            if (api.getModuleId() == null || api.getModuleId() != 0) {
                api.setModuleId(0);
                apiService.save(api);
            }
        }

        mav.addObject("modules", modules);
        mav.addObject("moduleApiMap", moduleApiMap);
        mav.addObject("noModuleApis", noModuleApis);

        mav.setViewName("index");
        return mav;
    }

}
