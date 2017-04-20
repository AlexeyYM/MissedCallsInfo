package com.javir.callmanager;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int LAYOUT_RES_ID = R.layout.activity_main;
    public static final String APP_PREFERENCES = "appsettings";
    public static final String APP_PREFERENCES_MAIL_RECIPIENT = "mailRecipient";
    public static final String APP_PREFERENCES_LOGIN = "login";
    public static final String APP_PREFERENCES_PASS = "pass";

    private SharedPreferences sh;

    private EditText mailRecipient;
    private TextView mailRecipientText;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT_RES_ID);

        sh = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        mailRecipient = (EditText) findViewById(R.id.mailRecipient);
        mailRecipientText = (TextView) findViewById(R.id.mailRecipientText);
        mailRecipientText.setText(sh.getString(APP_PREFERENCES_MAIL_RECIPIENT, String.valueOf(R.string.emptyMailRecipientText)));

        statusText = (TextView) findViewById(R.id.statusText);
        statusText.setText(getString(R.string.statusTextLogin) + " " + sh.getString(APP_PREFERENCES_LOGIN, String.valueOf(R.string.statusTextNotLogin)));
    }

    public void authOnClick(View view) {
        View authDialogView = getLayoutInflater().inflate(R.layout.auth_dialog, null);
        final EditText authDialogLoginInput = (EditText) authDialogView.findViewById(R.id.authDialogLoginInput);
        final EditText authDialogPassInput = (EditText) authDialogView.findViewById(R.id.authDialogPassInput);

        new AlertDialog.Builder(this)
                .setTitle(R.string.authDialogTitle)
                .setView(authDialogView)
                .setPositiveButton(R.string.authDialogButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String login = authDialogLoginInput.getText().toString();
                        String pass = authDialogPassInput.getText().toString();

                        if (!login.isEmpty() && !pass.isEmpty()) {
                            SharedPreferences.Editor editor = sh.edit();
                            editor.putString(APP_PREFERENCES_LOGIN, login).apply();
                            editor.putString(APP_PREFERENCES_PASS, pass).apply();
                            statusText.setText(getString(R.string.statusTextLogin) + " " + sh.getString(APP_PREFERENCES_LOGIN, String.valueOf(R.string.statusTextNotLogin)));
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.emptyAuthDialog, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .create()
                .show();
    }

    public void mailRecipientOnClick(View view) {
        if (!mailRecipient.getText().toString().isEmpty()) {
            String mail = mailRecipient.getText().toString();

            SharedPreferences.Editor editor = sh.edit();
            editor.putString(APP_PREFERENCES_MAIL_RECIPIENT, mail).apply();

            mailRecipientText.setText(sh.getString(APP_PREFERENCES_MAIL_RECIPIENT, String.valueOf(R.string.emptyMailRecipientText)));
        } else {
            Toast.makeText(this, R.string.emptyMailRecipient, Toast.LENGTH_SHORT).show();
        }
    }
}
