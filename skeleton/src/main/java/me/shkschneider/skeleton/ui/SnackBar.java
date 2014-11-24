package me.shkschneider.skeleton.ui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import me.shkschneider.skeleton.Executor;
import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.StringHelper;

/**
 * SnackBar is an enhanced and contextual Toast that can have a Button inside it.
 * Introduced with Material Design.
 *
 * @see <https://github.com/navasmdc/MaterialDesignLibrary>
 */
public class SnackBar extends Dialog {

    private String mMessageText;
    private String mButtonText;
    private View.OnClickListener mOnClickListener;
    private Activity mActivity;
    private View mView;

    public SnackBar(@NotNull final Activity activity, @NotNull final String message) {
        super(activity, android.R.style.Theme_Translucent);
        mActivity = activity;
        mMessageText = message;
        setCanceledOnTouchOutside(false);
    }

    public SnackBar setButton(@NotNull final String text, final View.OnClickListener onClickListener) {
        mButtonText = text;
        mOnClickListener = onClickListener;
        return this;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.snackbar);
        setCanceledOnTouchOutside(false);
        mView = findViewById(R.id.snackbar);
        ((TextView) mView.findViewById(R.id.textview)).setText(mMessageText);
        final Button button = (Button) findViewById(R.id.button);
        if (StringHelper.nullOrEmpty(mButtonText)) {
            button.setVisibility(View.GONE);
        }
        else {
            button.setText(mButtonText);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    dismiss();
                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(view);
                    }
                }
            });
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        return mActivity.dispatchTouchEvent(motionEvent);
    }

    @Override
    public void onBackPressed() {
        // Ignore
    }

    @Override
    public void show() {
        super.show();
        mView.setVisibility(View.VISIBLE);
        mView.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.snackbar_show_animation));
        Executor.delayRunnable(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 4, TimeUnit.SECONDS);
    }

    @Override
    public void dismiss() {
        final Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.snackbar_hide_animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(final Animation animation) {
                // Ignore
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {
                // Ignore
            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                SnackBar.super.dismiss();
            }
        });
        mView.startAnimation(animation);
    }

}
