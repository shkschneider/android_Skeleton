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
        if (! TextUtils.isEmpty(string)) {
            return Character.toUpperCase(string.charAt(0)) + string.substring(1).toLowerCase();
        }
        else {
            LogHelper.w("String was NULL");
        }
        return string;
    }

    public static Boolean numeric(final String string) {
        if (! TextUtils.isEmpty(string)) {
            return TextUtils.isDigitsOnly(string);
        }
        else {
            LogHelper.w("String was NULL");
        }
        return false;
    }

    public static Boolean contains(final String[] strings, final String string) {
        if (strings != null) {
            for (final String s : strings) {
                if (s.equals(string)) {
                    return true;
                }
            }
        }
        else {
            LogHelper.w("StringHelper was NULL");
        }
        return false;
    }

}
