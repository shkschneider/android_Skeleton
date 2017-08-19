package me.shkschneider.skeleton.helper;

import android.Manifest;
import android.accounts.Account;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountHelper {

    private static final String TYPE_GOOGLE = "com.google";

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    @Nullable
    public static List<String> names(@NonNull final String type) {
        final List<String> names = new ArrayList<>();
        for (final Account account : SystemServices.accountManager().getAccountsByType(type)) {
            names.add(account.name);
        }
        return (names.size() > 0 ? names : null);
    }

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    @Nullable
    public static String email() {
        final Account account = account();
        return (account != null ? account.name : null);
    }

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    @Nullable
    public static List<String> emails() {
        final List<String> emails = new ArrayList<>();
        for (final Account account : accounts()) {
            emails.add(account.name);
        }
        return (emails.size() > 0 ? emails : null);
    }

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    @Nullable
    public static Account account() {
        final Account[] accounts = SystemServices.accountManager().getAccountsByType(TYPE_GOOGLE);
        if (accounts.length > 0) {
            return accounts[0];
        }
        return null;
    }

    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    public static List<Account> accounts() {
        final List<Account> accounts = new ArrayList<>();
        Collections.addAll(accounts, SystemServices.accountManager().getAccounts());
        return accounts;
    }

}
