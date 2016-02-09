package rs.laxsrbija.piro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class GlavnaAktivnost extends AppCompatActivity {

    Switch ledDesno;
    Komunikacija mKomunikacija;
    UcitavanjePodataka mUcitavanjePodataka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavna);

        ledDesno = (Switch) findViewById(R.id.ledDesno);

        mKomunikacija = new Komunikacija();
        mKomunikacija.inicijalizuj(this);

        mUcitavanjePodataka = new UcitavanjePodataka(this);

        ledDesno.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mKomunikacija.getRequestQueue()
                        .add(posaljiZahtevBezOdgovora(Konstante.adresaServera + Konstante.setLedDesno));
                Log.v(getResources().getString(R.string.app_name),
                        "Izvršen zahtev desne LED rasvete!");
            }
        });

    }

    public StringRequest posaljiZahtevBezOdgovora(String s) {
        return new StringRequest(Request.Method.GET, s, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(getResources().getString(R.string.app_name),
                        "Nije moguće poslati zahtev!");
            }
        });
    }
}