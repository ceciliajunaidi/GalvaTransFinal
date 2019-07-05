package galvatrans.galindra.galva.cecilia.galvatrans.MainActivity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.Rute;
import galvatrans.galindra.galva.cecilia.galvatrans.R;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.MainView {
    String kodeDriver = "20141464";
    MainActivityPresenterImpl mainActivityPresenterImpl;
    RecyclerView rvRute;
    TextView txtNoRute;
    MainActivityAdapter mainActivityAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Rute> ruteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityPresenterImpl = new MainActivityPresenterImpl(this, getApplicationContext());
        initLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();

        swipeRefreshLayout.setOnRefreshListener(this::onGetDataRute);
        swipeRefreshLayout.post(this::onGetDataRute);
    }

    private void initLayout() {
        txtNoRute = findViewById(R.id.txtNoRute);

        swipeRefreshLayout = findViewById(R.id.swipeLayout);

        rvRute = findViewById(R.id.rvRute);
        rvRute.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvRute.setHasFixedSize(true);

    }

    private void onGetDataRute(){
        ruteList.clear();
        txtNoRute.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);

        if (mainActivityAdapter != null) {
            mainActivityAdapter.notifyDataSetChanged();
        }

        mainActivityPresenterImpl.onGetDataRute(kodeDriver);
    }

    @Override
    public void onGetDataRuteSuccess(List<Rute> ruteList) {
        this.ruteList = ruteList;

        mainActivityAdapter = new MainActivityAdapter(MainActivity.this, this.ruteList);
        rvRute.setAdapter(mainActivityAdapter);

        if (ruteList.size() == 0){
            txtNoRute.setVisibility(View.VISIBLE);
        } else {
            txtNoRute.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGetDataRuteFailed() {
        Toast.makeText(getApplicationContext(),"ERROR", Toast.LENGTH_SHORT).show();
    }
}

