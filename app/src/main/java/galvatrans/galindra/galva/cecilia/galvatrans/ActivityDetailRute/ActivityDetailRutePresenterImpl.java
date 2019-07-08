package galvatrans.galindra.galva.cecilia.galvatrans.ActivityDetailRute;

import android.content.Context;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.RuteDetail;
import galvatrans.galindra.galva.cecilia.galvatrans.Retrofit.ApiClient;
import galvatrans.galindra.galva.cecilia.galvatrans.Retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDetailRutePresenterImpl implements ActivityDetailRutePresenter.MainPresenter {

    private ActivityDetailRutePresenter.MainView mainView;
    private Context context;

    public ActivityDetailRutePresenterImpl(ActivityDetailRutePresenter.MainView mainView, Context context) {
        this.mainView = mainView;
        this.context = context;
    }

    @Override
    public void onGetDetailRute(String nomorOrder, String nomorRute) {
        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<RuteDetail>> getDataRuteDetail = apiInterface.getRuteDetailByNomorOrderAndNomorRute("GGMCCMGIN",
                "select * from rute_det where id_rute='" + nomorOrder + "'" + "and rute='" + nomorRute + "'");
        getDataRuteDetail.enqueue(new Callback<List<RuteDetail>>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call<List<RuteDetail>> call, Response<List<RuteDetail>> response) {
                mainView.onGetDetailRuteSuccess(response.body());
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void onFailure(Call<List<RuteDetail>> call, Throwable t) {
                mainView.onGetDetailRuteFailed();
            }
        });
    }

    @Override
    public void updateWaktuBerangkat(String nomorOrder, String nomorRute) {
        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<RuteDetail>> getDataRuteDetail = apiInterface.getUpdateBerangkat("GGMCCMGIN",
                "UPDATE rute_det set berangkat='asd' where id_rute='" + nomorOrder + "'" + "and rute='" + nomorRute + "'");
        getDataRuteDetail.enqueue(new Callback<List<RuteDetail>>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call<List<RuteDetail>> call, Response<List<RuteDetail>> response) {
                mainView.updateWaktuBerangkatSuccess();
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void onFailure(Call<List<RuteDetail>> call, Throwable t) {

            }
        });
    }
}
