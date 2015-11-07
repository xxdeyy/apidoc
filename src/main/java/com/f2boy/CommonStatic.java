package com.f2boy;

public final class CommonStatic {

    /**
     * 不可实例化
     */
    private CommonStatic() {
    }

    /**
     * velocity 页面title的变量，在defaul.vm文件中使用
     */
    public static final String PAGE_TITLE_VAR = "_page_title";

    // 发生错误的视图
    public static final String ERROR_VIEW = "common/error";

    // session
    public static final String CURRENT_ADMIN_SESSION_KEY = "CURRENT_ADMIN";
    public static final String VER_CODE_SESSION_KEY = "VER_CODE";
    public static final String REDIRECT_URL_SESSION_KEY = "REDIRECT_URL";
    public static final String LAST_URL_SESSION_KEY = "LAST_URL";

    // cookie
    public static final String CURRENT_ADMIN_ID_COOKIE_KEY = "CURRENT_ADMIN_ID";
    public static final String CURRENT_ADMIN_SESSION_ID_COOKIE_KEY = "CURRENT_ADMIN_SESSION_ID";
    public static final String CURRENT_ADMIN_QRCODE_KEY = "QRCODE";

    /**
     * 上传头像允许的文件格式
     */
    public static final String[] AVATAR_EXTENSION_RANGE = {".jpg", ".png", ".gif", ".jpeg"};

    // 用户没有上传头像时，默认显示的图像
    public static final String NOAVATAR_IMAGE_BIG = "/static/images/noavatar/big.jpg";
    public static final String NOAVATAR_IMAGE_MIDDLE = "/static/images/noavatar/middle.jpg";
    public static final String NOAVATAR_IMAGE_SMALL = "/static/images/noavatar/small.jpg";
    public static final String NOAVATAR_IMAGE_TINY = "/static/images/noavatar/tiny.jpg";

    // 用户上传文件的临时目录
    public static final String FILE_TMP_DIR = "/upload/tmp";

    // 图片水印
    public static final String WATERMARK_IMAGE = "/storage/watermark.png";

}
