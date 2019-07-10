package galvatrans.galindra.galva.cecilia.galvatrans.ActivitySplash;

import android.content.Context;

import galvatrans.galindra.galva.cecilia.galvatrans.Model.SessionManager;

public class ActivitySplashPresenterImpl implements ActivitySplashPresenter.MainPresenster {
    private ActivitySplashPresenter.MainView mainView;
    private Context context;

    ActivitySplashPresenterImpl(ActivitySplashPresenter.MainView mainView, Context context) {
        this.mainView = mainView;
        this.context = context;
    }

    @Override
    public void checkLoggedIn() {
        SessionManager sessionManager;
        sessionManager = new SessionManager(context);

        if (sessionManager.isLoggedIn()) {
            mainView.onLoggedInTrue();
        } else {
            mainView.onLoggedInFalse();
        }
    }
}
