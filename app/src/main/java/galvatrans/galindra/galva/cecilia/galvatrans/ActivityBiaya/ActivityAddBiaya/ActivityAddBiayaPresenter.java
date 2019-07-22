package galvatrans.galindra.galva.cecilia.galvatrans.ActivityBiaya.ActivityAddBiaya;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.ResponseUpdate;
import galvatrans.galindra.galva.cecilia.galvatrans.RoomDatabase.BiayaEntity;

interface ActivityAddBiayaPresenter {

    interface MainPresenter {

        void onAddBiaya(BiayaEntity biaya);

        void onUpdateBiaya(int idBiaya, String jumlah, String biaya, String keterangan, String kmAkhir);

        void onGetKm(String dbMaster, String kodeMobil, String kodeJenis);

    }

    interface MainView {

        void onAddBiayaSuccess();

        void onUpdateBiayaSuccess();

        void onGetKmSuccess(List<ResponseUpdate> body);

        void onGetKmFailed();

    }

}
