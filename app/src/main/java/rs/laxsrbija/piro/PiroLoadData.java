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
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by LAX on 8.2.2016..
 * Project piro-android
 */

public class PiroLoadData {

    protected AppCompatActivity mContext;
    private XMLParser mParser;
    private PiroComms mComms;

    public PiroLoadData(final AppCompatActivity mContext) {
        this.mContext = mContext;

        mParser = new XMLParser();

        mComms = new PiroComms();
        mComms.initialize(mContext);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                PiroConstants.WEATHER_UPDATE,
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
                Log.v(PiroConstants.APP_NAME, error.toString());
            }
        });

        mComms.getRequestQueue().add(stringRequest);
    }

    public void reloadData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                PiroConstants.WEATHER_UPDATE,
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
                Log.v(PiroConstants.APP_NAME, error.toString());
            }
        });

        mComms.getRequestQueue().add(stringRequest);
    }

    public boolean onLoadingComplete(ArrayList<String> data) {
        Log.v(PiroConstants.APP_NAME, "Učitavam status komponenti na osnovu XML podataka...");

        ((Switch)mContext.findViewById(R.id.ledMain)).setChecked("1".equals(data.get(0)));
        ((Switch)mContext.findViewById(R.id.ledRight)).setChecked("1".equals(data.get(1)));
        ((Switch)mContext.findViewById(R.id.ledLeft)).setChecked("1".equals(data.get(2)));

        return true;
    }

    class XMLParser extends AsyncTask<Void, Void, ArrayList<String>> {

        private ArrayList<String> parseDataFromXML(String str) {

            try {

                ArrayList<String> array = new ArrayList<>();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();

                InputStream inputStream = new ByteArrayInputStream(
                        str.getBytes(StandardCharsets.UTF_8));
                parser.setInput(inputStream, null);

                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG && parser.getName().equals("status")) {
                        eventType = parser.next();
                        if (eventType == XmlPullParser.TEXT)
                            array.add(parser.getText());
                    }
                    eventType = parser.next();
                }

                Log.v(PiroConstants.APP_NAME, "Štampanje učitanog niza:");
                for (int i = 0; i < array.size(); i++)
                    Log.v(PiroConstants.APP_NAME, array.get(i));

                return array;

            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {

            HttpURLConnection urlConnection;
            BufferedReader reader;
            String xmlResult;

            try {

                URL url = new URL(PiroConstants.GET_XML);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

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

                xmlResult = buffer.toString();

                //Log.v(mKontekst.getResources().getString(R.string.app_name),
                // "XML string: \n" + xmlRezultat);
                return parseDataFromXML(xmlResult);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            onLoadingComplete(result);
        }

    }

}