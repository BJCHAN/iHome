package com.tianchuang.ihome_b;

/**
 * Created by Abyss on 2017/2/14.
 * description:项目常量
 */

public class Constants {
    // TODO 开发用，发布时置为false
    public static final boolean DEBUG_MODE = true;// 应用内各种日志打印的开关，debug版本为true，release版本为false
    // TODO 开发用，发布时置为false
    public static final boolean HTTP_DEBUG_MODE = true;// 网络请求主机地址开关，debug版本为true，release版本为false

    public static class ConfigureTimeouts {
        /* 从连接池中取连接的超时时间 */
        public static final int GET_REQUEST_TIME_OUT = 10;
        /* 连接超时 */
        public static final int CONNECT_TIME_OUT = 15;
        /* 请求超时 */
        public static final int REQUEST_TIME_OUT = 30;
    }

    public static class Http {
        public static final String KEY = "parrot_android";
        public static final String SECRET = "ihomeba749952bc8c34d";

        public final static String getUrl() {
            if (HTTP_DEBUG_MODE) {
                return "http://api-s.wuguan365.com/";
            }
            return "http://api-s.wuguan365.com/";
        }
    }

    public static class QrCode {

        public static final int TASK_OPEN_CODE = 999;//任务请求扫码
        public static final int HOME_OPEN_CODE = 888;//主页扫码
    }

    public static class PERMISSION_REQUEST_CODE {
        //读写权限
        public static final int BASIC_PERMISSION_REQUEST_CODE = 100;
        //电话权限
        public static final int BASIC_PERMISSION_READ_PHONE_STATE_REQUEST_CODE = 110;
        //相机权限
        public static final int BASIC_PERMISSION_CAMERA_REQUEST_CODE = 120;
        //录音权限
        public static final int BASIC_PERMISSION_RECORD_AUDIO_REQUEST_CODE = 130;
        //位置权限
        public static final int BASIC_PERMISSION_LOCATION_REQUEST_CODE = 140;
        //通讯录权限
        public static final int BASIC_PERMISSION_CONTACTS_REQUEST_CODE = 150;
    }
}
