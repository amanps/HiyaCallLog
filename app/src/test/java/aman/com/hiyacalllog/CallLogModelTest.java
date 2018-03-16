package aman.com.hiyacalllog;

import android.provider.CallLog;

import org.junit.Test;
import static org.junit.Assert.*;

import aman.com.hiyacalllog.model.CallLogItem;

/**
 * Created by amanpreetsingh on 3/16/18.
 */

public class CallLogModelTest {

    @Test
    public void callLogItemConstructorValidator() {
        CallLogItem item = new CallLogItem("+12345435674", CallLog.Calls.INCOMING_TYPE, "1521224513");
        assertEquals("+1 (234) 543-5674", item.getPhoneNumber());
        item.setPhoneNumber("+1 234 543 56 78");
        assertEquals("+1 (234) 543-5678", item.getPhoneNumber());
        item.setPhoneNumber("234 543 56 78");
        assertEquals("+1 (234) 543-5678", item.getPhoneNumber());
    }
}
