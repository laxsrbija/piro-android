package rs.laxsrbija.piro;

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

}
