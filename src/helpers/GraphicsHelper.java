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
package me.shkschneider.skeleton.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.TypedValue;
import android.widget.ProgressBar;

public abstract class GraphicsHelper {

    public static class Get {

        public static float density(final Context context) {
            if (context != null) {
                return context.getResources().getDisplayMetrics().density;
            }
            else {
                LogHelper.w("Context was NULL");
            }
            return 0F;
        }

        public static int height(final Context context) {
            if (context != null) {
                return context.getResources().getDisplayMetrics().heightPixels;
            }
            else {
                LogHelper.w("Context was NULL");
            }
            return 0;
        }

        public static int width(final Context context) {
            if (context != null) {
                return context.getResources().getDisplayMetrics().widthPixels;
            }
            else {
                LogHelper.w("Context was NULL");
            }
            return 0;
        }

        public static Drawable indeterminateDrawable(final Context context) {
            if (context != null) {
                return new ProgressBar(context).getIndeterminateDrawable();
            }
            else {
                LogHelper.w("Context was NULL");
            }
            return null;
        }

    }

    public static class Transform {

        public static Bitmap rotateBitmap(final Context context, final int id, final float degrees) {
            if (context != null) {
                final Bitmap oldBitmap = BitmapFactory.decodeResource(context.getResources(), id);
                final Matrix matrix = new Matrix();
                matrix.postRotate(degrees);
                return Bitmap.createBitmap(oldBitmap, 0, 0, oldBitmap.getWidth(), oldBitmap.getHeight(), matrix, true);
            }
            else {
                LogHelper.w("Context was NULL");
            }
            return null;
        }

        public static Drawable rotateDrawable(final Context context, final int id, final float degrees) {
            if (context != null) {
                return new BitmapDrawable(context.getResources(), GraphicsHelper.Transform.rotateBitmap(context, id, degrees));
            }
            else {
                LogHelper.w("Context was NULL");
            }
            return null;
        }

        public static void animate(final AnimationDrawable animationDrawable, final Callback callback, final Long time) {
            if (animationDrawable != null) {
                animationDrawable.start();

                if (time > 0) {
                    animateListener(animationDrawable, callback, time);
                }
                else {
                    LogHelper.w("Time was negative");
                }
            }
            else {
                LogHelper.w("AnimationDrawable was NULL");
            }
        }

        public static void animate(final AnimationDrawable animationDrawable, final Callback callback) {
            animate(animationDrawable, callback, 100L);
        }

        private static void animateListener(final AnimationDrawable animationDrawable, final Callback callback, final Long time) {
            if (animationDrawable != null) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (animationDrawable.getCurrent() != animationDrawable.getFrame(animationDrawable.getNumberOfFrames() - 1)) {
                            animateListener(animationDrawable, callback, time);
                        }
                        else if (callback != null) {
                            callback.AnimationCallback();
                        }
                        else {
                            LogHelper.d("Callback was NULL");
                        }
                    }

                }, time);
            }
        }

    }

    public static class Convert {

        public static int pixelsFromDp(final Context context, final Float dp) {
            if (context != null && dp > 0) {
                return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
            }
            else {
                LogHelper.w("Context was NULL");
            }
            return 0;
        }
        
        public static Bitmap bitmapFromUri(final Context context, final Uri uri) {
            if (context != null) {
                ContentResolver contentResolver = context.getContentResolver();
                InputStream inputStream = contentResolver.openInputStream(uri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                return BitmapFactory.decodeStream(inputStream, null, options);
            }
            return null;
        }

        public static Bitmap bitmapFromDrawable(final Drawable drawable) {
            if (drawable != null) {
                if (drawable instanceof BitmapDrawable) {
                    return ((BitmapDrawable)drawable).getBitmap();
                }

                final Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
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

        public static Drawable drawableFromBitmap(final Context context, final Bitmap bitmap) {
            return new BitmapDrawable(context.getResources(), bitmap);
        }

    }

    public static interface Callback {

        public void AnimationCallback();

    }

}
