package me.shkschneider.skeleton.ui;

/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import me.shkschneider.app.R;

public class ActionBarTabStripCell extends LinearLayout {

    private int mPosition;
    private float mPositionOffset;
    private final int mIndicatorThickness;
    private final Paint mIndicatorPaint;

    public ActionBarTabStripCell(final Context context) {
        this(context, null);
    }

    public ActionBarTabStripCell(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        final float density = getResources().getDisplayMetrics().density;
        mIndicatorThickness = (int) (8 * density);
        mIndicatorPaint = new Paint();
        mIndicatorPaint.setColor(getResources().getColor(R.color.white));
        mPosition = 0;
        mPositionOffset = 0;
    }

    void onViewPagerPageChanged(final int position, final float positionOffset) {
        mPosition = position;
        mPositionOffset = positionOffset;
        invalidate();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        final int height = getHeight();
        if (getChildCount() > 0) {
            final View selectedTitle = getChildAt(mPosition);
            int left = selectedTitle.getLeft();
            int right = selectedTitle.getRight();
            final int color = mIndicatorPaint.getColor();
            if (mPositionOffset > 0F && mPosition < (getChildCount() - 1)) {
                final View nextTitle = getChildAt(mPosition + 1);
                left = (int) (mPositionOffset * nextTitle.getLeft() + (1.0F - mPositionOffset) * left);
                right = (int) (mPositionOffset * nextTitle.getRight() + (1.0F - mPositionOffset) * right);
            }
            mIndicatorPaint.setColor(color);
            canvas.drawRect(left, height - mIndicatorThickness, right, height, mIndicatorPaint);
        }
    }

}
