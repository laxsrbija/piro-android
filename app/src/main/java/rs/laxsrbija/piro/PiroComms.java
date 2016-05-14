package rs.laxsrbija.piro;

import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by LAX on 8.2.2016.
 * Project piro-android
 */

public class PiroComms {

    private static RequestQueue requestQueue;
    private AppCompatActivity context;

    public PiroComms() {}

    public void initialize(AppCompatActivity context) {
        requestQueue = Volley.newRequestQueue(context);
        this.context = context;
    }

    public AppCompatActivity getContext() {
        return context;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue != null)
            return requestQueue;
        else
            throw new IllegalStateException("Niz zahteva nije inicijalizovan!");
    }

}
