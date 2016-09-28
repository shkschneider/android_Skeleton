package me.shkschneider.skeleton.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Html;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.LogHelper;

// android.text.Html.fromHtml(String, MyImageGetter, null)
// <http://stackoverflow.com/a/25530488>
public class MyImageGetter implements Html.ImageGetter {

    private Context mContext;
    private TextView mTextView;

    public MyImageGetter(@NonNull final Context context, @NonNull final TextView textView) {
        mContext = context;
        mTextView = textView;
    }

    @Override
    public Drawable getDrawable(final String source) {
        final BitmapDrawablePlaceholder bitmapDrawablePlaceholder = new BitmapDrawablePlaceholder();
        Proxy.get(mContext).getImageLoader().get(source, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(final ImageLoader.ImageContainer response, final boolean isImmediate) {
                final Bitmap bitmap = response.getBitmap();
                if (bitmap == null) {
                    LogHelper.warning("Bitmap was NULL");
                    return;
                }
                final BitmapDrawable bitmapDrawable = new BitmapDrawable(ApplicationHelper.resources(mContext), bitmap);
                final int width = bitmapDrawable.getIntrinsicWidth();
                final int height = bitmapDrawable.getIntrinsicHeight();
                bitmapDrawable.setBounds(0, 0, width, height);
                bitmapDrawablePlaceholder.setDrawable(bitmapDrawable);
                bitmapDrawablePlaceholder.setBounds(0, 0, width, height);
                mTextView.setText(mTextView.getText()); // HACK: invalidate()
            }

            @Override
            public void onErrorResponse(final VolleyError error) {
                LogHelper.error(error.getCause().getMessage());
            }
        });
        return bitmapDrawablePlaceholder;
    }

    @SuppressWarnings("deprecation")
    private static class BitmapDrawablePlaceholder extends BitmapDrawable {

        private Drawable mDrawable;

        @Override
        public void draw(final Canvas canvas) {
            if (mDrawable == null) return;
            mDrawable.draw(canvas);
        }

        public void setDrawable(final Drawable drawable) {
            mDrawable = drawable;
        }

    }

}
