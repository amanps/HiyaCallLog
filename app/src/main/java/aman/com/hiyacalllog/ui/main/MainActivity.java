package aman.com.hiyacalllog.ui.main;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import aman.com.hiyacalllog.R;
import aman.com.hiyacalllog.Utils;
import aman.com.hiyacalllog.model.CallLogItem;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    MainPresenter mPresenter;
    @BindView(R.id.call_log_list) RecyclerView mCallLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPresenter = new MainPresenter(this, getSupportLoaderManager());
        mPresenter.attachView(this);

        mPresenter.loadCallLog();
    }

    @Override
    public void displayCallLog(ArrayList<CallLogItem> callLog) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mCallLog.setLayoutManager(layoutManager);
        mCallLog.setAdapter(new CallListAdapter(callLog));
    }

    @Override
    public void displayError() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utils.Constants.CALL_LOG_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPresenter.loadCallLog();
                } else {
                    /* TODO display error */
                }
        }
    }
}
