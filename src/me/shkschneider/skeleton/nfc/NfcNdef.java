package me.shkschneider.skeleton.nfc;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;

import java.io.IOException;

import me.shkschneider.skeleton.helpers.LogHelper;

@SuppressWarnings("unused")
public class NfcNdef {

    public static NdefMessage get(final Intent intent) {
        final Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            LogHelper.warning("Tag was NULL");
            return null;
        }

        final Ndef ndef = Ndef.get(tag);
        if (ndef == null) {
            LogHelper.warning("Ndef was NULL");
            return null;
        }

        if (! ndef.isConnected()) {
            try {
                ndef.connect();
            }
            catch (final IOException e) {
                LogHelper.error("IOException: " + e.getMessage());
                return null;
            }
        }

        try {
            return ndef.getNdefMessage();
        }
        catch (final FormatException e) {
            LogHelper.error("FormatException: " + e.getMessage());
            return null;
        }
        catch (final IOException e) {
            LogHelper.error("IOException: " + e.getMessage());
            return null;
        }
    }

}
