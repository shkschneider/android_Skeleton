package me.shkschneider.skeleton.extensions.android

import android.content.Context
import androidx.annotation.RequiresApi
import me.shkschneider.skeleton.helper.AndroidHelper
import kotlin.reflect.KClass

@RequiresApi(AndroidHelper.API_23)
fun <T: Any> Context.getSystemService(klass: KClass<T>): T? =
        getSystemService(klass.java) ?: null

@RequiresApi(AndroidHelper.API_23)
fun <T: Any> Context.getSystemServiceName(klass: KClass<T>): String? =
        getSystemServiceName(klass.java) ?: null
