package galvatrans.galindra.galva.cecilia.galvatrans.ActivitySplash;

public interface ActivitySplashPresenter {
    interface MainPresenster {
        void checkLoggedIn();
    }

    interface MainView {
        void onLoggedInTrue();

        void onLoggedInFalse();
    }
}
