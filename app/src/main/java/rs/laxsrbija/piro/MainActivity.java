package rs.laxsrbija.piro;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataLoadTask.displayStoredData(this);
        new DataLoadTask(this, DataLoadTask.MODE_REGULAR).execute();

        ((SwipeRefreshLayout) findViewById(R.id.swipeContainer)).setOnRefreshListener(this);

    }

    @Override
    public void onRefresh() {
        new DataLoadTask(this, DataLoadTask.MODE_FORCE).execute();
    }

}