package galvatrans.galindra.galva.cecilia.galvatrans.ActivityDetailRute;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.RuteDetail;

public interface ActivityDetailRutePresenter {

    interface MainPresenter {
        void onGetDetailRute(String nomorOrder, String nomorRute);
    }

    interface MainView {
        void onGetDetailRuteSuccess(List<RuteDetail> ruteDetails);

        void onGetDetailRuteFailed();
    }
}
