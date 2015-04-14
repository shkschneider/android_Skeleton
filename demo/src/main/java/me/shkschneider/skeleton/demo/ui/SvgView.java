package me.shkschneider.skeleton.demo.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import me.shkschneider.skeleton.helper.LogHelper;

public class SvgView extends ImageView {

    private Bitmap mBitmap;
    private int mResId;
    private SVG mSvg;

    public SvgView(final Context context) {
        this(context, null);
    }

    public SvgView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SvgView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(final AttributeSet attrs) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, new int[] { android.R.attr.src });
        final int resId = typedArray.getResourceId(0, 0);
        typedArray.recycle();
        if (resId != 0) {
            setRawResource(resId);
        }
    }

    public void setRawResource(int resId) {
        if (mResId == resId) {
            return ;
        }
        mResId = resId;
        if (mResId == 0) {
            setImageBitmap(null);
            return ;
        }
        try {
            mSvg = SVG.getFromResource(getContext(), mResId);
        }
        catch (final SVGParseException e) {
            LogHelper.wtf(e);
        }
        if (getLayoutParams() != null
                && (MeasureSpec.getMode(getLayoutParams().width) == MeasureSpec.UNSPECIFIED
                || MeasureSpec.getMode(getLayoutParams().height) == MeasureSpec.UNSPECIFIED)) {
            requestLayout();
        }
        else {
            render();
        }
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth(), height = getMeasuredHeight();
        if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY && mSvg != null) {
            width = (int) (mSvg.getDocumentWidth() * 1.0F);
            width += getPaddingLeft() + getPaddingRight();
        }
        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY && mSvg != null) {
            height = (int) (mSvg.getDocumentHeight() * 1.0F);
            height += getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(final boolean changed, final int left, final int top, final int right, final int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (isInEditMode() || mResId == 0) {
            return ;
        }
        if (mBitmap == null || changed &&
                mBitmap.getWidth() != right - left - getPaddingLeft() - getPaddingRight() &&
                mBitmap.getHeight() != bottom - top - getPaddingTop() - getPaddingBottom()) {
            render();
        }
    }

    private synchronized void render() {
        if (mSvg == null) {
            return ;
        }
        if (mResId == 0) {
            return ;
        }
        final int width = getWidth() - getPaddingLeft() - getPaddingRight();
        final int height = getHeight() - getPaddingTop() - getPaddingBottom();
        if (width <= 0 || height <= 0) {
            return ;
        }
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(mBitmap);
        mSvg.setDocumentWidth(width);
        mSvg.setDocumentHeight(height);
        mSvg.renderToCanvas(canvas);
        setImageBitmap(mBitmap);
    }

}
