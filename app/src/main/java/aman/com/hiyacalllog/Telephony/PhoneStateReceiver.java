package aman.com.hiyacalllog.Telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

/**
 * Created by amanpreetsingh on 3/16/18.
 */

public class PhoneStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            PhoneCallStateManager.getInstance().onNewOutgoingIntent(intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER));
        } else if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            PhoneCallStateManager.getInstance().onNewIncomingIntent(intent.getStringExtra(TelephonyManager.EXTRA_STATE),
                    intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER));
        }
    }
}
