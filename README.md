## Makefile

Requires ant.

make {update,check,debug,release,clean,install,run,uninstall,log,help}

## Skeleton

Usefull static classes.

### Skeleton.Log

Get StackTrace automatically - no need to put a tag.

- void v(String msg)
- void d(String msg)
- void i(String msg)
- void w(String msg)
- void e(String msg)
- void checkpoint(String name)

### Skeleton.Android

- int API_[...]
- void testFlight(Application application, String token)
- String account(Context context)
- String signature(Context context)
- Boolean tablet(Context context)
- String id(Context context)
- String deviceId(Context context)
- String uuid(Context context)
- String randomId()
- String codename()
- String manufacturer()
- String device()
- String release()
- Integer api()
- Boolean debug()
- String packageName(Context context)
- String name(Context context)
- String versionName(Context context)
- Integer versionCode(Context context)
- TelephonyManager sim(Context context)
- Boolean permission(Context context, String permission)
- Permissions
    - String [...]
- Boolean feature(Context context, String feature)
- Features
    - String [...]

### Skeleton.System

- String SYSTEM_PROPERTY_[...]
- String systemProperty(String property)
- String uname()
- String SYSTEM_SERVICE_[...]
- Object systemService(Context context, String service)

### Skeleton.Locale

- Locale locale()
- String language()
- String language2()
- String language3()
- String country()
- String country2()
- String country3()

### Skeleton.File

- String ASSETS_PREFIX
- File get(String path)
- InputStream openFile(File file)
- InputStream openRaw(Context context, int id)
- InputStream openAsset(Context context, String name)
- String readString(InputStream inputStream)
- String readString(File file)
- Bitmap readBitmap(File file)
- Boolean writeString(OutputStream outputStream, String content)
- Boolean writeString(File file, String content)
- Boolean writeBitmap(File file, Bitmap bitmap)
- Boolean serialize(File file, Object object)
- Object unserialize(File file)
- Boolean remove(File file)
- String internalDir(Context context)
- String externalDir(Context context)
- String internalCacheDir(Context context)
- String externalCacheDir(Context context)
- String downloadCache()
- Boolean sdCardAvailable()
- String sdCard()

### Skeleton.Keyboard

- void show(Activity activity)
- void hide(Activity activity)
- void keyboardCallback(EditText editText, KeyboardCallback callback)

### Skeleton.Audio

- Integer volume(Context context, int streamType)
- Integer volume(Context context)
- void play(String path)
- void play(Context context, Uri uri)
- void play(Context context, int rawId)

### Skeleton.Vibrator

- void vibrate(Context context, long duration)
- void vibrate(Context context, long[] durations, Boolean repeat)

### Skeleton.Network

- String defaultUserAgent()
- String userAgent(Context context)
- Boolean online(Context context)
- String macAddress(Context context)
- Boolean validUrl(String url)
- List<String> ipAddresses()

### Skeleton.Notification

- void toastShort(Context context, String text)
- void toastLong(Context context, String text)
- void croutonInfo(Activity activity, String text)
- void croutonConfirm(Activity activity, String text)
- void croutonAlert(Activity activity, String text)
- void onDestroy(Activity activity)
- NotificationManager notificationManager(Context context)
- Notification notification(Context context, int smallIcon, String title, String message, PendingIntent pendingIntent)
- Notification notification(Context context, int smallIcon, String title, String message)
- void notify(NotificationManager notificationManager, Notification notification, Integer id)
- void cancel(NotificationManager notificationManager, Integer id)

### Skeleton.Runtime

- Integer processors()
- Long freeMemory()
- Long maxMemory()
- Long totalMemory()

### Skeleton.String

- String capitalize(String string)
- Boolean numeric(String string)
- Boolean contains(String[] strings, String string)

### Skeleton.Time

- Long timestamp()
- String relative(Long time)
- String relative(Long from, Long to)

### Skeleton.WebView

- String CHARSET
- String MIME_TYPE
- WebView fromUrl(Context context, String url)
- WebView fromAsset(Context context, String asset)
- WebView fromHtml(Context context, String source)

### Skeleton.Hash

- String MD5
- String SHA
- String md5(String string)
- String sha(String string)

### Skeleton.Facebook

- String PERMISSION_[...]
- Facebook newInstance(Context context, String appId, Integer requestCode)
- Facebook getInstance()
- void auth(Activity activity, FacebookCallback callback, String permissions)
- void unauth()
- String getToken()
- void onActivityResult(int requestCode, int resultCode, Intent data)
- void onDestroy()

### Skeleton.WebService

- WebService(Context context, Integer id, String url)
- void run(WebServiceCallback callback)
- void run()
- class Response
    - Integer code
    - Boolean success
    - String content
    - Long duration

### Skeleton.ImageDownloader

- ImageDownloader(Context context, ImageView imageView, String url)
- ImageDownloader cache(Boolean file, Boolean memory)
- void run(ImageDownloaderCallback callback)
- void run()

### Skeleton.Location

- Location(Context context, LocationCallback locationCallback)
- Location(Context context)
- Location Location start(Boolean gps)
- void stop()
- Location location()

### Skeleton.Screen

- int DENSITY_[...]
- void wakeLock(Activity activity)
- Boolean isOn(Context context)
- Float density(Context context)
- Integer height(Context context)
- Integer width(Context context)
- Integer orientation(Context context)
- Integer pixelsFromDp(Context context, Float dp)

### Skeleton.Intent

- String BROADCAST_[...]
- Boolean canHandle(Context context, Intent intent)
- void web(Activity activity, String url)
- void market(Activity activity, String pkg)
- void market(Activity activity)
- void email(Activity activity, String[] to, String subject, String text)
- void image(Activity activity, Uri uri)
- void camera(Activity activity)
- void gallery(Activity activity)
- Bitmap onActivityResult(Context context, int requestCode, int resultCode, Intent intent)

### Skeleton.Activity

- void indeterminate(Object activity)
- void indeterminate(Object activity, Boolean on)
- void error(Context context, String message, DialogInterface.OnClickListener onClickListener)
- void error(Context context, String message)
- void showcase(Activity activity, int id, String title, String message, ShowcaseCallback callback)
- void showcase(Activity activity, int id, String title, String message)

### Skeleton.Graphics

- Bitmap decodeUri(Context context, Uri uri, Integer downsample)
- Bitmap decodeUri(Context context, Uri uri)
- Bitmap bitmapFromUri(Context context, Uri uri)
- Bitmap bitmapFromDrawable(Drawable drawable)
- Bitmap rotateBitmap(Bitmap bitmap, float degrees)
- Drawable drawableFromBitmap(Context context, Bitmap bitmap)
- Drawable indeterminateDrawable(Context context)

## SkeletonActivity

Not much.
Simple ListView with results from various Skeleton functions.

## SkeletonApplication

- CONTEXT
- DEBUG
- TAG
- LOCALE

## SkeletonReceiver

Not much.
Simple example of receiver.
