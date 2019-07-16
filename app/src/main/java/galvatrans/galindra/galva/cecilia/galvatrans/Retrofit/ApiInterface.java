package galvatrans.galindra.galva.cecilia.galvatrans.Retrofit;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.Driver;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.MasterBiaya;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.MasterMobil;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.MasterSatuan;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.ResponseUpdate;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.Rute;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.RuteDetail;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("login.ashx")
    Call<List<Driver>> getDriver(@Query("user") String email,
                                 @Query("pwd") String password);

    @POST("getglobal.ashx")
    @FormUrlEncoded
    Call<List<Rute>> getRuteByKodeDriver(@Field("db") String dbName,
                                         @Field("sql") String querry);

    @POST("getglobal.ashx")
    @FormUrlEncoded
    Call<List<RuteDetail>> getRuteDetailByIdRute(@Field("db") String dbName,
                                                 @Field("sql") String querry);

    @POST("getglobal.ashx")
    @FormUrlEncoded
    Call<List<RuteDetail>> getRuteDetailByNomorOrderAndNomorRute(@Field("db") String dbName,
                                                                 @Field("sql") String querry);

    @POST("update.ashx")
    @FormUrlEncoded
    Call<List<RuteDetail>> getUpdateBerangkat(@Field("tipe") String tipe,
                                              @Field("pt") String pt,
                                              @Field("id_rute") String id_rute,
                                              @Field("no_rute") String no_rute,
                                              @Field("mulai") String mulai);

    @POST("update.ashx")
    @FormUrlEncoded
    Call<List<RuteDetail>> getUpdateSampai(@Field("tipe") String tipe,
                                           @Field("pt") String pt,
                                           @Field("id_rute") String id_rute,
                                           @Field("no_rute") String no_rute,
                                           @Field("sampai") String sampai,
                                           @Field("notesopir") String note);

    @POST("update.ashx")
    @FormUrlEncoded
    Call<List<ResponseUpdate>> updateStatusRute(@Field("tipe") String tipe,
                                                @Field("pt") String pt,
                                                @Field("id_rute") String idRute,
                                                @Field("id_status") String idStatus);

    @POST("getglobal.ashx")
    @FormUrlEncoded
    Call<List<MasterSatuan>> getMasterSatuan(@Field("db") String dbName,
                                             @Field("sql") String querry);

    @POST("getglobal.ashx")
    @FormUrlEncoded
    Call<List<MasterBiaya>> getMasterBiaya(@Field("db") String dbName,
                                           @Field("sql") String querry);


    @POST("getglobal.ashx")
    @FormUrlEncoded
    Call<List<MasterMobil>> getMasterMobil(@Field("db") String dbName,
                                           @Field("sql") String querry);

    @POST("getglobal.ashx")
    @FormUrlEncoded
    Call<List<ResponseUpdate>> getTotalIdExist(@Field("db") String dbName,
                                               @Field("sql") String querry);

    @POST("getglobal.ashx")
    @FormUrlEncoded
    Call<List<ResponseUpdate>> getKm(@Field("db") String dbName,
                                     @Field("sql") String querry);

    @POST("upload.ashx")
    @FormUrlEncoded
    Call<List<ResponseUpdate>> uploadFoto(@Field("doku") String doku,
                                          @Field("id") String id,
                                          @Field("filetype") String filetype,
                                          @Field("image") String image);

    @POST("postbiaya.ashx")
    @FormUrlEncoded
    Call<List<ResponseUpdate>> postBiaya(@Field("pt") String pt,
                                         @Field("no_bukti") String noBukti,
                                         @Field("no_kendaraan") String noKendaraan,
                                         @Field("kode_sopir") String kodeSopir,
                                         @Field("total_harga") String totalHarga,
                                         @Field("kode_area") String kodeArea,
                                         @Field("kode_dept") String kodeDept,
                                         @Field("tanggal") String tanggal);

    @POST("postsubbiaya.ashx")
    @FormUrlEncoded
    Call<List<ResponseUpdate>> postSubBiaya(@Field("pt") String pt,
                                            @Field("no_bukti") String noBukti,
                                            @Field("no_kendaraan") String noKendaraan,
                                            @Field("kode_jenis") String kodeJenis,
                                            @Field("jumlah") String jumlah,
                                            @Field("kode_satuan") String kodeSatuan,
                                            @Field("harga") String harga,
                                            @Field("userid") String userid,
                                            @Field("edit") String edit,
                                            @Field("km_awal") String km_awal,
                                            @Field("km_akhir") String km_akhir,
                                            @Field("tanggal") String tanggal,
                                            @Field("keterangan") String keterangan);
    }
