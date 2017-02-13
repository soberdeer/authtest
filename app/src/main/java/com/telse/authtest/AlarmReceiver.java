package com.telse.authtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.telse.authtest.db.SQLiteHelper;
import com.telse.authtest.encryption.KeystoreEncrypt;

import java.security.KeyStore;

public class AlarmReceiver extends BroadcastReceiver {

    SQLiteHelper db;
    KeystoreEncrypt keystoreEncrypt;
    KeyStore keyStore;

    public AlarmReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent) {
        if (db == null) {
            db = new SQLiteHelper(context);
        }
        try {
            keyStore = KeyStore.getInstance("AuthTest");
            keyStore.load(null);
        } catch (Exception e) {
        }
        keystoreEncrypt = new KeystoreEncrypt();
        keystoreEncrypt.refreshKeys();
        db.getWritableDatabase();
        db.deleteAccount();
        db.close();
        Toast.makeText(context, R.string.drop_after_five_mins, Toast.LENGTH_LONG).show();
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}
