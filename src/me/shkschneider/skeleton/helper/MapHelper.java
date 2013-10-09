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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class MapHelper {

    public static List<Object> keys(final Map<Object, Object> objects) {
        if (objects == null) {
            LogHelper.w("Objects was NULL");
            return null;
        }

        return Arrays.asList(objects.keySet().toArray());
    }

    public static List<Object> values(final Map<Object, Object> objects) {
        if (objects == null) {
            LogHelper.w("Objects was NULL");
            return null;
        }

        return Arrays.asList(objects.values().toArray());
    }

    public static Boolean keyEquals(final Map<Object, Object> objects, final Object object) {
        if (objects == null) {
            LogHelper.w("Objects was NULL");
            return null;
        }

        if (object == null) {
            LogHelper.w("Object was NULL");
            return null;
        }

        for (final Object o : keys(objects)) {
            if (! o.equals(object)) {
                return false;
            }
        }

        return true;
    }

    public static Boolean valueEquals(final Map<Object, Object> objects, final Object object) {
        if (objects == null) {
            LogHelper.w("Objects was NULL");
            return null;
        }

        if (object == null) {
            LogHelper.w("Object was NULL");
            return null;
        }

        for (final Object o : values(objects)) {
            if (! o.equals(object)) {
                return false;
            }
        }

        return true;
    }

}
