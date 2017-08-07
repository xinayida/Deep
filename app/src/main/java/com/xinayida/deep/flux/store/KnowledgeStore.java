//package com.xinayida.deep.flux.store;
//
//import com.xinayida.deep.flux.action.Key;
//import com.xinayida.deep.store.entity.JobMenu;
//import com.xinayida.rxflux.Action;
//import com.xinayida.rxflux.Store;
//
//import java.util.List;
//
//import javax.inject.Inject;
//
//import static com.xinayida.deep.flux.action.ActionType.LOAD_TAGS;
//import static com.xinayida.deep.flux.action.ActionType.LOAD_JOB_CONTENTS;
//
///**
// * Created by ww on 2017/7/26.
// */
//
//public class KnowledgeStore extends Store {
//
//    private List<JobMenu> subJobs;
//    private List<String> tags;
//
//    public boolean isEditState() {
//        return editState;
//    }
//
//    public void setEditState(boolean editState) {
//        this.editState = editState;
//    }
//
//    private boolean editState;
//
//    public int getJobIndex() {
//        return jobIndex;
//    }
//
//    public void setJobIndex(int jobIndex) {
//        this.jobIndex = jobIndex;
//    }
//
//    private int jobIndex;
//
//    @Inject
//    public KnowledgeStore() {
//
//    }
//
//    @Override
//    protected boolean onAction(Action action) {
//        String type = action.getType();
//        if (LOAD_JOB_CONTENTS.equals(type)) {
//            subJobs = action.get(Key.SUB_JOBS);
//        } else if (LOAD_TAGS.equals(type)) {
//            tags = action.get(Key.TAGS);
//        }
//        return true;
//    }
//
//    @Override
//    protected boolean onError(Action action, Throwable throwable) {
//        return true;
//    }
//
//    public List<JobMenu> getSubJobs() {
//        return subJobs;
//    }
//
//    public List<String> getTags() {
//        return tags;
//    }
//}
