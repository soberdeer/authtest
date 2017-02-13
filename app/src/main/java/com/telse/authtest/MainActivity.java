package com.telse.authtest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.telse.authtest.db.Account;
import com.telse.authtest.db.SQLiteHelper;
import com.telse.authtest.encryption.KeystoreEncrypt;

import java.security.KeyStore;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    static final String KEY_USER = "auth_test_user";
    static final String KEY_PASSWORD = "auth_test_password";
    private static final int LOGIN_RESULT_CODE = 0;

    KeystoreEncrypt keystoreEncrypt;
    KeyStore keyStore;
    SQLiteHelper db;
    Button dropLoginButton, toLoginScreenButton, exitButton;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            keyStore = KeyStore.getInstance("AuthTest");
            keyStore.load(null);
        } catch (Exception e) {
        }
        keystoreEncrypt = new KeystoreEncrypt();
        keystoreEncrypt.refreshKeys();
        if (db == null) {
            db = new SQLiteHelper(this);
        }
        setContentView(R.layout.activity_main);
        toLoginScreenButton = (Button) findViewById(R.id.btn_login_screen);
        dropLoginButton = (Button) findViewById(R.id.btn_drop_login);
        exitButton = (Button) findViewById(R.id.btn_exit);


        dropLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropLogin();
                loginActivity();
            }
        });

        toLoginScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginActivityWithExtra();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        db.getReadableDatabase();
        if (db.getCount() == 0) loginActivity();
        else loginActivityWithExtra();
        db.close();

    }

    public void loginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, LOGIN_RESULT_CODE);
    }

    private void loginActivityWithExtra() {
        Account account;
        Intent intent = new Intent(this, LoginActivity.class);
        db.getReadableDatabase();
        account = db.getAccount();
        String username = decrypt(KEY_USER, account.getUsername());
        String password = decrypt(KEY_PASSWORD, account.getPassword());
        intent.putExtra(KEY_USER, username);
        intent.putExtra(KEY_PASSWORD, password);
        db.close();
        startActivity(intent);
    }

    private void dropLogin() {

        db.getWritableDatabase();
        db.deleteAccount();

        keystoreEncrypt.deleteKey(KEY_USER);
        keystoreEncrypt.deleteKey(KEY_PASSWORD);

        Toast.makeText(getBaseContext(), R.string.successful_drop, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                String userReturn = data.getStringExtra(KEY_USER);
                String passwordReturn = data.getStringExtra(KEY_PASSWORD);
                String encryptedUser = encrypt(KEY_USER, userReturn);
                String encryptedPassword = encrypt(KEY_PASSWORD, passwordReturn);
                db.getReadableDatabase();
                db.addAccount(new Account(encryptedUser, encryptedPassword));
                db.close();
                Intent alarmIntent = new Intent(this, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                int interval = (int) TimeUnit.MINUTES.toMillis(5);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

            }
        }
    }

    public String encrypt(String alias, String string) {
        keystoreEncrypt.createNewKeys(alias);
        return keystoreEncrypt.encryptString(alias, string);
    }

    public String decrypt(String key, String string) {
        return keystoreEncrypt.decryptString(key, string);
    }

}
