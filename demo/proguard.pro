# https://code.google.com/p/android/issues/detail?id=78377
-repackageclasses ''
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v7.app.** { *; }
-keep interface android.support.v7.app.** { *; }
-keep class android.support.v7.widget.** { *; }
-keep interface android.support.v7.widget.** { *; }
-keep class android.support.v13.app.** { *; }
-keep interface android.support.v13.app.** { *; }
# NOT android.support.design
-keep public class *extends java.lang.annotation.Annotation { *; }
