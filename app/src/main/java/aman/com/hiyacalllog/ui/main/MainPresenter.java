package aman.com.hiyacalllog.ui.main;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import java.util.ArrayList;

import aman.com.hiyacalllog.Utils;
import aman.com.hiyacalllog.model.CallLogItem;
import aman.com.hiyacalllog.ui.base.BasePresenter;

/**
 * Created by amanpreetsingh on 3/15/18.
 */

public class MainPresenter<V extends MainView> extends BasePresenter<V> implements MainMvpPresenter<V>, LoaderManager.LoaderCallbacks<Cursor> {

    private Context mContext;
    private LoaderManager mLoaderManager;

    public MainPresenter(Context context, LoaderManager loaderManager) {
        mContext = context;
        mLoaderManager = loaderManager;
    }

    @Override
    public void loadCallLog() {
        getView().displayProgressBar();
        if (arePermissionsGranted()) {
            mLoaderManager.initLoader(0, null, this);
        } else {
            requestPermissionsWithRationale();
        }
    }

    @Override
    public boolean arePermissionsGranted() {
        return Utils.isPermissionGranted(mContext, Manifest.permission.READ_CALL_LOG) &&
                Utils.isPermissionGranted(mContext, Manifest.permission.PROCESS_OUTGOING_CALLS) &&
                Utils.isPermissionGranted(mContext, Manifest.permission.READ_PHONE_STATE);
    }

    @Override
    public void requestPermissionsWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                Manifest.permission.READ_CALL_LOG) ||
                ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                        Manifest.permission.PROCESS_OUTGOING_CALLS) ||
                ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                        Manifest.permission.READ_PHONE_STATE)) {
            getView().displayPermissionExplanationDialog();
        } else {
            requestPermissions();
        }
    }

    @Override
    public void requestPermissions() {
        Utils.requestPermissions(mContext,
                new String[]{Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.PROCESS_OUTGOING_CALLS,
                        Manifest.permission.READ_PHONE_STATE},
                Utils.Constants.MULTIPLE_PERMISSIONS_CODE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = new String[]{
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE,
                CallLog.Calls.NUMBER
        };
        return new CursorLoader(mContext, CallLog.Calls.CONTENT_URI, projection, null,
                null, CallLog.Calls.DATE + " DESC limit " + Utils.Constants.LOG_SIZE);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<CallLogItem> callLogItems = new ArrayList<>();
        data.moveToFirst();
        while (!data.isAfterLast()) {
            callLogItems.add(new CallLogItem(data));
            data.moveToNext();
        }
        getView().updateCallLog(callLogItems);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
