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

public class MainActivity extends AppCompatActivity {

    Switch ledRight;
    PiroComms mComms;
    PiroLoadData mLoadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ledRight = (Switch) findViewById(R.id.ledRight);

        mComms = new PiroComms();
        mComms.initialize(this);

        mLoadData = new PiroLoadData(this);

        ledRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mComms.getRequestQueue()
                        .add(PiroRequestSender.reqestWithoutResponse(PiroConstants.SET_LED_LIGHT));
                Log.v(PiroConstants.APP_NAME, "Izvršen zahtev desne LED rasvete!");
            }
        });

    }

}