package me.shkschneider.skeleton.ui;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.ScreenHelper;
import me.shkschneider.skeleton.helper.SystemHelper;

public class TooltipView1 {

    private static int DEFAULT_DURATION = 5000;

    private static PopupWindow mPopup;
    private static Handler mHandler;
    private static PopupWindow.OnDismissListener mDismissListener;
    private static Runnable mDismissCallback;

    public static void showToolTip(final Activity activity, final View anchor, final String text) {
        showToolTip(activity, anchor, text, DEFAULT_DURATION);
    }

    public static void showToolTip(final Activity activity, final View anchor, final String text, final int duration) {
        showToolTip(activity, anchor, text, duration, null, 0);
    }

    public static void showToolTip(final Activity activity, final View anchor, final String text, final PopupWindow.OnDismissListener dismissListener) {
        showToolTip(activity, anchor, text, DEFAULT_DURATION, dismissListener, 0);
    }

    public static void showToolTip(final Activity activity, final View anchor, final String text, final int duration, final PopupWindow.OnDismissListener dismissListener, int drawableRight) {
        mHandler = new Handler(Looper.getMainLooper());
        mDismissListener = dismissListener;
        final ViewGroup view = (ViewGroup) UiHelper.inflate(R.layout.tooltip);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        final TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableRight, 0);
        textView.setCompoundDrawablePadding(ScreenHelper.pixelsFromDp(4));
        textView.setText(Html.fromHtml(text, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(final String s) {
                int id = activity.getResources().getIdentifier(s.substring(0, s.indexOf(".")), "drawable", activity.getPackageName());
                if (id != 0) {
                    Drawable drawable = activity.getResources().getDrawable(id);
                    if (drawable != null) {
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    }
                    return drawable;
                }
                return null;
            }
        }, null));
        if (mPopup == null) {
            mPopup = new PopupWindow(activity);
            mPopup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
            mPopup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            mPopup.setBackgroundDrawable(null);
            mPopup.setTouchable(true);
            mPopup.setOutsideTouchable(true);
            mPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mHandler.removeCallbacks(mDismissCallback);
                    if (mDismissListener != null) {
                        mDismissListener.onDismiss();
                    }
                }
            });
            mPopup.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        dismissTooltip();
                        return true;
                    }
                    return false;
                }
            });
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                dismissTooltip();
            }
        });
        mPopup.setContentView(view);
        int xPos, yPos, arrowPos;
        int[] location = new int[2];
        anchor.getLocationOnScreen(location);
        Rect anchorRect = new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1] + anchor.getHeight());
        view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int rootHeight = view.getMeasuredHeight();
        int rootWidth = view.getMeasuredWidth();
        final WindowManager windowManager = (WindowManager) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_WINDOW);
        final Point size = new Point();
        int screenWidth = 0;
        int screenHeight = 0;
        windowManager.getDefaultDisplay().getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        if ((anchorRect.left + rootWidth) > screenWidth) {
            xPos = anchorRect.left - (rootWidth - anchor.getWidth());
            xPos = (xPos < 0) ? 0 : xPos;
            arrowPos = anchorRect.centerX() - xPos;
        }
        else {
            if (anchor.getWidth() > rootWidth) {
                xPos = anchorRect.centerX() - (rootWidth / 2);
            }
            else {
                xPos = anchorRect.left;
            }
            arrowPos = anchorRect.centerX() - xPos;
        }
        int dyTop = anchorRect.top;
        int dyBottom = screenHeight - anchorRect.bottom;
        boolean onTop = dyTop > dyBottom;
        if (onTop) {
            if (rootHeight > dyTop) {
                yPos = 15;
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.height = dyTop - anchor.getHeight();
                }
            }
            else {
                yPos = anchorRect.top - rootHeight;
            }
        }
        else {
            yPos = anchorRect.bottom;
            if (rootHeight > dyBottom) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.height = dyBottom;
                }
            }
        }
        showArrow(view, ((onTop) ? R.id.arrow_down : R.id.arrow_up), arrowPos);
        mPopup.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);
        mDismissCallback = new Runnable() {
            @Override
            public void run() {
                dismissTooltip();
            }
        };
        mHandler.postDelayed(mDismissCallback, duration);
    }

    private static void showArrow(final View root, final int whichArrow, final int requestedX) {
        final View showArrow = (whichArrow == R.id.arrow_up) ? root.findViewById(R.id.arrow_up) : root.findViewById(R.id.arrow_down);
        final View hideArrow = (whichArrow == R.id.arrow_up) ? root.findViewById(R.id.arrow_down) : root.findViewById(R.id.arrow_up);
        final int arrowWidth = showArrow.getMeasuredWidth();
        showArrow.setVisibility(View.VISIBLE);
        ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams) showArrow.getLayoutParams();
        if (param != null) {
            param.leftMargin = requestedX - arrowWidth / 2;
        }
        hideArrow.setVisibility(View.GONE);
    }

    public static void dismissTooltip() {
        dismissTooltip(true);
    }

    public static void dismissTooltip(final boolean callDismissListener) {
        if (mPopup != null && mPopup.isShowing()) {
            if (! callDismissListener) {
                mDismissListener = null;
            }
            mPopup.dismiss();
            mPopup = null;
        }
    }

}
