package com.xinayida.lib.network;

import android.net.ParseException;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;

import retrofit2.adapter.rxjava2.HttpException;

/**
 * Created by stephan on 16/7/21.
 */
public class ExceptionEngine {

    //对应HTTP的状态码
//    private static final int UNAUTHORIZED = 401;
//    private static final int FORBIDDEN = 403;
//    private static final int NOT_FOUND = 404;
//    private static final int REQUEST_TIMEOUT = 408;
//    private static final int INTERNAL_SERVER_ERROR = 500;
//    private static final int BAD_GATEWAY = 502;
//    private static final int SERVICE_UNAVAILABLE = 503;
//    private static final int GATEWAY_TIMEOUT = 504;

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        e.printStackTrace();
        if (e instanceof HttpException) {             //HTTP错误
            HttpException httpException = (HttpException) e;
            String errorBody = null;
            ErrorBean errorBean = null;
            try {
                errorBody = httpException.response().errorBody().string();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if(errorBody!=null){
                Gson gson = new Gson();
                errorBean = gson.fromJson(errorBody, ErrorBean.class);
            }

            ex = new ApiException(httpException.message(), httpException.code(), errorBean);
//            switch (httpException.code()) {
//                case UNAUTHORIZED:
//                case FORBIDDEN:
//                case NOT_FOUND:
//                case REQUEST_TIMEOUT:
//                case GATEWAY_TIMEOUT:
//                case INTERNAL_SERVER_ERROR:
//                case BAD_GATEWAY:
//                case SERVICE_UNAVAILABLE:
//                default:
//                    ex.message = "网络错误";  //均视为网络错误
//                    break;
//            }
            return ex;
//        } else if (e instanceof ServerException) {    //服务器返回的错误
//            ServerException resultException = (ServerException) e;
//            ex = new ApiException(resultException, resultException.code);
//            ex.message = resultException.message;
//            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ApiException("解析错误", ERROR.PARSE_ERROR);
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException("连接失败", ERROR.NETWORD_ERROR);
            return ex;
        } else {
//            e.printStackTrace();
            ex = new ApiException(e.toString(), ERROR.PROGRAM);
            return ex;
        }
    }


    /**
     * 约定异常
     */

    public class ERROR {
        /**
         * 程序错误
         */
        public static final int PROGRAM = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
//        /**
//         * 协议出错
//         */
//        public static final int HTTP_ERROR = 1003;
    }

}
