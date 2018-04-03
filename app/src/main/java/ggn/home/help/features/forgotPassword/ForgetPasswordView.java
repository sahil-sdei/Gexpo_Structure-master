package ggn.home.help.features.forgotPassword;

import android.view.View;

import ggn.home.help.features.internal.base.contract.Viewable;

public interface ForgetPasswordView extends Viewable<ForgetPasswordPresenter> {
//    void saveDataLocally(UserModel data);
    void hideKeyboard(View view);
}