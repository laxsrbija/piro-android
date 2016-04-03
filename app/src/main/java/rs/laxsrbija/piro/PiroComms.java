package rs.laxsrbija.piro;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by LAX on 8.2.2016.
 * Project piro-android
 */

public class PiroComms {

    private static RequestQueue requestQueue;

    public PiroComms() {}

    public void initialize(Context kontekst) {
        requestQueue = Volley.newRequestQueue(kontekst);
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue != null)
            return requestQueue;
        else
            throw new IllegalStateException("Niz zahteva nije inicijalizovan!");
    }

}
