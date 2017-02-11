package com.telse.authtest.validation2;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.telse.authtest.R;

import java.util.ArrayList;
import java.util.List;


// this class will apply ContactValidate class on each TextEdit on the given View
public class ValidatorFactory<T extends View> {
    private View view;
    private List<View> possibleTextViews = new ArrayList<>();

    public void setUpValidators(T t) {
        view = t;
        possibleTextViews = view.getFocusables(View.FOCUS_LEFT);

        for (View possibleTextView : possibleTextViews) {
            if (possibleTextView instanceof EditText) {
                EditText textView = (EditText) possibleTextView;

                if (textView.equals(view.findViewById(R.id.input_email))) {
                    textView.addTextChangedListener(new UsernameValidator(textView));
                    textView.setOnFocusChangeListener(new UsernameValidator(textView));
                } else if (textView.equals(view.findViewById(R.id.input_password))) {
                    textView.addTextChangedListener(new PasswordValidator(textView));
                    textView.setOnFocusChangeListener(new PasswordValidator(textView));
                }
            }
        }
    }

}
