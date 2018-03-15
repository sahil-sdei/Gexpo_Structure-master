package ggn.home.help.features.signIn;

import android.view.View;

import ggn.home.help.features.internal.base.contract.Viewable;

public interface SignInView extends Viewable<SignInPresenter> {
//    void saveDataLocally(UserModel data);
    void hideKeyboard(View view);
}