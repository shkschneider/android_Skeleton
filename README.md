Skeleton
========

> Android library with usefull classes to be used as a "Skeleton" for an application.

Current version: **4.0.0**

Release date: 02-2015

Specifications
--------------

**API**

- minSdkVersion [**14**](https://developer.android.com/reference/android/os/Build.VERSION_CODES.html#ICE_CREAM_SANDWICH)
- targetSdkVersion [21](https://developer.android.com/reference/android/os/Build.VERSION_CODES.html#LOLLIPOP)

**Libraries**

- [com.google.android.gms:play-services](https://developer.android.com/google/play-services/index.html)
- [com.android.support:support-v4](https://developer.android.com/tools/support-library/features.html#v4)
- [com.android.support:appcompat-v7](https://developer.android.com/tools/support-library/features.html#v7-appcompat)
- [com.android.support:cardview-v7](https://developer.android.com/tools/support-library/features.html#v7-cardview)
- [com.android.support:palette-v7](https://developer.android.com/tools/support-library/features.html#v7-palette)
- [com.squareup.phrase:phrase](https://github.com/square/phrase)
- [com.koushikdutta.ion:ion](https://github.com/koush/ion)
- [support-v4-preferencefragment](https://github.com/kolavar/android-support-v4-preferencefragment)

**Uses**

- Gradle 1.0.0
- [Android Studio 1.0.0](https://developer.android.com/sdk/index.html)
- [android.support.v7.app.ActionBarActivity](https://developer.android.com/reference/android/support/v7/app/ActionBarActivity.html)
- [android.support.v7.widget.Toolbar](https://developer.android.com/reference/android/support/v7/widget/Toolbar.html)
- [android.support.v4.app.Fragment](https://developer.android.com/reference/android/support/v4/app/Fragment.html)
- [Material Design](http://www.google.com/design/spec/material-design/introduction.html)
- Makefile

Demo
----

Compiling the 'app' module will produce an APK:

- package: me.shkschneider.app
- permissions:
  - `ACCESS_NETWORK_STATE`
  - `INTERNET`
  - `READ_EXTERNAL_STORAGE`
  - `WRITE_EXTERNAL_STORAGE`
- uses-feature: touchscreen
- supports-screens:
  - small
  - normal
  - large
  - anyDensity

This application is a demo application that shows an `Activity` using a `Toolbar` presenting a `NavigationDrawer` with multiple `Fragment`s:
refreshing layout, webservice calls, refreshable content, fading `ActionBar`, `FloatingActionMenu`/`FloatingActionButton`, `SnackBar`s etc.

Usage
-----

You can clone this repository and modify *app* to get started with a new project, or just add *skeleton* as a library project.

**Setup**

Your Application class MUST extend `SkeletonApplication` or set a global `Context` to it:

    SkeletonApplication.CONTEXT = getApplicationContext();

**Start coding**

- Use or extend `SkeletonApplication` for `Application` class.
- Use or extend `SkeletonActivity` for `(ActionBar)Activity` classes.
- Use or extend `SkeletonFragmentActivity` for `FragmentActivity` classes.
- Use or extend `SkeletonNavigationDrawerActivity` for an `Activity` with a `NavigationDrawer`.
- Use or extend `SkeletonPreferenceFragment` for `PreferenceFragment` classes.
- Use or extend `SkeletonOvrlayActivity` for an `Activity` with a fading `Toolbar`.

**Styling**

Change the main colors by creating/modifying *res/values/colors.xml*:

    <color name="accentColor">@color/green</color>
    <color name="primaryColor">@color/green500</color>
    <color name="primaryColorDark">@color/green700</color>

The default application's theme is `AppTheme` from skeleton's `SkeletonTheme` itself from `Theme.AppCompat.Light.DarkActionBar` from AppCompat library (with customizations for API-21+).

**Building**

Use your IDE or Gradle from CLI or the provided Makefile:

    $ make <debug|release|lint|clean|distclean>


Packages and classes
--------------------

**me.shkschneider.skeleton**

- .SkeletonActivity
  - void onViewCreated()
  - boolean alive()
  - void toolbar(boolean)
  - void home(boolean)
  - void home(Drawable)
  - void title(String)
  - void subtitle(String)
  - void logo(Drawable)
  - boolean refreshable()
  - boolean refreshable(boolean, OnRefreshListener)
  - boolean loading()
  - void loading(int)
  - void loading(boolean)
  - void searchable(String, SearchCallback)
  - void onHomeAsUpPressed()
  - void onBackPressed()
- .SkeletonApplication
  - static Context CONTEXT
- .SkeletonFragmentActivity
  - void setContentFragment(Fragment)
  - void onResumeFragments()
- .SkeletonFragment
  - boolean alive()
  - String title()
  - void title(String)
  - SkeletonActivity skeletonActivity()
- .SkeletonNavigationDrawerActivity
  - abstract ArrayAdapter getAdapter()
  - abstract SkeletonFragment getFragment(int)
  - void navigationDrawer(int)
  - int navigationDrawer()
  - boolean navigationDrawerOpenedOrOpening()
  - void openNavigationDrawer()
  - void onNavigationDrawerOpened()
  - void closeNavigationDrawer()
  - void onNavigationDrawerClosed()
- .SkeletonOverlayActivity
  - void overlay(Drawable)
  - void overlay(MyScrollView, View)
- .SkeletonPreferenceFragment
  - SkeletonActivity skeletonActivity()

**me.shkschneider.skeleton.data**

- .CharsetHelper
  - static String ASCII
  - static String UTF8
  - static String UTF16
  - static String ISO_8859_1
- .DiskCache
  - static class Internal
  - static class External
  - boolean put(String, Serializable)
  - Serializable get(String)
  - void clear()
  - int size()
- .ExternalDataHelper
  - static File download()
  - static File cache()
  - static File dir()
  - static File file(String)
  - static boolean delete(String)
- .FileHelper
  - static String PREFIX_ASSETS
  - static String PREFIX_RES
  - static String join(String, String)
  - static File get(String)
  - static InputStream openFile(File)
  - static InputStream openRaw(int)
  - static InputStream openAsset(String)
  - static String readString(InputStream)
  - static String readString(File)
  - static Bitmap readBitmap(File)
  - static boolean writeString(OutputStream, String)
  - static boolean writeString(File, String)
  - static boolean writeBitmap(File, Bitmap)
  - static List<String> list(File)
  - static boolean remove(File)
- .GsonParser
  - static JsonObject parse(String)
  - static JsonObject parse(InputStream)
  - static List<String> keys(JsonObject)
  - static List<JsonElement> values(JsonObject)
  - static boolean has(JsonObject, String)
  - static JsonObject object(JsonObject, String)
  - static JsonArray array(JsonObject, String)
  - static String string(JsonObject, String)
- .InternalDataHelper
  - static FileInputStream openInput(String)
  - static FileOutputStream openOuput(String)
  - static File root()
  - static File cache()
  - static File cache()
  - static File file(String)
  - static boolean delete(String)
- .MemoryCache
  - static int DEFAULT_SIZE
  - MemoryCache<k, V>(int)
  - boolean put(K, V)
  - V get(K)
  - void clear()
  - int size()
  - static class Bitmap
    - Bitmap(int)
    - void clear()
- .MimeTypeHelper
  - static String FILE
  - static String TEXT
  - static String TEXT_*
  - static String APPLICATION
  - static String APPLICATION_*
  - static String IMAGE
  - static String IMAGE_*
  - static String AUDIO
  - static String VIDEO
- .SerializeHelper
  - static boolean write(Object, File)
  - static Object read(File)

**me.shkschneider.skeleton.helper**

- .ActivityHelper
  - static View contentView(Activity)
  - static void toast(String)
  - static boolean portrait()
  - static boolean landscape()
  - static String title()
  - static ActivityInfo activityInfo(Activity)
- .ActivityTransitionHelper
  - static void tag(View, String)
  - static void transition(Activity, Intent, Pair<View, String>...)
- .AndroidHelper
  - static int API_1
  - static int API_2
  - static int API_3
  - static int API_4
  - static int API_5
  - static int API_6
  - static int API_7
  - static int API_8
  - static int API_9
  - static int API_10
  - static int API_11
  - static int API_12
  - static int API_13
  - static int API_14
  - static int API_15
  - static int API_16
  - static int API_17
  - static int API_18
  - static int API_19
  - static int API_20
  - static int API_21
  - static String PLATFORM
  - static int ANDROID_1
  - static int ANDROID_2
  - static int ANDROID_3
  - static int ANDROID_4
  - static int ANDROID_5
  - static int api()
  - static String versionName()
  - static int versionCode()
- .ApplicationHelper
  - static Context context()
  - static boolean debug()
  - static Resources resources()
  - static AssetManager assets()
  - static String[] files()
  - static int wipe()
  - static String packageName()
  - static PackageManager packageManager()
  - static String name()
  - static String versionName()
  - static int versionCode()
  - static Bitmap icon()
  - static List<String> permissions()
  - static String signature()
  - static boolean fromMarket()
- .AssetsHelper
  - static AssetManager assetManager()
  - static List<String> list()
  - static InputStream open(String)
  - static boolean dump()
- .BundleHelper
  - static Bundle pack(String, Serializable)
  - static Serializable unpack(Bundle, String)
- .DateTimeHelper
  - static int day()
  - static int month()
  - static int year()
  - static String format(Calendar, String)
  - static long timestamp()
  - static long milliTimestamp()
  - static int gmtOffset()
  - static String relative(long)
  - static String relative(long, long)
- .DeviceHelper
  - static boolean tablet()
  - static Strign codename()
  - static String manufacturer()
  - static String model()
  - static Strign fingerprint()
- .FeaturesHelper
  - static String FEATURE_*
  - static boolean feature(String)
- .GooglePlayServicesHelper
  - static String GOOGLE_ACCOUNT_TYPE
  - static int status()
  - static boolean check()
  - static Dialog dialog(Activity)
  - static List<String> accounts()
  - static String account()
- .IdHelper
  - static String androidId()
  - static String uuid()
  - static String randomUuid()
- .IntentHelper
  - static int MAIN_FLAGS
  - static int HOME_FLAGS
  - static String BROADCAST_*
  - static int REQUEST_CODE_CAMERA
  - static int REQUEST_CODE_GALLERY
  - static Intent home()
  - static Intent view(Uri)
  - static Intent web(String)
  - static Intent share(String, String)
  - static Intent directions(LatLng, LatLng)
  - static Intent applicationSettings()
  - static Intent systemSettings()
  - static Intent text(Uri)
  - static Intent audio(Uri)
  - static Intent video(Uri)
  - static Intent picture(Uri)
  - static Intent gallery()
  - static Intent camera(File)
  - static Intent file()
  - static Intent dial(String)
  - static Intent call(String)
  - static Intent contact()
  - static class GooglePlay
    - static Intent application(String)
    - static Intent publisher(String)
    - static Intent search(String)
  - static boolean canHandle(Intent)
  - static Bitmap onActivityResult(int, int, Intent)
- .KeyboardHelper
  - static int ENTER
  - static boolean show(Window, EditText)
  - static boolean show(Window)
  - static boolean hide(Window)
  - static boolean hide(IBinder)
  - static boolean keyboardCallback(EditText, Callback, boolean)
  - static boolean keyboardCallback(EditText, Callback)
  - static interface Callback
    - void keyboardCallback(int)
- .LocaleHelper
  - static Locale locale()
  - static String language()
  - static String language2()
  - static String language3()
  - static String country()
  - static String country2()
  - static String country3()
- .LogHelper
  - static int VERBOSE
  - static int DEBUG
  - static int INFO
  - static int WARN
  - static int ERROR
  - static int WTF
  - static void debug(String)
  - static void verbose(String)
  - static void info(String)
  - static void warning(String)
  - static void error(String)
  - static void wtf(Throwable)
- .PermissionsHelper
  - static String *
  - static boolean permission(String)
- .RandomHelper
  - static boolean binary()
  - static int inclusive(int, int)
  - static int exclusive(int, int)
- .RunnableHelper
  - static void delay(Runnable, int, TimeUnit)
  - static void runOnMainLooper(Runnable)
  - static void runOnUiThread(Activity, Runnable)
- .ScreenHelper
  - static int DENSITY_*
  - static float BRIGHTNESS_*
  - static int ROTATION_*
  - static boolean wakeLock(Activity)
  - static boolean on()
  - static float brightness(Window)
  - static boolean brightness(Window, float)
  - static float density()
  - static int dpi()
  - static int height()
  - static int width()
  - static int rotation()
  - static int pixelsFromDp(float)
- .SharedPreferencesHelper
  - static boolean putPublic(String, String)
  - static String getPublic(String, String)
  - static boolean putPrivate(String, String, String)
  - static String getPrivate(String, String, String)
- .SpannableStringHelper
  - SpannableStringHelper(String)
  - SpannableStringHelper strikethrough(int, int)
  - SpannableStringHelper underline(int, int)
  - SpannableStringHelper boldify(int, int)
  - SpannableStringHelper italize(int, int)
  - SpannableStringHelper colorize(int, int, int)
  - SpannableStringHelper mark(int, int, int)
  - SpannableStringHelper proportionate(int, int, float)
  - Spannable apply()
- .SystemHelper
  - static String uname()
  - static long sinceBoot()
  - static long sinceCurrentThreadBirth()
  - static void safeSleep(long)
- .SystemProperties
  - static String SYSTEM_PROPERTY_*
  - static String javaVmName()
  - static String javaVmVendor()
  - static String javaVmVersion()
  - static String javaHome()
  - static String userDir()
  - static String userRegion()
  - static String javaIoTmpdir()
  - static String javaRuntimeName()
  - static String httpAgent()
  - static String fileSeparator()
  - static String fileEncoding()
  - static String lineSeparator()
  - static String osArch()
  - static String osName()
  - static String osVersion()
  - static String pathSeparator()
- .SystemServices
  - static AccessibilityManager accessibilityManager()
  - static AccountManager accountManager()
  - static ActivityManager activityManager()
  - static AlarmManager alarmManager()
  - static AudioManager audioManager()
  - static ClipboardManager clipboardManager()
  - static ConnectivityManager connectivityManager()
  - static DevicePolicyManager devicePolicyManager()
  - static DownloadManager downloadManager()
  - static DropBoxManager dropBoxManager()
  - static InputMethodManager inputMethodManager()
  - static InputManager inputManager()
  - static KeyguardManager keyguardManager()
  - static LayoutInflater layoutInflater()
  - static LocationManager locationManager()
  - static MediaRouter mediaRouter()
  - static NfcManager nfcManager()
  - static NotificationManager notificationManager()
  - static NsdManager nsdManager()
  - static PowerManager powerManager()
  - static SearchManager searchManager()
  - static SensorManager sensorManager()
  - static StorageManager storageManager()
  - static TelephonyManager telephonyManager()
  - static TextServicesManager textServicesManager()
  - static UiModeManager uiModeManager()
  - static UsbManager usbManager()
  - static Vibrator vibrator()
  - static WifiP2pManager wifiP2pManager()
  - static WifiManager wifiManager()
  - static WindowManager windowManager()
- .SystemUiHelper
  - static int LEVEL_LOW_PROFILE
  - static int LEVEL_HIDE_STATUS_BAR
  - static int LEVEL_LEAN_BACK
  - static int LEVEL_IMMERSIVE
  - static int FLAG_NONE
  - static int FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES
  - static int FLAG_IMMERSIVE_STICKY
  - SystemUiHelper(ActionBarActivity, int, int)
  - SystemUiHelper(ActionBarActivity, int, int, OnVisibilityChangeListener)
  - boolean isShowing()
  - void show()
  - void hide()
  - void delayHide(long)
  - void toggle()
  - interface OnVisibilityChangeListener
    - void onVisibilityChange(boolean)
- .VibratorHelper
  - static boolean hasVibrator()
  - static boolean vibrate(long[], boolean)
  - static boolean vibrate(long[])
  - static boolean vibrate(long)

**me.shkschneider.skeleton.java**

- .ArrayHelper
  - static List<T> list(T[])
  - static boolean contains(T[], T)
  - static T first(T[])
  - static T last(T[])
- .ClassHelper
  - static String canonicalName(Class)
  - static String packageName(Class)
  - static String simpleName(Class)
- .ListHelper
  - static boolean contains(List<T>, T)
  - static T first(List<T>)
  - static T last(List<T>)
- .MapHelper
  - static Map<K, V> item(K, V)
  - static List<K> keys(Map<K, V>)
  - static List<V> values(Map<K, V>)
  - static boolean containsKey(Map<K, V>, K)
  - static boolean containsValue(Map<K, V>, V)
- .StringHelper
  - static String ALPHA
  - static String NUMERIC
  - static String HEX
  - static String ALPHA_NUMERIC
  - static boolean nummOrEmpty(String)
  - static String camelCase(String[])
  - static String capitalize(String)
  - static String upper(String)
  - static String lower(String)
  - static boolean alpha(String)
  - static boolean numeric(String)
  - static boolean alphanumeric(String)
  - static boolean url(String)
  - static boolean email(String)
  - static boolean phone(String)
- .WarningsHelper
  - static String ALL
  - static String CAST
  - static String DEPRECATION
  - static String FINALLY
  - static String NULL
  - static String UNCHECKED
  - static String UNUSED

**me.shkschneider.skeleton.network**

- .NetworkHelper
  - static String userAgent()
  - static boolean connectedOrConnecting()
  - static boolean wifi()
  - static String macAddress()
  - static boolean validUrl(String)
  - static List<String> ipAddresses()
  - static String ipAddress()
- .UrlHelper
  - static uri.Builder builder()
  - static Uri.Builder builder(String)
  - static String encode(String)
  - static String decode(String)
  - static uri uri(Uri.Builder)
  - static String url(Uri)
- .WebService
  - WebService()
  - void getInputStream(Stirng, Callback)
  - void getString(String, Callback)
  - void getJsonObject(String, Callback)
  - void getJsonArray(String, Callback)
  - void getBitmap(String, Callback)
  - void clearCache()
  - void cancelAll()
  - static class WebServiceException
    - WebServiceException(int, String)
    - int getCode()
    - String getMessage()
  - static interface Callback
    - void webServiceCallback(WebServiceException, Object)

**me.shkschneider.skeleton.security**

- .Base64Helper
  - static int FLAGS
  - static String encrypt(String)
  - static String encrypt(byte[])
  - static String decrypt(String)
  - static String decrypt(byte[])
- .ComplexCrypt
  - ComplexCrypt(byte[])
  - String secret()
  - String key()
  - String algorithm()
  - byte[] encrypt(byte[])
  - byte[] decrypt(byte[])
- .HashHelper
  - static String MD5
  - static String SHA
  - static String md5(String)
  - static String sha(String)
- .SimpleCrypt
  - SimpleCrypt(String)
  - String algorithm()
  - String key()
  - String encrypt(String)
  - String decrypt(String)

**me.shkschneider.ui**

- .transforms.ABaseTransformer
- .transforms.AccordionTransformer
- .transforms.BackgroundToForegroundTransformer
- .transforms.CubeInTransformer
- .transforms.CubeOutTransformer
- .transforms.DefaultTransformer
- .transforms.DepthPageTransformer
- .transforms.FlipHorizontalTransformer
- .transforms.FlipVerticalTransformer
- .transforms.ForegroundToBackgroundTransformer
- .transforms.RotateDownTransformer
- .transforms.RotateUpTransformer
- .transforms.StackTransformer
- .transforms.TabletTransformer
- .transforms.ZoomInTransformer
- .transforms.ZoomOutSlideTransformer
- .transforms.ZoomOutTranformer
- .viewpager.ActionBarViewPagerIconIndicator
- .viewpager.ActionBarViewPagerLineIndicator
- .viewpager.ActionBarViewPagerTextIndicator
- .viewpager.ViewPagerCircleIndicator
  - void setViewPager(ViewPager)
  - void setIndicator(int)
  - void setOnPageChangeListener(OnPageChangeListener)
- .viewpager.ViewPagerIconIndicator
- .viewpager.ViewPagerIndicatorAdapter
  - void setOnPageChangeListener(OnPageChangeListener)
  - void setViewpager(ViewPager)
- .viewpager.ViewPagerIndicator
  - CharSequence getPageTitle(int)
  - Drawable getPageIcon(int)
  - int getCount()
  - Fragment getItem(int)
- .viewpager.ViewPagerTextIndicator
- .BitmapHelper
  - static Bitmap fromDrawable(Drawable)
  - static Bitmap fromView(View)
  - static Bitmap fromUri(Uri)
  - static Bitmap decodeUri(Uri)
  - static Bitmap rotate(Bitmap, float)
  - static int vibrantColor(Bitmap)
  - static int mutedColor(Bitmap)
- .DrawableHelper
  - static Drawable fromBitmap(Bitmap)
  - static Drawable circular(Bitmap)
- .FloatingActionButton
  - static int SIZE_NORMAL
  - static int SIZE_MINI
  - void setColors(int, int)
  - void setIcon(int)
  - void setSize(int)
- .FloatingActionMenu
  - void setButtonColor(int)
  - void setSize(int)
  - void addButton(FloatingActionButton)
  - void removeButton(FloatingActionButton)
  - void clear()
  - void setOnFloatingActionMenuListener(OnFloatingActionmenuListener)
- .MyScrollView
  - void setOnScrollViewListener(OnScrollViewListener)
- .MySwipeRefreshLayout
  - void setEnabled(boolean)
  - boolean isEnabled()
  - boolean isRefreshing()
  - void setRefreshing(boolean)
  - static void absListViewCompat(MySwipeRefreshLayout, AbsListView)
  - static void scrollViewCompat(MySwipeRefreshLayout, ScrollView)
- .SnackBar
  - static int DURATION_SHORT
  - static int DURATION_LONG
  - static int DURATION_INFINITE
  - static SnackBar with(Activity, String)
  - SnackBar duration(int)
  - SnackBar singleLine()
  - SnackBar multiLine()
  - SnackBar action(String, OnClickListener)
  - SnackBar attachToView(View)
  - void show()
  - boolean showing()
  - void dismiss()
- .TextFitTextView
  - void sizeToFit()
- .UiHelper
  - static float CARD_ELEVATION_0
  - static float CARD_ELEVATION_1
  - static float CARD_ELEVATION_2
  - static float CARD_ELEVATION_3
  - static float CARD_ELEVATION_4
  - static View inflate(ViewGroup, int)
  - static View inflate(int)
- .ViewHelper
  - static List<View> children(View)
- .WebViewHelper
  - static String META_VIEWPORT
  - static String CHARSET
  - static String MIME_TYPE
  - static WebView getInstance()
  - static webView fromUrl(String)
  - static WebView fromAsset(String)
  - static WebView fromRaw(String)
  - static WebView fromHtml(String)
  - static boolean javascriptInterface(WebView, Object, String)
  - static boolean javascriptInterface(WebView, Object)
  - static boolean back(WebView)
  - static boolean forward(WebView)
  - static String original(WebView)

Author
------

ShkSchneider

https://shkschneider.me

https://github.com/shkschneider/android_Skeleton
