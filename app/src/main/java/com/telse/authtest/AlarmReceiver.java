package com.telse.authtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.telse.authtest.db.SQLiteHelper;
import com.telse.authtest.encryption.KeystoreEncrypt;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;

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
//        deleteKey(KEY_USER);
//        deleteKey(KEY_PASSWORD);
        db.close();
        Toast.makeText(context, R.string.drop_after_five_mins, Toast.LENGTH_LONG).show();
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    private void deleteKey(String alias) {
        try {
            keyStore = KeyStore.getInstance("AuthTest");
            keyStore.load(null);
        } catch (Exception e) {
        }
        try {
            if (keyStore.getEntry(alias, null) != null) {
                keystoreEncrypt.deleteKey(alias);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }
}
