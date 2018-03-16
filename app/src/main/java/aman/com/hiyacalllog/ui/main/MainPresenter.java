package aman.com.hiyacalllog.ui.main;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
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

public class MainPresenter extends BasePresenter<MainView> implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context mContext;
    private LoaderManager mLoaderManager;

    public MainPresenter(Context context, LoaderManager loaderManager) {
        mContext = context;
        mLoaderManager = loaderManager;

    }

    protected void loadCallLog() {
        getView().displayProgressBar();
        if (!Utils.isPermissionGranted(mContext, Manifest.permission.READ_CALL_LOG) ||
                !Utils.isPermissionGranted(mContext, Manifest.permission.PROCESS_OUTGOING_CALLS)) {
            Utils.requestPermissions((Activity) mContext,
                    new String[]{Manifest.permission.READ_CALL_LOG,
                            Manifest.permission.PROCESS_OUTGOING_CALLS,
                            Manifest.permission.READ_PHONE_STATE},
                    Utils.Constants.MULTIPLE_PERMISSIONS_CODE);
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
