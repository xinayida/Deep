package com.xinayida.lib.network;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 服务器错误异常处理代理类
 * Created by stephan on 16/7/21.
 */

//import rx.Observable;
//import rx.functions.Func1;

public class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
//    @Override
//    public Observable<T> call(Throwable throwable) {
//        //ExceptionEngine为处理异常的驱动器
//        return Observable.error(ExceptionEngine.handleException(throwable));
//    }


    @Override
    public Observable<T> apply(@NonNull Throwable throwable) throws Exception {
        //ExceptionEngine为处理异常的驱动器
        return Observable.error(ExceptionEngine.handleException(throwable));
    }
}