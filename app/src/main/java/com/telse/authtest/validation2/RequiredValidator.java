package com.telse.authtest.validation2;

import android.text.TextUtils;
import android.widget.TextView;


public class RequiredValidator extends TextValidator {
    public RequiredValidator(TextView textView) {
        super(textView);
    }

    @Override
    public boolean validate(TextView textView) {

        if (TextUtils.isEmpty(textView.getText())) {
            textView.setError((textView.getHint() + " is required!"));
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
