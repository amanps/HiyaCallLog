package aman.com.hiyacalllog.ui.main;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import aman.com.hiyacalllog.R;
import aman.com.hiyacalllog.Utils;
import aman.com.hiyacalllog.model.CallLogItem;

public class MainActivity extends AppCompatActivity implements MainView {

    MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainPresenter(this, getSupportLoaderManager());
        mPresenter.attachView(this);

        mPresenter.loadCallLog();
    }

    @Override
    public void displayCallLog(ArrayList<CallLogItem> callLog) {

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
