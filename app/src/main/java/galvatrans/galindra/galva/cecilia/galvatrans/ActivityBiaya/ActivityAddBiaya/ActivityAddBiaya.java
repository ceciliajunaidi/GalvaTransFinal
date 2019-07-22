package galvatrans.galindra.galva.cecilia.galvatrans.ActivityBiaya.ActivityAddBiaya;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.ResponseUpdate;
import galvatrans.galindra.galva.cecilia.galvatrans.R;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.BiayaEntity;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.MasterJenisBiayaEntity;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.MasterMobilEntity;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.MasterSatuanEntity;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.Repository;

public class ActivityAddBiaya extends AppCompatActivity implements ActivityAddBiayaPresenter.MainView {

    ActivityAddBiayaPresenterImpl activityAddBiayaPresenterImpl;
    Repository repositoryDatabase;

    List<MasterJenisBiayaEntity> masterJenisBiaya = new ArrayList<>();
    List<MasterSatuanEntity> masterSatuan = new ArrayList<>();
    List<BiayaEntity> biayaExist = new ArrayList<>();

    MasterMobilEntity mobilSelected;
    MasterJenisBiayaEntity biayaSelected;
    MasterSatuanEntity satuanSelected;
    BiayaEntity biayaOnEdit;

    ConstraintLayout layoutAddBiaya;
    Toolbar toolbarAddBiaya;
    TextInputEditText editJenisBiaya;
    TextInputEditText editJumlah;
    TextInputEditText editSatuan;
    TextInputEditText editBiaya;
    TextInputEditText editKilometerAwal;
    TextInputEditText editKilometerAkhir;
    TextInputEditText editKeterangan;
    LinearLayout layoutKilometer;
    LinearLayout layoutJumlahSatuan;
    Button btnSubmit;

    String kodeBiayaSelected;
    String kodeSatuanSelected;

    String actionBiaya;
    String namaPerusahaan;
    String idSuratJalan;
    String kodeMobil;
    String kodeSopir;
    String dbMaster;
    int idBiaya;
    int kmAwalFinal;

