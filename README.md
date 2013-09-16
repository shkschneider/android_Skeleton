# android_Skeleton

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

### Skeleton.System

- String SYSTEM_PROPERTY_JAVA_VM_NAME = "java.vm.name";
- String SYSTEM_PROPERTY_JAVA_VM_VENDOR = "java.vm.vendor";
- String SYSTEM_PROPERTY_JAVA_VM_VERSION = "java.vm.version";
- String SYSTEM_PROPERTY_JAVA_HOME = "java.home";
- String SYSTEM_PROPERTY_USER_DIR = "user.dir";
- String SYSTEM_PROPERTY_USER_REGION = "user.region";
- String SYSTEM_PROPERTY_JAVA_IO_TMPDIR = "java.io.tmpdir";
- String SYSTEM_PROPERTY_JAVA_RUNTIME_NAME = "java.runtime.name";
- String SYSTEM_PROPERTY_HTTP_AGENT = "http.agent";
- String SYSTEM_PROPERTY_FILE_SEPARATOR = "file.separator";
- String SYSTEM_PROPERTY_FILE_ENCODING = "file.encoding";
- String SYSTEM_PROPERTY_LINE_SEPARATOR = "line.separator";
- String SYSTEM_PROPERTY_OS_ARCH = "os.arch";
- String SYSTEM_PROPERTY_OS_NAME = "os.name";
- String SYSTEM_PROPERTY_OS_VERSION = "os.version";
- String SYSTEM_PROPERTY_PATH_SEPARATOR = "path.separator";

- String getSystemProperty(String property)
- String uname()

- String SYSTEM_SERVICE_WINDOW_SERVICE = Context.WINDOW_SERVICE;
- String SYSTEM_SERVICE_LAYOUT_INFLATER_SERVICE = Context.LAYOUT_INFLATER_SERVICE;
- String SYSTEM_SERVICE_ACTIVITY_SERVICE = Context.ACTIVITY_SERVICE;
- String SYSTEM_SERVICE_POWER_SERVICE = Context.POWER_SERVICE;
- String SYSTEM_SERVICE_ALARM_SERVICE = Context.ALARM_SERVICE;
- String SYSTEM_SERVICE_NOTIFICATION_SERVICE = Context.NOTIFICATION_SERVICE;
- String SYSTEM_SERVICE_KEYGUARD_SERVICE = Context.KEYGUARD_SERVICE;
- String SYSTEM_SERVICE_LOCATION_SERVICE = Context.LOCATION_SERVICE;
- String SYSTEM_SERVICE_SEARCH_SERVICE = Context.SEARCH_SERVICE;
- String SYSTEM_SERVICE_VIBRATOR_SERVICE = Context.VIBRATOR_SERVICE;
- String SYSTEM_SERVICE_CONNECTIVITY_SERVICE = Context.CONNECTIVITY_SERVICE;
- String SYSTEM_SERVICE_WIFI_SERVICE = Context.WIFI_SERVICE;
- String SYSTEM_SERVICE_INPUT_METHOD_SERVICE = Context.INPUT_METHOD_SERVICE;
- String SYSTEM_SERVICE_UI_MODE_SERVICE = Context.UI_MODE_SERVICE;
- String SYSTEM_SERVICE_DOWNLOAD_SERVICE = Context.DOWNLOAD_SERVICE;

- Object getSystemService(Context context, String service)

### Skeleton.File

- String ASSETS_PREFIX = "file:///android_asset/";

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

- String CHARSET = HTTP.UTF_8;
- String MIME_TYPE = "text/html";

- WebView fromUrl(Context context, String url)
- WebView fromAsset(Context context, String asset)
- WebView fromHtml(Context context, String source)

...

### Skeleton.Hash

- String MD5 = "MD5";
- String SHA = "SHA";

- String md5(String string)
- String sha(String string)

### Skeleton.Facebook

- String PERMISSION_BASIC = "basic_info";
- String PERMISSION_FRIENDS = "read_friendlists";
- String PERMISSION_PUBLISH = "publish_actions";

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
