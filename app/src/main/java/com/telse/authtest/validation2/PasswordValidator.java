package com.telse.authtest.validation2;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import com.telse.authtest.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator extends TextValidator implements View.OnFocusChangeListener {

    private static final String PATTERN_PASSWORD = "((?=(?:.*[a-z]){3})(?:.*\\d){3})\1";

    public PasswordValidator(TextView textView) {
        super(textView);
    }

    @Override
    public boolean validate(TextView textView) {
        String value=textView.getText().toString().trim();
        boolean check;
        Pattern p;
        Matcher m;

        p = Pattern.compile(PATTERN_PASSWORD);

        m = p.matcher(value);
        check = m.matches();

        if (!check) {
            textView.setError("incorrect password");
        }
        else{
            textView.setError(null);
        }
        return check;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}
