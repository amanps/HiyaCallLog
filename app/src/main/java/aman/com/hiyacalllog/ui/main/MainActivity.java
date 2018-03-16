package aman.com.hiyacalllog.ui.main;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import aman.com.hiyacalllog.R;
import aman.com.hiyacalllog.Utils;
import aman.com.hiyacalllog.model.CallLogItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    MainMvpPresenter<MainView> mPresenter;

    @BindView(R.id.call_log_list) RecyclerView mCallLog;
    @BindView(R.id.progress_indicator) ProgressBar mProgressBar;
    @BindView(R.id.permissions_denied_layout) LinearLayout mPermissionsDeniedLayout;
    @BindView(R.id.understand_btn) Button mUnderstandButton;

    CallListAdapter mCallLogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPresenter = new MainPresenter<>(this, getSupportLoaderManager());
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
        dismissError();
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
        mProgressBar.setVisibility(View.GONE);
        mCallLog.setVisibility(View.GONE);
        mPermissionsDeniedLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissError() {
        mPermissionsDeniedLayout.setVisibility(View.GONE);
    }

    @Override
    public void displayPermissionExplanationDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.permissions_request_message);
        alertDialogBuilder.setPositiveButton(R.string.i_understand, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.requestPermissions();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.deny, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                displayError();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
                    displayError();
                    break;
                }
                boolean logPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean outgoingPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                boolean statePermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                if (logPermission && outgoingPermission && statePermission) {
                    dismissError();
                    mPresenter.loadCallLog();
                } else {
                    displayError();
                }
                break;
        }
    }

    @OnClick(R.id.understand_btn)
    public void onClick() {
        mPresenter.requestPermissions();
    }

}
