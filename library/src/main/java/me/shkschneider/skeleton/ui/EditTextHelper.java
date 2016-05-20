package me.shkschneider.skeleton.ui;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class EditTextHelper {

    public static void maxLength(@NonNull final EditText editText, final int maxLength) {
        editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(maxLength) });
    }

    // <http://stackoverflow.com/a/20520755>
    public static void scrollCompat(@NonNull final EditText editText, final int editTextId) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                if (v.getId() == editTextId) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
    }

}
