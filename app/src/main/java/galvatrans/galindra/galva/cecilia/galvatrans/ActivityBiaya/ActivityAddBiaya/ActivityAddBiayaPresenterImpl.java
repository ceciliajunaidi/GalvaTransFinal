package galvatrans.galindra.galva.cecilia.galvatrans.ActivityBiaya.ActivityAddBiaya;

import android.content.Context;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.ResponseUpdate;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.SessionManager;
import galvatrans.galindra.galva.cecilia.galvatrans.Retrofit.ApiClient;
import galvatrans.galindra.galva.cecilia.galvatrans.Retrofit.ApiInterface;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.BiayaEntity;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.Repository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAddBiayaPresenterImpl implements ActivityAddBiayaPresenter.MainPresenter {

    private ActivityAddBiayaPresenter.MainView mainView;
    private Context context;

    ActivityAddBiayaPresenterImpl(ActivityAddBiayaPresenter.MainView mainView, Context context) {
        this.mainView = mainView;
        this.context = context;
    }

    @Override
    public void onAddBiaya(BiayaEntity biaya) {
        Repository repositoryDatabase = new Repository(context);

        repositoryDatabase.insertBiaya(biaya);
        mainView.onAddBiayaSuccess();
    }

    @Override
    public void onUpdateBiaya(int idBiaya, String jumlah, String biaya, String keterangan, String kmAkhir) {
        Repository repositoryDatabase = new Repository(context);

        repositoryDatabase.updateBiayaById(idBiaya, jumlah, biaya, keterangan, kmAkhir);
        mainView.onUpdateBiayaSuccess();
    }

    @Override
    public void onGetKm(String dbMaster, String kodeMobil, String kodeJenis) {
        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ResponseUpdate>> getKm = apiInterface.getKm(dbMaster,
                "select top 1 km_akhir as response from subbiayakendaraan left join biayakendaraan on " +
                        "subbiayakendaraan.no_bukti = biayakendaraan.no_bukti where biayakendaraan.no_kendaraan = '" +
                        kodeMobil + "' and subbiayakendaraan.kode_jenis = '" +
                        kodeJenis + "' order by km_akhir desc");
        getKm.enqueue(new Callback<List<ResponseUpdate>>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call<List<ResponseUpdate>> call, Response<List<ResponseUpdate>> response) {
                mainView.onGetKmSuccess(response.body());
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void onFailure(Call<List<ResponseUpdate>> call, Throwable t) {
                mainView.onGetKmFailed();
            }
        });
    }
}
