package com.telse.authtest.validation2;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

/**
 * Created by telse on 11/02/2017.
 */

public abstract class TextValidator implements TextWatcher {
    private final TextView textView;

    public TextValidator(TextView textView) {
        this.textView = textView;
    }

    public abstract boolean validate(TextView textView);

    @Override
    final public void afterTextChanged(Editable s) {
        validate(textView);
    }


    final public void onFocusChange(View v, boolean hasFocus) {
        if( !hasFocus ){
            validate(textView);
        }
    }

}
