package rs.laxsrbija.piro;

/**
 * Konstante koje projekat koristi
 * Created by LAX on 8.2.2016.
 */

public class Konstante {

    private static String adresaUpita = "piro/rpi/piro-querry.php";

    static String adresaServera = "http://192.168.1.2/";

    static String getXML = "piro/rpi/piro.xml";
    static String azurirajVreme = adresaUpita.concat("?f=azurirajVreme&arg=android");

    static String setLedDesno = adresaUpita.concat("?f=toggleRelay&arg=1");

}
