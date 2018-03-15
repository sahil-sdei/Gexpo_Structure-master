package ggn.home.help.features.splash;

import android.os.Handler;

import ggn.home.help.features.internal.base.BasePresenter;

public class SplashPresenter extends BasePresenter<SplashView> implements Runnable {

//    private UserModel userModel;
    private boolean isNotLoggedIn;

    public void splashCheck() {
//        isNotLoggedIn = getView().getLocalData().getUserId().isEmpty();

        new Handler().postDelayed(this, getView().splashTimer());
    }

    @Override
    public void run() {
        if (isNotLoggedIn)
            getView().showOptions();
        else
            getView().openDashBoard();
    }
}
