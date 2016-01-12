package me.shkschneider.skeleton.ui;

import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.widget.EditText;

public class EditTextHelper {

    public static void maxLength(@NonNull final EditText editText, final int maxLength) {
        editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(maxLength) });
    }

}
