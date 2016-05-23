package rs.laxsrbija.piro;

import android.os.AsyncTask;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
        if (result != null)
            try {

                PiroLoadDataUIThread.runOnUIThreadSwitch(context, context.findViewById(R.id.ledMain),
                        result.getString("ledCentar"));
                PiroLoadDataUIThread.runOnUIThreadSwitch(context, context.findViewById(R.id.ledRight),
                        result.getString("ledDesno"));
                PiroLoadDataUIThread.runOnUIThreadSwitch(context, context.findViewById(R.id.ledLeft),
                        result.getString("ledLevo"));

                PiroLoadDataUIThread.runOnUIThreadSwitch(context, context.findViewById(R.id.pc),
                        result.getString("racunar"));

                PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.city),
                        result.getString("grad"));
                PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.day),
                        result.getString("dan"));

                PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.currentTemperature),
                        result.getString("trenutnaTemperatura").concat("°"));
                PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.currentConditions),
                        result.getString("trenutnaStanje"));
                /* TODO: Find a place to display a UV value
                PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.uv),
                        result.getString("uvIndeks"));*/
                PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.precipitation),
                        result.getString("padavine").concat("%"));
                PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.visibility),
                        result.getString("vidljivost"));
                PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.feelsLike),
                        result.getString("subjektivniOsecaj").concat("°"));
                PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.dailyConditions),
                        result.getString("dnevnaStanje"));
                PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.dailyMin),
                        result.getString("dnevnaMin").concat("°"));
                PiroLoadDataUIThread.runOnUIThreadTextView(context.findViewById(R.id.dailyMax),
                        result.getString("dnevnaMax").concat("°"));

                PiroLoadDataUIThread.runOnUIThreadImageView(context, context.findViewById(R.id.currentTemperatureImg),
                        result.getString("trenutnaIkona"));
                PiroLoadDataUIThread.runOnUIThreadImageView(context, context.findViewById(R.id.dailyTemperatureImg),
                        result.getString("dnevnaIkona"));

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

            ArrayList<ImageButton> buttons = new ArrayList<>();
            buttons.add((ImageButton) context.findViewById(R.id.modeAuto));
            buttons.add((ImageButton) context.findViewById(R.id.modeManual));
            buttons.add((ImageButton) context.findViewById(R.id.modeDay));
            buttons.add((ImageButton) context.findViewById(R.id.modeNight));
            buttons.add((ImageButton) context.findViewById(R.id.modeFrost));

            for (int i = 0; i < buttons.size(); ++i) {
                buttons.get(i).setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                        context.getResources().getIdentifier("mode_".concat(Integer.toString(i + 1)),
                                "drawable", context.getPackageName()), null));
            }

            if ("1".equals(result.getString("statusPeci"))) {
                int mode = Integer.valueOf(result.getString("rezimPeci"));
                buttons.get(mode).setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                        context.getResources().getIdentifier("mode_".concat(Integer.toString(mode + 1)
                                .concat("_sel")), "drawable", context.getPackageName()), null));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void setRefreshLayout(SwipeRefreshLayout layout) {
        refreshLayout = layout;
    }
}