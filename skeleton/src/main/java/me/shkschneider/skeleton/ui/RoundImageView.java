package me.shkschneider.skeleton.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.LogHelper;

public class RoundImageView extends ImageView {

    private static final Xfermode sXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

    private Bitmap mMaskBitmap = null;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private WeakReference<Bitmap> mWeakBitmap = null;

    public RoundImageView(final Context context) {
        this(context, null);
    }

    public RoundImageView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @SuppressWarnings("NewApi")
    @SuppressLint("NewApi")
    @TargetApi(AndroidHelper.API_21)
    public RoundImageView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        if (! isInEditMode()) {
            final int i = canvas.saveLayer(0.0F, 0.0F, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
            try {
                Bitmap bitmap = mWeakBitmap != null ? mWeakBitmap.get() : null;
                // Bitmap not loaded.
                if (bitmap == null || bitmap.isRecycled()) {
                    final Drawable drawable = getDrawable();
                    if (drawable != null) {
                        // Allocation onDraw but it's ok because it will not always be called.
                        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas bitmapCanvas = new Canvas(bitmap);
                        drawable.setBounds(0, 0, getWidth(), getHeight());
                        drawable.draw(bitmapCanvas);
                        if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
                            mMaskBitmap = getBitmap();
                        }
                        mPaint.reset();
                        mPaint.setFilterBitmap(false);
                        mPaint.setXfermode(sXfermode);
                        bitmapCanvas.drawBitmap(mMaskBitmap, 0.0F, 0.0F, mPaint);
                        mWeakBitmap = new WeakReference<Bitmap>(bitmap);
                    }
                }
                if (bitmap != null) {
                    mPaint.setXfermode(null);
                    canvas.drawBitmap(bitmap, 0.0F, 0.0F, mPaint);
                    return ;
                }
            }
            catch (Exception e) {
                LogHelper.wtf(e);
                System.gc();
            }
            finally {
                canvas.restoreToCount(i);
            }
        }
        else {
            super.onDraw(canvas);
        }
    }

    @Override
    public void invalidate() {
        mWeakBitmap = null;
        if (mMaskBitmap != null) {
            mMaskBitmap.recycle();
        }
        super.invalidate();
    }

    public static Bitmap getBitmap(final int width, final int height) {
        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        canvas.drawOval(new RectF(0.0F, 0.0F, width, height), paint);
        return bitmap;
    }

    public Bitmap getBitmap() {
        return getBitmap(getWidth(), getHeight());
    }

}
