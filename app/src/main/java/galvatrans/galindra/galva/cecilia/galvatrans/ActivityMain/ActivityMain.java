package galvatrans.galindra.galva.cecilia.galvatrans.ActivityMain;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.ActivityListRute.ActivityListRute;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.Rute;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.SessionManager;
import galvatrans.galindra.galva.cecilia.galvatrans.R;

public class ActivityMain extends AppCompatActivity implements ActivityMainPresenter.MainView {

    String username;

    ActivityMainPresenterImpl activityMainPresenterImpl;
    List<Rute> ruteList = new ArrayList<>();

    Dialog dialogLoading;
    Dialog dialogYesNo;
    RecyclerView rvRute;
    TextView txtNoRute;
    ActivityMainAdapter activityMainAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    SessionManager sessionManager;
    HashMap<String, String> sessionUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainPresenterImpl = new ActivityMainPresenterImpl(this, getApplicationContext());

        sessionManager = new SessionManager(getApplicationContext());
        sessionUser = sessionManager.getUserDetails();
        username = sessionUser.get(SessionManager.KEY_USERNAME);

        initLayout();
        createLoadingDialog();
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
        activityMainAdapter = new ActivityMainAdapter(this, this.ruteList, ruteClicked -> {
            if (ruteClicked.getStatus().equals("Belum Diantar")){
                int onProgressActive = 0;
                for (Rute rute : ruteList){
                    if (rute.getStatus().equals("Sedang Diantar")){
                        onProgressActive = onProgressActive + 1;

                    }
                }

                if (onProgressActive > 0) {
                    Toast.makeText(this, "Selesaikan order berlangsung", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intentListRute = new Intent(this, ActivityListRute.class);
                    intentListRute.putExtra("nomorOrder", ruteClicked.getNoRute());

                    startActivity(intentListRute);

                }
            } else {
                Intent intentListRute = new Intent(this, ActivityListRute.class);
                intentListRute.putExtra("nomorOrder", ruteClicked.getNoRute());

                startActivity(intentListRute);
            }
        });
        rvRute.setAdapter(activityMainAdapter);
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

    private void onGetDataRute() {
        ruteList.clear();
        txtNoRute.setVisibility(View.GONE);
        dialogLoading.show();
        swipeRefreshLayout.setRefreshing(false);

        if (activityMainAdapter != null) {
            activityMainAdapter.notifyDataSetChanged();
        }

        activityMainPresenterImpl.onGetDataRute(username);
    }

    @Override
    public void onGetDataRuteSuccess(List<Rute> ruteList) {
        if (ruteList.size() == 0) {
            txtNoRute.setVisibility(View.VISIBLE);
        } else {
            txtNoRute.setVisibility(View.GONE);

            for (Rute rute : ruteList){
                if (rute.getMulai().equals("") && rute.getSelesai().equals("")){
                    rute.setStatus("Belum Diantar");
                } else if (!rute.getMulai().equals("") && rute.getSelesai().equals("")){
                    rute.setStatus("Sedang Diantar");
                } else {
                    rute.setStatus("Selesai");
                }
                this.ruteList.add(rute);
            }
            activityMainAdapter.notifyDataSetChanged();
        }

        dialogLoading.dismiss();
    }

    @Override
    public void onGetDataRuteFailed() {
        dialogLoading.dismiss();

        showYesOrNoDialog("Gagal mengambil data",
                "Ulagi", "Keluar", "ON_GET_LIST_RUTE_FAILED");
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
                    onGetDataRute();

                    break;
            }
        });

        btnNo.setText(buttonNo);
        btnNo.setOnClickListener(listener -> {
            dialogYesNo.dismiss();

            switch (stateActive) {
                case "ON_GET_LIST_RUTE_FAILED":
                    this.finish();

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

