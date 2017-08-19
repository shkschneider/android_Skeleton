package me.shkschneider.skeleton.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.ScreenHelper;

// <https://github.com/javiersantos/BottomDialogs>
public class BottomSheet {

    private Dialog mDialog;

    private BottomSheet() {
        // Forbidden
    }

    private BottomSheet(@NonNull final Builder builder) {
        mDialog = new Dialog(builder.activity, R.style.SkeletonTheme_BottomSheet);
        @SuppressLint("InflateParams")
        final View view = builder.activity.getLayoutInflater().inflate(R.layout.sk_bottomsheet, null);
        final ImageView icon = view.findViewById(R.id.sk_bottomsheet_icon);
        if (builder.icon != null) {
            icon.setVisibility(View.VISIBLE);
            icon.setImageDrawable(builder.icon);
        }
        final TextView title = view.findViewById(R.id.sk_bottomsheet_title);
        if (builder.title != null) {
            title.setText(builder.title);
        }
        final TextView content = view.findViewById(R.id.sk_bottomsheet_content);
        if (builder.content != null) {
            content.setText(builder.content);
        }
        final FrameLayout customView = view.findViewById(R.id.sk_bottomsheet_customView);
        if (builder.customView != null) {
            customView.addView(builder.customView);
            customView.setPadding(builder.paddingLeft, builder.paddingTop, builder.paddingRight, builder.paddingBottom);
        }
        final Button negative = view.findViewById(R.id.sk_bottomsheet_cancel);
        if (builder.negative != null) {
            negative.setVisibility(View.VISIBLE);
            negative.setText(builder.negative);
            negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (builder.negativeCallback != null)
                        builder.negativeCallback.onClick(mDialog, DialogInterface.BUTTON_NEGATIVE);
                    mDialog.dismiss();
                }
            });
        }
        final Button positive = view.findViewById(R.id.sk_bottomsheet_ok);
        if (builder.positive != null) {
            positive.setVisibility(View.VISIBLE);
            positive.setText(builder.positive);
            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (builder.positiveCallback != null)
                        builder.positiveCallback.onClick(mDialog, DialogInterface.BUTTON_POSITIVE);
                    mDialog.dismiss();
                }
            });
        }
        mDialog.setContentView(view);
        mDialog.setCancelable(builder.cancelable);
        final Window window = mDialog.getWindow();
        if (window != null) {
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.BOTTOM);
        }
    }

    @UiThread
    public void show() {
        mDialog.show();
    }

    @UiThread
    public void dismiss() {
        mDialog.dismiss();
    }

    public static class Builder {

        public final Activity activity;
        public Drawable icon;
        public String title;
        public String content;
        public String negative;
        public String positive;
        public DialogInterface.OnClickListener negativeCallback;
        public DialogInterface.OnClickListener positiveCallback;
        public View customView;
        public int paddingLeft;
        public int paddingTop;
        public int paddingRight;
        public int paddingBottom;
        public boolean cancelable;

        public Builder(@NonNull final Activity activity) {
            this.activity = activity;
            this.cancelable = true;
        }

        public Builder setTitle(@NonNull final String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(@NonNull final String content) {
            this.content = content;
            return this;
        }

        public Builder setIcon(@NonNull final Drawable icon) {
            this.icon = icon;
            return this;
        }

        public Builder setPositive(@NonNull final String positive, final DialogInterface.OnClickListener onClickListener) {
            this.positive = positive;
            this.positiveCallback = onClickListener;
            return this;
        }

        public Builder setNegative(@NonNull final String negative, final DialogInterface.OnClickListener onClickListener) {
            this.negative = negative;
            this.negativeCallback = onClickListener;
            return this;
        }

        public Builder setCancelable(final boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setCustomView(@NonNull final View customView) {
            this.customView = customView;
            this.paddingLeft = 0;
            this.paddingTop = 0;
            this.paddingRight = 0;
            this.paddingBottom = 0;
            return this;
        }

        public Builder setCustomView(@NonNull final View customView, final int left, final int top, final int right, final int bottom) {
            this.customView = customView;
            this.paddingLeft = ScreenHelper.pixelsFromDp(left);
            this.paddingTop = ScreenHelper.pixelsFromDp(top);
            this.paddingRight = ScreenHelper.pixelsFromDp(right);
            this.paddingBottom = ScreenHelper.pixelsFromDp(bottom);
            return this;
        }

        @UiThread
        public BottomSheet build() {
            return new BottomSheet(this);
        }

    }

}
