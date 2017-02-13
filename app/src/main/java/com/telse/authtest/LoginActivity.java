package com.telse.authtest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.telse.authtest.validator.PasswordValidator;
import com.telse.authtest.validator.UsernameValidator;

import static com.telse.authtest.MainActivity.KEY_PASSWORD;
import static com.telse.authtest.MainActivity.KEY_USER;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    EditText mUsernameText, mPasswordText;
    Button mLoginButton;
    Bundle extras;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsernameText = (EditText) findViewById(R.id.input_username);
        mPasswordText = (EditText) findViewById(R.id.input_password);


        extras = getIntent().getExtras();
        if (extras != null) {
            String username = extras.getString(KEY_USER);
            String password = extras.getString(KEY_PASSWORD);
            mUsernameText.setText(username);
            mPasswordText.setText(password);
            mUsernameText.setKeyListener(null);
            mPasswordText.setKeyListener(null);
            Toast.makeText(getBaseContext(), R.string.error_change_log, Toast.LENGTH_LONG).show();
        } else {
            mUsernameText.addTextChangedListener(new UsernameValidator(mUsernameText));
            mUsernameText.setOnFocusChangeListener(new UsernameValidator(mUsernameText));
            mPasswordText.addTextChangedListener(new PasswordValidator(mPasswordText));
            mPasswordText.setOnFocusChangeListener(new PasswordValidator(mPasswordText));
            mUsernameText.setText("");
            mPasswordText.setText("");

        }


        mLoginButton = (Button) findViewById(R.id.btn_login);

        mLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!(validate(mUsernameText) || validate(mPasswordText))) {
            onLoginFailed();
            return;
        }

        mLoginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        if (extras == null) {
            String username = mUsernameText.getText().toString();
            String password = mPasswordText.getText().toString();
            Intent intent = new Intent();
            intent.putExtra(KEY_USER, username);
            intent.putExtra(KEY_PASSWORD, password);
            setResult(RESULT_OK, intent);
        }

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onLoginSuccess();
                        progressDialog.dismiss();
                    }
                }, 300);


        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            this.finish();

        }
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        mLoginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), R.string.error_login, Toast.LENGTH_LONG).show();

        mLoginButton.setEnabled(true);
    }

    private boolean validate(TextView textView) {
        if (textView.getError() != null) return false;
        return true;
    }


}

