package com.telse.authtest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.telse.authtest.validation2.PasswordValidator;
import com.telse.authtest.validation2.UsernameValidator;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    EditText _emailText, _passwordText;
    Button _loginButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _emailText.addTextChangedListener(new UsernameValidator(_emailText));
        _emailText.setOnFocusChangeListener(new UsernameValidator(_emailText));
        _passwordText.addTextChangedListener(new PasswordValidator(_passwordText));
        _passwordText.setOnFocusChangeListener(new PasswordValidator(_passwordText));
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!(validate(_emailText) || validate(_passwordText))) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            startMainActivity();
            this.finish();

        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), R.string.error_login, Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }


    private void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private boolean validate(TextView textView) {
        if (textView.getHint() == null) return false;
        return true;

    }

}

