package me.shkschneider.skeleton.helper

import android.Manifest
import android.accounts.Account
import androidx.annotation.RequiresPermission
import me.shkschneider.skeleton.helperx.SystemServices

object AccountHelper {

    const val TYPE_GOOGLE = "com.google"

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    fun names(type: String): List<String>? {
        val accounts = SystemServices.accountManager()?.getAccountsByType(type)
        return accounts?.mapTo(ArrayList<String>()) { it.name }
    }

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    fun email(): String? {
        val account = account()
        return account?.name
    }

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    fun emails(): List<String>? {
        val accounts = accounts()
        return accounts?.mapTo(ArrayList<String>()) { it.name }
    }

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    fun account(): Account? {
        val accounts = SystemServices.accountManager()?.getAccountsByType(TYPE_GOOGLE)
        return accounts?.get(0)
    }

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    fun accounts(): List<Account>? {
        val accounts = SystemServices.accountManager()?.accounts
        return accounts?.toList()
    }

}
