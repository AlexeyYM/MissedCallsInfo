package com.javir.callmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

//Класс нашего receiver'а, отслеживающий звонки и запускающий CallService при звонке
public class CallReceiver extends BroadcastReceiver {
    final String LOG_TAG = "myLogs";

    public CallReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");

        String currentTime = "";
        String phoneNumber = "";
        String phone_state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            if (phone_state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                currentTime = String.valueOf(Calendar.getInstance().getTime());
                Log.d(LOG_TAG, "Ringing");
            } else if (phone_state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {

            } else if (phone_state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                currentTime = String.valueOf(Calendar.getInstance().getTime());
                context.startService(new Intent(context, CallService.class)
                        .putExtra(Constants.PHONE_NUMBER_EXTRA, phoneNumber)
                        .putExtra(Constants.CURRENT_TIME_EXTRA, currentTime));
            }
        }
    }
}
