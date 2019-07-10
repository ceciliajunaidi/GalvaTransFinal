package galvatrans.galindra.galva.cecilia.galvatrans.ActivityLogin;

import java.util.List;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.Driver;
import retrofit2.Response;

public interface ActivityLoginPresenter {

    interface MainPresenter{
        void onLogin(String email, String password);

        void onCreateSession(List<Driver> driver);

        void onLoginFailedAndQuit();

    }

    interface MainView{
        void onLoginSuccess(Response<List<Driver>> response);

        void onLoginFailed(Throwable t);

        void onCreateSessionSuccess();

        void onFailedLoginAndQuitSuccess();

    }
}
