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

import android.util.Base64;

@SuppressWarnings("unused")
public class Base64Helper {

    protected static final int FLAGS = Base64.NO_WRAP | Base64.URL_SAFE;

    public static String encrypt(final String string) {
        byte[] bytes = string.getBytes();
        return Base64.encodeToString(bytes, FLAGS);
    }

    public static String encrypt(final byte[] bytes) {
        return Base64.encodeToString(bytes, FLAGS);
    }

    public static String decrypt(final String string) {
        byte[] bytes = Base64.decode(string, FLAGS);
        return new String(bytes);
    }

    public static String decrypt(final byte[] bytes) {
        return Base64.encodeToString(bytes, FLAGS);
    }

}
