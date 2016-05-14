package rs.laxsrbija.piro;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

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
 */

public class PiroLoadDataTask extends AsyncTask<Void, Void, JSONObject> {

    protected URL url;
    protected AppCompatActivity context;
    protected SwipeRefreshLayout refreshLayout;

    public PiroLoadDataTask(String url, AppCompatActivity context) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.context = context;
        refreshLayout = null;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        HttpURLConnection urlConnection = null;
        StringBuilder stringBuilder;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line);

            if (stringBuilder.length() == 0)
                return null;

            return new JSONObject(stringBuilder.toString());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONObject result) {

        try {

            PiroLoadDataUIThread.runOnUIThreadSwitch(context.findViewById(R.id.ledMain),
                    result.getString("ledCentar"));
            PiroLoadDataUIThread.runOnUIThreadSwitch(context.findViewById(R.id.ledRight),
                    result.getString("ledDesno"));
            PiroLoadDataUIThread.runOnUIThreadSwitch(context.findViewById(R.id.ledLeft),
                    result.getString("ledLevo"));

            PiroLoadDataUIThread.runOnUIThreadSwitch(context.findViewById(R.id.pc),
                    result.getString("racunar"));

            PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.city),
                    result.getString("grad"));
            PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.day),
                    result.getString("dan"));

            PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.currentTemperature),
                    result.getString("trenutnaTemperatura").concat("°"));
            PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.currentConditions),
                    result.getString("trenutnaStanje"));
            PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.uv),
                    result.getString("uvIndeks"));
            PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.precipitation),
                    result.getString("padavine").concat("%"));
            PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.visibility),
                    result.getString("vidljivost"));
            PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.feelsLike),
                    result.getString("subjektivniOsecaj").concat("°"));
            PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.dailyConditions),
                    result.getString("dnevnaStanje"));
            PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.dailyMin),
                    result.getString("dnevnaMax").concat("°"));
            PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.dailyMax),
                    result.getString("dnevnaMin").concat("°"));

            PiroLoadDataUIThread.runOnUIThreadImageView(context, context.findViewById(R.id.currentTemperatureImg),
                    PiroConstants.WEATHER_ICONS.concat(result.getString("trenutnaIkona")).concat(".png"));
            PiroLoadDataUIThread.runOnUIThreadImageView(context, context.findViewById(R.id.dailyTemperatureImg),
                    PiroConstants.WEATHER_ICONS.concat(result.getString("dnevnaIkona")).concat(".png"));

            thermalStatusBuilder(result);
            systemStatusBuilder(result);

            if (refreshLayout != null)
                refreshLayout.setRefreshing(false);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void systemStatusBuilder(JSONObject result) {
        try {

            StringBuilder builder = new StringBuilder();

            builder.append(context.getResources().getString(R.string.systemTemp));
            builder.append(" ");
            builder.append(result.getString("systemTemperature"));
            builder.append("°");

            PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.systemTemp),
                    builder.toString());

            String days = result.getString("systemUptime");
            String param = context.getResources().getString(R.string.systemUptimeToday);
            if (Integer.parseInt(days) > 0) {
                builder = new StringBuilder();

                builder.append(context.getResources().getString(R.string.systemUptime));
                builder.append(" ");
                builder.append(days);
                builder.append(" dan");

                if (Integer.parseInt(days) > 0 && (days.charAt(days.length() - 1) != '1'
                        || days.lastIndexOf("11") != -1
                        && days.length() - 2 == days.lastIndexOf("11")))
                    builder.append("a");

                param = builder.toString();
            }

            PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.systemUptime), param);

            builder = new StringBuilder();

            builder.append(context.getResources().getString(R.string.systemLoad));
            builder.append(" ");
            builder.append(result.getString("systemLoad"));
            builder.append("%");

            PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.systemLoad),
                    builder.toString());

        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    protected void thermalStatusBuilder(JSONObject result) {

        try {

            String res = result.getString("temperaturaPeci").concat("°");
            if ("0".equals(result.getString("statusPeci")))
                res = context.getResources().getString(R.string.thermalOff);

            PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.thermalTemp), res);

            PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.thermalMode),
                    result.getString("rezimPeci"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void setRefreshLayout(SwipeRefreshLayout layout) {
        refreshLayout = layout;
    }
}