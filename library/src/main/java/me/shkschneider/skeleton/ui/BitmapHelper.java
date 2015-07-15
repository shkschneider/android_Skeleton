package me.shkschneider.skeleton.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.ScreenHelper;

public class BitmapHelper {

    public static Bitmap fromResource(@DrawableRes final int id) {
        return BitmapFactory.decodeResource(ApplicationHelper.resources(), id);
    }

    public static Bitmap fromDrawable(@NonNull final Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        final Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap changeWhiteColor(@NonNull final Bitmap sourceBitmap, @ColorInt final int color) {
        final Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, (sourceBitmap.getWidth() - 1), (sourceBitmap.getHeight() - 1));
        final Paint paint = new Paint();
        final ColorFilter colorFilter = new LightingColorFilter(color, 1);
        paint.setColorFilter(colorFilter);
        final Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, paint);
        return resultBitmap;
    }

    @Deprecated
    public static Bitmap circular(@NonNull final Bitmap bitmap) {
        final Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        final Paint paint = new Paint();
        paint.setShader(shader);
        final Canvas canvas = new Canvas(circleBitmap);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        bitmap.recycle();
        return circleBitmap;
    }

    @Nullable
    public static Bitmap fromView(@NonNull final View view) {
        final DisplayMetrics displayMetrics = ApplicationHelper.resources().getDisplayMetrics();
        if (displayMetrics == null) {
            LogHelper.w("DisplayMetrics was NULL");
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

    @Nullable
    public static Bitmap fromUri(@NonNull final Uri uri) {
        try {
            return BitmapFactory.decodeStream(ApplicationHelper.context().getContentResolver().openInputStream(uri), null, new BitmapFactory.Options());
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return null;
        }
    }

    public static Bitmap compress(@NonNull final Bitmap bitmap, final int factor) {
        if (factor <= 0) {
            return bitmap;
        }
        if ((factor & (factor - 1)) != 0) {
            LogHelper.w("Factor should be a power of 2");
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = factor;
        options.inPreferQualityOverSpeed = true;
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        final byte[] bytes = byteArrayOutputStream.toByteArray();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    @Nullable
    public static Bitmap decodeUri(@NonNull final Uri uri) {
        final BitmapFactory.Options bitmapFactoryOptionsTmp = new BitmapFactory.Options();
        bitmapFactoryOptionsTmp.inJustDecodeBounds = true;
        try {
            final InputStream inputStream = ApplicationHelper.context().getContentResolver().openInputStream(uri);
            if (inputStream == null) {
                LogHelper.w("InputStream was NULL");
                return null;
            }

            BitmapFactory.decodeStream(inputStream, null, bitmapFactoryOptionsTmp);
            int width = bitmapFactoryOptionsTmp.outWidth;
            int height = bitmapFactoryOptionsTmp.outHeight;
            int scale = 1;

            final int downsample = (int) ScreenHelper.density();
            if (downsample <= 0) {
                LogHelper.w("Downsample was invalid");
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
            return BitmapFactory.decodeStream(inputStream, null, bitmapFactoryOptions);
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return null;
        }
    }

    public static Bitmap rotate(@NonNull final Bitmap bitmap, final float degrees) {
        final Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static String toBase64(@NonNull final Bitmap bitmap) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        final byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

}
