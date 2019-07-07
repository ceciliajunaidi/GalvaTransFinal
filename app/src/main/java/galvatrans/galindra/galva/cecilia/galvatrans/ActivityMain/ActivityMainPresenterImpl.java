package galvatrans.galindra.galva.cecilia.galvatrans.ActivityMain;

import android.content.Context;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.Rute;
import galvatrans.galindra.galva.cecilia.galvatrans.Retrofit.ApiClient;
import galvatrans.galindra.galva.cecilia.galvatrans.Retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMainPresenterImpl implements ActivityMainPresenter.MainPresenter {

    private Context context;
    private ActivityMainPresenter.MainView mainView;

    ActivityMainPresenterImpl(ActivityMainPresenter.MainView mainView, Context context) {
        this.context = context;
        this.mainView = mainView;
    }

    @Override
    public void onGetDataRute(String kodeDriver) {
        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Rute>> getDataRute = apiInterface.getRuteByKodeDriver("GGMCCMGIN",
                "select * from rute where id_karyawan='" + kodeDriver + "'");
        getDataRute.enqueue(new Callback<List<Rute>>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call<List<Rute>> call, Response<List<Rute>> response) {
                mainView.onGetDataRuteSuccess(response.body());
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void onFailure(Call<List<Rute>> call, Throwable t) {
                mainView.onGetDataRuteFailed();
            }
        });

    }
}
