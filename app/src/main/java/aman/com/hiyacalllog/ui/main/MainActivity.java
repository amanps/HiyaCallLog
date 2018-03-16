package aman.com.hiyacalllog.ui.main;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import aman.com.hiyacalllog.R;
import aman.com.hiyacalllog.Utils;
import aman.com.hiyacalllog.model.CallLogItem;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    MainPresenter mPresenter;

    @BindView(R.id.call_log_list) RecyclerView mCallLog;
    @BindView(R.id.progress_indictor) ProgressBar mProgressBar;

    CallListAdapter mCallLogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPresenter = new MainPresenter(this, getSupportLoaderManager());
        mPresenter.attachView(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mCallLog.setLayoutManager(layoutManager);
        mCallLogAdapter = new CallListAdapter();
        mCallLog.setAdapter(mCallLogAdapter);

        mPresenter.loadCallLog();
    }

    @Override
    public void updateCallLog(ArrayList<CallLogItem> callLog) {
        dismissProgressBar();
        if (mCallLogAdapter == null) {
            mCallLogAdapter = new CallListAdapter();
        }
        mCallLogAdapter.setData(callLog);
    }

    @Override
    public void displayProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mCallLog.setVisibility(View.GONE);
    }

    @Override
    public void dismissProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mCallLog.setVisibility(View.VISIBLE);
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
            case Utils.Constants.MULTIPLE_PERMISSIONS_CODE:
                if (grantResults.length <= 0){
                    break;
                }
                boolean logPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                boolean statePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (logPermission) {
                    mPresenter.loadCallLog();
                } else {
                    displayError();
                }
                break;
        }
    }

}
