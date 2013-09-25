package me.shkschneider.skeleton;

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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@SuppressWarnings("unused")
public class Networks {

    public static String userAgent() {
        final String userAgent = Systems.systemProperty(Systems.SYSTEM_PROPERTY_HTTP_AGENT);
        return (userAgent != null ? userAgent : String.format("%s-%d", Skeleton.PLATFORM, Skeleton.Android.api()));
    }

    public static Boolean online(final Context context) {
        if (context != null) {
            final NetworkInfo networkInfo = ((ConnectivityManager) Systems.systemService(context, Systems.SYSTEM_SERVICE_CONNECTIVITY)).getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isConnected();
            }
            else {
                Log.w("NetworkInfo was NULL");
            }
        }
        else {
            Log.w("Context was NULL");
        }
        return false;
    }

    public static Boolean wifi(final Context context) {
        if (context != null) {
            final WifiManager wifiManager = ((WifiManager) Systems.systemService(context, Systems.SYSTEM_SERVICE_WIFI));
            if (wifiManager != null) {
                return (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED);
            }
            else {
                Log.w("NetworkInfo was NULL");
            }
        }
        else {
            Log.w("Context was NULL");
        }
        return false;
    }

    public static String macAddress(final Context context) {
        if (context != null) {
            final WifiManager wifiManager = (WifiManager) Systems.systemService(context, Systems.SYSTEM_SERVICE_WIFI);
            final WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            final String macAddress = wifiInfo.getMacAddress();
            if (! TextUtils.isEmpty(macAddress)) {
                return macAddress;
            }
            else {
                Log.w("MacAddress was NULL");
            }
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

    public static Boolean validUrl(final String url) {
        if (! TextUtils.isEmpty(url)) {
            return URLUtil.isValidUrl(url);
        }
        else {
            Log.w("Url was null");
        }
        return false;
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
            Log.e("SocketException: " + e.getMessage());
        }
        return ipAddresses;
    }

}
