package com.telse.authtest.validation2;

import android.content.res.Resources;
import android.widget.TextView;

import com.telse.authtest.R;

public class LengthValidator extends TextValidator {
    private int length;

    public LengthValidator(TextView textView, int length) {
        super(textView);
        this.length = length;
    }

    @Override
    public boolean validate(TextView textView) {
        String value = textView.getText().toString().trim();
        if (length > value.length()) {
            textView.setError("This field is too short");
            return false;
        }
        else{
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
