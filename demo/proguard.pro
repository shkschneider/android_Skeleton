# <https://code.google.com/p/android/issues/detail?id=78377#c188>
# <https://stackoverflow.com/questions/30526173>
-keepattributes **
-keep class !android.support.v7.internal.view.menu.**, ** {
    !private <fields>;
    protected <fields>;
    public <fields>;
    <methods>;
}
#-dontpreverify
#-dontobfuscate
#-dontoptimize
#-dontshrink
-dontwarn **
-dontnote **

# <https://github.com/Pixplicity/Proguard-rules/blob/master/proguard-rules.pro>

# android
-keep class android.**
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference

# native
-keepclasseswithmembernames class * {
    native <methods>;
}

# public xml constructors
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int, int);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context);
}
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
-keepclassmembers class **.R$* {
    public static <fields>;
}

# serializable
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# remove logs
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}
-assumenosideeffects class me.shkschneider.skeleton.helper.Log {
    public static *** d(...);
    public static *** v(...);
}

# play services
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}
-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
