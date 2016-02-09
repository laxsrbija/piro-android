package rs.laxsrbija.piro;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by LAX on 8.2.2016..
 */

public class Komunikacija {

    private static RequestQueue requestQueue;

    public Komunikacija() {}

    public void inicijalizuj(Context kontekst) {
        requestQueue = Volley.newRequestQueue(kontekst);
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue != null)
            return requestQueue;
        else
            throw new IllegalStateException("Niz zahteva nije inicijalizovan!");
    }

}
