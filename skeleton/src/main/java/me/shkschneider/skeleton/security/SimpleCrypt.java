package me.shkschneider.skeleton.security;

import android.support.annotation.NonNull;

public class SimpleCrypt {

    private static final String ALGORITHM = "XOR";

    private char[] mKey;

    public SimpleCrypt(@NonNull final String key) {
        mKey = key.toCharArray();
    }

    public String algorithm() {
        return ALGORITHM;
    }

    public String key() {
        return new String(mKey);
    }

    public String encrypt(@NonNull final String string) {
        final char[] chars = string.toCharArray();
        final char[] encrypted = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            encrypted[i] = (char) (chars[i] ^ mKey[i % mKey.length]);
        }
        return Base64Helper.encrypt(new String(encrypted).getBytes());
    }

    public String decrypt(@NonNull final String string) {
        char[] encrypted = Base64Helper.decrypt(string).toCharArray();
        char[] decrypted = new char[encrypted.length];
        for (int i = 0; i < encrypted.length; i++) {
            decrypted[i] = (char) (encrypted[i] ^ mKey[i % mKey.length]);
        }
        return new String(decrypted);
    }

    // TODO

}
