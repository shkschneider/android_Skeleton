package me.shkschneider.skeleton.security;

import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.java.StringHelper;

@SuppressWarnings("unused")
public final class SimpleCrypt {

    private static final String ALGORITHM = "XOR";

    private char[] mKey;

    public SimpleCrypt(final String key) {
        if (StringHelper.nullOrEmpty(key)) {
            LogHelper.debug("Key was NULL");
            mKey = "".toCharArray();
            return ;
        }

        mKey = key.toCharArray();
    }

    public String algorithm() {
        return ALGORITHM;
    }

    public String key() {
        return new String(mKey);
    }

    public String encrypt(final String string) {
        if (StringHelper.nullOrEmpty(string)) {
            LogHelper.warning("String was NULL");
            return null;
        }

        final char[] chars = string.toCharArray();
        final char[] encrypted = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            encrypted[i] = (char) (chars[i] ^ mKey[i % mKey.length]);
        }
        return Base64Helper.encrypt(new String(encrypted).getBytes());
    }

    public String decrypt(final String string) {
        if (StringHelper.nullOrEmpty(string)) {
            LogHelper.warning("String was NULL");
            return null;
        }

        char[] encrypted = Base64Helper.decrypt(string).toCharArray();
        char[] decrypted = new char[encrypted.length];
        for (int i = 0; i < encrypted.length; i++) {
            decrypted[i] = (char) (encrypted[i] ^ mKey[i % mKey.length]);
        }
        return new String(decrypted);
    }

}
