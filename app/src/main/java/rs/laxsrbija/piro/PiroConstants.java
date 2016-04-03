package rs.laxsrbija.piro;

/**
 * Konstante koje projekat koristi
 * Created by LAX on 8.2.2016.
 * Project piro-android
 */

public class PiroConstants {

    public static final String APP_NAME = "PIRO";

    public static final String SERVER_ADDRES = "http://192.168.1.2/";

    private static final String QUERY_ADDRESS = SERVER_ADDRES.concat("piro/rpi/piro-query.php");

    public static final String GET_XML = SERVER_ADDRES.concat("piro/rpi/piro.xml");
    public static final String WEATHER_UPDATE =
            QUERY_ADDRESS.concat("?f=azurirajVreme&arg=android");

    public static final String SET_LED_LIGHT = QUERY_ADDRESS.concat("?f=toggleRelay&arg=1");

}
