package galvatrans.galindra.galva.cecilia.galvatrans.ActivityLogin;

import android.app.Dialog;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import galvatrans.galindra.galva.cecilia.galvatrans.ActivityMain.ActivityMain;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.Driver;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.MasterBiaya;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.MasterMobil;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.MasterSatuan;
import galvatrans.galindra.galva.cecilia.galvatrans.R;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.MasterJenisBiayaEntity;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.MasterMobilEntity;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.MasterSatuanEntity;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.Repository;
import retrofit2.Response;

public class ActivityLogin extends AppCompatActivity implements ActivityLoginPresenter.MainView {
    Dialog alertLoading;
    ConstraintLayout layoutLogin;
    TextInputEditText editEmail;
    TextInputEditText editPassword;
    TextView txtError;
    Button btnLogin;

    ActivityLoginPresenterImpl activityLoginPresenterImpl;
    String dbMaster = "GGMCCMGIN";

    Repository repositoryDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activityLoginPresenterImpl = new ActivityLoginPresenterImpl(this, getApplicationContext());
        repositoryDatabase = new Repository(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        createLoadingDialog();

        repositoryDatabase.deleteMasterMobil();
        repositoryDatabase.deleteMasterJenisBiaya();
        repositoryDatabase.deleteMasterSatuan();

        initLayout();
    }

    private void createLoadingDialog() {
        alertLoading = new Dialog(this);
        alertLoading.setContentView(R.layout.dialog_loading);
        alertLoading.setCancelable(false);
        alertLoading.setCanceledOnTouchOutside(false);

        TextView txtLoading = alertLoading.findViewById(R.id.txtLoading);
        txtLoading.setText("Mohon tunggu...");

        if (alertLoading.getWindow() != null) {
            alertLoading.getWindow().setLayout
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    private void initLayout() {
        layoutLogin = findViewById(R.id.layoutLogin);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        txtError = findViewById(R.id.txtError);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            layoutLogin.requestFocus();
            setOnStateLogingIn(1);
            if ((editEmail.getText().toString().trim().equals("")) || (editPassword.getText().toString().trim().equals(""))) {
                txtError.setText("Lengkapi data login");

                setOnStateLogingIn(0);
            } else {
                activityLoginPresenterImpl.onLogin(editEmail.getText().toString(), editPassword.getText().toString());
            }
        });
    }

    @Override
    public void onLoginSuccess(Response<List<Driver>> response) {
        if (response.body() != null) {
            List<Driver> driver = response.body();
            try {
                if (!driver.get(0).getNamaDriver().equals("")) {
                    activityLoginPresenterImpl.onCreateSession(driver);
                }
            } catch (Exception e) {
                txtError.setText("Password atau username salah");

                setOnStateLogingIn(0);
            }
        }
    }

    @Override
    public void onLoginFailed(Throwable t) {
        txtError.setText("Tidak ada jaringan");

        setOnStateLogingIn(0);
    }

    private void onGetMasterMobil() {
        activityLoginPresenterImpl.getMasterMobil(dbMaster);
    }

    @Override
    public void onGetMasterMobilSuccess(List<MasterMobil> masterMobils) {
        for (MasterMobil masterMobil : masterMobils) {
            MasterMobilEntity mobil = new MasterMobilEntity();

            mobil.setKode(masterMobil.getKodeMobil());
            mobil.setNama(masterMobil.getNamaMobil());
            mobil.setKodeArea(masterMobil.getKodeArea());
            mobil.setNomorTnkb(masterMobil.getNomorTnkb());
            mobil.setBahanBakar(masterMobil.getBahanBakar());

            repositoryDatabase.insertMasterMobil(mobil);
        }

        onGetMasterBiaya();
    }

    @Override
    public void onGetMasterMobilFailed() {
        createDialogRetry(1);
    }

    private void onGetMasterBiaya() {
        activityLoginPresenterImpl.getMasterBiaya(dbMaster);
    }

