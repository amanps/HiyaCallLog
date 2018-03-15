package aman.com.hiyacalllog.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import aman.com.hiyacalllog.R;
import aman.com.hiyacalllog.model.CallLogItem;

public class MainActivity extends AppCompatActivity implements MainView {

    MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter.attachView(this);

        mPresenter = new MainPresenter();

        mPresenter.loadCallLog();
    }

    @Override
    public void displayCallLog(ArrayList<CallLogItem> callLog) {

    }

    @Override
    public void displayError() {

    }
}
