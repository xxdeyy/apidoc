package com.f2boy.handler;

import com.f2boy.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义的全局异常处理类
 */
public class CustomMappingExceptionResolver extends SimpleMappingExceptionResolver {

    private static Logger logger = LoggerFactory.getLogger(CustomMappingExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                              Exception ex) {

        logger.error("请求悲剧了！！", ex);

        // 是否ajax请求
        String header = request.getHeader("X-Requested-With");
        if (header != null && "xmlhttprequest".equals(StringUtils.lowerCase(header))) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", 0);
            map.put("info", "服务器异常");

            try {
                WebUtils.writeJsonResponse(response, map);
            } catch (IOException e) {
                logger.error("输出json居然也异常了！！", e);
            }
            return null;
        } else {
            return super.doResolveException(request, response, handler, ex);
        }
    }

}
