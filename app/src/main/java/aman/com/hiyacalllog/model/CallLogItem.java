package aman.com.hiyacalllog.model;

import android.database.Cursor;
import android.graphics.Color;
import android.provider.CallLog;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by amanpreetsingh on 3/15/18.
 */

public class CallLogItem {

    private String mPhoneNumber;
    private String mType;
    private int mTypeColor;
    private String mDate;

    public CallLogItem(Cursor cursor) {
        this(cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)),
                cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE)),
                cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE)));
    }

    public CallLogItem(String phoneNumber, int type, String date) {
        setPhoneNumber(phoneNumber);
        setType(type);
        setDate(date);
    }

    public CallLogItem(String phoneNumber, int type, Date date) {
        setPhoneNumber(phoneNumber);
        setType(type);
        setDate(date);
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

    public int getTypeColor() {
        return mTypeColor;
    }

    private void setDate(String dateString) {
        setDate(new Date(Long.valueOf(dateString)));
    }

    private void setDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mma E MMMM d yyyy");
        mDate = format.format(date);
        mDate = mDate.replace("AM", "am").replace("PM", "pm");
    }

    public void setType(int type) {
        switch (type) {
            case CallLog.Calls.INCOMING_TYPE:
                mType = "INCOMING";
                mTypeColor = Color.GREEN;
                break;
            case CallLog.Calls.OUTGOING_TYPE:
                mType = "OUTGOING";
                mTypeColor = Color.GREEN;
                break;
            case CallLog.Calls.MISSED_TYPE:
                mType = "MISSED";
                mTypeColor = Color.RED;
                break;
            default:
                mType = "UNKNOWN";
                mTypeColor = Color.GREEN;
                break;
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.substring(phoneNumber.length() - 10, phoneNumber.length());
        phoneNumber = "+1 (" + phoneNumber.substring(0, 3) + ") " + phoneNumber.substring(3, 6) + "-" +
                phoneNumber.substring(6, phoneNumber.length());

        mPhoneNumber = phoneNumber;
    }
}
