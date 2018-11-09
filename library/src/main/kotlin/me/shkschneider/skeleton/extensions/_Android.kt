package me.shkschneider.skeleton.extensions

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import me.shkschneider.skeleton.helper.AndroidHelper
import kotlin.reflect.KClass

// Intent

fun <T:Any> Intent(context: Context, klass: KClass<T>):
        Intent = Intent(context, klass.java)

fun <T:Any> Intent(action: String, uri: Uri, context: Context, klass: KClass<T>):
        Intent = Intent(action, uri, context, klass.java)

inline fun <reified T:Activity> ContextWrapper.startActivity(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class).apply {
        block(this)
    })
}

inline fun <reified T:Activity> Activity.startActivityForResult(requestCode: Int, block: Intent.() -> Unit = {}) {
    startActivityForResult(Intent(this, T::class).apply {
        block(this)
    }, requestCode)
}

// ComponentName

fun <T:Any> ComponentName(pkg: Context, klass: KClass<T>):
        ComponentName = ComponentName(pkg, klass.java)

// Context

@RequiresApi(AndroidHelper.API_23)
fun <T:Any> Context.getSystemService(klass: KClass<T>): T? {
    return getSystemService(klass.java) ?: null
}

@RequiresApi(AndroidHelper.API_23)
fun <T:Any> Context.getSystemServiceName(klass: KClass<T>): String? {
    return getSystemServiceName(klass.java) ?: null
}

// View

fun ViewGroup.addOrUpdateView(view: View) {
    // Avoids IllegalStateException for already added views
    if (view.parent != null) {
       this.removeView(view)
    }
    this.addView(view)
}
