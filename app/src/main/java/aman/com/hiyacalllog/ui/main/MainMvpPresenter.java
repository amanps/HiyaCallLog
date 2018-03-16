package aman.com.hiyacalllog.ui.main;

import aman.com.hiyacalllog.ui.base.Presenter;

/**
 * Created by amanpreetsingh on 3/16/18.
 */

public interface MainMvpPresenter<V extends MainView> extends Presenter<V> {
    void loadCallLog();
}
