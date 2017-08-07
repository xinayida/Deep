//package com.xinayida.deep.flux.action;
//
//import android.arch.lifecycle.LiveData;
//
//import com.xinayida.deep.api.AppService;
//import com.xinayida.deep.pages.category.model.ContentItem;
//import com.xinayida.deep.store.AppDatabase;
//import com.xinayida.deep.store.entity.JobMenu;
//import com.xinayida.deep.store.entity.Tag;
//import com.xinayida.rxflux.Action;
//import com.xinayida.rxflux.Dispatcher;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.inject.Inject;
//
//import io.reactivex.MaybeOnSubscribe;
//import io.reactivex.Observable;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.internal.operators.maybe.MaybeObserveOn;
//import io.reactivex.schedulers.Schedulers;
//
///**
// * Created by ww on 2017/7/26.
// */
//
//public class KnowlegeActionCreator {
//
//    @Inject
//    public KnowlegeActionCreator() {
//    }
//
//    @Inject
//    AppDatabase database;
//
//    /**
//     * 获取工作列表
//     */
//    public void loadJobsMenu(String CategoryId) {
//
//        Action action = Action.type(ActionType.LOAD_JOBS_MENU).build();
//        Observable.create((ObservableOnSubscribe<List<JobMenu>>) e -> {
//            List<JobMenu> list = new ArrayList<JobMenu>();
//            list.add(new JobMenu(1, 1, "test1"));
//            list.add(new JobMenu(2, 2, "test2"));
//            //TODO request from network or db
//            e.onNext(list);
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subJobs -> {
//                    action.getData().put(Key.SUB_JOBS, subJobs);
//                    Dispatcher.get().postAction(action);
//                });
//    }
//
//    public void loadTags() {
//        Action action = Action.type(ActionType.LOAD_TAGS).build();
//        Observable.create((ObservableOnSubscribe<List<Tag>>) e -> {
//            e.onNext(database.commonDao().getTags());
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(tags -> {
//                    action.getData().put(Key.TAGS, tags);
//                    Dispatcher.get().postAction(action);
//                });
//    }
//
//    public LiveData<List<ContentItem>> loadCategory(List<String> tags, String job) {
//        return database.categoryDao().getBaseContent(tags, job);
//    }
//}
