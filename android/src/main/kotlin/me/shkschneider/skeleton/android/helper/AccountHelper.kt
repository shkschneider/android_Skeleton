package me.shkschneider.skeleton.android.helper

import android.Manifest
import android.accounts.Account
import androidx.annotation.RequiresPermission
import me.shkschneider.skeleton.android.provider.SystemServices

object AccountHelper {

    const val TYPE_GOOGLE = "com.google"

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    fun names(type: String): List<String>? =
        SystemServices.accountManager()?.getAccountsByType(type)?.mapTo(ArrayList<String>()) { it.name }

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    fun email(): String? =
        account()?.name

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    fun emails(): List<String>? =
        accounts()?.mapTo(ArrayList<String>()) { it.name }

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    fun account(): Account? =
        SystemServices.accountManager()?.getAccountsByType(TYPE_GOOGLE)?.get(0)

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    fun accounts(): List<Account>? =
        SystemServices.accountManager()?.accounts?.toList()

}
