package galvatrans.galindra.galva.cecilia.galvatrans.ActivityDetailRute;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.RuteDetail;
import galvatrans.galindra.galva.cecilia.galvatrans.R;

public class ActivityDetailRute extends AppCompatActivity implements ActivityDetailRutePresenter.MainView {

    Dialog dialogLoading;
    Dialog dialogYesNo;
    String nomorOrder;
    String nomorRute;
    String stateActive;
    TextView txtNomorOrder, txtTujuan, txtWaktuSampai, txtWaktuBerangkat, txtNoteAdmin;
    MaterialButton btnAddBiayaTambahan, btnUploadImage, btnBatal, btnProses;

    ActivityDetailRutePresenterImpl activityDetailRutePresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rute);

        activityDetailRutePresenterImpl = new ActivityDetailRutePresenterImpl(this, getApplicationContext());

        Intent intent = getIntent();
        nomorOrder = intent.getStringExtra("nomorOrder");
        nomorRute = intent.getStringExtra("nomorRute");

        createLoadingDialog();
        initLayout();
        onGetDetailRute();
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

    private void initLayout() {
        txtNomorOrder = findViewById(R.id.txtNomorOrder);
        txtTujuan = findViewById(R.id.txtTujuan);
        txtWaktuBerangkat = findViewById(R.id.txtWaktuBerangkat);
        txtWaktuSampai = findViewById(R.id.txtWaktuSampai);
        txtNoteAdmin = findViewById(R.id.txtNoteAdmin);

        btnAddBiayaTambahan = findViewById(R.id.btnAddBiayaTambahan);
        btnAddBiayaTambahan.setOnClickListener(v -> {

        });

        btnUploadImage = findViewById(R.id.btnUploadImage);
        btnUploadImage.setOnClickListener(v -> {

        });

        btnBatal = findViewById(R.id.btnBatalkan);
        btnBatal.setOnClickListener(v -> {

        });

        btnProses = findViewById(R.id.btnProses);
        btnProses.setOnClickListener(v -> {
            switch (stateActive) {
                case "BERANGKAT":
                    Toast.makeText(this, stateActive, Toast.LENGTH_SHORT).show();

                    break;

                case "SAMPAI":
                    Toast.makeText(this, stateActive, Toast.LENGTH_SHORT).show();

                    break;
            }
        });
    }

    private void onGetDetailRute() {
        dialogLoading.show();

        activityDetailRutePresenterImpl.onGetDetailRute(nomorOrder, nomorRute);
    }

    @Override
    public void onGetDetailRuteSuccess(List<RuteDetail> ruteDetails) {
        setToLayout(ruteDetails.get(0));
    }

    private void setToLayout(RuteDetail ruteDetail) {
        txtNomorOrder.setText(ruteDetail.getIdRute());
        txtTujuan.setText(ruteDetail.getTujuan());
        txtWaktuBerangkat.setText(ruteDetail.getBerangkat());
        txtWaktuSampai.setText(ruteDetail.getSampai());
        txtNoteAdmin.setText(ruteDetail.getNoteAdmin());

        if (ruteDetail.getBerangkat().equals("") && ruteDetail.getSampai().equals("")) {
            stateActive = "BERANGKAT";
            btnProses.setText("ANTAR");
            btnProses.setEnabled(true);
        } else if (!ruteDetail.getBerangkat().equals("") && ruteDetail.getSampai().equals("")) {
            stateActive = "SAMPAI";
            btnProses.setText("SELESAI");
            btnProses.setEnabled(true);
        } else {
            stateActive = "SELESAI";
            btnProses.setText("SELESAI");
            btnProses.setEnabled(false);
        }

        dialogLoading.dismiss();
    }

    @Override
    public void onGetDetailRuteFailed() {
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
                    onGetDetailRute();

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
