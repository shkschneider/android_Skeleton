package me.shkschneider.skeleton.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.ScreenHelper;
import me.shkschneider.skeleton.helper.SystemHelper;
import me.shkschneider.skeleton.java.StringHelper;

public class TooltipView {

    public static final int LENGTH_SHORT = 1500;
    public static final int LENGTH_LONG = 3000;

    public static void showToolTip(final Activity activity, final View anchor, final String text, final int duration) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return ;
        }
        if (anchor == null) {
            LogHelper.warning("View was NULL");
            return ;
        }
        else if (anchor.getVisibility() != View.VISIBLE) {
            LogHelper.warning("View was not VISIBLE");
            return ;
        }
        if (StringHelper.nullOrEmpty(text)) {
            LogHelper.warning("Text was NULL");
            return ;
        }

        anchor.post(new Runnable() {
            @SuppressLint("InflateParams")
            @Override
            public void run() {
                final Handler handler = new Handler();
                final ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.tooltip, null);
                viewGroup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                final TextView textView = (TextView) viewGroup.findViewById(android.R.id.text1);
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                textView.setText(text);
                final PopupWindow popupWindow = new PopupWindow(activity);
                popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setBackgroundDrawable(null);
                popupWindow.setTouchable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, final MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                            dissmiss(popupWindow);
                            return true;
                        }
                        return false;
                    }
                });
                viewGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        dissmiss(popupWindow);
                    }
                });
                popupWindow.setContentView(viewGroup);

                int posX;
                int posY;
                int posArrow;
                final int[] location = new int[2];
                anchor.getLocationOnScreen(location);
                final Rect rect = new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1] + anchor.getHeight());
                viewGroup.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int viewGroupMeasuredWidth = viewGroup.getMeasuredWidth();
                int viewGroupMeasuredHeight = viewGroup.getMeasuredHeight();
                final WindowManager windowManager = (WindowManager) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_WINDOW);
                final Point point = new Point();
                windowManager.getDefaultDisplay().getSize(point);
                if ((rect.left + viewGroupMeasuredWidth) > point.x) {
                    posX = rect.left - (viewGroupMeasuredWidth - anchor.getWidth());
                    posX = (posX < 0) ? 0 : posX;
                    posArrow = rect.centerX() - posX;
                }
                else {
                    if (anchor.getWidth() > viewGroupMeasuredWidth) {
                        posX = rect.centerX() - (viewGroupMeasuredWidth / 2);
                    }
                    else {
                        posX = rect.left;
                    }
                    posArrow = rect.centerX() - posX;
                }
                final int dyTop = rect.top;
                final int dyBottom = point.y - rect.bottom;
                final boolean onTop = (dyTop > dyBottom);
                if (onTop) {
                    if (viewGroupMeasuredHeight > dyTop) {
                        posY = 15;
                        final ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
                        if (layoutParams != null) {
                            layoutParams.height = dyTop - anchor.getHeight();
                        }
                    }
                    else {
                        posY = rect.top - viewGroupMeasuredHeight;
                    }
                }
                else {
                    posY = rect.bottom;
                    if (viewGroupMeasuredHeight > dyBottom) {
                        ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
                        if (layoutParams != null) {
                            layoutParams.height = dyBottom;
                        }
                    }
                }
                showArrow(viewGroup, ((onTop) ? R.id.arrow_down : R.id.arrow_up), posArrow);
                popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, posX, posY);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dissmiss(popupWindow);
                    }
                }, duration);
            }
        });
    }

    private static void showArrow(View root, int whichArrow, int requestedX) {
        final View showArrow = (whichArrow == R.id.arrow_up) ? root.findViewById(R.id.arrow_up) : root.findViewById(R.id.arrow_down);
        final View hideArrow = (whichArrow == R.id.arrow_up) ? root.findViewById(R.id.arrow_down) : root.findViewById(R.id.arrow_up);
        // final int arrowWidth = showArrow.getMeasuredWidth();
        showArrow.setVisibility(View.VISIBLE);
        final ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) showArrow.getLayoutParams();
        if (marginLayoutParams != null) {
            marginLayoutParams.leftMargin = requestedX - ScreenHelper.pixelsFromDp(8);
        }
        hideArrow.setVisibility(View.GONE);
    }

    private static void dissmiss(final PopupWindow popupWindow) {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

}
