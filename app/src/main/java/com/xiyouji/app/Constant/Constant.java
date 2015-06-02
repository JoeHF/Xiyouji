package com.xiyouji.app.Constant;

/**
 * Created by houfang on 15/5/17.
 */
public class Constant {
    //StartActivityForResult constant number
    public static final int START_LBS = 10;
    public static final int START_REGISTER = 11;
    public static final int START_CAR_INFO = 12;
    public static final int START_CAR_LOC = 13;
    public static final int START_PHONE_NUMBER = 14;


    public static final int START_LBS_BACK = 100;
    public static final int START_REGISTER_BACK = 101;
    public static final int START_CAR_INFO_BACK = 102;
    public static final int START_CAR_LOC_BACK = 103;
    public static final int START_PHONE_NUMBER_BACK = 104;


    //rest http request
    public static final String GET_WEATHER_BY_CITY = "my/getWeatherByCity";
    public static final String REGISTER = "my/addUserByAccount";
    public static final String LOGIN = "my/userLoginByAccount";
    public static final String ADD_CAR_INFO = "my/addCarById";
    public static final String ADD_CAR_LOC = "my/addSiteById";
    public static final String GET_CAR_LIST_INFO = "my/getCarListById";
    public static final String GET_CAR_LOC_LIST_INFO = "my/getSiteListById";
    public static final String ADD_ORDER = "order/addOrderByAccount";
}
