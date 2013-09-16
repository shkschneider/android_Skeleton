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
    - String ACCESS_CHECKIN_PROPERTIES
    - String ACCESS_COARSE_LOCATION
    - String ACCESS_FINE_LOCATION
    - String ACCESS_LOCATION_EXTRA_COMMANDS
    - String ACCESS_MOCK_LOCATION
    - String ACCESS_NETWORK_STATE
    - String ACCESS_SURFACE_FLINGER
    - String ACCESS_WIFI_STATE
    - String ACCOUNT_MANAGER
    - String ADD_VOICEMAIL
    - String AUTHENTICATE_ACCOUNTS
    - String BATTERY_STATS
    - String BIND_ACCESSIBILITY_SERVICE
    - String BIND_APPWIDGET
    - String BIND_DEVICE_ADMIN
    - String BIND_INPUT_METHOD
    - String BIND_WALLPAPER
    - String BLUETOOTH
    - String BLUETOOTH_ADMIN
    - String BRICK
    - String BROADCAST_PACKAGE_REMOVED
    - String BROADCAST_SMS
    - String BROADCAST_STICKY
    - String BROADCAST_WAP_PUSH
    - String CALL_PHONE
    - String CALL_PRIVILEGED
    - String CAMERA
    - String CHANGE_COMPONENT_ENABLED_STATE
    - String CHANGE_CONFIGURATION
    - String CHANGE_NETWORK_STATE
    - String CHANGE_WIFI_MULTICAST_STATE
    - String CHANGE_WIFI_STATE
    - String CLEAR_APP_CACHE
    - String CLEAR_APP_USER_DATA
    - String CONTROL_LOCATION_UPDATES
    - String DELETE_CACHE_FILES
    - String DELETE_PACKAGES
    - String DEVICE_POWER
    - String DIAGNOSTIC
    - String DISABLE_KEYGUARD
    - String DUMP
    - String EXPAND_STATUS_BAR
    - String FACTORY_TEST
    - String FLASHLIGHT
    - String FORCE_BACK
    - String GET_ACCOUNTS
    - String GET_PACKAGE_SIZE
    - String GET_TASKS
    - String GLOBAL_SEARCH
    - String HARDWARE_TEST
    - String INJECT_EVENTS
    - String INSTALL_LOCATION_PROVIDER
    - String INSTALL_PACKAGES
    - String INTERNAL_SYSTEM_WINDOW
    - String INTERNET
    - String KILL_BACKGROUND_PROCESSES
    - String MANAGE_ACCOUNTS
    - String MANAGE_APP_TOKENS
    - String MASTER_CLEAR
    - String MODIFY_AUDIO_SETTINGS
    - String MODIFY_PHONE_STATE
    - String MOUNT_FORMAT_FILESYSTEMS
    - String MOUNT_UNMOUNT_FILESYSTEMS

### Skeleton.System

- String SYSTEM_PROPERTY_JAVA_VM_NAME
- String SYSTEM_PROPERTY_JAVA_VM_VENDOR
- String SYSTEM_PROPERTY_JAVA_VM_VERSION
- String SYSTEM_PROPERTY_JAVA_HOME
- String SYSTEM_PROPERTY_USER_DIR
- String SYSTEM_PROPERTY_USER_REGION
- String SYSTEM_PROPERTY_JAVA_IO_TMPDIR
- String SYSTEM_PROPERTY_JAVA_RUNTIME_NAME
- String SYSTEM_PROPERTY_HTTP_AGENT
- String SYSTEM_PROPERTY_FILE_SEPARATOR
- String SYSTEM_PROPERTY_FILE_ENCODING
- String SYSTEM_PROPERTY_LINE_SEPARATOR
- String SYSTEM_PROPERTY_OS_ARCH
- String SYSTEM_PROPERTY_OS_NAME
- String SYSTEM_PROPERTY_OS_VERSION
- String SYSTEM_PROPERTY_PATH_SEPARATOR
- String getSystemProperty(String property)
- String uname()
- String SYSTEM_SERVICE_WINDOW_SERVICE
- String SYSTEM_SERVICE_LAYOUT_INFLATER_SERVICE
- String SYSTEM_SERVICE_ACTIVITY_SERVICE
- String SYSTEM_SERVICE_POWER_SERVICE
- String SYSTEM_SERVICE_ALARM_SERVICE
- String SYSTEM_SERVICE_NOTIFICATION_SERVICE
- String SYSTEM_SERVICE_KEYGUARD_SERVICE
- String SYSTEM_SERVICE_LOCATION_SERVICE
- String SYSTEM_SERVICE_SEARCH_SERVICE
- String SYSTEM_SERVICE_SENSOR
- String SYSTEM_SERVICE_STORAGE
- String SYSTEM_SERVICE_VIBRATOR
- String SYSTEM_SERVICE_CONNECTIVITY
- String SYSTEM_SERVICE_WIFI
- String SYSTEM_SERVICE_AUDIO
- String SYSTEM_SERVICE_MEDIA_ROUTER
- String SYSTEM_SERVICE_TELEPHONY
- String SYSTEM_SERVICE_INPUT_METHOD
- String SYSTEM_SERVICE_UI_MODE
- String SYSTEM_SERVICE_DOWNLOAD
- Object getSystemService(Context context, String service)

### Skeleton.File

- String ASSETS_PREFIX = "file:///android_asset/"
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

- String CHARSET = HTTP.UTF_8
- String MIME_TYPE = "text/html"
- WebView fromUrl(Context context, String url)
- WebView fromAsset(Context context, String asset)
- WebView fromHtml(Context context, String source)

...

### Skeleton.Hash

- String MD5 = "MD5"
- String SHA = "SHA"
- String md5(String string)
- String sha(String string)

### Skeleton.Facebook

- String PERMISSION_BASIC_INFO
- String PERMISSION_READ_STREAM
- String PERMISSION_READ_FRIENDLISTS
- String PERMISSION_MANAGE_FRIENDLISTS
- String PERMISSION_MANAGE_NOTIFICATIONS
- String PERMISSION_PUBLISH_STREAM
- String PERMISSION_PUBLISH_CHECKINS
- String PERMISSION_OFFLINE_ACCESS
- String PERMISSION_USER_PHOTOS
- String PERMISSION_USER_LIKES
- String PERMISSION_USER_GROUPS
- String PERMISSION_FRIENDS_PHOTOS
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

- int DENSITY_LDPI
- int DENSITY_MDPI
- int DENSITY_HDPI
- int DENSITY_XHDPI
- int DENSITY_XXHDPI
- int DENSITY_XXXHDPI
- int DENSITY_TV
- void wakeLock(Activity activity)
- Boolean isOn(Context context)
- float density(final Context context)
- int height(final Context context)
- int width(final Context context)
- Integer orientation(Context context)
- int pixelsFromDp(final Context context, final Float dp)

### Skeleton.Intent

- String BROADCAST_TIME_TICK
- String BROADCAST_TIME_CHANGED
- String BROADCAST_TIMEZONE_CHANGED
- String BROADCAST_BOOT_COMPLETED
- String BROADCAST_PACKAGE_ADDED
- String BROADCAST_PACKAGE_CHANGED
- String BROADCAST_PACKAGE_REMOVED
- String BROADCAST_PACKAGE_RESTARTED
- String BROADCAST_PACKAGE_DATA_CLEARED
- String BROADCAST_UID_REMOVED
- String BROADCAST_BATTERY_CHANGED
- String BROADCAST_POWER_CONNECTED
- String BROADCAST_POWER_DISCONNECTED
- String BROADCAST_SHUTDOWN
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
