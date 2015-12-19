package com.f2boy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

    public static class BBB {
        List<AAA> aaas;

        public List<AAA> getAaas() {
            return aaas;
        }

        public void setAaas(List<AAA> aaas) {
            this.aaas = aaas;
        }

        @Override
        public String toString() {
            return "BBB{" +
                    "aaas=" + aaas +
                    '}';
        }
    }

    public static class AAA {
        private String a;
        private String b;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        @Override
        public String toString() {
            return "AAA{" +
                    "a='" + a + '\'' +
                    ", b='" + b + '\'' +
                    '}';
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doLogin(BBB bbb) {

        System.out.println(bbb);

        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("info", "呵呵");

        return map;
    }

}
