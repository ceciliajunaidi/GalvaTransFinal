package galvatrans.galindra.galva.cecilia.galvatrans.Retrofit;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.Rute;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.RuteDetail;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

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

}
