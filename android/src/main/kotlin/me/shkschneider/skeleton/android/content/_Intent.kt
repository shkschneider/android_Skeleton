package me.shkschneider.skeleton.android.content

import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlin.reflect.KClass

fun <T : Any> intent(context: Context, klass: KClass<T>): Intent =
        Intent(context, klass.java)

fun <T : Any> intent(action: String, uri: Uri, context: Context, klass: KClass<T>): Intent =
        Intent(action, uri, context, klass.java)
