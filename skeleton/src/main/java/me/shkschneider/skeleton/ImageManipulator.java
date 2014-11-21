package me.shkschneider.skeleton;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.jetbrains.annotations.NotNull;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.LogHelper;

/**
 * Handles Bitmaps (locally).
 *
 * - Bitmap fromDrawable(Drawable)
 * - Bitmap circular(Bitmap)
 * - Bitmap rotate(Bitmap, float)
 * - Bitmap fromView(View)
 *
 * @see android.graphics.Bitmap
 */
public class ImageManipulator {

    @Deprecated
    public static Drawable fromBitmap(@NotNull final Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    public static Bitmap fromDrawable(@NotNull final Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        final Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap circular(@NotNull final Bitmap bitmap) {
        final Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final BitmapShader shader = new BitmapShader (bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        final Paint paint = new Paint();
        paint.setShader(shader);
        final Canvas canvas = new Canvas(circleBitmap);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        return circleBitmap;
    }

    public static Bitmap rotate(@NotNull final Bitmap bitmap, final float degrees) {
        final Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap fromView(@NotNull final View view) {
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

    public static int vibrantColor(@NotNull final Bitmap bitmap) {
        final Palette palette = Palette.generate(bitmap);
        return palette.getVibrantColor(ApplicationHelper.resources().getColor(R.color.transparent));
    }

    public static int mutedColor(@NotNull final Bitmap bitmap) {
        final Palette palette = Palette.generate(bitmap);
        return palette.getMutedColor(ApplicationHelper.resources().getColor(R.color.transparent));
    }

}
