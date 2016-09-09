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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    public static Bitmap tint(@NonNull final Bitmap bitmap, @ColorInt final int color) {
        final Bitmap tintedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        final Paint paint = new Paint();
        final ColorFilter colorFilter = new LightingColorFilter(color, 0);
        paint.setColorFilter(colorFilter);
        final Canvas canvas = new Canvas(tintedBitmap);
        canvas.drawBitmap(tintedBitmap, 0, 0, paint);
        return tintedBitmap;
    }

    public static Bitmap tint(@NonNull final Bitmap bitmap, @ColorInt final int from, @ColorInt final int to) {
        final Bitmap tintedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        for (int x = 0; x < bitmap.getHeight(); x++) {
            for (int y = 0; y < bitmap.getWidth(); y++) {
                final int px = tintedBitmap.getPixel(x, y);
                if (px == from) {
                    tintedBitmap.setPixel(x, y, to);
                }
            }
        }
        return tintedBitmap;
    }

    public static Bitmap circular(@NonNull final Bitmap bitmap) {
        final Bitmap circularBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Paint paint = new Paint();
        final BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(bitmapShader);
        final Canvas canvas = new Canvas(circularBitmap);
        canvas.drawCircle(circularBitmap.getWidth() / 2, circularBitmap.getHeight() / 2, circularBitmap.getWidth() / 2, paint);
        return circularBitmap;
    }

    @Nullable
    public static Bitmap fromView(@NonNull final View view) {
        final DisplayMetrics displayMetrics = ApplicationHelper.resources().getDisplayMetrics();
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

    @Nullable
    public static Bitmap fromInputStream(@NonNull final InputStream inputStream, BitmapFactory.Options options) {
        if (options == null) {
            options = new BitmapFactory.Options();
        }
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    @Nullable
    public static Bitmap fromUri(@NonNull final Uri uri, BitmapFactory.Options options) {
        if (options == null) {
            options = new BitmapFactory.Options();
        }
        try {
            final InputStream inputStream = ApplicationHelper.context().getContentResolver().openInputStream(uri);
            if (inputStream == null) {
                return null;
            }
            return BitmapHelper.fromInputStream(inputStream, options);
        }
        catch (final FileNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    @Nullable
    public static Bitmap fromFile(@NonNull final File file) {
        try {
            return BitmapHelper.fromInputStream(new FileInputStream(file), new BitmapFactory.Options() {
                {
                    inPreferredConfig = Bitmap.Config.ARGB_8888;
                }
            });
        }
        catch (final FileNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static Bitmap compress(@NonNull final Bitmap bitmap, final int factor) {
        if (factor <= 0) {
            return bitmap;
        }
        if ((factor & (factor - 1)) != 0) {
            LogHelper.warning("Factor should be a power of 2");
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

    @Deprecated // Avoid
    @Nullable
    public static Bitmap decodeUri(@NonNull final Uri uri) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            final InputStream inputStream = ApplicationHelper.context().getContentResolver().openInputStream(uri);
            if (inputStream == null) {
                LogHelper.warning("InputStream was NULL");
                return null;
            }
            // Tries to optimize the process
            int width = options.outWidth;
            int height = options.outHeight;
            int scale = 1;
            final int downsample = (int) ScreenHelper.density();
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
            options.inJustDecodeBounds = false;
            options.inSampleSize = scale;
            return BitmapHelper.fromInputStream(inputStream, options);
        }
        catch (final FileNotFoundException e) {
            LogHelper.wtf(e);
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
