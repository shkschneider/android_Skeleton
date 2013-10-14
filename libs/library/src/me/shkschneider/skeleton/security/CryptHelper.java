/**
 * Copyright 2013 ShkSchneider
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.shkschneider.skeleton.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.StringHelper;

@SuppressWarnings("unused")
public class CryptHelper {

    public static List<String> algorithms() {
        final List<String> algorithms = new ArrayList<String>();
        for (Provider provider : Security.getProviders()) {
            final Set<Provider.Service> services = provider.getServices();
            for (final Provider.Service service : services) {
                algorithms.add(provider.toString().split("\\s")[0] + ":" + service.getAlgorithm());
            }
        }
        return algorithms;
    }

    protected String mSecret;
    protected IvParameterSpec mIvSpec;
    protected SecretKeySpec mKeySpec;
    protected SecretKey mKey;
    protected Cipher mCipher;

    public CryptHelper(final String secret) {
        try {
            mSecret = secret;
            mIvSpec = new IvParameterSpec(StringHelper.HEX.getBytes());
            final KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            mKey = keyGenerator.generateKey();
            mKeySpec = new SecretKeySpec(mKey.getEncoded(), "AES");
            mCipher = Cipher.getInstance("AES/CBC/NoPadding");
        }
        catch (NoSuchAlgorithmException e) {
            LogHelper.w("NoSuchAlgorithmException: " + e.getMessage());
        }
        catch (NoSuchPaddingException e) {
            LogHelper.w("NoSuchPaddingException: " + e.getMessage());
        }
    }

    protected byte[] pad(byte[] bytes) {
        final int extras = 16 - (bytes.length % 16);
        if (extras == 0 || extras == 16) {
            return bytes;
        }

        byte[] padded = new byte[bytes.length + extras];
        Arrays.fill(padded, (byte) 0);
        System.arraycopy(bytes, 0, padded, 0, bytes.length);
        return padded;
    }

    public byte[] encrypt(final byte[] bytes) {
        try {
            mCipher.init(Cipher.ENCRYPT_MODE, mKeySpec, mIvSpec);
            return mCipher.doFinal(pad(bytes));
        }
        catch (IllegalBlockSizeException e) {
            LogHelper.w("IllegalBlockSizeException: " + e.getMessage());
        }
        catch (BadPaddingException e) {
            LogHelper.w("BadPaddingException: " + e.getMessage());
        }
        catch (InvalidAlgorithmParameterException e) {
            LogHelper.w("InvalidAlgorithmParameterException: " + e.getMessage());
        }
        catch (InvalidKeyException e) {
            LogHelper.w("InvalidKeyException: " + e.getMessage());
        }
        return null;
    }

    public byte[] decrypt(final byte[] bytes) {
        try {
            mCipher.init(Cipher.DECRYPT_MODE, mKeySpec, mIvSpec);
            byte[] decrypted = mCipher.doFinal(pad(bytes));
            if (decrypted.length > 0) {
                int trim = 0;
                for (int i = decrypted.length - 1; i >= 0; i--) {
                    if (decrypted[i] == 0) {
                        trim++;
                    }
                }
                if (trim > 0) {
                    byte[] newArray = new byte[decrypted.length - trim];
                    System.arraycopy(decrypted, 0, newArray, 0, decrypted.length - trim);
                    decrypted = newArray;
                }
            }
            return decrypted;
        }
        catch (IllegalBlockSizeException e) {
            LogHelper.w("IllegalBlockSizeException: " + e.getMessage());
        }
        catch (BadPaddingException e) {
            LogHelper.w("BadPaddingException: " + e.getMessage());
        }
        catch (InvalidAlgorithmParameterException e) {
            LogHelper.w("InvalidAlgorithmParameterException: " + e.getMessage());
        }
        catch (InvalidKeyException e) {
            LogHelper.w("InvalidKeyException: " + e.getMessage());
        }
        return null;
    }

    public byte[] decrypt(final String string) {
        return decrypt(string.getBytes());
    }

    public static String rot13(final String string) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'N' && c <= 'Z') c -= 13;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

}
