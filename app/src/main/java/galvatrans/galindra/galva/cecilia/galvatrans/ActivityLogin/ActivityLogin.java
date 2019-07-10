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

import galvatrans.galindra.galva.cecilia.galvatrans.ActivityMain.ActivityMain;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.Driver;
import galvatrans.galindra.galva.cecilia.galvatrans.R;
import retrofit2.Response;

public class ActivityLogin extends AppCompatActivity implements ActivityLoginPresenter.MainView {
    Dialog alertLoading;
    ConstraintLayout layoutLogin;
    TextInputEditText editEmail;
    TextInputEditText editPassword;
    TextView txtError;
    Button btnLogin;

    ActivityLoginPresenterImpl activityLoginPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activityLoginPresenterImpl = new ActivityLoginPresenterImpl(this, getApplicationContext());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        createLoadingDialog();

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
                    Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                    startActivity(intent);

                    this.finish();
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

    @Override
    public void onCreateSessionSuccess() {

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
}
