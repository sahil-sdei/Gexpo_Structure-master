package ggn.home.help.features.internal.base.contract;

import android.content.Context;

import ggn.home.help.utils.SharedPrefHelper;

/**
 * Android contract for every MVP View
 */
public interface Viewable<T>
{

    /**
     * initialize all views here.
     */
    void initViews();

    SharedPrefHelper getLocalData();

    Context getActivityG();

    /**
     * Every Viewable must be able to access to its attached Presenter
     *
     * @return Presentable
     */
    T getPresenter();

    /**
     * Every Viewable must be able to inject its Presenter
     *
     * @param presenter Presentable
     */
    void injectPresenter(T presenter);

    /**
     * Every Viewable must have a error message system
     */
    void displayError(String message);

    /**
     * Every Viewable must implement one show loading feature
     */
    void showLoading();

    void showLoading(String title, String message);

    /**
     * Every Viewable must implement one hide loading feature
     */
    void hideLoading();

}
