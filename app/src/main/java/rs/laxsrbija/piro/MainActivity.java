package rs.laxsrbija.piro;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_icon);

        DataLoadTask.displayStoredData(this);
        new DataLoadTask(MainActivity.this, DataLoadTask.MODE_REGULAR).execute();

        ((SwipeRefreshLayout) findViewById(R.id.swipeContainer)).setOnRefreshListener(this);
        PiroOnClickListener.setOnClickListener(MainActivity.this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onRefresh() {
        new DataLoadTask(this, DataLoadTask.MODE_FORCE).execute();
    }

}