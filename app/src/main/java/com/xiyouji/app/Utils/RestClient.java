package com.xiyouji.app.Utils;

/**
 * Created by houfang on 15/5/28.
 */
import com.loopj.android.http.*;

public class RestClient {
    //private static final String BASE_URL = "http://121.40.130.54/xiyouji/index.php/";
    private static final String BASE_URL = "http://121.43.149.36/xiyouji/index.php/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}