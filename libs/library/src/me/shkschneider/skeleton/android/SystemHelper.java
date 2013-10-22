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
package me.shkschneider.skeleton.android;

import android.content.Context;
import android.text.TextUtils;

@SuppressWarnings("unused")
public class SystemHelper {

    public static final String SYSTEM_PROPERTY_JAVA_VM_NAME = "java.vm.name";
    public static final String SYSTEM_PROPERTY_JAVA_VM_VENDOR = "java.vm.vendor";
    public static final String SYSTEM_PROPERTY_JAVA_VM_VERSION = "java.vm.version";
    // java.version is not implemented on Android
    public static final String SYSTEM_PROPERTY_JAVA_HOME = "java.home";
    public static final String SYSTEM_PROPERTY_USER_DIR = "user.dir";
    // user.home is not implemented on Android
    public static final String SYSTEM_PROPERTY_USER_REGION = "user.region";
    public static final String SYSTEM_PROPERTY_JAVA_IO_TMPDIR = "java.io.tmpdir";
    public static final String SYSTEM_PROPERTY_JAVA_RUNTIME_NAME = "java.runtime.name";
    public static final String SYSTEM_PROPERTY_HTTP_AGENT = "http.agent";
    public static final String SYSTEM_PROPERTY_FILE_SEPARATOR = "file.separator";
    public static final String SYSTEM_PROPERTY_FILE_ENCODING = "file.encoding";
    public static final String SYSTEM_PROPERTY_LINE_SEPARATOR = "line.separator";
    public static final String SYSTEM_PROPERTY_OS_ARCH = "os.arch";
    public static final String SYSTEM_PROPERTY_OS_NAME = "os.name";
    public static final String SYSTEM_PROPERTY_OS_VERSION = "os.version";
    public static final String SYSTEM_PROPERTY_PATH_SEPARATOR = "path.separator";

    public static String systemProperty(final String property) {
        if (TextUtils.isEmpty(property)) {
            LogHelper.w("SystemProperty was NULL");
            return null;
        }

        final String systemProperty = java.lang.System.getProperty(property);
        if (systemProperty == null) {
            LogHelper.w("SystemProperty was NULL");
            return null;
        }

        return systemProperty;
    }

    public static String uname() {
        return String.format("%s %s %s",
                systemProperty(SYSTEM_PROPERTY_OS_NAME),
                systemProperty(SYSTEM_PROPERTY_OS_VERSION),
                systemProperty(SYSTEM_PROPERTY_OS_ARCH));
    }

    public static final String SYSTEM_SERVICE_WINDOW_SERVICE = Context.WINDOW_SERVICE;
    public static final String SYSTEM_SERVICE_LAYOUT_INFLATER_SERVICE = Context.LAYOUT_INFLATER_SERVICE;
    public static final String SYSTEM_SERVICE_ACTIVITY_SERVICE = Context.ACTIVITY_SERVICE;
    public static final String SYSTEM_SERVICE_POWER_SERVICE = Context.POWER_SERVICE;
    public static final String SYSTEM_SERVICE_ALARM_SERVICE = Context.ALARM_SERVICE;
    public static final String SYSTEM_SERVICE_NOTIFICATION_SERVICE = Context.NOTIFICATION_SERVICE;
    public static final String SYSTEM_SERVICE_KEYGUARD_SERVICE = Context.KEYGUARD_SERVICE;
    public static final String SYSTEM_SERVICE_LOCATION_SERVICE = Context.LOCATION_SERVICE;
    public static final String SYSTEM_SERVICE_SEARCH_SERVICE = Context.SEARCH_SERVICE;
    public static final String SYSTEM_SERVICE_SENSOR = Context.SENSOR_SERVICE;
    //public static final String SYSTEM_SERVICE_STORAGE = Context.STORAGE_SERVICE;
    public static final String SYSTEM_SERVICE_VIBRATOR = Context.VIBRATOR_SERVICE;
    public static final String SYSTEM_SERVICE_CONNECTIVITY = Context.CONNECTIVITY_SERVICE;
    public static final String SYSTEM_SERVICE_WIFI = Context.WIFI_SERVICE;
    public static final String SYSTEM_SERVICE_AUDIO = Context.AUDIO_SERVICE;
    //public static final String SYSTEM_SERVICE_MEDIA_ROUTER = Context.MEDIA_ROUTER_SERVICE;
    public static final String SYSTEM_SERVICE_TELEPHONY = Context.TELEPHONY_SERVICE;
    public static final String SYSTEM_SERVICE_INPUT_METHOD = Context.INPUT_METHOD_SERVICE;
    public static final String SYSTEM_SERVICE_UI_MODE = Context.UI_MODE_SERVICE;
    //public static final String SYSTEM_SERVICE_DOWNLOAD = Context.DOWNLOAD_SERVICE;

    @SuppressWarnings("MagicConstant")
    public static Object systemService(final Context context, final String service) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return null;
        }

        if (TextUtils.isEmpty(service)) {
            LogHelper.w("Service was NULL");
            return null;
        }

        return context.getSystemService(service);
    }

}
