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

    PiroComms mComms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mComms = new PiroComms();
        mComms.initialize(this);

        PiroLoadData.loadData(this);

        findViewById(R.id.ledRight).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mComms.getRequestQueue()
                        .add(PiroLoadData.reqestWithoutResponse(PiroConstants.SET_LED_RIGHT));
            }
        });

    }

}