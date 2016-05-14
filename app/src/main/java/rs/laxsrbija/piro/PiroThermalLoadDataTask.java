package rs.laxsrbija.piro;

import android.support.v7.app.AppCompatActivity;

import org.json.JSONObject;

/**
 * Created by LAX on 12.5.2016..
 * Project piro-android
 */
public class PiroThermalLoadDataTask extends PiroLoadDataTask {

    public PiroThermalLoadDataTask(String url, AppCompatActivity context) {
        super(url, context);
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        thermalStatusBuilder(result);
    }
}
