package rs.laxsrbija.piro;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by LAX on 8.2.2016..
 */

public class UcitavanjePodataka {

    protected AppCompatActivity mKontekst;
    private DohvatiXML mParser;
    private Komunikacija mKomunikacija;

    public UcitavanjePodataka(AppCompatActivity kontekst) {
        mKontekst = kontekst;

        mParser = new DohvatiXML();

        mKomunikacija = new Komunikacija();
        mKomunikacija.inicijalizuj(mKontekst);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Konstante.adresaServera + Konstante.azurirajVreme,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Izvršava učitavanje XML fajla tek nakon dobijanja
                        // najnovijih vremenskih podataka
                        mParser.execute();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(mKontekst.getResources().getString(R.string.app_name),
                        error.toString());
            }
        });

        mKomunikacija.getRequestQueue().add(stringRequest);
    }

    public void ponovoUcitaj() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Konstante.adresaServera.concat(Konstante.azurirajVreme),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Izvršava učitavanje XML fajla tek nakon dobijanja
                        // najnovijih vremenskih podataka
                        mParser.execute();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(mKontekst.getResources().getString(R.string.app_name),
                        error.toString());
            }
        });

        mKomunikacija.getRequestQueue().add(stringRequest);
    }

    public boolean podaciUcitani(ArrayList<String> podaci) {
        Log.v(mKontekst.getResources().getString(R.string.app_name), "Učitavam status komponenti na osnovu XML podataka...");

        ((Switch)mKontekst.findViewById(R.id.ledCentar)).setChecked(podaci.get(0).equals("1"));
        ((Switch)mKontekst.findViewById(R.id.ledDesno)).setChecked(podaci.get(1).equals("1"));
        ((Switch)mKontekst.findViewById(R.id.ledLevo)).setChecked(podaci.get(2).equals("1"));

        return true;
    }

    class DohvatiXML extends AsyncTask<Void, Void, ArrayList<String>> {

        private ArrayList<String> parsirajPodatkeIzXML(String str) {

            try {

                ArrayList<String> niz = new ArrayList<>();

                XmlPullParserFactory fabrika = XmlPullParserFactory.newInstance();
                fabrika.setNamespaceAware(true);
                XmlPullParser parser = fabrika.newPullParser();

                InputStream st = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
                parser.setInput(st, null);

                int eventType = parser.getEventType();

                //Log.v(mKontekst.getResources().getString(R.string.app_name), "Pocetak parsiranja!");

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG && parser.getName().equals("status")) {
                        eventType = parser.next();
                        if (eventType == XmlPullParser.TEXT)
                            niz.add(parser.getText());
                    }
                    eventType = parser.next();
                }

                Log.v(mKontekst.getResources().getString(R.string.app_name), "Štampanje ucitanog niza:");
                for (int i = 0; i < niz.size(); i++)
                    Log.v(mKontekst.getResources().getString(R.string.app_name), niz.get(i));

                return niz;

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {

            HttpURLConnection urlConnection;
            BufferedReader reader;
            String xmlRezultat;

            try {

                URL url = new URL(Konstante.adresaServera.concat(Konstante.getXML));
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //Log.v(mKontekst.getResources().getString(R.string.app_name), "Povezan na server!");

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();

                if (inputStream == null)
                    return null;

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null)
                    buffer.append(line.concat("\n"));

                if (buffer.length() == 0)
                    return null;

                xmlRezultat = buffer.toString();

                //Log.v(mKontekst.getResources().getString(R.string.app_name),
                // "XML string: \n" + xmlRezultat);
                return parsirajPodatkeIzXML(xmlRezultat);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            podaciUcitani(result);
        }

    }

}