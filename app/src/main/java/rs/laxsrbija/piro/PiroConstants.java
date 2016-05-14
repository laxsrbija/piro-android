package rs.laxsrbija.piro;

/**
 * Konstante koje projekat koristi
 * Created by LAX on 8.2.2016.
 * Project piro-android
 */

public class PiroConstants {

    public static final String APP_NAME = "PIRO";

    public static final String SERVER_ADDRES = "http://192.168.1.2/piro/";

    public static final String QUERY_ADDRESS = SERVER_ADDRES.concat("rpi/piro-query.php");

    public static final String WEATHER_ICONS = SERVER_ADDRES.concat("img/weather/png/");

    public static final String GET_JSON = QUERY_ADDRESS.concat("?f=getJSON&arg=android");
    public static final String GET_JSON_FORCE = QUERY_ADDRESS.concat("?f=getJSON&arg=force");

    public static final String SET_LED_MAIN = QUERY_ADDRESS.concat("?f=toggleRelay&arg=0");
    public static final String SET_LED_RIGHT = QUERY_ADDRESS.concat("?f=toggleRelay&arg=1");
    public static final String SET_LED_LEFT = QUERY_ADDRESS.concat("?f=toggleRelay&arg=2");

    public static final String TOGGLE_PC = QUERY_ADDRESS.concat("?f=togglePC");

    public static final String THERMAL_POWER = QUERY_ADDRESS.concat("?f=toggleThermal");
    public static final String THERMAL_INCREMENT = QUERY_ADDRESS.concat("?f=increment");
    public static final String THERMAL_DECREMENT = QUERY_ADDRESS.concat("?f=decrement");

    public static final String THERMAL_MODE_SET = QUERY_ADDRESS.concat("?f=setMode&arg=");

}
