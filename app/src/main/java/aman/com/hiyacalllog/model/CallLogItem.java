package aman.com.hiyacalllog.model;

import android.database.Cursor;
import android.provider.CallLog;

/**
 * Created by amanpreetsingh on 3/15/18.
 */

public class CallLogItem {

    private String mPhoneNumber;
    private String mType;
    private String mDate;

    public CallLogItem(Cursor cursor) {
        mPhoneNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
        mType = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
        mDate = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getType() {
        return mType;
    }

    public String getDate() {
        return mDate;
    }
}
