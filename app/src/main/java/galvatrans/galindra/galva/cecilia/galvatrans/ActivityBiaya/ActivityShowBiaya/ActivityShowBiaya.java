package galvatrans.galindra.galva.cecilia.galvatrans.ActivityBiaya.ActivityShowBiaya;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import galvatrans.galindra.galva.cecilia.galvatrans.ActivityBiaya.ActivityAddBiaya.ActivityAddBiaya;
import galvatrans.galindra.galva.cecilia.galvatrans.R;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.BiayaEntity;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.Repository;

public class ActivityShowBiaya extends AppCompatActivity implements ActivityShowBiayaPresenter.MainView {

    String namaPerusahaan;
    String nomorOrder;
    String kodeMobil;
    String kodeSopir;

    Toolbar toolbarShowBiaya;
    RecyclerView rvBiaya;
    FloatingActionButton btnAddBiaya;
    ActivityShowBiayaAdapter activityShowBiayaAdapter;

    ActivityShowBiayaPresenterImpl activityShowBiayaPresenterImpl;
    Repository repositoryDatabase;

    List<BiayaEntity> listBiaya = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_biaya);

        Intent intent = getIntent();
        nomorOrder = intent.getStringExtra("nomorOrder");
        namaPerusahaan = intent.getStringExtra("pt");
        kodeMobil = intent.getStringExtra("kodeMobil");
        kodeSopir = intent.getStringExtra("kodeSopir");

        activityShowBiayaPresenterImpl = new ActivityShowBiayaPresenterImpl(this);
        repositoryDatabase = new Repository(this);

        initLayout();
        prepareData();
    }

    private void prepareData() {
        repositoryDatabase.getAllBiaya(nomorOrder).observe(this, biayas -> {
            listBiaya = biayas;

            activityShowBiayaAdapter = new ActivityShowBiayaAdapter(listBiaya, this);
            rvBiaya.setAdapter(activityShowBiayaAdapter);
        });
    }

    private void initLayout() {
        toolbarShowBiaya = findViewById(R.id.toolbarShowBiaya);
        setSupportActionBar(toolbarShowBiaya);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        rvBiaya = findViewById(R.id.rvBiaya);
        rvBiaya.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvBiaya.setLayoutManager(linearLayoutManager);

        btnAddBiaya = findViewById(R.id.btnAddBiaya);
        btnAddBiaya.setOnClickListener(v -> {
            Intent intentAddBiaya = new Intent(this, ActivityAddBiaya.class);
            intentAddBiaya.putExtra("actionBiaya", "add");
            intentAddBiaya.putExtra("pt", namaPerusahaan);
            intentAddBiaya.putExtra("nomorOrder", nomorOrder);
            intentAddBiaya.putExtra("kodeSopir", kodeSopir);
            intentAddBiaya.putExtra("kodeMobil", kodeMobil);

            startActivity(intentAddBiaya);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        prepareData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }
}
