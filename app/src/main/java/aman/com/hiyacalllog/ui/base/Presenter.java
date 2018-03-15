package aman.com.hiyacalllog.ui.base;

import android.view.View;

/**
 * Created by amanpreetsingh on 3/15/18.
 */

public interface Presenter<V extends BaseView> {

    void attachView(V v);

    void detachView();
}
