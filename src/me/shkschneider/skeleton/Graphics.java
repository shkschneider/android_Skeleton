package me.shkschneider.skeleton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ProgressBar;

import java.io.FileNotFoundException;
import java.io.InputStream;

@SuppressWarnings("unused")
public class Graphics {

    public static Bitmap decodeUri(final Context context, final Uri uri, final Integer downsample) {
        if (context != null) {
            final BitmapFactory.Options bitmapFactoryOptionsTmp = new BitmapFactory.Options();
            bitmapFactoryOptionsTmp.inJustDecodeBounds = true;
            try {
                final InputStream inputStream = context.getContentResolver().openInputStream(uri);
                if (inputStream != null) {
                    BitmapFactory.decodeStream(inputStream, null, bitmapFactoryOptionsTmp);

                    int width = bitmapFactoryOptionsTmp.outWidth;
                    int height = bitmapFactoryOptionsTmp.outHeight;
                    int scale = 1;
                    if (downsample > 0) {
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
                    else {
                        Log.w("Downsample was negative");
                    }
                }
                else {
                    Log.w("InputStream was NULL");
                }
            }
            catch (FileNotFoundException e) {
                Log.e("FileNotFoundException: " + e.getMessage());
            }
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

    public static Bitmap decodeUri(final Context context, final Uri uri) {
        return decodeUri(context, uri, Skeleton.Screen.density(context));
    }

    public static Bitmap bitmapFromUri(final Context context, final Uri uri) {
        if (context != null) {
            try {
                final BitmapFactory.Options options = new BitmapFactory.Options();
                return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
            }
            catch (FileNotFoundException e) {
                Log.e("FileNotFoundException: " + e.getMessage());
            }
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

    public static Bitmap bitmapFromDrawable(final Drawable drawable) {
        if (drawable != null) {
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }

            final Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;
        }
        else {
            Log.w("Drawable was NULL");
        }
        return null;
    }

    public static Bitmap rotateBitmap(final Bitmap bitmap, final float degrees) {
        if (bitmap != null) {
            final Matrix matrix = new Matrix();
            matrix.postRotate(degrees);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        else {
            Log.w("Bitmap was NULL");
        }
        return null;
    }

    public static Drawable drawableFromBitmap(final Context context, final Bitmap bitmap) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static Drawable indeterminateDrawable(final Context context) {
        if (context != null) {
            return new ProgressBar(context).getIndeterminateDrawable();
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

}