    Dialog dialogLoading;
    Dialog dialogYesNo;
    Dialog dialogOk;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_biaya);

        activityAddBiayaPresenterImpl = new ActivityAddBiayaPresenterImpl(this, getApplicationContext());
        repositoryDatabase = new Repository(this);

        intent = getIntent();
        actionBiaya = intent.getStringExtra("actionBiaya");

        dbMaster = "GGMCCMGIN";

        createLoadingDialog();
        initLayout();
        onCheckAction();
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
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    private void initLayout() {
        layoutAddBiaya = findViewById(R.id.layoutAddBiaya);

        toolbarAddBiaya = findViewById(R.id.toolbarAddBiaya);
        setSupportActionBar(toolbarAddBiaya);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        editJenisBiaya = findViewById(R.id.editJenisBiaya);
        editJumlah = findViewById(R.id.editJumlah);
        editSatuan = findViewById(R.id.editSatuan);
        editBiaya = findViewById(R.id.editBiaya);
        editKilometerAwal = findViewById(R.id.editKmAwal);
        editKilometerAkhir = findViewById(R.id.editKmAkhir);
        editKeterangan = findViewById(R.id.editKeterangan);
        layoutKilometer = findViewById(R.id.containerKilometer);
        layoutJumlahSatuan = findViewById(R.id.containerJumlahSatuan);
        btnSubmit = findViewById(R.id.btnSubmitAddBiaya);

        if (actionBiaya.equals("add")) {
            editJenisBiaya.setClickable(true);
            editSatuan.setClickable(true);
        }

        editJenisBiaya.setOnClickListener(v -> {
            AlertDialog.Builder dialogSelectBiaya = new AlertDialog.Builder(this);
            dialogSelectBiaya.setCancelable(true);
            ArrayAdapter<String> jenisBiayaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item);

            for (MasterJenisBiayaEntity biaya : masterJenisBiaya) {
                jenisBiayaAdapter.add(biaya.getJenisBiaya());
            }

            dialogSelectBiaya.setAdapter(jenisBiayaAdapter, (dialog, position) -> {
                kodeBiayaSelected = masterJenisBiaya.get(position).getKodeJenis();
                repositoryDatabase.getJenisBiayaById(kodeBiayaSelected).observe(this, biaya -> {
                    if (biaya != null) {
                        biayaSelected = biaya;

                        editJenisBiaya.setText(biayaSelected.getJenisBiaya());
                        if (!biayaSelected.getKodeSatuan().equals("")) {
                            for (MasterSatuanEntity masterSatuanSelected : masterSatuan) {
                                if (masterSatuanSelected.getKodeSatuan().equals(biayaSelected.getKodeSatuan())) {
                                    kodeSatuanSelected = masterSatuanSelected.getKodeSatuan();
                                    repositoryDatabase.getSatuanById(kodeSatuanSelected).observe(this, satuans -> {
                                        if (satuans != null) {
                                            satuanSelected = satuans;
                                            editSatuan.setText(satuanSelected.getNamaSatuan());
                                            editSatuan.setClickable(false);
                                            editSatuan.setFocusable(false);
                                        }
                                    });
                                }
                            }
                        } else {
                            editSatuan.setText("");
                            editSatuan.setClickable(true);
                            editSatuan.setFocusable(true);
                        }

                        if (biayaSelected.getStatusKm()) {
                            layoutJumlahSatuan.setVisibility((View.VISIBLE));
                            editJumlah.setText("");
                            editSatuan.setText("");

                            onGetKmAwal();
                        } else {
                            layoutJumlahSatuan.setVisibility(View.GONE);
                            satuanSelected = new MasterSatuanEntity();

                            layoutKilometer.setVisibility(View.GONE);
                            editKilometerAwal.setText("0");
                            editKilometerAkhir.setText("0");

                            editJumlah.setText("0");
                            editSatuan.setText("-");
                        }
                    }
                });
            });

            dialogSelectBiaya.show();
        });

        editSatuan.setOnClickListener(v -> {
            AlertDialog.Builder dialogSelectSatuan = new AlertDialog.Builder(this);
            dialogSelectSatuan.setCancelable(true);
            ArrayAdapter<String> satuanAdapter = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item);

            for (MasterSatuanEntity satuan : masterSatuan) {
                satuanAdapter.add(satuan.getNamaSatuan());
            }

            dialogSelectSatuan.setAdapter(satuanAdapter, (dialog, position) -> {
                kodeSatuanSelected = masterSatuan.get(position).getKodeSatuan();
                repositoryDatabase.getSatuanById(kodeSatuanSelected).observe(this, satuans -> {
                    if (satuans != null) {
                        satuanSelected = satuans;
                        editSatuan.setText(satuanSelected.getNamaSatuan());
                    }
                });
            });

            dialogSelectSatuan.show();
        });

        btnSubmit.setOnClickListener(v -> {
            layoutAddBiaya.requestFocus();
            DateFormat formatDateEntry = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            String dateEntry = formatDateEntry.format(Calendar.getInstance().getTime());

            DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String date = formatDate.format(Calendar.getInstance().getTime());

            if (editJenisBiaya.getText().toString().trim().equals("") || editJumlah.getText().toString().trim().equals("") ||
                    editSatuan.getText().toString().trim().equals("") || editBiaya.getText().toString().trim().equals("") ||
                    editKilometerAwal.getText().toString().trim().equals("") || editKilometerAkhir.getText().toString().trim().equals("")) {

                showOkDialog("Lengkapi data", "Ok", "IGNORE_WINDOW");
            } else {
                switch (actionBiaya) {
                    case "add":
                        for (BiayaEntity biaya : biayaExist) {
                            if (biayaSelected.getKodeJenis().equals(biaya.getKodeJenis())) {
                                showOkDialog(biaya.getJenisBiaya() + " sudah ada", "Ok", "IGNORE_WINDOW");

                                return;
                            }
                        }

                        if (Integer.parseInt(editKilometerAwal.getText().toString()) > Integer.parseInt(editKilometerAkhir.getText().toString())) {
                            showOkDialog("Pastikan km akhir lebih besar", "Ok", "IGNORE_WINDOW");

                            return;
                        }

                        dialogLoading.show();
                        BiayaEntity biaya = new BiayaEntity();

                        biaya.setSjBiaya(idSuratJalan);
                        biaya.setKodeSopir(kodeSopir);
                        biaya.setNoKendaraan(mobilSelected.getKode());
                        biaya.setKodeArea(mobilSelected.getKodeArea());
                        biaya.setKodeJenis(biayaSelected.getKodeJenis());
                        biaya.setJenisBiaya(biayaSelected.getJenisBiaya());

                        if (satuanSelected.getKodeSatuan() == null) {
                            biaya.setJumlah(editJumlah.getText().toString());
                            biaya.setKodeSatuan("");
                            biaya.setSatuan(editSatuan.getText().toString());
                        } else {
                            biaya.setJumlah(editJumlah.getText().toString());
                            biaya.setKodeSatuan(satuanSelected.getKodeSatuan());
                            biaya.setSatuan(satuanSelected.getNamaSatuan());
                        }

                        biaya.setHarga(editBiaya.getText().toString());
                        biaya.setKmAwal(editKilometerAwal.getText().toString());
                        biaya.setKmAkhir(editKilometerAkhir.getText().toString());
                        biaya.setKeterangan(editKeterangan.getText().toString());
                        biaya.setEntryDate(dateEntry);
                        biaya.setDate(date);
                        biaya.setNamaPerusahaan(namaPerusahaan);

                        activityAddBiayaPresenterImpl.onAddBiaya(biaya);

                        break;

                    case "edit":
                        if (Integer.parseInt(editKilometerAwal.getText().toString()) > Integer.parseInt(editKilometerAkhir.getText().toString())) {
                            showOkDialog("Pastikan km akhir lebih besar", "Ok", "IGNORE_WINDOW");

                            return;
                        }

                        dialogLoading.show();
                        activityAddBiayaPresenterImpl.onUpdateBiaya(idBiaya,
                                editJumlah.getText().toString(),
                                editBiaya.getText().toString(),
                                editKeterangan.getText().toString(),
                                editKilometerAkhir.getText().toString());

                        break;
                }

            }
        });
    }

    private void onGetKmAwal() {
        dialogLoading.show();

        activityAddBiayaPresenterImpl.onGetKm(dbMaster, mobilSelected.getKode(), biayaSelected.getKodeJenis());
    }

    @Override
    public void onGetKmSuccess(List<ResponseUpdate> kmAwal) {
        if (kmAwal.get(0).getResponse().equals("")) {
            kmAwalFinal = 0;
        } else {
            kmAwalFinal = Integer.parseInt(kmAwal.get(0).getResponse());
        }
        layoutKilometer.setVisibility(View.VISIBLE);
        editKilometerAwal.setText(String.valueOf(kmAwalFinal));
        editKilometerAwal.setClickable(false);
        editKilometerAwal.setFocusable(false);

        editKilometerAkhir.setText("");

        dialogLoading.dismiss();
    }

    @Override
    public void onGetKmFailed() {
        dialogLoading.dismiss();

        showYesOrNoDialog("Gagal mendapatkan data", "Ulangi", "Kembali", "GET_KM_BIAYA_FAILED");
    }

    private void onCheckAction() {
        switch (actionBiaya) {
            case "add":
                namaPerusahaan = intent.getStringExtra("pt");
                idSuratJalan = intent.getStringExtra("nomorOrder");
                kodeMobil = intent.getStringExtra("kodeMobil");
                kodeSopir = intent.getStringExtra("kodeSopir");

                getAllBiayaBySj();
                onPrepareDataMobil();
                onPrepareDataBiaya();
                onPrepareDataSatuan();

                break;

            case "edit":
                idBiaya = intent.getIntExtra("idBiaya", 0);
                repositoryDatabase.getBiayaByIdBiaya(idBiaya).observe(this, listBiaya -> {
                    biayaOnEdit = listBiaya;
                    if (biayaOnEdit != null) {
                        editJenisBiaya.setClickable(false);
                        editJenisBiaya.setText(biayaOnEdit.getJenisBiaya());

                        editSatuan.setClickable(false);
                        editSatuan.setText(biayaOnEdit.getSatuan());

                        editJumlah.setText(biayaOnEdit.getJumlah());
                        editBiaya.setText(biayaOnEdit.getHarga());
                        editKilometerAwal.setText(biayaOnEdit.getKmAwal());
                        editKilometerAkhir.setText(biayaOnEdit.getKmAkhir());
                        editKeterangan.setText(biayaOnEdit.getKeterangan());

                        if (!biayaOnEdit.getKmAwal().equals("0") && !biayaOnEdit.getKmAkhir().equals("0")) {
                            layoutKilometer.setVisibility(View.VISIBLE);

                            editKilometerAwal.setClickable(false);
                            editKilometerAwal.setFocusable(false);
                        }
                    }
                });

                break;
        }
    }

    private void getAllBiayaBySj() {
        repositoryDatabase.getAllBiaya(idSuratJalan).observe(this, biayas -> biayaExist = biayas);
    }

    private void onPrepareDataMobil() {
        repositoryDatabase.getMobilById(kodeMobil).observe(this, mobils -> mobilSelected = mobils);
    }

    private void onPrepareDataBiaya() {
        repositoryDatabase.getAllJenisBiaya().observe(this, listBiayas -> masterJenisBiaya = listBiayas);
    }

    private void onPrepareDataSatuan() {
        repositoryDatabase.getAllSatuan().observe(this, listSatuan -> masterSatuan = listSatuan);
    }

    @Override
    public void onAddBiayaSuccess() {
        new Handler().postDelayed(() -> {
            dialogLoading.dismiss();

            super.onBackPressed();
        }, 2000);
    }

    @Override
    public void onUpdateBiayaSuccess() {
        new Handler().postDelayed(() -> {
            dialogLoading.dismiss();

            super.onBackPressed();
        }, 2000);
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
                case "GET_KM_BIAYA_FAILED":
                    dialogLoading.show();
                    onGetKmAwal();

                    break;

            }
        });

        btnNo.setText(buttonNo);
        btnNo.setOnClickListener(listener -> {
            dialogYesNo.dismiss();
            switch (stateActive) {
                case "GET_KM_BIAYA_FAILED":
                    super.onBackPressed();

                    break;

            }
        });

        if (dialogOk.getWindow() != null) {
            dialogOk.getWindow().setLayout
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        dialogOk.show();
    }

    private void showOkDialog(String message, String buttonOk, String stateActive) {
        dialogOk = new Dialog(this);
        dialogOk.setContentView(R.layout.dialog_konfirmasi_ok);
        dialogOk.setCancelable(false);
        dialogOk.setCanceledOnTouchOutside(false);

        TextView txtMessage = dialogOk.findViewById(R.id.txtMessage);
        Button btnOk = dialogOk.findViewById(R.id.btnOk);
        txtMessage.setText(message);

        btnOk.setText(buttonOk);
        btnOk.setOnClickListener(listener -> {
            dialogOk.dismiss();
            switch (stateActive) {
                case "IGNORE_WINDOW":

                    break;
            }
        });

        if (dialogOk.getWindow() != null) {
            dialogOk.getWindow().setLayout
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        dialogOk.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }

}
