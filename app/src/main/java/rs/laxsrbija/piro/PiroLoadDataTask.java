package rs.laxsrbija.piro;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by LAX on 7.4.2016..
 * Project piro-android
 *
 * type: 1 - Switch
 */
public class PiroLoadDataTask extends AsyncTask<Void, Void, String> {

    protected URL url;
    protected View view;
    protected int type;

    public PiroLoadDataTask(String url, View view, int type) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.view = view;
        this.type = type;
    }

    @Override
    protected String doInBackground(Void... params) {
        return loadDataFromURL();
    }

    @Override
    protected void onPostExecute(String result) {

        switch (type) {
            case 1:
                PiroLoadDataUIThread.runOnUIThreadSwitch(view, result);
                break;
        }

    }

    protected String loadDataFromURL() {

        HttpURLConnection urlConnection = null;
        StringBuilder builder;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            builder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null)
                builder.append(line);

            if (builder.length() == 0)
                return null;

            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        return null;
    }
}
