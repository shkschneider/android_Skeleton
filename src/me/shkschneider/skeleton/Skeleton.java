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
package me.shkschneider.skeleton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.auth.FacebookHandle;
import com.androidquery.callback.AbstractAjaxCallback;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.apache.http.HttpStatus;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.protocol.HTTP;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/*
 * Skeleton
 *  Log
 *  Android
 *  System
 *  File
 *  Keyboard
 *  Vibrator
 *  Network
 *  Notification
 *  Runtime
 *  String
 *  Time
 *  WebView
 *  Hash
 *  Facebook
 *  WebService
 */

@SuppressWarnings("unused")
public abstract class Skeleton {

    public static final java.lang.String PLATFORM = "Android";

    public static class Log {

        private static final int VERBOSE = 10;
        private static final int DEBUG = 20;
        private static final int INFO = 30;
        private static final int WARN = 40;
        private static final int ERROR = 50;

        private static void log(final int state, final java.lang.String msg) {
            final java.lang.String tag = SkeletonApplication.TAG;

            // Uses StackTrace to build the log tag
            final StackTraceElement[] elements = new Throwable().getStackTrace();
            java.lang.String callerClassName = "?";
            java.lang.String callerMethodName = "?";
            if (elements.length >= 3) {
                callerClassName = elements[2].getClassName();
                callerClassName = callerClassName.substring(callerClassName.lastIndexOf('.') + 1);
                if (callerClassName.indexOf("$") > 0) {
                    callerClassName = callerClassName.substring(0, callerClassName.indexOf("$"));
                }
                callerMethodName = elements[2].getMethodName();
                callerMethodName = callerMethodName.substring(callerMethodName.lastIndexOf('_') + 1);
            }

            final java.lang.String stack = callerClassName + " " + callerMethodName + "()";

            switch (state) {
                case VERBOSE:
                    android.util.Log.v(tag, "[" + stack + "] " + msg);
                    break ;

                case DEBUG:
                    android.util.Log.d(tag, "[" + stack + "] " + msg);
                    break ;

                case INFO:
                    android.util.Log.i(tag, "[" + stack + "] " + msg);
                    break ;

                case WARN:
                    android.util.Log.w(tag, "[" + stack + "] " + msg);
                    break ;

                case ERROR:
                    android.util.Log.e(tag, "[" + stack + "] " + msg);
                    break ;
            }
        }

        public static void v(final java.lang.String msg) {
            log(VERBOSE, msg);
        }

        public static void d(final java.lang.String msg) {
            log(DEBUG, msg);
        }

        public static void i(final java.lang.String msg) {
            log(INFO, msg);
        }

        public static void w(final java.lang.String msg) {
            log(WARN, msg);
        }

        public static void e(final java.lang.String msg) {
            log(ERROR, msg);
        }

    }

    public static class Android {

        // If SCREENLAYOUT_SIZE is XLARGE for API >= HONEYCOMB

