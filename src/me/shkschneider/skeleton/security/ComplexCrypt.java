package me.shkschneider.skeleton.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.java.StringHelper;

@SuppressWarnings("unused")
public final class ComplexCrypt {

    private static final String ALGORITHM = "AES/CTR/NoPadding"; // CFB OFB CTR
    private static final int ALGORITHM_KEY_LENGTH = 128;
    private static final int ALGORITHM_KEY_PAD = 16;

    private byte[] mSecret;
    private IvParameterSpec mIvParameterSpec;
    private SecretKeySpec mSecretKeySpec;
    private SecretKey mSecretKey;
    private Cipher mCipher;

    public ComplexCrypt(final byte[] secret) throws NoSuchAlgorithmException, NoSuchPaddingException {
        // salt
        mSecret = new String(secret).getBytes();
        // initialzation vector
        mIvParameterSpec = new IvParameterSpec(StringHelper.random(ALGORITHM_KEY_PAD).getBytes());
        // key
        final KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM.split("/")[0]);
        keyGenerator.init(ALGORITHM_KEY_LENGTH);
        mSecretKey = keyGenerator.generateKey();
        mSecretKeySpec = new SecretKeySpec(mSecretKey.getEncoded(), ALGORITHM.split("/")[0]);
        // cipher
        mCipher = Cipher.getInstance(ALGORITHM);
    }

    public String secret() {
        return Base64Helper.encrypt(mSecret);
    }

    public String key() {
        return Base64Helper.encrypt(mSecretKey.getEncoded());
    }

    public String algorithm() {
        return mSecretKey.getAlgorithm();
    }

    protected byte[] pad(final byte[] bytes) {
        final int extras = ALGORITHM_KEY_PAD - (bytes.length % ALGORITHM_KEY_PAD);
        if (extras == 0 || extras == ALGORITHM_KEY_PAD) {
            return bytes;
        }

        byte[] padded = new byte[bytes.length + extras];
        Arrays.fill(padded, (byte) 0x00);
        System.arraycopy(bytes, 0, padded, 0, bytes.length);
        return padded;
    }

    public byte[] encrypt(final byte[] bytes) {
        if (StringHelper.nullOrEmpty(bytes)) {
            LogHelper.warning("Bytes was NULL");
            return null;
        }

        try {
            mCipher.init(Cipher.ENCRYPT_MODE, mSecretKeySpec, mIvParameterSpec);
            return mCipher.doFinal(pad(bytes));
        }
        catch (final IllegalBlockSizeException e) {
            LogHelper.warning("IllegalBlockSizeException: " + e.getMessage());
            return null;
        }
        catch (final BadPaddingException e) {
            LogHelper.warning("BadPaddingException: " + e.getMessage());
            return null;
        }
        catch (final InvalidAlgorithmParameterException e) {
            LogHelper.warning("InvalidAlgorithmParameterException: " + e.getMessage());
            return null;
        }
        catch (final InvalidKeyException e) {
            LogHelper.warning("InvalidKeyException: " + e.getMessage());
            return null;
        }
    }

    public byte[] decrypt(final byte[] bytes) {
        try {
            mCipher.init(Cipher.DECRYPT_MODE, mSecretKeySpec, mIvParameterSpec);
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
        catch (final IllegalBlockSizeException e) {
            LogHelper.warning("IllegalBlockSizeException: " + e.getMessage());
            return null;
        }
        catch (final BadPaddingException e) {
            LogHelper.warning("BadPaddingException: " + e.getMessage());
            return null;
        }
        catch (final InvalidAlgorithmParameterException e) {
            LogHelper.warning("InvalidAlgorithmParameterException: " + e.getMessage());
            return null;
        }
        catch (final InvalidKeyException e) {
            LogHelper.warning("InvalidKeyException: " + e.getMessage());
            return null;
        }
    }

}
