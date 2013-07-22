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

import android.text.format.DateUtils;

import java.util.Date;

public class TimeHelper {

    // UNIX Timestamp (length: 1-11)
    public static Integer timestamp() {
        return Integer.valueOf(Long.toString(System.currentTimeMillis() / DateUtils.SECOND_IN_MILLIS));
    }

    // Relative elapsed time
    public static String relative(final Long time) {
        return DateUtils.getRelativeTimeSpanString(time, new Date().getTime(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
    }

}
