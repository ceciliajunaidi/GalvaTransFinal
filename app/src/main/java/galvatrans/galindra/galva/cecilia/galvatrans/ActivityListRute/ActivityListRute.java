package galvatrans.galindra.galva.cecilia.galvatrans.ActivityListRute;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.ActivityDetailRute.ActivityDetailRute;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.Rute;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.RuteDetail;
import galvatrans.galindra.galva.cecilia.galvatrans.R;

public class ActivityListRute extends AppCompatActivity implements ActivityListRutePresenter.MainView {

    String nomorOrder;
    String kodeMobil;
    String kodeSopir;

    ActivityListRutePresenterImpl activityListRutePresenterImpl;
    ActivityListRuteAdapter activityListRuteAdapter;

    Dialog dialogLoading;
    Dialog dialogYesNo;
    RecyclerView rvListRute;
    SwipeRefreshLayout swipeRefreshLayout;
    List<RuteDetail> ruteDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_rute);

        activityListRutePresenterImpl = new ActivityListRutePresenterImpl(this, getApplicationContext());
        Intent intent = getIntent();
        nomorOrder = intent.getStringExtra("nomorOrder");
        kodeMobil = intent.getStringExtra("kodeMobil");
        kodeSopir = intent.getStringExtra("kodeSopir");

        createLoadingDialog();
        initLayout();
    }

    private void initLayout() {
        swipeRefreshLayout = findViewById(R.id.swipeLayout);

        rvListRute = findViewById(R.id.rvRute);
        rvListRute.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvListRute.setHasFixedSize(true);
        activityListRuteAdapter = new ActivityListRuteAdapter(this, this.ruteDetails, ruteDetailClicked -> {
            if (ruteDetailClicked.getStatus().equals("Belum Diantar")){
                int onProgressActive = 0;
                for (RuteDetail rute : ruteDetails){
                    if (rute.getStatus().equals("Sedang Diantar")){
                        onProgressActive = onProgressActive + 1;
                    }
                }

                if (onProgressActive > 0) {
                    Toast.makeText(this, "Selesaikan order berlangsung", Toast.LENGTH_SHORT).show();
                } else {
                    if (ruteDetailClicked.getRute().equals("100") && ruteDetails.size() > 1){
                        Toast.makeText(this, "Order belum selesai", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intentListRute = new Intent(this, ActivityDetailRute.class);
                        intentListRute.putExtra("nomorRute", ruteDetailClicked.getRute());
                        intentListRute.putExtra("nomorOrder", ruteDetailClicked.getIdRute());
                        intentListRute.putExtra("kodeMobil", kodeMobil);
                        intentListRute.putExtra("kodeSopir", kodeSopir);

                        startActivity(intentListRute);
                    }
                }
            } else {
                Intent intentListRute = new Intent(this, ActivityDetailRute.class);
                intentListRute.putExtra("nomorRute", ruteDetailClicked.getRute());
                intentListRute.putExtra("nomorOrder", ruteDetailClicked.getIdRute());
                intentListRute.putExtra("kodeMobil", kodeMobil);
                intentListRute.putExtra("kodeSopir", kodeSopir);

                startActivity(intentListRute);
            }
        });

        rvListRute.setAdapter(activityListRuteAdapter);
    }

    private void createLoadingDialog() {
        dialogLoading = new Dialog(this);
        dialogLoading.setContentView(R.layout.dialog_loading);
        dialogLoading.setCancelable(false);
        dialogLoading.setCanceledOnTouchOutside(false);

        TextView txtLoading = dialogLoading.findViewById(R.id.txtLoading);
        txtLoading.setText(R.string.loadingMessage);

        if (dialogLoading.getWindow() != null) {
            dialogLoading.getWindow().setLayout
                    (LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        swipeRefreshLayout.setOnRefreshListener(this::onGetListRute);
        swipeRefreshLayout.post(this::onGetListRute);
    }

    private void onGetListRute() {
        ruteDetails.clear();
        dialogLoading.show();
        swipeRefreshLayout.setRefreshing(false);

        if (activityListRuteAdapter != null) {
            activityListRuteAdapter.notifyDataSetChanged();
        }

        activityListRutePresenterImpl.onGetListRute(nomorOrder);
    }

    @Override
    public void onGetListRuteSuccess(List<RuteDetail> ruteDetails) {
        for (RuteDetail detail : ruteDetails){
            if (detail.getBerangkat().equals("") && detail.getSampai().equals("")){
                detail.setStatus("Belum Diantar");
            } else if (!detail.getBerangkat().equals("") && detail.getSampai().equals("")){
                detail.setStatus("Sedang Diantar");
            } else {
                detail.setStatus("Selesai");
            }
            this.ruteDetails.add(detail);
        }
        activityListRuteAdapter.notifyDataSetChanged();

        dialogLoading.dismiss();
    }

    @Override
    public void onGetListRuteFailed() {
        dialogLoading.dismiss();

        showYesOrNoDialog("Gagal mengambil data",
                "Ulangi", "Kembali", "ON_GET_LIST_RUTE_FAILED");
    }

    private void showYesOrNoDialog(String message, String buttonYes, String buttonNo, String stateActive) {
        dialogYesNo = new Dialog(this);
        dialogYesNo.setContentView(R.layout.dialog_konfirmasi_yes_or_no);
        dialogYesNo.setCancelable(false);
        dialogYesNo.setCanceledOnTouchOutside(false);

        TextView txtMessage = dialogYesNo.findViewById(R.id.txtMessage);
        Button btnYes = dialogYesNo.findViewById(R.id.btnYes);
        Button btnNo = dialogYesNo.findViewById(R.id.btnNo);

        txtMessage.setText(message);

        btnYes.setText(buttonYes);
        btnYes.setOnClickListener(listener -> {
            dialogYesNo.dismiss();
            switch (stateActive) {
                case "ON_GET_LIST_RUTE_FAILED":
                    onGetListRute();

                    break;
            }
        });

        btnNo.setText(buttonNo);
        btnNo.setOnClickListener(listener -> {
            dialogYesNo.dismiss();

            switch (stateActive) {
                case "ON_GET_LIST_RUTE_FAILED":
                    super.onBackPressed();

                    break;
            }
        });

        dialogYesNo.show();
        if (dialogYesNo.getWindow() != null) {
            dialogYesNo.getWindow().setLayout
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }
}
