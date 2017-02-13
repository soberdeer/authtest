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

import com.telse.authtest.db.SQLiteHelper;
import com.telse.authtest.encryption.KeystoreEncrypt;
import com.telse.authtest.validator.PasswordValidator;
import com.telse.authtest.validator.UsernameValidator;

import static com.telse.authtest.MainActivity.KEY_PASSWORD;
import static com.telse.authtest.MainActivity.KEY_USER;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    EditText _usernameText, _passwordText;
    Button _loginButton;
    Bundle extras;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _usernameText = (EditText) findViewById(R.id.input_username);
        _passwordText = (EditText) findViewById(R.id.input_password);


        extras = getIntent().getExtras();
        if (extras != null) {
            String username = extras.getString(KEY_USER);
            String password = extras.getString(KEY_PASSWORD);
            _usernameText.setText(username);
            _passwordText.setText(password);
            _usernameText.setKeyListener(null);
            _passwordText.setKeyListener(null);
            Toast.makeText(getBaseContext(), R.string.error_change_log, Toast.LENGTH_LONG).show();
        } else {
            _usernameText.addTextChangedListener(new UsernameValidator(_usernameText));
            _usernameText.setOnFocusChangeListener(new UsernameValidator(_usernameText));
            _passwordText.addTextChangedListener(new PasswordValidator(_passwordText));
            _passwordText.setOnFocusChangeListener(new PasswordValidator(_passwordText));
            _usernameText.setText("");
            _passwordText.setText("");

        }


        _loginButton = (Button) findViewById(R.id.btn_login);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!(validate(_usernameText) || validate(_passwordText))) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        if (extras == null) {
            String username = _usernameText.getText().toString();
            String password = _passwordText.getText().toString();
            Intent intent = new Intent();
            intent.putExtra(KEY_USER, username);
            intent.putExtra(KEY_PASSWORD, password);
            setResult(RESULT_OK, intent);
        }

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 300);


        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //  startMainActivity();
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

    private boolean validate(TextView textView) {
        if (textView.getError() != null) return false;
        return true;
    }


}

