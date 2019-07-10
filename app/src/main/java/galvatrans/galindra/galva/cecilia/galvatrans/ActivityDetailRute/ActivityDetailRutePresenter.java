package galvatrans.galindra.galva.cecilia.galvatrans.ActivityDetailRute;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.RuteDetail;

public interface ActivityDetailRutePresenter {

    interface MainPresenter {
        void onGetDetailRute(String nomorOrder, String nomorRute);

        void updateWaktuBerangkat(String nomorOrder, String nomorRute);

        void updateWaktuSampai(String nomorOrder, String nomorRute);
    }

    interface MainView {
        void onGetDetailRuteSuccess(RuteDetail ruteDetail);

        void onGetDetailRuteFailed();

        void updateWaktuBerangkatSuccess();

        void updateWaktuSampaiSuccess();


    }
}
