package rs.laxsrbija.piro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    PiroComms mComms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mComms = new PiroComms();
        mComms.initialize(this);

        PiroLoadData.loadData(this);

        PiroOnClickListeners.setOnClickListener(this, mComms);

    }

}