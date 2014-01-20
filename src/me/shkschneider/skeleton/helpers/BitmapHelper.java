package me.shkschneider.skeleton.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;

import me.shkschneider.skeleton.SkeletonApplication;

@SuppressWarnings("unused")
public final class BitmapHelper {

    public static Bitmap decodeUri(final Uri uri) {
        final BitmapFactory.Options bitmapFactoryOptionsTmp = new BitmapFactory.Options();
        bitmapFactoryOptionsTmp.inJustDecodeBounds = true;
        try {
            final InputStream inputStream = SkeletonApplication.CONTEXT.getContentResolver().openInputStream(uri);
            if (inputStream == null) {
                LogHelper.warning("InputStream was NULL");
                return null;
            }

            BitmapFactory.decodeStream(inputStream, null, bitmapFactoryOptionsTmp);
            int width = bitmapFactoryOptionsTmp.outWidth;
            int height = bitmapFactoryOptionsTmp.outHeight;
            int scale = 1;

            final int downsample = ScreenHelper.density();
            if (downsample <= 0) {
                LogHelper.warning("Downsample was invalid");
                return null;
            }

            while (true) {
                if (width / 2 < downsample || height / 2 < downsample) {
                    break ;
                }
                width /= 2;
                height /= 2;
                scale *= 2;
            }
            final BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
            bitmapFactoryOptions.inJustDecodeBounds = false;
            bitmapFactoryOptions.inSampleSize = scale;
            bitmapFactoryOptions.inPurgeable = true;
            return BitmapFactory.decodeStream(inputStream, null, bitmapFactoryOptions);
        }
        catch (final FileNotFoundException e) {
            LogHelper.error("FileNotFoundException: " + e.getMessage());
            return null;
        }
    }

    public static Bitmap fromUri(final Uri uri) {
        try {
            return BitmapFactory.decodeStream(SkeletonApplication.CONTEXT.getContentResolver().openInputStream(uri), null, new BitmapFactory.Options());
        }
        catch (final FileNotFoundException e) {
            LogHelper.error("FileNotFoundException: " + e.getMessage());
            return null;
        }
    }

    public static Bitmap fromDrawable(final Drawable drawable) {
        if (drawable == null) {
            LogHelper.warning("Drawable was NULL");
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        final Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap fromView(final View view) {
        if (view == null) {
            LogHelper.warning("View was NULL");
            return null;
        }

        // read-only
        final DisplayMetrics displayMetrics = SkeletonApplication.CONTEXT.getResources().getDisplayMetrics();
        if (displayMetrics == null) {
            LogHelper.warning("DisplayMetrics was NULL");
            return null;
        }

        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();

        final Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        final Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    public static Bitmap rotate(final Bitmap bitmap, final float degrees) {
        if (bitmap == null) {
            LogHelper.warning("Bitmap was NULL");
            return null;
        }

        final Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

}
