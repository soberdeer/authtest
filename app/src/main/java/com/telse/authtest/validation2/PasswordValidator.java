package com.telse.authtest.validation2;

import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.telse.authtest.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator extends TextValidator implements View.OnFocusChangeListener {

    private static final String PATTERN_PASSWORD = "^.*(?=(.*\\d){3,})(?=(.*[a-zA-Z]){3,}).*$";
    private static final String PATTERN_DOUBLE = "^.*(.)\\1.*$";
    private static final int passwordLength = 6;

    public PasswordValidator(TextView textView) {
        super(textView);
    }

    @Override
    public boolean validate(TextView textView) {
        String value = textView.getText().toString().trim();
        Pattern patternPassword, patternDouble;
        Matcher matcherPassword, matcherDouble;

        patternPassword = Pattern.compile(PATTERN_PASSWORD);
        patternDouble = Pattern.compile(PATTERN_DOUBLE);

        matcherPassword = patternPassword.matcher(value);
        matcherDouble = patternDouble.matcher(value);

        if (TextUtils.isEmpty(textView.getText())){
            textView.setError("This field is required");
            return false;
        }

        if (passwordLength > value.length()) {
            textView.setError("This field is too short");
            return false;
        }
        if (!matcherPassword.matches()) {
            textView.setError("Password should contain at least 3 letters and 3 digits");
            return false;
        } else if (matcherDouble.matches()) {
            textView.setError("Password should not contain double symbols");
            return false;
        } else {
            textView.setError(null);
            return true;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}
