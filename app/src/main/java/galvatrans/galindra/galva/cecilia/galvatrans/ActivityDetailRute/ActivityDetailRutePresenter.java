package galvatrans.galindra.galva.cecilia.galvatrans.ActivityDetailRute;

import android.graphics.Bitmap;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.ResponseUpdate;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.RuteDetail;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.BiayaEntity;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.ImagesEntity;

public interface ActivityDetailRutePresenter {

    interface MainPresenter {
        void onGetDetailRute(String nomorOrder, String nomorRute);

        void updateWaktuBerangkat(String nomorOrder, String nomorRute);

        void updateWaktuSampai(String nomorOrder, String nomorRute, String memo);

        void onUploadImage(List<ImagesEntity> imagesEncodedList, RuteDetail rute);

        void onPostBiaya(List<BiayaEntity> listBiaya, String kodeDept, String idHeaderBiaya);

        void onPostSubBiaya(List<BiayaEntity> listBiaya, String idHeaderBiaya, String sopirName);

        void onEncodeImage(Bitmap imgCaptured);

        void onGetIdExist(String dbMaster, String kode);

        void setStatusRute(RuteDetail ruteDetail, String idStatus);

    }

    interface MainView {
        void onGetDetailRuteSuccess(RuteDetail ruteDetail);

        void onGetDetailRuteFailed();

        void updateWaktuBerangkatSuccess();

        void updateWaktuSampaiSuccess();

        void onUploadImgSuccess(List<ResponseUpdate> responseUpdates);

        void onUploadImgFailed(List<ImagesEntity> imageFailed);

        void onPostBiayaSuccess(List<ResponseUpdate> responseUpdates);

        void onPostBiayaFailed();

        void onPostSubBiayaSuccess(List<ResponseUpdate> responseUpdates);

        void onPostSubBiayaFailed(List<BiayaEntity> biayaFailed);

        void onEncodeImageSuccess(String imageEncoded);

        void onGetIdBiayaSuccess(List<ResponseUpdate> body);

        void onGetIdBiayaFailed();

        void setStatusRuteSuccess();

        void setStatusRuteFailed();
    }
}
