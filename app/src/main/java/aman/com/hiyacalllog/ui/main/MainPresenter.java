package aman.com.hiyacalllog.ui.main;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import aman.com.hiyacalllog.Utils;
import aman.com.hiyacalllog.ui.base.BasePresenter;

/**
 * Created by amanpreetsingh on 3/15/18.
 */

public class MainPresenter extends BasePresenter<MainView> implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context mContext;
    private LoaderManager mLoaderManager;

    public MainPresenter(Context context, LoaderManager loaderManager) {
        mContext = context;
        mLoaderManager = loaderManager;

    }

    protected void loadCallLog() {
        if (!Utils.isPermissionGranted(mContext, Manifest.permission.READ_CALL_LOG)) {
            Utils.requestPermission((Activity) mContext, Manifest.permission.READ_CALL_LOG, Utils.Constants.CALL_LOG_PERMISSION);
            return;
        }
        mLoaderManager.initLoader(0, null, this);
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
        Log.d("MainPresenter", "Load finished");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
