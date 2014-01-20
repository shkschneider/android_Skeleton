package me.shkschneider.skeleton.nfc;

import android.content.Intent;
import android.nfc.NfcAdapter;

import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.java.StringHelper;

@SuppressWarnings("unused")
public class NfcHelper {

    public static boolean tech(final Intent intent) {
        if (intent == null) {
            LogHelper.warning("Intent was NULL");
            return false;
        }

        final String action = intent.getAction();
        if (StringHelper.nullOrEmpty(action)) {
            LogHelper.warning("Action was NULL");
            return false;
        }

        return (action.equals(NfcAdapter.ACTION_TECH_DISCOVERED));
    }

    public static boolean tag(final Intent intent) {
        if (intent == null) {
            LogHelper.warning("Intent was NULL");
            return false;
        }

        final String action = intent.getAction();
        if (StringHelper.nullOrEmpty(action)) {
            LogHelper.warning("Action was NULL");
            return false;
        }

        return (action.equals(NfcAdapter.ACTION_TAG_DISCOVERED));
    }

    public static boolean ndef(final Intent intent) {
        if (intent == null) {
            LogHelper.warning("Intent was NULL");
            return false;
        }

        final String action = intent.getAction();
        if (StringHelper.nullOrEmpty(action)) {
            LogHelper.warning("Action was NULL");
            return false;
        }

        return (action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED));
    }

}
