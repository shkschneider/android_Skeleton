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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.widget.ProgressBar;

public abstract class GraphicsHelper {

    public static class Get {

        public static float density(final Context context) {
            if (context != null) {
                return context.getResources().getDisplayMetrics().density;
            }
            return 0F;
        }

        public static int height(final Context context) {
            if (context != null) {
                return context.getResources().getDisplayMetrics().heightPixels;
            }
            return 0;
        }

        public static int width(final Context context) {
            if (context != null) {
                return context.getResources().getDisplayMetrics().widthPixels;
            }
            return 0;
        }

        public static Drawable indeterminateDrawable(final Context context) {
            if (context != null) {
                return new ProgressBar(context).getIndeterminateDrawable();
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
            return null;
        }

        public static Drawable rotateDrawable(final Context context, final int id, final float degrees) {
            if (context != null) {
                return new BitmapDrawable(context.getResources(), GraphicsHelper.Transform.rotateBitmap(context, id, degrees));
            }
            return null;
        }

    }

    public static class Convert {

        public static int pixelsFromDp(final Context context, final Float dp) {
            if (context != null && dp > 0) {
                return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
            }
            return 0;
        }

        public static Bitmap bitmapFromDrawable(final Drawable drawable) {
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable)drawable).getBitmap();
            }

            final Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;
        }

        public static Drawable drawableFromBitmap(final Context context, final Bitmap bitmap) {
            return new BitmapDrawable(context.getResources(), bitmap);
        }

    }

}
