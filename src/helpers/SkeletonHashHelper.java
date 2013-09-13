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
package me.shkschneider.skeleton.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import me.shkschneider.skeleton.SkeletonLog;

public abstract class SkeletonHashHelper {

    public static final String MD5 = "MD5";
    public static final String SHA = "SHA";

    private static final Integer MD5_LENGTH = 32;
    private static final Integer SHA_LENGTH = 40;

	private static String hash(final String algorithm, final String string, final Integer length) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            final StringBuilder stringBuilder = new StringBuilder();

            messageDigest.reset();
            messageDigest.update(string.getBytes());
            final byte digest[] = messageDigest.digest();
            stringBuilder.setLength(0);
            for (int i = 0; i < digest.length; i++) {
                final int b = digest[i] & 255;
                if (b < length) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(Integer.toHexString(b));
            }
            return stringBuilder.toString();
        }
        catch (NoSuchAlgorithmException e) {
            SkeletonLog.e("NoSuchAlgorithmException: " + e.getMessage());
        }
		return null;
	}

	public static String md5(final String string) {
		return hash(string, SkeletonHashHelper.MD5, SkeletonHashHelper.MD5_LENGTH);
	}

	public static String sha(final String string) {
		return hash(string, SkeletonHashHelper.SHA, SkeletonHashHelper.SHA_LENGTH);
	}

}
