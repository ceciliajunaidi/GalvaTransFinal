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
                                           @Field("sampai") String sampai);

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


}
