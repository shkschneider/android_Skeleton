package me.shkschneider.skeleton.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlin.reflect.KClass

fun <T : Any> Intent(context: Context, klass: KClass<T>): Intent =
        Intent(context, klass.java)

fun <T : Any> Intent(action: String, uri: Uri, context: Context, klass: KClass<T>): Intent =
        Intent(action, uri, context, klass.java)
