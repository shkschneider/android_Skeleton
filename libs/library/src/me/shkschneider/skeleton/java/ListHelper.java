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

import java.util.List;

import me.shkschneider.skeleton.android.LogHelper;

@SuppressWarnings("unused")
public class ListHelper {

    public static Object[] array(final List<Object> objects) {
        if (objects == null) {
            LogHelper.w("Objects was NULL");
            return null;
        }

        return objects.toArray(new Object[objects.size()]);
    }

    public static Boolean equals(final List<Object> objects, final Object object) {
        if (objects == null) {
            LogHelper.w("Objects was NULL");
            return false;
        }

        if (object == null) {
            LogHelper.w("String was NULL");
            return false;
        }

        for (final Object o : objects) {
            if (o == null) {
                return false;
            }
            if (! o.equals(object)) {
                return false;
            }
        }

        return true;
    }

}
