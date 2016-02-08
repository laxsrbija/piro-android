package rs.laxsrbija.piro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class GlavnaAktivnost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavna);

        final Switch ledDesno = (Switch) findViewById(R.id.switch1);
        final RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.2/piro/rpi/piro-querry.php?f=getRelayStatus&arg=1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("PIRO", "Odgovor desne LED rasvete: " + response);
                        ((Switch)findViewById(R.id.switch1)).setChecked(response.equals("1"));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

        ledDesno.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(posaljiZahtevBezOdgovora("http://192.168.1.2/piro/rpi/piro-querry.php?f=toggleRelay&arg=1"));
                Log.v("PIRO", "Izvršen zahtev desne LED rasvete!");
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