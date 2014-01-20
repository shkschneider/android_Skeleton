package me.shkschneider.skeleton.nfc;

import android.content.Intent;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import me.shkschneider.skeleton.data.CharsetHelper;
import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.java.StringHelper;

@SuppressWarnings("unused")
public class NdefHelper {

    private static final String[] URI_PREFIXES = new String[] {
            "",
            "http://www.",
            "https://www.",
            "http://",
            "https://",
            "tel:",
            "mailto:",
            "ftp://anonymous:anonymous@",
            "ftp://ftp.",
            "ftps://",
            "sftp://",
            "smb://",
            "nfs://",
            "ftp://",
            "dav://",
            "news:",
            "telnet://",
            "imap:",
            "rtsp://",
            "urn:",
            "pop:",
            "sip:",
            "sips:",
            "tftp:",
            "btspp://",
            "btl2cap://",
            "btgoep://",
            "tcpobex://",
            "irdaobex://",
            "file://",
            "urn:epc:id:",
            "urn:epc:tag:",
            "urn:epc:pat:",
            "urn:epc:raw:",
            "urn:epc:",
            "urn:nfc:",
    };

    public static final String TAG_FORMAT = "Ndef";
    public static final Map<String, String> TAG_TYPES = new HashMap<String, String>() {
        {
            put(Ndef.MIFARE_CLASSIC, "Mifare Classic");
            put(Ndef.NFC_FORUM_TYPE_1, "NFC Forum Tag Type 1");
            put(Ndef.NFC_FORUM_TYPE_2, "NFC Forum Tag Type 2");
            put(Ndef.NFC_FORUM_TYPE_3, "NFC Forum Tag Type 3");
            put(Ndef.NFC_FORUM_TYPE_4, "NFC Forum Tag Type 4");
        }
    };
    public static final Map<Short, String> RECORD_TNF = new HashMap<Short, String>() {
        {
            put(NdefRecord.TNF_ABSOLUTE_URI, "uri");
            put(NdefRecord.TNF_EMPTY, "empty");
            put(NdefRecord.TNF_EXTERNAL_TYPE, "external");
            put(NdefRecord.TNF_MIME_MEDIA, "media");
            put(NdefRecord.TNF_UNCHANGED, "unchanged");
            put(NdefRecord.TNF_UNKNOWN, "unknown");
            put(NdefRecord.TNF_WELL_KNOWN, "know");
        }
    };
    public static final byte[] RTD_ANDROID_APP = "android.com:pkg".getBytes();
    public static final Map<byte[], String> RECORD_TYPES = new HashMap<byte[], String>() {
        {
            put(NdefRecord.RTD_ALTERNATIVE_CARRIER, "alternative-cattier");
            put(NdefRecord.RTD_HANDOVER_CARRIER, "handover-carrier");
            put(NdefRecord.RTD_HANDOVER_REQUEST, "handover-request");
            put(NdefRecord.RTD_HANDOVER_SELECT, "handover-select");
            put(NdefRecord.RTD_SMART_POSTER, "smart-poster");
            put(NdefRecord.RTD_TEXT, "text");
            put(NdefRecord.RTD_URI, "uri");
        }
    };

    // Empty

