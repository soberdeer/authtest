package com.telse.authtest.validation2;

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
                textView.addTextChangedListener(new RequiredValidator(textView));

                if (textView.equals(view.findViewById(R.id.input_email))) {
                    textView.addTextChangedListener(new UsernameValidator(textView));
                    textView.addTextChangedListener(new LengthValidator(textView, 4));
                    textView.setOnFocusChangeListener(new UsernameValidator(textView));
                } else {
                    textView.addTextChangedListener(new PasswordValidator(textView));
                    textView.addTextChangedListener(new LengthValidator(textView, 6));
                    textView.setOnFocusChangeListener(new PasswordValidator(textView));
                }
            }
        }
    }
}