        public static Boolean isTablet(final Context context) {
            if (context != null) {
                if (getApi() >= Build.VERSION_CODES.HONEYCOMB) {
                    final Configuration configuration = context.getResources().getConfiguration();
                    if (configuration != null) {
                        try {
                            return (Boolean) configuration.getClass().getMethod("isLayoutSizeAtLeast", int.class)
                                    .invoke(configuration, Configuration.SCREENLAYOUT_SIZE_XLARGE);
                        }
                        catch (NoSuchMethodException e) {
                            Log.e("NoSuchMethodException: " + e.getMessage());
                        }
                        catch (IllegalAccessException e) {
                            Log.e("IllegalAccessException: " + e.getMessage());
                        }
                        catch (InvocationTargetException e) {
                            Log.e("InvocationTargetException: " + e.getMessage());
                        }
                    }
                    else {
                        Log.w("Configuration was NULL");
                    }
                }
                else {
                    Log.d("Api was < HONEYCOMB");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return false;
        }

        // Get Android ID (length: 16)
        // The value may change if a factory reset is performed on the device.

        public static java.lang.String getId(final Context context) {
            if (context != null) {
                final java.lang.String id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                if (! TextUtils.isEmpty(id)) {
                    return id.toLowerCase();
                }
                else {
                    Log.w("Id was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        // Get Device ID (length: 32)
        // The value may change if a factory reset is performed on the device.

        public static java.lang.String getDeviceId(final Context context) {
            if (context != null) {
                final java.lang.String id = getId(context);
                if (! TextUtils.isEmpty(id)) {
                    try {
                        final MessageDigest messageDigest = MessageDigest.getInstance(Hash.MD5);
                        if (messageDigest != null) {
                            return new BigInteger(1, messageDigest.digest(id.getBytes())).toString(16).toLowerCase();
                        }
                        else {
                            Log.w("MessageDigest was NULL");
                        }
                    }
                    catch (NoSuchAlgorithmException e) {
                        Log.e("NoSuchAlgorithmException: " + e.getMessage());
                    }
                }
                else {
                    Log.w("Id was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        // Get UUID from Device ID (RFC4122, length: 36)
        // The value may change if a factory reset is performed on the device.

        public static java.lang.String getUUID(final Context context) {
            if (context != null) {
                final java.lang.String deviceId = getDeviceId(context);
                if (! TextUtils.isEmpty(deviceId)) {
                    return UUID.nameUUIDFromBytes(deviceId.getBytes()).toString();
                }
                else {
                    Log.w("DeviceId was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        // Get a random ID (RFC4122, length: 32)
        // Random

        public static java.lang.String getRandomId() {
            return UUID.randomUUID().toString().replace("-", "");
        }

        public static java.lang.String getDevice() {
            return Build.DEVICE;
        }

        public static java.lang.String getRelease() {
            return Build.VERSION.RELEASE;
        }

        public static int getApi() {
            return Build.VERSION.SDK_INT;
        }

        public static Boolean isDebug() {
            return BuildConfig.DEBUG;
        }

        public static java.lang.String getPackage(final Context context) {
            if (context != null) {
                return context.getPackageName();
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static java.lang.String getName(final Context context) {
            if (context != null) {
                return context.getResources().getString(R.string.app_name);
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static Integer getVersionCode(final Context context) {
            if (context != null) {
                try {
                    final PackageManager packageManager = context.getPackageManager();
                    if (packageManager != null) {
                        return packageManager.getPackageInfo(getPackage(context), PackageManager.GET_META_DATA).versionCode;
                    }
                    else {
                        Log.w("PackageManager was NULL");
                    }
                }
                catch (PackageManager.NameNotFoundException e) {
                    Log.e("NameNotFoundException: " + e.getMessage());
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return 0;
        }

        public static java.lang.String getVersionName(final Context context) {
            if (context != null) {
                try {
                    final PackageManager packageManager = context.getPackageManager();
                    if (packageManager != null) {
                        return packageManager.getPackageInfo(getPackage(context), PackageManager.GET_META_DATA).versionName;
                    }
                    else {
                        Log.w("PackageManager was NULL");
                    }
                }
                catch (PackageManager.NameNotFoundException e) {
                    Log.e("NameNotFoundException: " + e.getMessage());
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return "0";
        }

    }

    public static class System {

        public static final java.lang.String SYSTEM_PROPERTY_JAVA_VM_NAME = "java.vm.name";
        public static final java.lang.String SYSTEM_PROPERTY_JAVA_VM_VENDOR = "java.vm.vendor";
        public static final java.lang.String SYSTEM_PROPERTY_JAVA_VM_VERSION = "java.vm.version";
        // java.version is not implemented on Android
        public static final java.lang.String SYSTEM_PROPERTY_JAVA_HOME = "java.home";
        public static final java.lang.String SYSTEM_PROPERTY_USER_DIR = "user.dir";
        // user.home is not implemented on Android
        public static final java.lang.String SYSTEM_PROPERTY_USER_REGION = "user.region";
        public static final java.lang.String SYSTEM_PROPERTY_JAVA_IO_TMPDIR = "java.io.tmpdir";
        public static final java.lang.String SYSTEM_PROPERTY_JAVA_RUNTIME_NAME = "java.runtime.name";
        public static final java.lang.String SYSTEM_PROPERTY_HTTP_AGENT = "http.agent";
        public static final java.lang.String SYSTEM_PROPERTY_FILE_SEPARATOR = "file.separator";
        public static final java.lang.String SYSTEM_PROPERTY_FILE_ENCODING = "file.encoding";
        public static final java.lang.String SYSTEM_PROPERTY_LINE_SEPARATOR = "line.separator";
        public static final java.lang.String SYSTEM_PROPERTY_OS_ARCH = "os.arch";
        public static final java.lang.String SYSTEM_PROPERTY_OS_NAME = "os.name";
        public static final java.lang.String SYSTEM_PROPERTY_OS_VERSION = "os.version";
        public static final java.lang.String SYSTEM_PROPERTY_PATH_SEPARATOR = "path.separator";

        public static java.lang.String getSystemProperty(final java.lang.String property) {
            if (! TextUtils.isEmpty(property)) {
                final java.lang.String systemProperty = System.getSystemProperty(property);
                if (systemProperty != null) {
                    return systemProperty;
                }
                else {
                    Log.w("SystemProperty was NULL");
                }
            }
            return null;
        }

        public static java.lang.String uname() {
            return java.lang.String.format("%s %s %s",
                    getSystemProperty(SYSTEM_PROPERTY_OS_NAME),
                    getSystemProperty(SYSTEM_PROPERTY_OS_VERSION),
                    getSystemProperty(SYSTEM_PROPERTY_OS_ARCH));
        }

        public static final java.lang.String SYSTEM_SERVICE_WINDOW_SERVICE = Context.WINDOW_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_LAYOUT_INFLATER_SERVICE = Context.LAYOUT_INFLATER_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_ACTIVITY_SERVICE = Context.ACTIVITY_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_POWER_SERVICE = Context.POWER_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_ALARM_SERVICE = Context.ALARM_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_NOTIFICATION_SERVICE = Context.NOTIFICATION_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_KEYGUARD_SERVICE = Context.KEYGUARD_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_LOCATION_SERVICE = Context.LOCATION_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_SEARCH_SERVICE = Context.SEARCH_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_VIBRATOR_SERVICE = Context.VIBRATOR_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_CONNECTIVITY_SERVICE = Context.CONNECTIVITY_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_WIFI_SERVICE = Context.WIFI_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_INPUT_METHOD_SERVICE = Context.INPUT_METHOD_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_UI_MODE_SERVICE = Context.UI_MODE_SERVICE;
        public static final java.lang.String SYSTEM_SERVICE_DOWNLOAD_SERVICE = Context.DOWNLOAD_SERVICE;

        public static Object getSystemService(final Context context, final java.lang.String service) {
            if (context != null) {
                return context.getSystemService(service);
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

    }

    public static class File {

        public static final java.lang.String ASSETS_PREFIX = "file:///android_asset/";

        // Get

        public static java.io.File get(final java.lang.String path) {
            if (! TextUtils.isEmpty(path)) {
                return new java.io.File(path);
            }
            else {
                Log.w("Path was NULL");
            }
            return null;
        }

        // Open

        public static InputStream openFile(final java.io.File file) {
            if (file != null) {
                InputStream inputStream = null;

                try {
                    inputStream = new FileInputStream(file);
                    inputStream.close();
                }
                catch (FileNotFoundException e) {
                    Log.e("FileNotFoundException: " + e.getMessage());
                }
                catch (IOException e) {
                    Log.e("IOException: " + e.getMessage());
                }

                return inputStream;
            }
            else {
                Log.w("File was NULL");
            }
            return null;
        }

        public static InputStream openRaw(final Context context, final int id) {
            if (context != null) {
                return context.getResources().openRawResource(id);
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static InputStream openAsset(final Context context, final java.lang.String name) {
            if (context != null) {
                try {
                    return context.getAssets().open(name);
                }
                catch (IOException e) {
                    Log.e("IOException: " + e.getMessage());
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        // Read & Write

        public static java.lang.String readString(final InputStream inputStream) {
            if (inputStream != null) {
                java.lang.String string = "";
                final Scanner scanner = new Scanner(inputStream);
                while (scanner.hasNextLine()) {
                    string = string.concat(scanner.nextLine() + "\n");
                }
                return string;
            }
            else {
                Log.w("InputStream was NULL");
            }
            return null;
        }

        public static java.lang.String readString(final java.io.File file) {
            if (file != null) {
                try {
                    return readString(new FileInputStream(file));
                }
                catch (FileNotFoundException e) {
                    Log.e("FileNotFoundException: " + e.getMessage());
                }
            }
            else {
                Log.w("File was NULL");
            }
            return null;
        }

        public static Bitmap readBitmap(final java.io.File file) {
            if (file != null) {
                try {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    return BitmapFactory.decodeStream(new FileInputStream(file), null, options);
                }
                catch (FileNotFoundException e) {
                    Log.e("FileNotFoundException: " + e.getMessage());
                }
            }
            else {
                Log.w("File was NULL");
            }
            return null;
        }

        public static Boolean writeString(final OutputStream outputStream, final java.lang.String content) {
            if (outputStream != null) {
                if (! TextUtils.isEmpty(content)) {
                    try {
                        outputStream.write(content.getBytes());
                        outputStream.close();
                        return true;
                    }
                    catch (FileNotFoundException e) {
                        Log.e("FileNotFoundException: " + e.getMessage());
                    }
                    catch (IOException e) {
                        Log.e("IOException: " + e.getMessage());
                    }
                }
                else {
                    Log.w("String was NULL");
                }
            }
            else {
                Log.w("OutputStream was NULL");
            }
            return false;
        }

        public static Boolean writeString(final java.io.File file, final java.lang.String content) {
            try {
                return writeString(new FileOutputStream(file), content);
            }
            catch (FileNotFoundException e) {
                Log.e("FileNotFoundException: " + e.getMessage());
            }
            return null;
        }

        public static Boolean writeBitmap(final java.io.File file, final Bitmap bitmap) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
                return true;
            }
            catch (FileNotFoundException e) {
                Log.e("FileNotFoundException: " + e.getMessage());
            }
            return false;
        }

        // Serialize & Unserialize

        public static Boolean serialize(final java.io.File file, final Object object) {
            try {
                final FileOutputStream fileOutputStream = new FileOutputStream(file);
                final ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(object);
                objectOutputStream.flush();
                objectOutputStream.close();
                return true;
            }
            catch (IOException e) {
                Log.e("IOException: " + e.getMessage());
            }
            return false;
        }

        public static Object unserialize(final java.io.File file) {
            try {
                final FileInputStream fileInputStream = new FileInputStream(file);
                final ObjectInputStream objectOutputStream = new ObjectInputStream(fileInputStream);
                return objectOutputStream.readObject();
            }
            catch (IOException e) {
                Log.e("IOException: " + e.getMessage());
            }
            catch (ClassNotFoundException e) {
                Log.e("ClassNotFoundException: " + e.getMessage());
            }
            return null;
        }

        // Remove

        public static Boolean remove(final java.io.File file) {
            if (file != null) {
                return file.delete();
            }
            else {
                Log.w("File was NULL");
            }
            return false;
        }

        // Internal

        public static java.lang.String getInternalDir(final Context context, final java.lang.String name) {
            if (context != null) {
                final java.io.File dir = context.getFilesDir();
                if (dir != null) {
                    return (new java.io.File(dir.getAbsolutePath(), name).getAbsolutePath());
                }
                else {
                    Log.w("Context was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        // External

        public static java.lang.String getExternalDir(final Context context, final java.lang.String name) {
            if (context != null) {
                final java.io.File dir = context.getExternalFilesDir(name);
                if (dir != null) {
                    return dir.getAbsolutePath();
                }
                else {
                    Log.w("File was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        // Cache

        public static java.lang.String getInternalCacheDir(final Context context) {
            if (context != null) {
                final java.io.File dir = context.getCacheDir();
                if (dir != null) {
                    return dir.getAbsolutePath();
                }
                else {
                    Log.w("File was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static java.lang.String getExternalCacheDir(final Context context) {
            if (context != null) {
                final java.io.File dir = context.getExternalCacheDir();
                if (dir != null) {
                    return dir.getAbsolutePath();
                }
                else {
                    Log.w("File was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static java.lang.String getDownloadCache() {
            return Environment.getDownloadCacheDirectory().getAbsolutePath();
        }

        // SDCard

        public static Boolean hasSdCardAvailable() {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        }

        public static java.lang.String getSdCard() {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }

    }

    public static int pixelsFromDp(final Context context, final Float dp) {
        if (context != null) {
            if (dp > 0) {
                return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
            }
            else {
                return 0;
            }
        }
        else {
            Log.w("Context was NULL");
        }
        return 0;
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
                return ((BitmapDrawable)drawable).getBitmap();
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

    public static Drawable drawableFromBitmap(final Context context, final Bitmap bitmap) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static float density(final Context context) {
        if (context != null) {
            return context.getResources().getDisplayMetrics().density;
        }
        else {
            Log.w("Context was NULL");
        }
        return 0F;
    }

    public static int height(final Context context) {
        if (context != null) {
            return context.getResources().getDisplayMetrics().heightPixels;
        }
        else {
            Log.w("Context was NULL");
        }
        return 0;
    }

    public static int width(final Context context) {
        if (context != null) {
            return context.getResources().getDisplayMetrics().widthPixels;
        }
        else {
            Log.w("Context was NULL");
        }
        return 0;
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

    public static class Keyboard {

        // Behavior can vary

        public static void showKeyboard(final Activity activity) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

            /*
            final InputMethodManager inputMethodManager = (InputMethodManager) System.getSystemService(activity, Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                final View view = activity.getCurrentFocus();
                if (view != null) {
                    inputMethodManager.toggleSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                }
                else {
                    L.d("View was NULL");
                }
            }
            else {
                L.d("InputMethodManager was NULL");
            }
            */
        }

        // Behavior can vary

        public static void hideKeyboard(final Activity activity) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            /*
            final InputMethodManager inputMethodManager = (InputMethodManager) System.getSystemService(activity, Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                final View view = activity.getCurrentFocus();
                if (view != null) {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                else {
                    L.d("View was NULL");
                }
            }
            else {
                L.d("InputMethodManager was NULL");
            }
            */
        }

        public static void keyboardCallback(final EditText editText, final KeyboardCallback callback) {
            if (editText != null) {
                if (callback != null) {
                    editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                        @Override
                        public boolean onEditorAction(final TextView textView, final int i, final KeyEvent keyEvent) {
                            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                                    (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                                callback.keyboardCallback();
                            }
                            // Keep false
                            return false;
                        }

                    });
                }
                else {
                    Log.w("Callback was NULL");
                }
            }
            else {
                Log.w("EditText was NULL");
            }
        }

        public static interface KeyboardCallback {

            public void keyboardCallback();

        }

    }

    public static class Vibrator {

        @SuppressLint("NewApi")
        private static void vibrateNew(final android.os.Vibrator vibrator, final long duration) {
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(duration);
            }
            else {
                Log.d("Vibrator cannot vibrate");
            }
        }

        @SuppressLint("NewApi")
        private static void vibrateNew(final android.os.Vibrator vibrator, final long[] durations, final Boolean repeat) {
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(durations, (repeat ? 0 : -1));
            }
            else {
                Log.d("Vibrator cannot vibrate");
            }
        }

        private static void vibrateOld(final android.os.Vibrator vibrator, final long duration) {
            vibrator.vibrate(duration);
        }

        private static void vibrateOld(final android.os.Vibrator vibrator, final long[] durations, final Boolean repeat) {
            vibrator.vibrate(durations, (repeat ? 0 : -1));
        }

        public static void vibrate(final Context context, final long duration) {
            final android.os.Vibrator vibrator = (android.os.Vibrator) System.getSystemService(context, System.SYSTEM_SERVICE_VIBRATOR_SERVICE);
            if (vibrator != null) {
                if (Android.getApi() < Build.VERSION_CODES.HONEYCOMB) {
                    vibrateOld(vibrator, duration);
                }
                else {
                    vibrateNew(vibrator, duration);
                }
            }
            else {
                Log.w("Vibrator was NULL");
            }
        }

        public static void vibrate(final Context context, final long[] durations, final Boolean repeat) {
            final android.os.Vibrator vibrator = (android.os.Vibrator) System.getSystemService(context, System.SYSTEM_SERVICE_VIBRATOR_SERVICE);
            if (vibrator != null) {
                if (Android.getApi() < Build.VERSION_CODES.HONEYCOMB) {
                    vibrateOld(vibrator, durations, repeat);
                }
                else {
                    vibrateNew(vibrator, durations, repeat);
                }
            }
            else {
                Log.w("Vibrator was NULL");
            }
        }

    }

    public static class Network {

        public static java.lang.String getDefaultUserAgent() {
            final java.lang.String userAgent = System.getSystemProperty(System.SYSTEM_PROPERTY_HTTP_AGENT);
            return (userAgent != null ? userAgent : java.lang.String.format("%s-%d", PLATFORM, Android.getApi()));
        }

        public static java.lang.String makeUserAgent(final Context context) {
            if (context != null) {
                return PLATFORM + "-" +
                        Android.getApi() + "/" +
                        Android.getPackage(context) + "/" +
                        Android.getVersionCode(context);
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static Boolean isConnectedToInternet(final Context context) {
            final NetworkInfo networkInfo = ((ConnectivityManager) System.getSystemService(context, System.SYSTEM_SERVICE_CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (networkInfo != null) {
                return (! networkInfo.isConnected());
            }
            else {
                Log.w("NetworkInfo was NULL");
            }
            return true;
        }

        public static java.lang.String getMacAddress(final Context context) {
            if (context != null) {
                final WifiManager manager = (WifiManager) System.getSystemService(context, System.SYSTEM_SERVICE_WIFI_SERVICE);
                final WifiInfo info = manager.getConnectionInfo();
                final java.lang.String macAddress = info.getMacAddress();
                if (! TextUtils.isEmpty(macAddress)) {
                    return macAddress;
                }
                else {
                    Log.w("MacAddress was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static Boolean isValidUrl(final java.lang.String url) {
            if (! TextUtils.isEmpty(url)) {
                return URLUtil.isValidUrl(url);
            }
            else {
                Log.w("Url was null");
            }
            return false;
        }

        public static List<java.lang.String> ipAddresses() {
            final List<java.lang.String> ipAddresses = new ArrayList<java.lang.String>();
            try {
                for (final Enumeration<NetworkInterface> enumerationNetworkInterface = NetworkInterface.getNetworkInterfaces(); enumerationNetworkInterface.hasMoreElements();) {
                    final NetworkInterface networkInterface = enumerationNetworkInterface.nextElement();
                    for (Enumeration<InetAddress> enumerationInetAddress = networkInterface.getInetAddresses(); enumerationInetAddress.hasMoreElements();) {
                        final InetAddress inetAddress = enumerationInetAddress.nextElement();
                        final java.lang.String ipAddress = inetAddress.getHostAddress();
                        if (! inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipAddress)) {
                            ipAddresses.add(ipAddress);
                        }
                    }
                }
            } catch (SocketException e) {
                Log.e("SocketException: " + e.getMessage());
            }
            return ipAddresses;
        }

    }

    public static class Notification {

        public static void showToastShort(final Context context, final java.lang.String text) {
            if (context != null) {
                if (! TextUtils.isEmpty(text)) {
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.w("String was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
        }

        public static void showToastLong(final Context context, final java.lang.String text) {
            if (context != null) {
                if (! TextUtils.isEmpty(text)) {
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                }
                else {
                    Log.w("String was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
        }

        public static NotificationManager getNotificationManager(final Context context) {
            if (context != null) {
                return (NotificationManager) System.getSystemService(context, System.SYSTEM_SERVICE_NOTIFICATION_SERVICE);
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static void notify(final NotificationManager notificationManager, final android.app.Notification notification, final Integer id) {
            if (notificationManager != null) {
                if (notification != null) {
                    notificationManager.notify(id, notification);
                }
                else {
                    Log.w("Notification was NULL");
                }
            }
            else {
                Log.w("NotificationManager was NULL");
            }
        }

        public static void cancel(final NotificationManager notificationManager, final Integer id) {
            if (notificationManager != null) {
                notificationManager.cancel(id);
            }
            else {
                Log.w("NotificationManager was NULL");
            }
        }

        public static android.app.Notification buildNotification(final Context context, final int smallIcon, final java.lang.String title, final java.lang.String message, final PendingIntent pendingIntent) {
            if (context != null) {
                final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
                notificationBuilder.setSmallIcon(smallIcon);
                if (! TextUtils.isEmpty(title)) {
                    notificationBuilder.setContentTitle(title);
                }
                if (! TextUtils.isEmpty(message)) {
                    notificationBuilder.setContentText(message);
                }
                if (pendingIntent != null) {
                    notificationBuilder.setContentIntent(pendingIntent);
                }
                return notificationBuilder.build();
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static android.app.Notification buildNotification(final Context context, final int smallIcon, final java.lang.String title, final java.lang.String message) {
            return buildNotification(context, smallIcon, title, message, null);
        }

    }

    public static class Runtime {

        public static int getProcessors() {
            return java.lang.Runtime.getRuntime().availableProcessors();
        }

        public static long getFreeMemory() {
            return java.lang.Runtime.getRuntime().freeMemory();
        }

        public static long getMaxMemory() {
            return java.lang.Runtime.getRuntime().maxMemory();
        }

        public static long getTotalMemory() {
            return java.lang.Runtime.getRuntime().totalMemory();
        }

    }

    public static class String {

        public static java.lang.String capitalize(final java.lang.String string) {
            if (! TextUtils.isEmpty(string)) {
                return Character.toUpperCase(string.charAt(0)) + string.substring(1).toLowerCase();
            }
            else {
                Log.w("String was NULL");
            }
            return string;
        }

        public static Boolean isNumeric(final java.lang.String string) {
            if (! TextUtils.isEmpty(string)) {
                return TextUtils.isDigitsOnly(string);
            }
            else {
                Log.w("String was NULL");
            }
            return false;
        }

        public static Boolean contains(final java.lang.String[] strings, final java.lang.String string) {
            if (strings != null) {
                for (final java.lang.String s : strings) {
                    if (s.equals(string)) {
                        return true;
                    }
                }
            }
            else {
                Log.w("Strings was NULL");
            }
            return false;
        }

    }

    public static class Time {

        // UNIX Timestamp (length: 1-11)

        public static Long timestamp() {
            return (java.lang.System.currentTimeMillis() / DateUtils.SECOND_IN_MILLIS);
        }

        // Relative elapsed time

        public static java.lang.String relative(final Long time) {
            return DateUtils.getRelativeTimeSpanString(time, new Date().getTime(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
        }

        public static java.lang.String relative(final Long from, final Long to) {
            return DateUtils.getRelativeTimeSpanString(from, to, DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
        }

    }

    public static class WebView {

        private static final java.lang.String CHARSET = HTTP.UTF_8;
        private static final java.lang.String MIME_TYPE = "text/html";

        public static android.webkit.WebView fromUrl(final Context context, final java.lang.String url) {
            if (context != null) {
                final android.webkit.WebView webView = new android.webkit.WebView(context);
                webView.loadUrl(url);
                return webView;
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static android.webkit.WebView fromAsset(final Context context, final java.lang.String asset) {
            if (context != null) {
                final android.webkit.WebView webView = new android.webkit.WebView(context);
                webView.loadDataWithBaseURL(File.ASSETS_PREFIX, asset, MIME_TYPE, CHARSET, "");
                return webView;
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

        public static android.webkit.WebView fromHtml(final Context context, final java.lang.String source) {
            if (context != null) {
                final android.webkit.WebView webView = new android.webkit.WebView(context);
                webView.loadData(source, MIME_TYPE, CHARSET);
                return webView;
            }
            else {
                Log.w("Context was NULL");
            }
            return null;
        }

    }

    public static Bitmap rotateBitmap(final Context context, final int id, final float degrees) {
        if (context != null) {
            final Bitmap oldBitmap = BitmapFactory.decodeResource(context.getResources(), id);
            final Matrix matrix = new Matrix();
            matrix.postRotate(degrees);
            return Bitmap.createBitmap(oldBitmap, 0, 0, oldBitmap.getWidth(), oldBitmap.getHeight(), matrix, true);
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

    public static Drawable rotateDrawable(final Context context, final int id, final float degrees) {
        if (context != null) {
            return new BitmapDrawable(context.getResources(), rotateBitmap(context, id, degrees));
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

    public static void animate(final AnimationDrawable animationDrawable, final AnimationCallback callback, final Long time) {
        if (animationDrawable != null) {
            animationDrawable.start();

            if (time > 0) {
                animateListener(animationDrawable, callback, time);
            }
            else {
                Log.w("Time was negative");
            }
        }
        else {
            Log.w("AnimationDrawable was NULL");
        }
    }

    public static void animate(final AnimationDrawable animationDrawable, final AnimationCallback callback) {
        animate(animationDrawable, callback, 100L);
    }

    private static void animateListener(final AnimationDrawable animationDrawable, final AnimationCallback callback, final Long time) {
        if (animationDrawable != null) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    if (animationDrawable.getCurrent() != animationDrawable.getFrame(animationDrawable.getNumberOfFrames() - 1)) {
                        animateListener(animationDrawable, callback, time);
                    }
                    else if (callback != null) {
                        callback.animationCallback();
                    }
                    else {
                        Log.w("Callback was NULL");
                    }
                }

            }, time);
        }
        else {
            Log.w("AnimationDrawable was NULL");
        }
    }

    public static interface AnimationCallback {

        public void animationCallback();

    }

    public static class Hash {

        public static final java.lang.String MD5 = "MD5";
        public static final java.lang.String SHA = "SHA";

        private static final Integer MD5_LENGTH = 32;
        private static final Integer SHA_LENGTH = 40;

        private static java.lang.String hash(final java.lang.String algorithm, final java.lang.String string, final Integer length) {
            try {
                final MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
                messageDigest.reset();
                messageDigest.update(string.getBytes());

                final StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.setLength(0);

                final byte digest[] = messageDigest.digest();
                for (final byte d : digest) {
                    final int b = d & 255;
                    if (b < length) {
                        stringBuilder.append('0');
                    }
                    stringBuilder.append(Integer.toHexString(b));
                }
                return stringBuilder.toString();
            }
            catch (NoSuchAlgorithmException e) {
                Log.e("NoSuchAlgorithmException: " + e.getMessage());
            }
            return null;
        }

        public static java.lang.String md5(final java.lang.String string) {
            return hash(string, MD5, MD5_LENGTH);
        }

        public static java.lang.String sha(final java.lang.String string) {
            return hash(string, SHA, SHA_LENGTH);
        }

    }

    public static class Facebook {

        public static final java.lang.String GRAPH_ME_URL = "https://graph.facebook.com/me/feed";

        public static final java.lang.String PERMISSION_BASIC = "basic_info";
        public static final java.lang.String PERMISSION_FRIENDS = "read_friendlists";
        public static final java.lang.String PERMISSION_PUBLISH = "publish_actions";

        private static Facebook INSTANCE = null;

        private java.lang.String mAppId;
        private Integer mRequestCode;
        private AQuery mAQuery;
        private FacebookHandle mHandle;

        public static Facebook newInstance(final Context context, final java.lang.String appId, final Integer requestCode) {
            if (INSTANCE == null) {
                if (context != null) {
                    if (! TextUtils.isEmpty(appId)) {
                        INSTANCE = new Facebook(context, appId, requestCode);
                    }
                    else {
                        Skeleton.Log.w("AppId was NULL");
                    }
                }
                else {
                    Skeleton.Log.w("Context was NULL");
                }
            }
            return INSTANCE;
        }

        public static Facebook getInstance() {
            return INSTANCE;
        }

        private Facebook(final Context context, final java.lang.String appId, final Integer requestCode) {
            mAppId = appId;
            mRequestCode = requestCode;
            mAQuery = new AQuery(context);
        }

        public void auth(final Activity activity, final FacebookCallback callback, final java.lang.String permissions) {
            mHandle = new FacebookHandle(activity, mAppId, permissions) {

                @Override
                public boolean expired(AbstractAjaxCallback<?, ?> callback, AjaxStatus status) {
                    if (status.getCode() == HttpStatus.SC_UNAUTHORIZED) {
                        return true;
                    }
                    return super.expired(callback, status);
                }

            };
            mHandle.sso(mRequestCode);

            mAQuery.auth(mHandle)
                    .ajax(GRAPH_ME_URL, java.lang.String.class, new AjaxCallback<java.lang.String>() {

                        @Override
                        public void callback(final java.lang.String url, final java.lang.String object, final AjaxStatus status) {
                            super.callback(url, object, status);

                            if (TextUtils.isEmpty(status.getError()) && ! status.getMessage().equalsIgnoreCase("cancel")) {
                                final java.lang.String token = mHandle.getToken();
                                if (! TextUtils.isEmpty(token)) {
                                    Skeleton.Log.d("Token: " + token);
                                    callback.facebookCallback(token);
                                }
                                else {
                                    Skeleton.Log.w("Token is NULL");
                                    if (callback != null) {
                                        callback.facebookCallback(null);
                                    }
                                }
                            }
                            else {
                                Skeleton.Log.w("Message: " + status.getMessage());
                                Skeleton.Log.w("Error: " + status.getError());
                                if (callback != null) {
                                    callback.facebookCallback(null);
                                }
                            }
                        }

                    });
        }

        public void unauth() {
            if (mHandle != null) {
                if (! TextUtils.isEmpty(mHandle.getToken())) {
                    mHandle.unauth();
                }
                else {
                    Skeleton.Log.w("Token was NULL");
                }
            }
            else {
                Skeleton.Log.w("Handle was NULL");
            }
        }

        public java.lang.String getToken() {
            if (mHandle != null) {
                return mHandle.getToken();
            }
            else {
                Skeleton.Log.w("Handle was NULL");
            }
            return null;
        }

        public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
            if (requestCode == mRequestCode) {
                if (mHandle != null) {
                    mHandle.onActivityResult(requestCode, resultCode, data);
                }
                else {
                    Skeleton.Log.w("Handle was NULL");
                }
            }
        }

        public void onDestroy() {
            if (mAQuery != null) {
                mAQuery.dismiss();
            }
            else {
                Skeleton.Log.w("AQuery was NULL");
            }
        }

        public static interface FacebookCallback {

            public void facebookCallback(final java.lang.String token);

        }

    }

    public static class WebService {

        private Context mContext;
        private Integer mId;
        private java.lang.String mUrl;

        private Boolean check() {
            if (mContext == null) {
                Skeleton.Log.w("Context was NULL");
                return false;
            }
            if (mId < 0) {
                Skeleton.Log.w("Id was invalid");
                return false;
            }
            if (TextUtils.isEmpty(mUrl)) {
                Skeleton.Log.w("Url was NULL");
                return false;
            }
            if (! Skeleton.Network.isValidUrl(mUrl)) {
                Skeleton.Log.w("Url was invalid");
                return false;
            }
            return true;
        }

        public WebService(final Context context, final Integer id, final java.lang.String url) {
            mContext = context;
            mId = id;
            mUrl = url;
        }

        public void run(final WebServiceCallback callback) {
            if (! check()) {
                Skeleton.Log.d("check() failed");
                if (callback != null) {
                    callback.webServiceCallback(mId, new Response(null, null));
                }
            }
            else {
                final AjaxCallback<java.lang.String> ajaxCallback = new AjaxCallback<java.lang.String>() {

                    @Override
                    public void callback(final java.lang.String url, final java.lang.String content, final AjaxStatus ajaxStatus) {
                        if (callback != null) {
                            callback.webServiceCallback(mId, new Response(ajaxStatus, content));
                        }
                        else {
                            Skeleton.Log.w("Callback was NULL");
                        }
                    }

                }
                        .url(mUrl)
                        .type(java.lang.String.class)
                        .header("User-Agent", Skeleton.Network.makeUserAgent(mContext));

                new AQuery(mContext).ajax(ajaxCallback);
            }
        }

        public static class Response {

            public Integer code;
            public Boolean success;
            public java.lang.String content;
            public Long duration;

            public Response(final AjaxStatus ajaxStatus, final java.lang.String content) {
                if (ajaxStatus != null) {
                    this.code = ajaxStatus.getCode();
                    this.success = (this.code == HttpStatus.SC_OK);
                    this.duration = ajaxStatus.getDuration();
                    this.content = (this.success ? content : Skeleton.String.capitalize(ajaxStatus.getMessage()));
                }
                else {
                    Skeleton.Log.w("AjaxStatus was NULL");
                    this.code = -1;
                    this.success = false;
                    this.duration = 0L;
                    this.content = content;
                }
            }

        }

        public static interface WebServiceCallback {

            public void webServiceCallback(final Integer id, final Response response);

        }

    }

}
