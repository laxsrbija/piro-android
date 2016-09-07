package rs.laxsrbija.piro;

import android.net.Uri;

/**
 * Konstante koje projekat koristi
 * Created by LAX on 8.2.2016.
 * Project piro-android
 */

public class PiroContract {

    public static final String APP_NAME = "PIRO";

    public static final String SERVER_SCHEME = "http";
    public static final String SERVER_ADDRESS2 = "192.168.1.2";
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

        public static final String RELAY_LED_CENTER = "ledCentar";
        public static final String RELAY_LED_RIGHT = "ledDesno";
        public static final String RELAY_LED_LEFT = "ledLevo";
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

        public static final String SYSTEM_UPTIME = "systemUptime";
        public static final String SYSTEM_LOAD = "systemLoad";
        public static final String SYSTEM_TEMP = "systemTemperature";

    }

    public static Uri.Builder buildPreliminaryURI() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(PiroContract.SERVER_SCHEME)
                .authority(PiroContract.SERVER_ADDRESS2)
                .appendPath(PiroContract.PIRO_DIR)
                .appendPath(PiroContract.QUERY_DIR)
                .appendPath(PiroContract.QUERY_TARGET);
        return builder;
    }

}
