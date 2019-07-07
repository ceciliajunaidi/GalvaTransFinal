package galvatrans.galindra.galva.cecilia.galvatrans.ActivityListRute;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.RuteDetail;

public interface ActivityListRutePresenter {

    interface MainPresenter {
        void onGetListRute(String nomorOrder);
    }

    interface MainView {
        void onGetListRuteSuccess(List<RuteDetail> ruteDetailList);

        void onGetListRuteFailed();
    }
}
