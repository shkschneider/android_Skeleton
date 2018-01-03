package me.shkschneider.skeleton.helper

import android.Manifest
import android.accounts.Account
import android.support.annotation.RequiresPermission

import java.util.ArrayList
import java.util.Collections

object AccountHelper {

    const val TYPE_GOOGLE = "com.google"

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    fun names(type: String): List<String>? {
        val names = SystemServices.accountManager()?.getAccountsByType(type)?.mapTo(ArrayList<String>()) { it.name }
        return if (names?.size != null) names else null
    }

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    fun email(): String? {
        val account = account()
        return account?.name
    }

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    fun emails(): List<String>? {
        val emails = accounts().mapTo(ArrayList<String>()) { it.name }
        return if (emails.size > 0) emails else null
    }

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    fun account(): Account? {
        val accounts = SystemServices.accountManager()?.getAccountsByType(TYPE_GOOGLE)
        return if (accounts?.isNotEmpty() != null) {
            accounts[0]
        } else null
    }

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    fun accounts(): List<Account> {
        val accounts = ArrayList<Account>()
        Collections.addAll(accounts, *SystemServices.accountManager()?.accounts)
        return accounts
    }

}
