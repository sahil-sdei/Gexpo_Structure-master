package ggn.home.help.features.signIn;

import android.view.View;

import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.LoginResponse;

public interface SignInView extends Viewable<SignInPresenter> {
    void saveDataLocally(LoginResponse loginResponse);
    void hideKeyboard(View view);
}