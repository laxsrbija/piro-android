package com.example.lax.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button);
        final Button button2 = (Button) findViewById(R.id.button1);

        final RequestQueue queue = Volley.newRequestQueue(this);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(posaljiZahtevBezOdgovora("http://192.168.1.2/piro/rpi/piro-querry.php?f=toggleRelay&arg=1"));
                Log.v("PIRO", "Zahtev izvršen!");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(posaljiZahtevBezOdgovora("http://192.168.1.2/piro/rpi/piro-querry.php?f=setMode&arg=2"));
                Log.v("PIRO", "Zahtev izvršen!");
            }
        });

    }

    public StringRequest posaljiZahtevBezOdgovora(String s) {
        return new StringRequest(Request.Method.GET, s, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("PIRO","Nije moguće poslati zahtev!");
            }
        });
    }
}
