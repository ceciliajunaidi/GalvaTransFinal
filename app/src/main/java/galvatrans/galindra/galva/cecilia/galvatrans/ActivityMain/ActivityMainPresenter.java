package galvatrans.galindra.galva.cecilia.galvatrans.ActivityMain;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.Rute;

public interface ActivityMainPresenter {
    interface MainPresenter{
        void onGetDataRute(String kodeDriver);

        void onLogoutUserLogin();
    }

    interface MainView{
        void onGetDataRuteSuccess(List<Rute> ruteList);

        void onGetDataRuteFailed();

        void onLogoutUserLoginFinish();
    }
}
