package ggn.home.help.features.signUp;

import android.view.View;

import ggn.home.help.features.internal.base.contract.Viewable;

public interface SignUpView extends Viewable<SignUpPresenter> {
//    void saveDataLocally(UserModel data);
    void hideKeyboard(View view);
}