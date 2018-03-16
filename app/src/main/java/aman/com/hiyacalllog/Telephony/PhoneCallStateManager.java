package aman.com.hiyacalllog.Telephony;

import android.arch.lifecycle.MutableLiveData;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Date;

import aman.com.hiyacalllog.model.CallLogItem;

/**
 * Created by amanpreetsingh on 3/16/18.
 */

public class PhoneCallStateManager {

    private static PhoneCallStateManager mInstance;

    private static final String TAG = "PhoneCallStateManager";
    private static MutableLiveData<CallLogItem> mLiveData;

    private static int STATE;
    private static boolean incoming;
    private static String phoneNumber;

    private PhoneCallStateManager() {

    }

    public static PhoneCallStateManager getInstance() {
        if (mInstance == null) {
            mInstance = new PhoneCallStateManager();
            mLiveData = new MutableLiveData<>();
        }
        return mInstance;
    }

    void onNewIncomingIntent(String state, String number) {
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            STATE = TelephonyManager.CALL_STATE_RINGING;
            incoming = true;
            phoneNumber = number;
        } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            if (incoming) {
                switch (STATE) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        Log.d(TAG, "Add call : Missed " + phoneNumber + " " + (new Date()).toString());
                        mLiveData.setValue(new CallLogItem(phoneNumber, CallLog.Calls.MISSED_TYPE, new Date()));
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        Log.d(TAG, "Add call : Incoming " + phoneNumber + " " + (new Date()).toString());
                        mLiveData.setValue(new CallLogItem(phoneNumber, CallLog.Calls.INCOMING_TYPE, new Date()));
                        break;
                }
            } else {
                Log.d(TAG, "Add call : Outgoing " + phoneNumber + " " + (new Date()).toString());
                mLiveData.setValue(new CallLogItem(phoneNumber, CallLog.Calls.OUTGOING_TYPE, new Date()));
            }
            STATE = TelephonyManager.CALL_STATE_IDLE;
        } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            if (incoming)
                STATE = TelephonyManager.CALL_STATE_OFFHOOK;
        }
    }

    void onNewOutgoingIntent(String number) {
        incoming = false;
        phoneNumber = number;
    }
}