    @Override
    public void onGetMasterBiayaSuccess(List<MasterBiaya> masterBiayas) {
        for (MasterBiaya masterBiaya : masterBiayas) {
            MasterJenisBiayaEntity biaya = new MasterJenisBiayaEntity();

            biaya.setKodeJenis(masterBiaya.getKodeBiaya());
            biaya.setJenisBiaya(masterBiaya.getJenisBiaya());
            biaya.setStatusKm(masterBiaya.getStatusKm());
            biaya.setKodeSatuan(masterBiaya.getSatuan());

            repositoryDatabase.insertMasterJenisBiaya(biaya);
        }

        onGetMasterSatuan();
    }

    @Override
    public void onGetMasterBiayaFailed() {
        createDialogRetry(2);

    }

    private void onGetMasterSatuan() {
        activityLoginPresenterImpl.getMasterSatuan(dbMaster);
    }

    @Override
    public void onGetMasterSatuanSuccess(List<MasterSatuan> masterSatuans) {
        for (MasterSatuan masterSatuan : masterSatuans) {
            MasterSatuanEntity satuan = new MasterSatuanEntity();

            satuan.setKodeSatuan(masterSatuan.getKodeSatuan());
            satuan.setNamaSatuan(masterSatuan.getJenisSatuan());

            repositoryDatabase.insertMasterSatuan(satuan);
        }

        intentToMainActivity();
    }

    @Override
    public void onGetMasterSatuanFailed() {
        createDialogRetry(3);
    }

    private void createDialogRetry(int stateRequest) {
        Dialog alertConfirmation = new Dialog(this);
        alertConfirmation.setContentView(R.layout.dialog_konfirmasi_yes_or_no);
        alertConfirmation.setCancelable(false);
        alertConfirmation.setCanceledOnTouchOutside(false);

        TextView txtMessage = alertConfirmation.findViewById(R.id.txtMessage);
        Button btnYes = alertConfirmation.findViewById(R.id.btnYes);
        Button btnNo = alertConfirmation.findViewById(R.id.btnNo);

        txtMessage.setText("Gagal mengambil data");

        btnYes.setText("Ulangi");
        btnYes.setOnClickListener(listener -> {
            switch (stateRequest) {
                case 1:
                    alertConfirmation.dismiss();

                    activityLoginPresenterImpl.getMasterMobil(dbMaster);
                    break;
                case 2:
                    alertConfirmation.dismiss();

                    activityLoginPresenterImpl.getMasterBiaya(dbMaster);
                    break;
                case 3:
                    alertConfirmation.dismiss();

                    activityLoginPresenterImpl.getMasterSatuan(dbMaster);
                    break;
            }

        });

        btnNo.setText("Keluar");
        btnNo.setOnClickListener(listener -> {
            switch (stateRequest) {
                case 1:
                    alertConfirmation.dismiss();

                    activityLoginPresenterImpl.onLoginFailedAndQuit();
                    break;
                case 2:
                    alertConfirmation.dismiss();

                    activityLoginPresenterImpl.onLoginFailedAndQuit();
                    break;
                case 3:
                    alertConfirmation.dismiss();

                    activityLoginPresenterImpl.onLoginFailedAndQuit();
                    break;
            }

        });

        alertConfirmation.show();

        Objects.requireNonNull(alertConfirmation.getWindow()).setLayout
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onCreateSessionSuccess() {
        onGetMasterMobil();
    }

    @Override
    public void onFailedLoginAndQuitSuccess() {
        finish();
    }

    private void setOnStateLogingIn(int isLogin) {
        switch (isLogin) {
            case 0:
                btnLogin.setEnabled(true);
                btnLogin.setText("Log in");

                editEmail.setEnabled(true);
                editPassword.setEnabled(true);
                break;
            case 1:
                txtError.setText("");
                btnLogin.setEnabled(false);
                btnLogin.setText("Logging in...");

                editEmail.setEnabled(false);
                editPassword.setEnabled(false);
                break;
        }
    }

    private void intentToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
        startActivity(intent);

        this.finish();
    }
}
