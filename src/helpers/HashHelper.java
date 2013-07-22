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

public abstract class HashHelper {

    public static final String MD5 = "MD5";
    public static final String SHA = "SHA";

	private static String hash(final String string, final String algorithm, final Integer length) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			StringBuilder stringBuilder = new StringBuilder();

			messageDigest.reset();
			messageDigest.update(string.getBytes());
			byte digest[] = messageDigest.digest();
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
			LogHelper.e("NoSuchAlgorithmException: " + e.getMessage());
		}
		return null;
	}

	public static String md5(final String string) {
		return hash(string, HashHelper.MD5, 32);
	}

	public static String sha(final String string) {
		return hash(string, HashHelper.SHA, 40);
	}

}
