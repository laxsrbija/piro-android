package rs.laxsrbija.piro;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Lazar on 11.8.2016..
 * Učitavanje JSON podataka sa servera
 */

public class DataLoadTask extends AsyncTask<Void, Void, Void> {

    // Tipovi zahteva koji se upućuju serveru.
    // Može se zatražiti redovno ili trenutno ažuriranje vremenske prognoze.
    public static final String MODE_REGULAR = "android";
    public static final String MODE_FORCE = "force";

    private Activity mContext;
    private String mRequestType;

    public DataLoadTask(Activity mContext, String mRequestType) {
        this.mContext = mContext;
        this.mRequestType = mRequestType;
    }

    @Override
    protected Void doInBackground(Void... params) {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(PiroContract.SERVER_SCHEME)
                .authority(PiroContract.SERVER_ADDRESS2)
                .appendPath(PiroContract.PIRO_DIR)
                .appendPath(PiroContract.QUERY_DIR)
                .appendPath(PiroContract.QUERY_TARGET)
                .appendQueryParameter(PiroContract.FUNCTION, PiroContract.GET_JSON_FUNCTION)
                .appendQueryParameter(PiroContract.ARGUMENT, mRequestType)
                .build();

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                builder.toString(),
                (String) null, listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(mContext).add(objectRequest);

        return null;

    }

