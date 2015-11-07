package com.f2boy.domain.jsondo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 接口入参列表
 */
public class ApiParams extends ArrayList<ApiParams.SinglePara> {

    public static class SinglePara implements Serializable, Comparable<SinglePara> {

        /**
         * 参数
         */
        private String key;

        /**
         * 参数类型
         */
        private String type;

        /**
         * 是否必须
         */
        private boolean required;

        /**
         * 参数描述
         */
        private String description;

        /**
         * 排序号
         */
        private int sortNo;

        private static final long serialVersionUID = 1L;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key == null ? null : key.trim();
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type == null ? null : type.trim();
        }

        public boolean getRequired() {
            return required;
        }

        public void setRequired(boolean required) {
            this.required = required;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description == null ? null : description.trim();
        }

        public int getSortNo() {
            return sortNo;
        }

        public void setSortNo(int sortNo) {
            this.sortNo = sortNo;
        }

        @Override
        public int compareTo(SinglePara o) {
            if (o == null) {
                return 1;
            }

            return this.getSortNo() - o.getSortNo();
        }
    }

}
