package rs.laxsrbija.piro;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by LAX on 3.4.2016.
 * Project piro-android
 */
public class PiroRequestSender {

    public static final StringRequest reqestWithoutResponse(String requestURL) {
        return new StringRequest(Request.Method.GET, requestURL,
                null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(PiroConstants.APP_NAME, "Nije moguće poslati zahtev!");
            }
        });
    }

}