    private Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            new SaveDataTask().execute(response);
        }
    };

    public class SaveDataTask extends AsyncTask<JSONObject, Void, Void> {

        @Override
        protected Void doInBackground(JSONObject... params) {

            SharedPreferences sharedPref = mContext.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            try {
                saveData(editor, params[0], mContext.getResources());
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                editor.apply();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            displayStoredData(mContext);
        }

        private void saveData(SharedPreferences.Editor editor, JSONObject json, Resources resources)
                throws JSONException {

            // Čuvanje vrednosti releja
            editor.putInt(resources.getString(R.string.data_relay_led_center_key),
                    json.getInt(PiroContract.JSON.RELAY_LED_CENTER));
            editor.putInt(resources.getString(R.string.data_relay_led_right_key),
                    json.getInt(PiroContract.JSON.RELAY_LED_RIGHT));
            editor.putInt(resources.getString(R.string.data_relay_led_left_key),
                    json.getInt(PiroContract.JSON.RELAY_LED_LEFT));
            editor.putInt(resources.getString(R.string.data_relay_pc_key),
                    json.getInt(PiroContract.JSON.RELAY_PC));

            // Čuvanje vrednosti grejnog tela
            editor.putInt(resources.getString(R.string.data_heater_status_key),
                    json.getInt(PiroContract.JSON.HEATER_STATUS));
            editor.putFloat(resources.getString(R.string.data_heater_temp_key),
                    (float)json.getDouble(PiroContract.JSON.HEATER_TEMP));
            editor.putInt(resources.getString(R.string.data_heater_mode_key),
                    json.getInt(PiroContract.JSON.HEATER_MODE));

            // Čuvanje vrednosti vremenske prognoze
            editor.putString(resources.getString(R.string.data_weather_city_key),
                    json.getString(PiroContract.JSON.WEATHER_CITY));
            editor.putInt(resources.getString(R.string.data_weather_current_temp_key),
                    json.getInt(PiroContract.JSON.WEATHER_CURRENT_TEMP));
            editor.putString(resources.getString(R.string.data_weather_current_conditions_key),
                    json.getString(PiroContract.JSON.WEATHER_CURRENT_CONDITIONS));
            editor.putString(resources.getString(R.string.data_weather_current_icon_key),
                    json.getString(PiroContract.JSON.WEATHER_CURRENT_ICON));
            editor.putInt(resources.getString(R.string.data_weather_current_precipitation_key),
                    json.getInt(PiroContract.JSON.WEATHER_CURRENT_PRECIPITATION));
            editor.putString(resources.getString(R.string.data_weather_current_visibility_key),
                    json.getString(PiroContract.JSON.WEATHER_CURRENT_VISIBILITY));
            editor.putInt(resources.getString(R.string.data_weather_current_feels_like_key),
                    json.getInt(PiroContract.JSON.WEATHER_CURRENT_FEELS_LIKE));
            editor.putInt(resources.getString(R.string.data_weather_current_uv_key),
                    json.getInt(PiroContract.JSON.WEATHER_CURRENT_UV));
            editor.putString(resources.getString(R.string.data_weather_daily_conditions_key),
                    json.getString(PiroContract.JSON.WEATHER_DAILY_CONDITIONS));
            editor.putInt(resources.getString(R.string.data_weather_daily_high_key),
                    json.getInt(PiroContract.JSON.WEATHER_DAILY_HIGH));
            editor.putInt(resources.getString(R.string.data_weather_daily_low_key),
                    json.getInt(PiroContract.JSON.WEATHER_DAILY_LOW));
            editor.putString(resources.getString(R.string.data_weather_daily_icon_key),
                    json.getString(PiroContract.JSON.WEATHER_DAILY_ICON));

            // Čuvanje informacija o sistemu
            editor.putInt(resources.getString(R.string.data_system_load_key),
                    json.getInt(PiroContract.JSON.SYSTEM_LOAD));
            editor.putInt(resources.getString(R.string.data_system_temp_key),
                    json.getInt(PiroContract.JSON.SYSTEM_TEMP));
            editor.putInt(resources.getString(R.string.data_system_uptime_key),
                    json.getInt(PiroContract.JSON.SYSTEM_UPTIME));

        }
    }

    public static void displayStoredData(Activity context) {
        Resources resources = context.getResources();
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);

        String notAvailable = resources.getString(R.string.not_available);

        String data = sharedPref.getString(context.getString(R.string.data_weather_city_key), null);
        if (data == null) {
            Log.v(PiroContract.APP_NAME, "Shared Preferences not set!");
            return;
        }

        TextView weatherCity = (TextView) context.findViewById(R.id.city);
        weatherCity.setText(sharedPref.getString(context.getString(R.string.data_weather_city_key),
                notAvailable));

        TextView weatherCurrentTemp = (TextView) context.findViewById(R.id.currentTemperature);
        weatherCurrentTemp.setText(resources.getString(R.string.format_temp,
                sharedPref.getInt(context.getString(R.string.data_weather_current_temp_key), -1)));

        TextView weatherCurrentConditions = (TextView) context.findViewById(R.id.currentConditions);
        weatherCurrentConditions.setText(sharedPref.getString(
                context.getString(R.string.data_weather_current_conditions_key), notAvailable));

        ImageView weatherCurrentIcon = (ImageView) context.findViewById(R.id.currentTemperatureImg);
        String currentIcon = sharedPref.getString(context.getString(
                R.string.data_weather_current_icon_key), null);
        Glide.with(context).load(resources.getIdentifier(
                currentIcon, "drawable", context.getPackageName())).into(weatherCurrentIcon);

        TextView weatherCurrentPrecipitation = (TextView) context.findViewById(R.id.precipitation);
        String precipitation = resources.getString(R.string.format_percent,
                sharedPref.getInt(context.getString(
                        R.string.data_weather_current_precipitation_key), -1));
        weatherCurrentPrecipitation.setText(precipitation);

        TextView weatherCurrentVisibility = (TextView) context.findViewById(R.id.visibility);
        weatherCurrentVisibility.setText(sharedPref.getString(
                context.getString(R.string.data_weather_current_visibility_key), notAvailable));

        TextView weatherCurrentFeelsLike = (TextView) context.findViewById(R.id.feelsLike);
        weatherCurrentFeelsLike.setText(resources.getString(R.string.format_temp,
                sharedPref.getInt(context.getString(
                        R.string.data_weather_current_feels_like_key), -1)));

        // TODO Pronaći lokaciju UV indeksa
        // TextView weatherCurrentUvIndex = (TextView) context.findViewById(R.id.

        TextView weatherDailyConditions = (TextView) context.findViewById(R.id.dailyConditions);
        weatherDailyConditions.setText(sharedPref.getString(
                context.getString(R.string.data_weather_daily_conditions_key), notAvailable));

        TextView weatherDailyTemp = (TextView) context.findViewById(R.id.dailyTemperature);
        int high = sharedPref.getInt(context.getString(R.string.data_weather_daily_high_key), -1);
        int low = sharedPref.getInt(context.getString(R.string.data_weather_daily_low_key), -1);
        weatherDailyTemp.setText(resources.getString(R.string.format_daily_temp, high, low));

        ImageView weatherDailyIcon = (ImageView) context.findViewById(R.id.dailyTemperatureImg);
        String dailyIcon = sharedPref.getString(context.getString(
                R.string.data_weather_daily_icon_key), null);
        Glide.with(context).load(resources.getIdentifier(
                dailyIcon, "drawable", context.getPackageName())).into(weatherDailyIcon);

        ToggleButton relayLedCenter = (ToggleButton) context.findViewById(R.id.ledMain);
        relayLedCenter.setTextColor(getToggleButtonColor(context,
                sharedPref.getInt(context.getString(R.string.data_relay_led_center_key), -1)));

        ToggleButton relayLedRight = (ToggleButton) context.findViewById(R.id.ledRight);
        relayLedRight.setTextColor(getToggleButtonColor(context,
                sharedPref.getInt(context.getString(R.string.data_relay_led_right_key), -1)));

        ToggleButton relayLedLeft = (ToggleButton) context.findViewById(R.id.ledLeft);
        relayLedLeft.setTextColor(getToggleButtonColor(context,
                sharedPref.getInt(context.getString(R.string.data_relay_led_left_key), -1)));

        ToggleButton relayPc = (ToggleButton) context.findViewById(R.id.pc);
        relayPc.setTextColor(getToggleButtonColor(context,
                sharedPref.getInt(context.getString(R.string.data_relay_pc_key), -1)));

        heaterHelper(context);
        systemHelper(context);
        weatherBackgroundHelper(context);

        ((SwipeRefreshLayout) context.findViewById(R.id.swipeContainer)).setRefreshing(false);

    }

    /**
     * Pomoćna funkcija koja vraća vrednost boje tastera,
     * zavisno od njegovog statusa
     *
     * @param  value vrednost tastera
     */
    private static int getToggleButtonColor(Activity context, int value) {
        if (value == 1) {
            return ContextCompat.getColor(context, R.color.colorAccent);
        }
        return ContextCompat.getColor(context, R.color.white);
    }

    /**
     * Pomoćna funkcija za učitavanje statusa grejnog tela
     */
    private static void heaterHelper(Activity context) {

        Resources resources = context.getResources();
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);

        String temperature;
        int status = sharedPref.getInt(context.getString(R.string.data_heater_status_key), -1);
        if (status == 1) {
            float tmp = sharedPref.getFloat(context.getString(R.string.data_heater_temp_key), -1);

            if (tmp == Math.ceil(tmp)) {
                temperature = resources.getString(R.string.format_temp,
                        sharedPref.getFloat(context.getString(R.string.data_heater_temp_key), -1));
            } else {
                temperature = resources.getString(R.string.format_temp_f,
                        sharedPref.getFloat(context.getString(R.string.data_heater_temp_key), -1));
            }
        } else if (status == 0) {
            temperature = resources.getString(R.string.thermalOff);
        } else {
            // TODO Prikazati odgovarajuće elemente kada peć nije povezana
            temperature = "N/P";
        }

        TextView heaterTemp = (TextView) context.findViewById(R.id.thermalTemp);
        heaterTemp.setText(temperature);

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

        if (status == 1) {
            int mode = sharedPref.getInt(context.getString(R.string.data_heater_mode_key), -1);
            buttons.get(mode).setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                    context.getResources().getIdentifier("mode_".concat(Integer.toString(mode + 1)
                            .concat("_sel")), "drawable", context.getPackageName()), null));
        }

    }

    /**
     * Pomoćna funkcija za učitavanje statusa grejnog tela
     */
    private static void systemHelper(Activity context) {

        Resources resources = context.getResources();
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);

        StringBuilder builder = new StringBuilder();
        builder.append(resources.getString(R.string.systemTemp));
        builder.append(" ");
        builder.append(resources.getString(R.string.format_temp,
                sharedPref.getInt(context.getString(R.string.data_system_temp_key), -1)));

        TextView systemTemp = (TextView) context.findViewById(R.id.systemTemp);
        systemTemp.setText(builder.toString());

        String days = String.valueOf(sharedPref.getInt(
                context.getString(R.string.data_system_uptime_key), -1));
        String param = resources.getString(R.string.systemUptimeToday);
        if (Integer.parseInt(days) > 0) {
            builder = new StringBuilder();

            builder.append(resources.getString(R.string.systemUptime));
            builder.append(" ");
            builder.append(days);
            builder.append(" dan");

            // Logika za računanje sufiksa reči "dan"
            // TODO Napraviti univerzalnu internacionalnu verziju
            if (Integer.parseInt(days) > 0 && (days.charAt(days.length() - 1) != '1'
                    || days.lastIndexOf("11") != -1
                    && days.length() - 2 == days.lastIndexOf("11")))
                builder.append("a");

            param = builder.toString();
        }

        TextView systemUptime = (TextView) context.findViewById(R.id.systemUptime);
        systemUptime.setText(param);

        builder = new StringBuilder();

        builder.append(resources.getString(R.string.systemLoad));
        builder.append(" ");
        builder.append(resources.getString(R.string.format_percent,
                sharedPref.getInt(context.getString(
                        R.string.data_system_load_key), -1)));

        TextView systemLoad = (TextView) context.findViewById(R.id.systemLoad);
        systemLoad.setText(builder.toString());

    }

    /**
     * Pomoćna funkcija za postavljanje pozadine vremenske prognoze
     */
    private static void weatherBackgroundHelper(Activity context) {

        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        ImageView background = (ImageView) context.findViewById(R.id.weather_background);

        String conditions = sharedPref.getString(
                context.getString(R.string.data_weather_current_icon_key), null);

        if (conditions == null)
            return;

        if (conditions.equals("clear") || conditions.equals("sunny")
                || conditions.equals("mostlysunny") || conditions.equals("sunny")) {
            Glide.with(context).load(R.drawable.w_clear).into(background);
        } else if (conditions.equals("mostlycloudy") || conditions.equals("partlycloudy")
                || conditions.equals("partlysunny")) {
            Glide.with(context).load(R.drawable.w_mostlysunny).into(background);
        } else if (conditions.equals("cloudy") || conditions.equals("nt_cloudy")
                || conditions.equals("chancetstorms")) {
            Glide.with(context).load(R.drawable.w_cloudy).into(background);
        } else if (conditions.equals("fog") || conditions.equals("hazy")
                || conditions.equals("nt_fog") || conditions.equals("nt_hazy")) {
            Glide.with(context).load(R.drawable.w_fog).into(background);
        } else if (conditions.equals("nt_clear") || conditions.equals("nt_sunny")
                || conditions.equals("nt_mostlysunny") || conditions.equals("nt_partlysunny")) {
            Glide.with(context).load(R.drawable.w_nt_clear).into(background);
        } else if (conditions.equals("nt_partlycloudy") || conditions.equals("nt_mostlycloudy")) {
            Glide.with(context).load(R.drawable.w_nt_cloudy).into(background);
        } else if (conditions.equals("chancerain") || conditions.equals("nt_rain")
                || conditions.equals("nt_chancerain") || conditions.equals("rain")) {
            Glide.with(context).load(R.drawable.w_rain).into(background);
        } else if (conditions.equals("chanceflurries") || conditions.equals("chancesleet")
                || conditions.equals("chancesnow") || conditions.equals("flurries")
                || conditions.equals("nt_chanceflurries") || conditions.equals("nt_chancesleet")
                || conditions.equals("nt_flurries") || conditions.equals("nt_snow")
                || conditions.equals("snow") || conditions.equals("sleet")
                || conditions.equals("nt_sleet")) {
            Glide.with(context).load(R.drawable.w_snow).into(background);
        } else if (conditions.equals("nt_chancetstorms") || conditions.equals("nt_tstorms")
                || conditions.equals("tstorms")) {
            Glide.with(context).load(R.drawable.w_tstorms).into(background);
        } else {
            Log.e(PiroContract.APP_NAME, "Unable to assign background: " + conditions);
        }


    }

}
