package galvatrans.galindra.galva.cecilia.galvatrans.ActivityDetailRute;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.ResponseUpdate;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.RuteDetail;
import galvatrans.galindra.galva.cecilia.galvatrans.Retrofit.ApiClient;
import galvatrans.galindra.galva.cecilia.galvatrans.Retrofit.ApiInterface;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.BiayaEntity;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.ImagesEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDetailRutePresenterImpl implements ActivityDetailRutePresenter.MainPresenter {

    private ActivityDetailRutePresenter.MainView mainView;
    private Context context;

    private int uploadImage;
    private int uploadImageSuccess;
    private int uploadImageFailed;

    private int subBiaya;
    private int subBiayaSuccess;
    private int subBiayaFailed;

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
    public void updateWaktuSampai(String nomorOrder, String nomorRute, String memo) {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<RuteDetail>> getDataRuteDetail = apiInterface.getUpdateSampai("akhirrute", "ggmccmg", nomorOrder, nomorRute, date, memo);
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

    @Override
    public void onUploadImage(List<ImagesEntity> imagesEncodedList, RuteDetail rute) {
        uploadImage = 0;
        uploadImageSuccess = 0;
        uploadImageFailed = 0;
        List<ImagesEntity> imageFailed = new ArrayList<>();
        for (ImagesEntity image : imagesEncodedList) {
            uploadImage = uploadImage + 1;
            ApiInterface apiInterface;
            apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<List<ResponseUpdate>> uploadFoto =
                    apiInterface.uploadFoto(rute.getIdRute(),
                            rute.getIdRute(),
                            "jpg",
                            image.getImage());

            uploadFoto.enqueue(new Callback<List<ResponseUpdate>>() {
                @SuppressWarnings("NullableProblems")
                @Override
                public void onResponse(Call<List<ResponseUpdate>> call, Response<List<ResponseUpdate>> response) {
                    uploadImageSuccess = uploadImageSuccess + 1;
                    if (uploadImage == imagesEncodedList.size() && (uploadImageFailed + uploadImageSuccess == imagesEncodedList.size())) {
                        if (uploadImageSuccess == uploadImage) {
                            mainView.onUploadImgSuccess(response.body());
                        } else {
                            mainView.onUploadImgFailed(imageFailed);
                        }
                    }
                }

                @SuppressWarnings("NullableProblems")
                @Override
                public void onFailure(Call<List<ResponseUpdate>> call, Throwable t) {
                    uploadImageFailed = uploadImageFailed + 1;
                    imageFailed.add(image);
                    if (uploadImage == imagesEncodedList.size() && (uploadImageFailed + uploadImageSuccess == imagesEncodedList.size())) {
                        mainView.onUploadImgFailed(imageFailed);
                    }
                }
            });
        }
    }

    @Override
    public void onPostBiaya(List<BiayaEntity> listBiaya, String kodeDept, String idHeaderBiaya) {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        int totalHarga = 0;
        for (BiayaEntity biaya : listBiaya) {
            totalHarga = totalHarga + Integer.parseInt(biaya.getHarga());
        }

        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ResponseUpdate>> postBiaya =
                apiInterface.postBiaya("GGMCCMG", idHeaderBiaya, listBiaya.get(0).getNoKendaraan(),
                        listBiaya.get(0).getKodeSopir(), String.valueOf(totalHarga), listBiaya.get(0).getKodeArea(), kodeDept, date);

        postBiaya.enqueue(new Callback<List<ResponseUpdate>>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call<List<ResponseUpdate>> call, Response<List<ResponseUpdate>> response) {
                mainView.onPostBiayaSuccess(response.body());
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void onFailure(Call<List<ResponseUpdate>> call, Throwable t) {
                mainView.onPostBiayaFailed();
            }
        });
    }

    @Override
    public void onPostSubBiaya(List<BiayaEntity> listBiaya, String idHeaderBiaya, String sopirName) {
        subBiaya = 0;
        subBiayaSuccess = 0;
        subBiayaFailed = 0;
        List<BiayaEntity> biayaFailed = new ArrayList<>();
        for (BiayaEntity biaya : listBiaya) {
            subBiaya = subBiaya + 1;
            ApiInterface apiInterface;
            apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<List<ResponseUpdate>> postSubBiaya =
                    apiInterface.postSubBiaya("GGMCCMG", idHeaderBiaya, biaya.getNoKendaraan(),
                            biaya.getKodeJenis(), biaya.getJumlah(), biaya.getKodeSatuan(),
                            biaya.getHarga(), sopirName, "", biaya.getKmAwal(),
                            biaya.getKmAkhir(), biaya.getDate(), biaya.getKeterangan());

            postSubBiaya.enqueue(new Callback<List<ResponseUpdate>>() {
                @SuppressWarnings("NullableProblems")
                @Override
                public void onResponse(Call<List<ResponseUpdate>> call, Response<List<ResponseUpdate>> response) {
                    subBiayaSuccess = subBiayaSuccess + 1;
                    if (subBiaya == listBiaya.size() && (subBiayaFailed + subBiayaSuccess == listBiaya.size())) {
                        if (subBiayaSuccess == subBiaya) {
                            mainView.onPostSubBiayaSuccess(response.body());
                        } else {
                            mainView.onPostSubBiayaFailed(biayaFailed);
                        }
                    }
                }

                @SuppressWarnings("NullableProblems")
                @Override
                public void onFailure(Call<List<ResponseUpdate>> call, Throwable t) {
                    uploadImageFailed = uploadImageFailed + 1;
                    biayaFailed.add(biaya);
                    if (subBiaya == listBiaya.size() && (subBiayaFailed + subBiayaSuccess == listBiaya.size())) {
                        mainView.onPostSubBiayaFailed(biayaFailed);
                    }
                }
            });
        }
    }

    @Override
    public void onEncodeImage(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap resizedImage = Bitmap.createScaledBitmap(bitmap, 1200, 1200, true);
        Bitmap finalBitmap = Bitmap.createBitmap(resizedImage, 0, 0, resizedImage.getWidth(), resizedImage.getHeight(), matrix, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bitmap.getWidth() * bitmap.getHeight());
        finalBitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        mainView.onEncodeImageSuccess(Base64.encodeToString(byteArray, Base64.NO_WRAP));
    }

    @Override
    public void onGetIdExist(String dbMaster, String kodeMobil) {
        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ResponseUpdate>> getTotalIdExist = apiInterface.getTotalIdExist(dbMaster,
                "select count(no_bukti) + 1 as response from biayakendaraan where no_kendaraan = '" +
                        kodeMobil + "' AND no_bukti LIKE'" +
                        Calendar.getInstance().get(Calendar.YEAR) + "%'");
        getTotalIdExist.enqueue(new Callback<List<ResponseUpdate>>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call<List<ResponseUpdate>> call, Response<List<ResponseUpdate>> response) {
                mainView.onGetIdBiayaSuccess(response.body());
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void onFailure(Call<List<ResponseUpdate>> call, Throwable t) {
                mainView.onGetIdBiayaFailed();
            }
        });
    }

    @Override
    public void setStatusRute(RuteDetail ruteDetail, String idStatus) {
        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ResponseUpdate>> getTotalIdExist = apiInterface.updateStatusRute("rutestatus",
                "GGMCCMG", ruteDetail.getIdRute(), idStatus);
        getTotalIdExist.enqueue(new Callback<List<ResponseUpdate>>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call<List<ResponseUpdate>> call, Response<List<ResponseUpdate>> response) {
                mainView.setStatusRuteSuccess();
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void onFailure(Call<List<ResponseUpdate>> call, Throwable t) {
                mainView.setStatusRuteFailed();
            }
        });
    }
}
