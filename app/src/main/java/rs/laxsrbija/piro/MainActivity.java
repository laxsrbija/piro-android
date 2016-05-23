package rs.laxsrbija.piro;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PiroComms mComms = new PiroComms();
        mComms.initialize(this);

        PiroLoadData.loadData(this);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        if (refreshLayout != null)
            refreshLayout.setOnRefreshListener(this);

        PiroOnClickListenerSetter.setOnClickListener(this, mComms);

    }

    @Override
    public void onRefresh() {
        PiroLoadDataTask task =  new PiroLoadDataTask(PiroConstants.GET_JSON_FORCE, this);

        task.setRefreshLayout(refreshLayout);
        task.execute();
    }
}