package galvatrans.galindra.galva.cecilia.galvatrans.ActivityListRute;

import android.content.Context;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.RuteDetail;
import galvatrans.galindra.galva.cecilia.galvatrans.Retrofit.ApiClient;
import galvatrans.galindra.galva.cecilia.galvatrans.Retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityListRutePresenterImpl implements ActivityListRutePresenter.MainPresenter {

    private ActivityListRutePresenter.MainView mainView;
    private Context context;

    ActivityListRutePresenterImpl(ActivityListRutePresenter.MainView mainView, Context context) {
        this.mainView = mainView;
        this.context = context;
    }

    @Override
    public void onGetListRute(String nomorOrder) {
        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<RuteDetail>> getDataRuteDetail = apiInterface.getRuteDetailByIdRute("GGMCCMGIN",
                "select * from rute_det where id_rute='" + nomorOrder + "'");
        getDataRuteDetail.enqueue(new Callback<List<RuteDetail>>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call<List<RuteDetail>> call, Response<List<RuteDetail>> response) {
                mainView.onGetListRuteSuccess(response.body());
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void onFailure(Call<List<RuteDetail>> call, Throwable t) {
                mainView.onGetListRuteFailed();
            }
        });
    }
}
