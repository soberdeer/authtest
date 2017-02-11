package com.telse.authtest.validation2;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UsernameValidator extends TextValidator implements View.OnFocusChangeListener {

    private static final String PATTERN_USERNAME = "^[^\\s\\.]*$";

    public UsernameValidator(TextView textView) {
        super(textView);
    }

    @Override
    public boolean validate(TextView textView) {
        String value = textView.getText().toString().trim();
        Pattern p;
        Matcher m;
        boolean check;
        p = Pattern.compile(PATTERN_USERNAME);

        m = p.matcher(value);
        check = m.matches();

        if (!check) {
            textView.setError((textView.getHint() + " is invalid!"));
        } else {
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