    public static NdefMessage fromNothing() {
        final byte[] empty = new byte[] {};
        final NdefRecord[] records = new NdefRecord[1];
        records[0] = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, empty, empty, empty);
        return new NdefMessage(records);
    }

    public static boolean empty(final Ndef ndef) {
        if (ndef == null) {
            LogHelper.warning("Ndef was NULL");
            return true;
        }

        return false;
    }

    public static boolean empty(final NdefMessage ndefMessage) {
        if (ndefMessage == null) {
            LogHelper.warning("NdefMessage was NULL");
            return true;
        }

        return (ndefMessage.equals(fromNothing()));
    }

    // From

    public static NdefMessage fromIntent(final Intent intent) {
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

    public static NdefMessage fromUri(final Uri uri) {
        if (uri == null) {
            LogHelper.warning("uri was NULL");
            return null;
        }

        return fromUri(uri.toString());
    }

    public static NdefMessage fromUri(final URI uri) {
        if (uri == null) {
            LogHelper.warning("URI was NULL");
            return null;
        }

        return fromUri(uri.toString());
    }

    public static NdefMessage fromUri(String uri) {
        if (StringHelper.nullOrEmpty(uri)) {
            LogHelper.warning("Uri was NULL");
            return null;
        }

        try {
            int prefix = 0;
            for (int i = 1; i < URI_PREFIXES.length; i++) {
                if (uri.startsWith(URI_PREFIXES[i])) {
                    prefix = i;
                    break;
                }
            }
            if (prefix > 0) {
                uri = uri.substring(URI_PREFIXES[prefix].length());
            }
            int len = uri.length();
            byte[] payload = new byte[len + 1];
            payload[0] = (byte) prefix;
            System.arraycopy(uri.getBytes(CharsetHelper.UTF8), 0, payload, 1, len);
            NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_URI, new byte[0], payload);
            NdefRecord[] records = new NdefRecord[] { record };
            return new NdefMessage(records);
        }
        catch (final NoClassDefFoundError e) {
            LogHelper.error("NoClassDefFoundError: " + e.getMessage());
            return null;
        }
        catch (final UnsupportedEncodingException e) {
            LogHelper.error("UnsupportedEncodingException: " + e.getMessage());
            return null;
        }
    }

    public static NdefMessage fromUrl(final URL url) {
        if (url == null) {
            LogHelper.warning("URL was NULL");
            return null;
        }

        return fromUri(url.toString());
    }

    public static NdefMessage fromMime(final String mimeType, final byte[] data) {
        if (StringHelper.nullOrEmpty(mimeType)) {
            LogHelper.warning("MimeType was NULL");
            return null;
        }
        if (data == null) {
            LogHelper.warning("Data was NULL");
            return null;
        }

        try {
            final NdefRecord record = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, mimeType.getBytes(), new byte[0], data);
            final NdefRecord[] records = new NdefRecord[] { record };
            return new NdefMessage(records);
        }
        catch (final NoClassDefFoundError e) {
            LogHelper.error("NoClassDefFoundError: " + e.getMessage());
            return null;
        }
    }

    public static NdefMessage fromText(final String text) {
        if (StringHelper.nullOrEmpty(text)) {
            LogHelper.warning("Text was NULL");
            return null;
        }

        try {
            final byte[] textBytes = text.getBytes();
            final byte[] textPayload = new byte[textBytes.length + 3];
            textPayload[0] = 0x02; // Status byte; UTF-8 and "en" encoding.
            textPayload[1] = 'e';
            textPayload[2] = 'n';
            System.arraycopy(textBytes, 0, textPayload, 3, textBytes.length);
            final NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], textPayload);
            final NdefRecord[] records = new NdefRecord[] { record };
            return new NdefMessage(records);
        }
        catch (final NoClassDefFoundError e) {
            LogHelper.error("NoClassDefFoundError: " + e.getMessage());
            return null;
        }
    }

    public static NdefMessage fromText(final String text, final String language) {
        if (StringHelper.nullOrEmpty(text)) {
            LogHelper.warning("Text was NULL");
            return null;
        }
        if (StringHelper.nullOrEmpty(language)) {
            LogHelper.warning("Language was NULL");
            return null;
        }

        try {
            final int languageCodeLength = language.length();
            final int textLength = text.length();
            final byte[] textPayload = new byte[textLength + 1 + languageCodeLength];
            textPayload[0] = (byte)(0x3F & languageCodeLength); // UTF-8 with the given language code length.
            System.arraycopy(language.getBytes(), 0, textPayload, 1, languageCodeLength);
            System.arraycopy(text.getBytes(), 0, textPayload, 1 + languageCodeLength, textLength);
            final NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], textPayload);
            final NdefRecord[] records = new NdefRecord[] { record };
            return new NdefMessage(records);
        }
        catch (final NoClassDefFoundError e) {
            LogHelper.error("NoClassDefFoundError: " + e.getMessage());
            return null;
        }
    }

    // To

    public static Uri toUri(final NdefRecord ndefRecord) {
        if (ndefRecord == null) {
            LogHelper.warning("NdefRecord was NULL");
            return null;
        }

        final int tnf = ndefRecord.getTnf();
        if (tnf == NdefRecord.TNF_ABSOLUTE_URI) {
            return Uri.parse(new String(ndefRecord.getType()));
        }
        else if (tnf == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(NdefRecord.RTD_URI, ndefRecord.getType())) {
            final byte[] payload = ndefRecord.getPayload();
            final int pre = (int) payload[0];
            if (! (pre >= 0 && pre < URI_PREFIXES.length)) {
                LogHelper.warning("Prefix was invalid");
                return null;
            }

            return Uri.parse(URI_PREFIXES[pre] + new String(payload, 1, payload.length - 1));
        }
        else {
            LogHelper.warning("Uri was invalid");
            return null;
        }
    }

    public static String toText(final NdefMessage ndefMessage) {
        if (ndefMessage == null) {
            LogHelper.warning("NdefMessage was NULL");
            return null;
        }

        String data = "";
        for (final NdefRecord ndefRecord : ndefMessage.getRecords()) {
            final Short tnf = ndefRecord.getTnf();
            if (tnf != NdefRecord.TNF_WELL_KNOWN) {
                LogHelper.debug("NdefRecord type: " + RECORD_TNF.get(tnf));
                continue ;
            }

            try {
                final byte[] payload = ndefRecord.getPayload();
                final String encoding = (((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16");
                final int length = (payload[0] & 0063);
                if (data.length() > 0) {
                    data = data.concat("\n");
                }
                data = data.concat(new String(payload, length + 1, payload.length - length - 1, encoding));
            }
            catch (final UnsupportedEncodingException e) {
                LogHelper.error("UnsupportedEncodingException: " + e.getMessage());
                return null;
            }
        }
        return data;
    }

}
