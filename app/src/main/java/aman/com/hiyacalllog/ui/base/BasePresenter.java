package aman.com.hiyacalllog.ui.base;

import android.view.View;

/**
 * Created by amanpreetsingh on 3/15/18.
 */

public class BasePresenter<T extends BaseView> implements Presenter<T> {

    T mView;

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void checkViewAttached() {
        if (mView == null) throw new ViewNotAttachedException();
    }

    public static class ViewNotAttachedException extends RuntimeException {
        public ViewNotAttachedException() {
            super("No view attached! Call Presenter.attachView(view) first.");
        }
    }
}
