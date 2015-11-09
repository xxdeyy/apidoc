package com.f2boy;

import org.apache.commons.lang.math.RandomUtils;

public class Environment {

//    private static final String SYS_VERSION = "20991231";
    private static final String SYS_VERSION = RandomUtils.nextInt(10000) + "";

    public String getSysVersion() {

        return SYS_VERSION;
    }

}
