package galvatrans.galindra.galva.cecilia.galvatrans.ActivitySplash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import galvatrans.galindra.galva.cecilia.galvatrans.ActivityLogin.ActivityLogin;
import galvatrans.galindra.galva.cecilia.galvatrans.ActivityMain.ActivityMain;
import galvatrans.galindra.galva.cecilia.galvatrans.R;

public class ActivitySplash extends AppCompatActivity implements ActivitySplashPresenter.MainView {
    ActivitySplashPresenterImpl activitySplashPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activitySplashPresenterImpl = new ActivitySplashPresenterImpl(this, getApplicationContext());
        onCheckLogin();
    }

    private void onCheckLogin() {
        new Handler().postDelayed(() -> {
            activitySplashPresenterImpl.checkLoggedIn();
        },3000);
    }

    @Override
    public void onLoggedInTrue() {
        Intent intentMainActivity = new Intent(this, ActivityMain.class);
        startActivity(intentMainActivity);
        finish();
    }

    @Override
    public void onLoggedInFalse() {
        Intent intentLoginActivity = new Intent(this, ActivityLogin.class);
        startActivity(intentLoginActivity);
        finish();
    }
}
