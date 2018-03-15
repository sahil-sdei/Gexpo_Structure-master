package ggn.home.help.features.splash;


import ggn.home.help.features.internal.base.contract.Viewable;

public interface SplashView extends Viewable<SplashPresenter> {
    /**
     * @return time in ms for splash delay.
     */
    long splashTimer();

    /**
     * show option to sign in/join or facebook
     */
    void showOptions();

    /**
     * already logged in ,just show the dashboard.
     */
    void openDashBoard();
}