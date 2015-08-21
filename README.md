Skeleton [![JitPack](https://img.shields.io/github/tag/shkschneider/android_Skeleton.svg?label=JitPack)](https://jitpack.io/#shkschneider/android_Skeleton/4.2.1)
========

> Android library with useful classes to be used as a "Skeleton" for an application.

[![Google Play](https://developer.android.com/images/brand/en_generic_rgb_wo_45.png)](https://play.google.com/store/apps/details?id=me.shkschneider.skeleton.demo)

Setup
-----

**Gradle**

Top-level *build.gradle*:

<pre>allprojects {
    repositories {
        jcenter()
        maven {
            url 'https://jitpack.io'
        }
    }
}</pre>

Application-level *build.gradle*:

<pre>dependencies {
    compile 'com.github.shkschneider:android_Skeleton:4.2.1@aar'
}</pre>

**AAR**

https://jitpack.io/com/github/shkschneider/android_Skeleton/4.2.1/android_Skeleton-4.2.1.aar

Specifications
--------------

**API**

- minSdkVersion [**14**](https://developer.android.com/reference/android/os/Build.VERSION_CODES.html#ICE_CREAM_SANDWICH)
- targetSdkVersion [22](https://developer.android.com/reference/android/os/Build.VERSION_CODES.html#LOLLIPOP_MR1)

**Libraries**


- [com.google.android.gms:play-services](https://developer.android.com/google/play-services/index.html)
- [com.google.code.gson:gson](https://github.com/google/gson)
- [com.android.support:support-v4](https://developer.android.com/tools/support-library/features.html#v4)
- [com.android.support:appcompat-v7](https://developer.android.com/tools/support-library/features.html#v7-appcompat)
- [com.android.support:palette-v7](https://developer.android.com/tools/support-library/features.html#v7-palette)

**Uses**

- [Gradle 1.3.0](http://tools.android.com/tech-docs/new-build-system)
- [Android Studio 1.3.1](https://developer.android.com/sdk/index.html)
- [android.support.v7.app.AppCompatActivity](https://developer.android.com/reference/android/support/v7/app/AppCompatActivity.html)
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
