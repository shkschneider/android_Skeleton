Skeleton
========

> Android library with useful classes to be used as a "Skeleton" for an application.

Current version: **4.0.1**

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

[Usage](https://github.com/shkschneider/android_Skeleton/wiki/Usage)


Packages and classes
--------------------

[Documentation](https://github.com/shkschneider/android_Skeleton/wiki/Documentation)

Author
------

[ShkSchneider](https://shkschneider.me)

https://github.com/shkschneider/android_Skeleton
