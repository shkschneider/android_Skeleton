/**
 * Copyright 2013 ShkSchneider
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.shkschneider.skeleton.helper;

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
public class BitmapHelper {

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
                        LogHelper.w("Downsample was negative");
                    }
                }
                else {
                    LogHelper.w("InputStream was NULL");
                }
            }
            catch (FileNotFoundException e) {
                LogHelper.e("FileNotFoundException: " + e.getMessage());
            }
        }
        else {
            LogHelper.w("Context was NULL");
        }
        return null;
    }

    public static Bitmap decodeUri(final Context context, final Uri uri) {
        return decodeUri(context, uri, ScreenHelper.density(context));
    }

    public static Bitmap bitmapFromUri(final Context context, final Uri uri) {
        if (context != null) {
            try {
                final BitmapFactory.Options options = new BitmapFactory.Options();
                return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
            }
            catch (FileNotFoundException e) {
                LogHelper.e("FileNotFoundException: " + e.getMessage());
            }
        }
        else {
            LogHelper.w("Context was NULL");
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
            LogHelper.w("Drawable was NULL");
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
            LogHelper.w("Bitmap was NULL");
        }
        return null;
    }

}
