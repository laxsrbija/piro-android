package rs.laxsrbija.piro;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

/**
 * Konstante koje projekat koristi
 * Created by LAX on 8.2.2016.
 * Project piro-android
 */

public class PiroContract {

    public static final String APP_NAME = "PIRO";

    public static final String SERVER_SCHEME = "http";

    /*
        Kako modem koji koristim ne može da se sa lokalne mreže poveže na svoju eksternu adresu (i obrnuto),
        neophodno je proveriti da li je uređaj povezan na lokalnu mrežu, ili ne. Ukliko jeste,
        koristi se lokalna IP, a u suprotnom eksterna.

        Ako server na kome se sistem nalazi nema ovu restrikciju,
        postaviti zastavicu ispod na 'false' i koristiti samo globalnu adresu.
        U suprotnom, neophodno je definisati i SSID Wifi mreže.
     */
    public static final boolean SEPARATE_LOCAL_AND_EXTERNAL_IP = true;
    public static final String SSID = "PAROV STELAR";
    public static final String SERVER_ADDRESS_INTERNAL = "192.168.1.2";
    public static final String SERVER_ADDRESS_EXTERNAL = "piro.ddns.net";

    public static final String PIRO_DIR = "piro";
    public static final String QUERY_DIR = "rpi";
    public static final String QUERY_TARGET ="piro-query.php";
    public static final String FUNCTION = "f";
    public static final String ARGUMENT = "arg";

    public static final String GET_JSON_FUNCTION = "getJSON";

    public static final String RELAY_TOGGLE_FUNCTION = "toggleRelay";
    public static final String PC_TOGGLE_FUNCTION = "togglePC";
    public static final String ARG_LED_RIGHT = "1";
    public static final String ARG_LED_CENTER = "0";
    public static final String ARG_LED_LEFT = "2";

    public static final String HEATING_TOGGLE_FUNCTION = "toggleThermal";
    public static final String HEATING_INCREMENT = "increment";
    public static final String HEATING_DECREMENT = "decrement";
    public static final String HEATING_MODE = "setMode";
    public static final String ARG_MODE_AUTO = "0";
    public static final String ARG_MODE_DAY = "2";
    public static final String ARG_MODE_NIGHT = "3";
    public static final String ARG_MODE_FROST = "4";

    public static class JSON {

        public static final String RELAY_LED_CENTER = "led_centar";
        public static final String RELAY_LED_RIGHT = "led_desno";
        public static final String RELAY_LED_LEFT = "led_levo";
        public static final String RELAY_PC = "racunar";

        public static final String HEATER_STATUS = "statusPeci";
        public static final String HEATER_TEMP = "temperaturaPeci";
        public static final String HEATER_MODE = "rezimPeci";

        public static final String WEATHER_CITY = "grad";
        public static final String WEATHER_CURRENT_TEMP = "trenutnaTemperatura";
        public static final String WEATHER_CURRENT_CONDITIONS = "trenutnaStanje";
        public static final String WEATHER_CURRENT_ICON = "trenutnaIkona";
        public static final String WEATHER_CURRENT_PRECIPITATION = "padavine";
        public static final String WEATHER_CURRENT_VISIBILITY = "vidljivost";
        public static final String WEATHER_CURRENT_FEELS_LIKE = "subjektivniOsecaj";
        public static final String WEATHER_CURRENT_UV = "uvIndeks";
        public static final String WEATHER_DAY = "dan";
        public static final String WEATHER_DAILY_CONDITIONS = "dnevnaStanje";
        public static final String WEATHER_DAILY_HIGH = "dnevnaMax";
        public static final String WEATHER_DAILY_LOW = "dnevnaMin";
        public static final String WEATHER_DAILY_ICON = "dnevnaIkona";

        public static final String SYSTEM_UPTIME = "system_uptime";
        public static final String SYSTEM_LOAD = "system_load";
        public static final String SYSTEM_TEMP = "systemTemperature";

    }

    public static Uri.Builder buildPreliminaryURI(Activity activity) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SERVER_SCHEME)
                .authority(getServerAddress(activity))
                .appendPath(PIRO_DIR)
                .appendPath(QUERY_DIR)
                .appendPath(QUERY_TARGET);
        return builder;
    }

    public static String getServerAddress(Activity activity) {

        if (!SEPARATE_LOCAL_AND_EXTERNAL_IP) {
            return SERVER_ADDRESS_EXTERNAL;
        }

        // Uređaj radi na emulatoru
        if (Build.FINGERPRINT.contains("generic")) {
            return SERVER_ADDRESS_INTERNAL;
        }

        WifiManager wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo;

        wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
            String ssid = wifiInfo.getSSID();
//            Log.v("PIRO", "Wifi SSID: " + ssid);

            if (ssid != null && ssid.contains(SSID)) {
                return SERVER_ADDRESS_INTERNAL;
            }
        }

//        Log.v("PIRO", "Wifi not connected");
        return SERVER_ADDRESS_EXTERNAL;

    }

}
