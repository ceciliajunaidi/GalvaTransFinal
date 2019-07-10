package galvatrans.galindra.galva.cecilia.galvatrans.ActivityLogin;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.Driver;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.MasterBiaya;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.MasterMobil;
import galvatrans.galindra.galva.cecilia.galvatrans.Model.MasterSatuan;
import retrofit2.Response;

public interface ActivityLoginPresenter {

    interface MainPresenter{
        void onLogin(String email, String password);

        void onCreateSession(List<Driver> driver);

        void onLoginFailedAndQuit();

        void getMasterBiaya(String dbMaster);

        void getMasterSatuan(String dbMaster);

        void getMasterMobil(String dbMaster);

    }

    interface MainView{
        void onLoginSuccess(Response<List<Driver>> response);

        void onLoginFailed(Throwable t);

        void onCreateSessionSuccess();

        void onFailedLoginAndQuitSuccess();

        void onGetMasterBiayaSuccess(List<MasterBiaya> body);

        void onGetMasterBiayaFailed();

        void onGetMasterSatuanSuccess(List<MasterSatuan> body);

        void onGetMasterSatuanFailed();

        void onGetMasterMobilSuccess(List<MasterMobil> body);

        void onGetMasterMobilFailed();

    }
}
