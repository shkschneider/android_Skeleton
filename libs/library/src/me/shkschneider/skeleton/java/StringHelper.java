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
package me.shkschneider.skeleton.java;

import android.text.TextUtils;

import java.util.Random;

import me.shkschneider.skeleton.android.LogHelper;

@SuppressWarnings("unused")
public class StringHelper {

    public static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    public static final String NUMERIC = "0123456789";
    public final static String HEX = NUMERIC + ALPHA.substring(0, 6);
    public static final String ALPHA_NUMERIC = ALPHA + NUMERIC;

    public static String capitalize(final String string) {
        if (TextUtils.isEmpty(string)) {
            LogHelper.w("String was NULL");
            return string;
        }

        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static Boolean alpha(final String string) {
        if (TextUtils.isEmpty(string)) {
            LogHelper.w("String was NULL");
            return false;
        }

        return chars(string, ALPHA);
    }

    public static Boolean numeric(final String string) {
        if (TextUtils.isEmpty(string)) {
            LogHelper.w("String was NULL");
            return false;
        }

        return chars(string, NUMERIC);
    }

    public static Boolean alphanumeric(final String string) {
        if (TextUtils.isEmpty(string)) {
            LogHelper.w("String was NULL");
            return false;
        }

        return chars(string, ALPHA_NUMERIC);
    }

    public static Boolean chars(final String string, final String chars) {
        if (TextUtils.isEmpty(string)) {
            LogHelper.w("String was NULL");
            return false;
        }

        if (TextUtils.isEmpty(chars)) {
            LogHelper.w("Chars was NULL");
            return false;
        }

        for (char c : chars.toCharArray()) {
            if (! chars.contains(String.valueOf(c))) {
                return false;
            }
        }

        return true;
    }

    public static String random(final String characters, final Integer length) {
        if (TextUtils.isEmpty(characters)) {
            LogHelper.w("Characters was NULL");
            return null;
        }
        if (length <= 0) {
            LogHelper.w("Length was invalid");
            return null;
        }

        final Random random = new Random();
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return stringBuilder.toString();
    }

    public static String random(final Integer length) {
        return random(ALPHA.toLowerCase() + ALPHA.toUpperCase() + NUMERIC, length);
    }

}
