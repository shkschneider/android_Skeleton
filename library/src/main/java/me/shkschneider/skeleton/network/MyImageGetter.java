package me.shkschneider.skeleton.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.Html;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.LogHelper;

// android.text.Html.fromHtml(String, MyImageGetter, null)
// <http://stackoverflow.com/a/25530488>
public class MyImageGetter implements Html.ImageGetter {

    private TextView mTextView;

    public MyImageGetter(@NonNull final TextView textView) {
        mTextView = textView;
    }

    @Override
    public Drawable getDrawable(final String source) {
        final BitmapDrawablePlaceholder result = new BitmapDrawablePlaceholder();
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(final Void... params) {
                try {
                    // URL
                    final URL url = new URL(source);
                    // URLConnection
                    final HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.addRequestProperty("Cache-Control", "no-cache");
                    // Response
                    final int responseCode = httpURLConnection.getResponseCode();
                    final String responseMessage = httpURLConnection.getResponseMessage();
                    if (responseCode == 200) {
                        // Buffered for performance
                        final InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                        return BitmapFactory.decodeStream(inputStream);
                    }
                    else {
                        LogHelper.error(String.format(Locale.US, "%d: %s", responseCode, responseMessage));
                        return null;
                    }
                }
                catch (final Exception e) {
                    LogHelper.wtf(e);
                    return null;
                }
            }
            @Override
            protected void onPostExecute(final Bitmap bitmap) {
                if (bitmap == null) return;
                final BitmapDrawable bitmapDrawable = new BitmapDrawable(ApplicationHelper.resources(), bitmap);
                final int width = bitmapDrawable.getIntrinsicWidth();
                final int height = bitmapDrawable.getIntrinsicHeight();
                bitmapDrawable.setBounds(0, 0, width, height);
                result.mDrawable = bitmapDrawable;
                result.setBounds(0, 0, width, height);
                mTextView.setText(mTextView.getText()); // FIX: invalidate()
            }
        }.execute((Void) null);
        return result;
    }

    @SuppressWarnings("deprecation")
    static class BitmapDrawablePlaceholder extends BitmapDrawable {

        protected Drawable mDrawable;

        @Override
        public void draw(final Canvas canvas) {
            if (mDrawable == null) return;
            mDrawable.draw(canvas);
        }

    }

}
