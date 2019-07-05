package galvatrans.galindra.galva.cecilia.galvatrans.MainActivity;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.Rute;

public interface MainActivityPresenter {
    interface MainPresenter{
        void onGetDataRute(String kodeDriver);
    }

    interface MainView{
        void onGetDataRuteSuccess(List<Rute> ruteList);

        void onGetDataRuteFailed();

    }
}
