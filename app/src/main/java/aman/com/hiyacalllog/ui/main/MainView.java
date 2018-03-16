package aman.com.hiyacalllog.ui.main;

import java.util.ArrayList;

import aman.com.hiyacalllog.model.CallLogItem;
import aman.com.hiyacalllog.ui.base.BaseView;

/**
 * Created by amanpreetsingh on 3/15/18.
 */

public interface MainView extends BaseView {

    void updateCallLog(ArrayList<CallLogItem> callLog);

    void displayError();

}
