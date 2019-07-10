package galvatrans.galindra.galva.cecilia.galvatrans.ActivityLogin;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.Driver;
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
        sessionManager.createLoginSession(driver.get(0).getNamaDriver());

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
}
