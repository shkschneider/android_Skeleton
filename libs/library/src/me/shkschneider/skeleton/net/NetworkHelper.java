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
package me.shkschneider.skeleton.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.webkit.URLUtil;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.SystemHelper;

@SuppressWarnings("unused")
public class NetworkHelper {

    public static String hostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e) {
            LogHelper.e("UnknownHostException: " + e.getMessage());
            return null;
        }
    }

    public static String userAgent() {
        final String userAgent = SystemHelper.systemProperty(SystemHelper.SYSTEM_PROPERTY_HTTP_AGENT);
        return (userAgent != null ? userAgent : String.format("%s-%d", AndroidHelper.PLATFORM, AndroidHelper.api()));
    }

    public static Boolean online(final Context context) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return false;
        }

        final NetworkInfo networkInfo = ((ConnectivityManager) SystemHelper.systemService(context, SystemHelper.SYSTEM_SERVICE_CONNECTIVITY)).getActiveNetworkInfo();
        if (networkInfo == null) {
            LogHelper.w("NetworkInfo was NULL");
            return false;
        }

        return networkInfo.isConnected();
    }

    public static Boolean wifi(final Context context) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return false;
        }

        final WifiManager wifiManager = ((WifiManager) SystemHelper.systemService(context, SystemHelper.SYSTEM_SERVICE_WIFI));
        if (wifiManager == null) {
            LogHelper.w("NetworkInfo was NULL");
            return null;
        }

        return (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED);
    }

    public static String macAddress(final Context context) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return null;
        }

        final WifiManager wifiManager = (WifiManager) SystemHelper.systemService(context, SystemHelper.SYSTEM_SERVICE_WIFI);
        final WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        final String macAddress = wifiInfo.getMacAddress();
        if (TextUtils.isEmpty(macAddress)) {
            LogHelper.w("MacAddress was NULL");
            return null;
        }

        return macAddress;
    }

    public static Boolean validUrl(final String url) {
        if (TextUtils.isEmpty(url)) {
            LogHelper.w("Url was null");
            return false;
        }

        return URLUtil.isValidUrl(url);
    }

    public static List<String> ipAddresses() {
        final List<String> ipAddresses = new ArrayList<String>();
        try {
            for (final Enumeration<NetworkInterface> enumerationNetworkInterface = NetworkInterface.getNetworkInterfaces(); enumerationNetworkInterface.hasMoreElements();) {
                final NetworkInterface networkInterface = enumerationNetworkInterface.nextElement();
                for (Enumeration<InetAddress> enumerationInetAddress = networkInterface.getInetAddresses(); enumerationInetAddress.hasMoreElements();) {
                    final InetAddress inetAddress = enumerationInetAddress.nextElement();
                    final String ipAddress = inetAddress.getHostAddress();
                    if (! inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipAddress)) {
                        ipAddresses.add(ipAddress);
                    }
                }
            }
        }
        catch (SocketException e) {
            LogHelper.e("SocketException: " + e.getMessage());
        }
        return ipAddresses;
    }

}
