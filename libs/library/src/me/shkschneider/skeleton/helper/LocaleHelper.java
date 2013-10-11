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

import java.util.Locale;

@SuppressWarnings("unused")
public class LocaleHelper {

    public static Locale locale() {
        return Locale.getDefault();
    }

    public static String language() {
        return locale().getDisplayLanguage();
    }

    public static String language2() {
        return locale().getLanguage();
    }

    public static String language3() {
        return locale().getISO3Language();
    }

    public static String country() {
        return locale().getDisplayCountry();
    }

    public static String country2() {
        return locale().getCountry();
    }

    public static String country3() {
        return locale().getISO3Country();
    }

}
