package com.example.bsproperty.net;

/**
 * Created by yezi on 2018/1/27.
 */

public class ApiManager {

    private static final String HTTP = "http://";
    private static final String IP = "192.168.55.103";
    private static final String PROT = ":8080";
    private static final String HOST = HTTP + IP + PROT;
    private static final String API = "/api";
    private static final String USER = "/user";
    private static final String QU = "/question";
    private static final String ERROR = "/error";


    public static final String USER_RG = HOST + API + USER + "/register";
    public static final String USER_LOGIN = HOST + API + USER + "/login";
    public static final String USER_MODIFY = HOST + API + USER + "/modify";

    public static final String QU_LIST = HOST + API + QU + "/list";
    public static final String QU_ERROR = HOST + API + QU + "/listError";
    public static final String QU_RIGHT = HOST + API + QU + "/listRight";

    public static final String ERROR_LIST = HOST + API + ERROR + "/list";
    public static final String ERROR_DEL = HOST + API + ERROR + "/del";
}
