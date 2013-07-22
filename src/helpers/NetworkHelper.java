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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.webkit.URLUtil;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public abstract class NetworkHelper {

	public static Boolean isConnectedToInternet(final Context context) {
		NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if (networkInfo == null || ! networkInfo.isConnected()) {
			return false;
		}
		return true;
	}

    public static Boolean isValidUrl(final String url) {
        if (! TextUtils.isEmpty(url)) {
            return URLUtil.isValidUrl(url);
        }
        return false;
    }

    public static String userAgent(final Context context) {
        if (context != null) {
            return AndroidHelper.PLATFORM + "-" +
                    AndroidHelper.getApi() +
                    ApplicationHelper.getPackage(context) + "/" +
                    ApplicationHelper.getVersionCode(context);
        }
        return null;
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
        } catch (SocketException e) {
            LogHelper.e("SocketException: " + e.getMessage());
        }
        return ipAddresses;
    }

}
