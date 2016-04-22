package me.shkschneider.skeleton.network;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import java.net.URL;

import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.RunnableHelper;

public class ImageDownloader {

    private static final int SIZE = (int) (Runtime.getRuntime().maxMemory() / 1024) / 8;
    private static LruCache<String, Bitmap> CACHE;

    public static void clearCache() {
        if (CACHE != null) {
            CACHE.evictAll();
            CACHE = null;
        }
    }

    public static void clearCache(final String key) {
        if (CACHE != null) {
            CACHE.remove(key);
        }
    }

    public static boolean cached(final String key) {
        if (CACHE == null) return false;
        return (CACHE.get(key) != null);
    }

    private Activity mActivity;
    private String mUrl;
    private boolean mCache = false;

    private ImageDownloader() {
        // Ignore
    }

    private ImageDownloader(@NonNull final Activity activity) {
        mActivity = activity;
    }

    public static ImageDownloader with(final Activity activity) {
        return new ImageDownloader(activity);
    }

    public ImageDownloader cache() {
        mCache = true;
        return this;
    }

    public ImageDownloader from(@NonNull final String url) {
        mUrl = url;
        return this;
    }

    public void into(@NonNull final ImageView imageView) {
        get(mActivity, mCache, mUrl, imageView, null);
    }

    public void get(@NonNull final Callback callback) {
        get(mActivity, false, mUrl, null, callback);
    }

    private static void get(@NonNull final Activity activity, final boolean cache, @NonNull final String from, final ImageView into, final Callback callback) {
        if (cache && CACHE != null) {
            final Bitmap bitmap = CACHE.get(from);
            if (bitmap != null) {
                if (into != null) {
                    RunnableHelper.runOnUiThread(activity, new Runnable() {
                        @Override
                        public void run() {
                            into.setImageBitmap(bitmap);
                        }
                    });
                }
                return;
            }
        }
        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(final Void... voids) {
                try {
                    final URL url = new URL(from);
                    final Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    if (bitmap != null) {
                        if (cache) {
                            if (CACHE == null) {
                                CACHE = new LruCache<String, Bitmap>(SIZE) {
                                    @Override
                                    protected int sizeOf(final String key, final Bitmap value) {
                                        return (bitmap.getByteCount() / 1024);
                                    }
                                };
                            }
                            CACHE.put(from, bitmap);
                        }
                        if (into != null) {
                            RunnableHelper.runOnUiThread(activity, new Runnable() {
                                @Override
                                public void run() {
                                    into.setImageBitmap(bitmap);
                                }
                            });
                        }
                        if (callback != null) {
                            RunnableHelper.runOnUiThread(activity, new Runnable() {
                                @Override
                                public void run() {
                                    callback.bitmap(bitmap);
                                }
                            });
                        }
                    }
                }
                catch (final Exception e) {
                    LogHelper.wtf(e);
                }
                return null;
            }

        }.execute();
    }

    public interface Callback {

        void bitmap(final Bitmap bitmap);

    }

    // TODO cancel()

}
