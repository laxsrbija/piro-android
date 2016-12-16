package rs.laxsrbija.piro;

import android.net.Uri;

/**
 * Konstante koje projekat koristi
 * Created by LAX on 8.2.2016.
 * Project piro-android
 */

class PiroContract {

    static final String APP_NAME = "PIRO";

    private static final String SERVER_SCHEME = "http";

    private static final String SERVER_ADDRESS_EXTERNAL = "<ADDRESS HERE>";

    private static final String PIRO_DIR = "piro";
    private static final String QUERY_DIR = "rpi";
    private static final String QUERY_TARGET ="piro-query.php";
    static final String FUNCTION = "f";
    static final String ARGUMENT = "arg";

    static final String GET_JSON_FUNCTION = "getJSON";

    static final String RELAY_TOGGLE_FUNCTION = "toggleRelay";
    static final String PC_TOGGLE_FUNCTION = "togglePC";
    static final String ARG_LED_RIGHT = "1";
    static final String ARG_LED_CENTER = "0";
    static final String ARG_LED_LEFT = "2";

    static final String HEATING_TOGGLE_FUNCTION = "toggleThermal";
    static final String HEATING_INCREMENT = "increment";
    static final String HEATING_DECREMENT = "decrement";
    static final String HEATING_MODE = "setMode";
    static final String ARG_MODE_AUTO = "0";
    static final String ARG_MODE_DAY = "2";
    static final String ARG_MODE_NIGHT = "3";
    static final String ARG_MODE_FROST = "4";

    static class JSON {

        static final String RELAY_LED_CENTER = "ledCentar";
        static final String RELAY_LED_RIGHT = "ledDesno";
        static final String RELAY_LED_LEFT = "ledLevo";
        static final String RELAY_PC = "racunar";

        static final String HEATER_STATUS = "statusPeci";
        static final String HEATER_TEMP = "temperaturaPeci";
        static final String HEATER_MODE = "rezimPeci";

        static final String WEATHER_CITY = "grad";
        static final String WEATHER_CURRENT_TEMP = "trenutnaTemperatura";
        static final String WEATHER_CURRENT_CONDITIONS = "trenutnaStanje";
        static final String WEATHER_CURRENT_ICON = "trenutnaIkona";
        static final String WEATHER_CURRENT_PRECIPITATION = "padavine";
        static final String WEATHER_CURRENT_VISIBILITY = "vidljivost";
        static final String WEATHER_CURRENT_FEELS_LIKE = "subjektivniOsecaj";
        static final String WEATHER_CURRENT_UV = "uvIndeks";
        static final String WEATHER_DAY = "dan";
        static final String WEATHER_DAILY_CONDITIONS = "dnevnaStanje";
        static final String WEATHER_DAILY_HIGH = "dnevnaMax";
        static final String WEATHER_DAILY_LOW = "dnevnaMin";
        static final String WEATHER_DAILY_ICON = "dnevnaIkona";

        static final String SYSTEM_UPTIME = "systemUptime";
        static final String SYSTEM_LOAD = "systemLoad";
        static final String SYSTEM_TEMP = "systemTemperature";

    }

    static Uri.Builder buildPreliminaryURI() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SERVER_SCHEME)
                .encodedAuthority(SERVER_ADDRESS_EXTERNAL)
                .appendPath(PIRO_DIR)
                .appendPath(QUERY_DIR)
                .appendPath(QUERY_TARGET);
        return builder;
    }

}
