package com.xiyouji.app.Constant;

/**
 * Created by houfang on 15/5/17.
 */
public class Constant {
    //public static final String IMAGE_UPLOAD_URL = "http://121.40.130.54/xiyouji/upload/";
    public static final String IMAGE_UPLOAD_URL = "http://121.43.149.36/xiyouji/upload/";

    public static final String DATABASE_NAME = "xiyouji.db";
    public static final int DB_VERSION = 1;

    //StartActivityForResult constant number
    public static final int START_LBS = 10;
    public static final int START_REGISTER = 11;
    public static final int START_CAR_INFO = 12;
    public static final int START_CAR_LOC = 13;
    public static final int START_PHONE_NUMBER = 14;
    public static final int START_ORDER = 15;
    public static final int START_WAIT_WASHING = 16;
    public static final int START_WAIT_WAITER = 17;
    public static final int START_ADVICE_PICTURE = 18;
    public static final int START_LOAD_IMAGE = 19;


    public static final int START_LBS_BACK = 100;
    public static final int START_REGISTER_BACK = 101;
    public static final int START_CAR_INFO_BACK = 102;
    public static final int START_CAR_LOC_BACK = 103;
    public static final int START_PHONE_NUMBER_BACK = 104;
    public static final int START_ORDER_SUCCESS_BACK = 105;
    public static final int CANCEL_ORDER_BACK = 106;
    public static final int START_ADVICE_PICTURE_BACK = 107;
    public static final int START_LOAD_IMAGE_BACK = 108;

    //rest http request
    public static final String GET_WEATHER_BY_CITY = "my/getWeatherByCity";
    public static final String REGISTER = "my/addUserByAccount";
    public static final String LOGIN = "my/userLoginByAccount";
    public static final String ADD_CAR_INFO = "my/addCarById";
    public static final String ADD_CAR_LOC = "my/addSiteById";
    public static final String GET_CAR_LIST_INFO = "my/getCarListById";
    public static final String GET_SITE_LIST = "my/getSiteListById";
    public static final String ADD_ORDER = "order/addOrderByAccount";
    public static final String GET_ORDER_LIST = "order/getOrderListById";
    public static final String GET_BRAND_LIST = "my/getBrandList";
    public static final String GET_VERSION_LIST_BY_BRAND = "my/getVersionListByBrand";
    public static final String DELETE_CAR_INFO = "my/deleteCarByAccount";
    public static final String DELETE_SITE = "my/deleteSiteByAccount";
    public static final String GET_DISCOUNT_LIST = "my/getTicketListById";
    public static final String GET_RECHARGE_RECORD_LIST = "my/getRechargeListById";
    public static final String GET_MONEY = "my/getMoneyById";
    public static final String RECHARGE = "my/rechargeByAccount";
    public static final String GET_ORDER_DETAIL = "order/getOrderDetail";
    public static final String GET_WAITER_INFO = "waiter/getWaiterByWaiterId";
    public static final String DELETE_ORDER = "order/deleteOrderByAccount";
    public static final String ADD_COMPLAIN = "order/addComplainById";
    public static final String UPLOAD_PIC = "image/uploadpic";
    public static final String UPLOAD_COMPLAIN_IMAGE = "image/userUploadComplainImage";
    public static final String GET_WAITER_IMAGE_AFTER = "image/getWaiterImageAfterWash";
    public static final String GET_WAITER_ICON = "image/getWaiterIcon";
    public static final String UPLOAD_USER_ICON ="image/userUploadIcon";
    public static final String GET_USER_ICON = "image/getUserIcon";
    public static final String SEND_MSG = "Sms/sendSms";
    public static final String PAY = "pay/pay";
}
