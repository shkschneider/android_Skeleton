package me.shkschneider.skeleton.helper;

import android.os.Build;

public class AndroidHelper {

    protected AndroidHelper() {
        // Empty
    }

    public static final int API_1 = Build.VERSION_CODES.BASE; // 1.0
    public static final int API_2 = Build.VERSION_CODES.BASE_1_1; // 1.1
    public static final int API_3 = Build.VERSION_CODES.CUPCAKE; // 1.5
    public static final int API_4 = Build.VERSION_CODES.DONUT; // 1.6
    public static final int API_5 = Build.VERSION_CODES.ECLAIR; // 2.0
    public static final int API_6 = Build.VERSION_CODES.ECLAIR_0_1; // 2.0.1
    public static final int API_7 = Build.VERSION_CODES.ECLAIR_MR1; // 2.1
    public static final int API_8 = Build.VERSION_CODES.FROYO; // 2.2.x
    public static final int API_9 = Build.VERSION_CODES.GINGERBREAD; // 2.3-2.3.2
    public static final int API_10 = Build.VERSION_CODES.GINGERBREAD_MR1; // 2.3.3-2.3.7
    public static final int API_11 = Build.VERSION_CODES.HONEYCOMB; // 3.0
    public static final int API_12 = Build.VERSION_CODES.HONEYCOMB_MR1; // 3.1
    public static final int API_13 = Build.VERSION_CODES.HONEYCOMB_MR2; // 3.2.x
    public static final int API_14 = Build.VERSION_CODES.ICE_CREAM_SANDWICH; // 4.0.1-4.0.2
    public static final int API_15 = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1; // 4.0.3-4.0.4
    public static final int API_16 = Build.VERSION_CODES.JELLY_BEAN; // 4.1.x
    public static final int API_17 = Build.VERSION_CODES.JELLY_BEAN_MR1; // 4.2.x
    public static final int API_18 = Build.VERSION_CODES.JELLY_BEAN_MR2; // 4.3.x
    public static final int API_19 = Build.VERSION_CODES.KITKAT; // 4.4-4.4.x
    public static final int API_20 = Build.VERSION_CODES.KITKAT_WATCH; // 4.4.x
    public static final int API_21 = Build.VERSION_CODES.LOLLIPOP; // 5.0
    public static final int API_22 = Build.VERSION_CODES.LOLLIPOP_MR1; // 5.1
    public static final int API_23 = Build.VERSION_CODES.M; // 6.0 "Marshmallow"

    public static final String PLATFORM = "Android";

    public static final int ANDROID_1 = API_1;
    public static final int ANDROID_2 = API_5;
    public static final int ANDROID_3 = API_11;
    public static final int ANDROID_4 = API_14;
    public static final int ANDROID_5 = API_21;
    public static final int ANDROID_6 = API_23;

    public static String codename(final int api) {
        switch (api) {
            case API_1:
            case API_2:
                return "Base";
            case API_3:
                return "Cupcake";
            case API_4:
                return "Donut";
            case API_5:
            case API_6:
            case API_7:
                return "Eclair";
            case API_8:
                return "Froyo";
            case API_9:
            case API_10:
                return "Gingerbread";
            case API_11:
            case API_12:
            case API_13:
                return "Honeycomb";
            case API_14:
            case API_15:
                return "IceCreamSandwich";
            case API_16:
            case API_17:
            case API_18:
                return "JellyBean";
            case API_19:
            case API_20:
                return "KitKat";
            case API_21:
            case API_22:
                return "Lollipop";
            case API_23:
                return "Marshmallow";
            default:
                return "unknown";
        }
    }

    public static String codename() {
        return codename(api());
    }

    public static int api() {
        return Build.VERSION.SDK_INT;
    }

}
