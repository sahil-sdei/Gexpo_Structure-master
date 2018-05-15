package ggn.home.help.features.signUp;

import android.view.View;

import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.LoginResponse;

public interface SignUpView extends Viewable<SignUpPresenter> {
    void hideKeyboard(View view);

    void saveDataLocally(LoginResponse output);

    void goToSignIn(LoginResponse output);
}