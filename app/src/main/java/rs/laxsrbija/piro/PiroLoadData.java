package rs.laxsrbija.piro;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by LAX on 8.2.2016..
 * Project piro-android
 */

public class PiroLoadData {

    public static StringRequest reqestWithoutResponse(String requestURL) {
        return new StringRequest(Request.Method.GET, requestURL,
                null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(PiroConstants.APP_NAME, "Nije moguće poslati zahtev!");
            }
        });
    }

    public static void loadData(AppCompatActivity context) {

        Log.v(PiroConstants.APP_NAME, "Pokrecem loadData...");

        (new PiroLoadDataTask(PiroConstants.GET_LED_RIGHT, context.findViewById(R.id.ledRight), 1))
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        (new PiroLoadDataTask(PiroConstants.GET_LED_LEFT, context.findViewById(R.id.ledLeft), 1))
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        (new PiroLoadDataTask(PiroConstants.GET_LED_MAIN, context.findViewById(R.id.ledMain), 1))
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }
}