package com.javir.callmanager;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class CallService extends IntentService {
    final String LOG_TAG = "myLogs";

    public CallService() {
        super("CallService");
    }

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    //Получаем данные о звонке и передаём методу sendMail(String, String, String, String, String) для отправки e-mail
    @Override
    protected void onHandleIntent(Intent intent) {
        String phoneNumber = intent.getStringExtra(Constants.PHONE_NUMBER_EXTRA);
        String currentTime = intent.getStringExtra(Constants.CURRENT_TIME_EXTRA);
        SharedPreferences sh = getSharedPreferences(MainActivity.APP_PREFERENCES, MODE_PRIVATE);
        String login = sh.getString(MainActivity.APP_PREFERENCES_LOGIN, "");
        String pass = sh.getString(MainActivity.APP_PREFERENCES_PASS, "");
        String where = sh.getString(MainActivity.APP_PREFERENCES_MAIL_RECIPIENT, "");

        if (!login.isEmpty() && !pass.isEmpty() && !where.isEmpty()) {
            try {
                MailSenderClass sender = new MailSenderClass(login, pass);
                String title = getText(R.string.mailTitle).toString();
                String text = "Звонил абонент " + phoneNumber + ", время звонка " + currentTime;

                sender.sendMail(title, text, login, where, "");
            } catch (Exception e) {
                Log.e(LOG_TAG, "Exception: " + e);
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }
}
