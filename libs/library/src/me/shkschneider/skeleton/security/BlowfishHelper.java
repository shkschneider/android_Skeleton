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

import org.mindroit.jbcrypt.BCrypt;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import me.shkschneider.skeleton.helper.LogHelper;

@SuppressWarnings("unused")
public class BlowfishHelper {

    public static String salt(final String salt) {
        try {
            final SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(salt.getBytes());
            return BCrypt.gensalt(10, secureRandom);
        }
        catch (NoSuchAlgorithmException e) {
            LogHelper.w("NoSuchAlgorithmException: " + e.getMessage());
            return null;
        }
    }

    public static String hash(final String salt, final String string) {
        return BCrypt.hashpw(string, salt);
    }

}
