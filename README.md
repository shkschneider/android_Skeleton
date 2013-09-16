## Skeleton

Abstract / static classes.

### Skeleton.Log

Get StackTrace automatically - no need to put a tag.

- void v(String msg)
- void d(String msg)
- void i(String msg)
- void w(String msg)
- void e(String msg)

### Skeleton.Android

- String getAccount(Context context)
- String getSignature(Context context)
- Boolean isTablet(Context context)
- String getId(Context context)
- String getDeviceId(Context context)
- String getUUID(Context context)
- String getRandomId()
- String getDevice()
- String getRelease()
- int getApi()
- Boolean isDebug()
- String getPackage(Context context)
- String getName(Context context)
- String getVersionName(Context context)
- Integer getVersionCode(Context context)
- TelephonyManager getSim(Context context)
- Boolean hasPermission(Context context, String permission)
- Permissions
    - String [...]
- Boolean hasFeature(final Context context, final java.lang.String feature)
- Features
    - String [...]

### Skeleton.System

- String SYSTEM_PROPERTY_[...]
- String getSystemProperty(String property)
- String uname()
- String SYSTEM_SERVICE_[...]
- Object getSystemService(Context context, String service)

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
- String getInternalDir(Context context, String name)
- String getExternalDir(Context context, String name)
- String getInternalCacheDir(Context context)
- String getExternalCacheDir(Context context)
- String getDownloadCache()
- Boolean hasSdCardAvailable()
- String getSdCard()

...

### Skeleton.Keyboard

- void showKeyboard(Activity activity)
- void hideKeyboard(Activity activity)
- void keyboardCallback(EditText editText, KeyboardCallback callback)

### Skeleton.Vibrator

- void vibrate(Context context, long duration)
- void vibrate(Context context, long[] durations, Boolean repeat)

### Skeleton.Network

- String getDefaultUserAgent()
- String makeUserAgent(Context context)
- Boolean isConnectedToInternet(Context context)
- String getMacAddress(Context context)
- Boolean isValidUrl(String url)
- List<String> ipAddresses()

### Skeleton.Notification

- void showToastShort(Context context, String text)
- void showToastLong(Context context, String text)
- NotificationManager getNotificationManager(Context context)
- void notify(NotificationManager notificationManager, Notification notification, Integer id)
- void cancel(NotificationManager notificationManager, Integer id)
- Notification buildNotification(Context context, int smallIcon, String title, String message, PendingIntent pendingIntent)
- Notification buildNotification(Context context, int smallIcon, String title, String message)

### Skeleton.Runtime

- int getProcessors()
- long getFreeMemory()
- long getMaxMemory()
- long getTotalMemory()

### Skeleton.String

- String capitalize(String string)
- Boolean isNumeric(String string)
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

...

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
- Location Location start(Boolean gps)
- void stop()
- Location getLocation()

### Skeleton.Screen

- int DENSITY_[...]
- void wakeLock(Activity activity)
- Boolean isOn(Context context)
- float density(final Context context)
- int height(final Context context)
- int width(final Context context)
- Integer orientation(Context context)
- int pixelsFromDp(final Context context, final Float dp)

### Skeleton.Intent

- String BROADCAST_[...]
- void web(Activity activity, String url)
- void market(Activity activity, String pkg)
- void market(Activity activity)
- void email(Activity activity, String[] to, String subject, String text)
- void gallery(Activity activity, Uri uri)

## SkeletonActivity

Not much.

## SkeletonApplication

- DEBUG
- TAG
- LOCALE

## SkeletonReceiver

Not much.
Simple example of receiver.

## Makefile

make {update,check,debug,release,clean,distclean,install,uninstall}
