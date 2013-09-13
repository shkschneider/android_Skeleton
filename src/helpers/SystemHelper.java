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

// java.version is not implemented on Android
public static final String SYSTEM_PROPERTY_JAVA_VM_NAME = "java.vm.name";
public static final String SYSTEM_PROPERTY_JAVA_VM_VENDOR = "java.vm.vendor";
public static final String SYSTEM_PROPERTY_JAVA_VM_VERSION = "java.vm.version";
public static final String SYSTEM_PROPERTY_JAVA_HOME = "java.home";
// user.home is not implemented on Android
public static final String SYSTEM_PROPERTY_USER_DIR = "user.dir";
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
public static final String SYSTEM_PROPERTY_OS_ARCH = "";

public static String getSystemProperty(final String property) {
    if (! TextUtils.isEmpty(property)) {
        final String systemProperty = System.getProperty(property);
        if (systemProperty != null) {
            return systemProperty;
        }
    }
    return "";
}

public static Integer timestamp() {
    return (Integer) (System.currentTimeMillis() / 1000);
}
