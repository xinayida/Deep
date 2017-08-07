package com.xinayida.deep.flux.store;


import com.xinayida.lib.rxflux.Action;
import com.xinayida.lib.rxflux.Store;

/**
 * Created by ww on 2017/7/26.
 */

public class AppStore extends Store {
    @Override
    protected boolean onAction(Action action) {
        return true;
    }

    @Override
    protected boolean onError(Action action, Throwable throwable) {
        return true;
    }
}
