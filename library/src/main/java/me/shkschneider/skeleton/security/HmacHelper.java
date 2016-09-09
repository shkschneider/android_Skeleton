package me.shkschneider.skeleton.security;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.java.StringHelper;

public class HmacHelper {

    private static final String ALGORITHM = "HmacSHA1";

    @Nullable
    public static String hash(@NonNull final String key, @NonNull final String string) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
            final Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(secretKeySpec);
            final byte[] digest = mac.doFinal(string.getBytes());
            if (digest == null) {
                return null;
            }
            return StringHelper.hexadecimal(digest);
        }
        catch (final NoSuchAlgorithmException e) {
            LogHelper.wtf(e);
            return null;
        }
        catch (final InvalidKeyException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

}
