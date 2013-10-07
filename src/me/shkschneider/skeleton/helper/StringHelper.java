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
package me.shkschneider.skeleton.helper;

import android.text.TextUtils;

@SuppressWarnings("unused")
public class StringHelper {

    public static String capitalize(final String string) {
        if (TextUtils.isEmpty(string)) {
            LogHelper.w("String was NULL");
            return string;
        }

        return Character.toUpperCase(string.charAt(0)) + string.substring(1).toLowerCase();
    }

    public static Boolean numeric(final String string) {
        if (TextUtils.isEmpty(string)) {
            LogHelper.w("String was NULL");
            return false;
        }

        return TextUtils.isDigitsOnly(string);
    }

    public static Boolean contains(final String[] strings, final String string) {
        if (strings == null) {
            LogHelper.w("Strings was NULL");
            return false;
        }

        if (TextUtils.isEmpty(string)) {
            LogHelper.w("String was NULL");
            return false;
        }

        for (final String s : strings) {
            if (! TextUtils.isEmpty(s) && s.equals(string)) {
                return true;
            }
        }
        return false;
    }

}
