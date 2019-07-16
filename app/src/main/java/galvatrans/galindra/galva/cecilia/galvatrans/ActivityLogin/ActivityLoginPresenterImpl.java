package galvatrans.galindra.galva.cecilia.galvatrans.ActivityLogin;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.Driver;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.MasterBiaya;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.MasterMobil;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.MasterSatuan;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.SessionManager;
import galvatrans.galindra.galva.cecilia.galvatrans.Retrofit.ApiClient;
import galvatrans.galindra.galva.cecilia.galvatrans.Retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLoginPresenterImpl implements ActivityLoginPresenter.MainPresenter {
    private ActivityLoginPresenter.MainView mainView;
    private Context context;

    ActivityLoginPresenterImpl(ActivityLoginPresenter.MainView mainView, Context context) {
        this.mainView = mainView;
        this.context = context;
    }

    @Override
    public void onLogin(String email, String password) {
        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Driver>> loginDriver = apiInterface.getDriver(email, password);
        loginDriver.enqueue(new Callback<List<Driver>>() {

            @Override
            public void onResponse(Call<List<Driver>> call, Response<List<Driver>> response) {
                mainView.onLoginSuccess(response);
            }

            @Override
            public void onFailure(Call<List<Driver>> call, Throwable t) {
                mainView.onLoginFailed(t);
            }
        });
    }

    @Override
    public void onCreateSession(List<Driver> driver) {
        SessionManager sessionManager = new SessionManager(context);
        sessionManager.createLoginSession(driver.get(0).getIdDriver(), driver.get(0).getNamaDriver(), driver.get(0).getArea());

        mainView.onCreateSessionSuccess();
    }

    @Override
    public void onLoginFailedAndQuit() {
        SessionManager sessionManager = new SessionManager(context);
        sessionManager.logoutUser();

        if (!sessionManager.isLoggedIn()) {
            mainView.onFailedLoginAndQuitSuccess();
        }
    }

    @Override
    public void getMasterBiaya(String dbMaster) {
        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<List<MasterBiaya>> loginDriver = apiInterface.getMasterBiaya(dbMaster, "select kode_jenis, nama_jenisbiaya, statuskm, kode_satuan from JenisBiayaKendaraan");
        loginDriver.enqueue(new Callback<List<MasterBiaya>>() {
            @Override
            public void onResponse(Call<List<MasterBiaya>> call, Response<List<MasterBiaya>> response) {
                mainView.onGetMasterBiayaSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<MasterBiaya>> call, Throwable t) {
                mainView.onGetMasterBiayaFailed();
            }
        });
    }

    @Override
    public void getMasterSatuan(String dbMaster) {
        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<List<MasterSatuan>> loginDriver = apiInterface.getMasterSatuan(dbMaster, "select kode, nama from Satuan");
        loginDriver.enqueue(new Callback<List<MasterSatuan>>() {
            @Override
            public void onResponse(Call<List<MasterSatuan>> call, Response<List<MasterSatuan>> response) {
                mainView.onGetMasterSatuanSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<MasterSatuan>> call, Throwable t) {
                mainView.onGetMasterSatuanFailed();
            }
        });

    }

    @Override
    public void getMasterMobil(String dbMaster) {
        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<List<MasterMobil>> getMobil = apiInterface.getMasterMobil(dbMaster, "select Kode, Nama, Kode_Area, nomorTNKB, bahan_bakar from Mobil order by Nama ASC");
        getMobil.enqueue(new Callback<List<MasterMobil>>() {
            @Override
            public void onResponse(Call<List<MasterMobil>> call, Response<List<MasterMobil>> response) {
                mainView.onGetMasterMobilSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<MasterMobil>> call, Throwable t) {
                mainView.onGetMasterMobilFailed();
            }
        });
    }
}
