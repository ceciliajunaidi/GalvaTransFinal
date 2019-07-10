package galvatrans.galindra.galva.cecilia.galvatrans.ActivityDetailRute;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
                mainView.onGetDetailRuteSuccess(response.body().get(0));
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
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<RuteDetail>> getDataRuteDetail = apiInterface.getUpdateBerangkat("awalrute", "ggmccmg", nomorOrder, nomorRute, date);
        getDataRuteDetail.enqueue(new Callback<List<RuteDetail>>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call<List<RuteDetail>> call, Response<List<RuteDetail>> response) {
                mainView.updateWaktuBerangkatSuccess();
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void onFailure(Call<List<RuteDetail>> call, Throwable t) {
                Toast.makeText(context, "Gagal update", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void updateWaktuSampai(String nomorOrder, String nomorRute) {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<RuteDetail>> getDataRuteDetail = apiInterface.getUpdateSampai("akhirrute", "ggmccmg", nomorOrder, nomorRute, date);
        getDataRuteDetail.enqueue(new Callback<List<RuteDetail>>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call<List<RuteDetail>> call, Response<List<RuteDetail>> response) {
                mainView.updateWaktuSampaiSuccess();
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void onFailure(Call<List<RuteDetail>> call, Throwable t) {
                Toast.makeText(context, "Gagal update", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
