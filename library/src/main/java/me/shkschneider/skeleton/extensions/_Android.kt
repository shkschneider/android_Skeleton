package me.shkschneider.skeleton.extensions

import android.content.Context
import android.net.Uri
import android.support.annotation.RequiresApi
import me.shkschneider.skeleton.helper.AndroidHelper
import kotlin.reflect.KClass

// Intent

fun <T:Any> Intent(context: Context, klass: KClass<T>):
        android.content.Intent = android.content.Intent(context, klass.java)

fun <T:Any> Intent(action: String, uri: Uri, context: Context, klass: KClass<T>):
        android.content.Intent = android.content.Intent(action, uri, context, klass.java)

// ComponentName

fun <T:Any> ComponentName(pkg: Context, klass: KClass<T>):
        android.content.ComponentName = android.content.ComponentName(pkg, klass.java)

// Context

@RequiresApi(AndroidHelper.API_23)
fun <T:Any> Context.getSystemService(klass: KClass<T>): T? {
    return getSystemService(klass.java) ?: null
}

@RequiresApi(AndroidHelper.API_23)
fun <T:Any> Context.getSystemServiceName(klass: KClass<T>): String? {
    return getSystemServiceName(klass.java) ?: null
}
