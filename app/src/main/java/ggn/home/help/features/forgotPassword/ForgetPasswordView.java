package ggn.home.help.features.forgotPassword;

import android.view.View;

import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.BasicResponse;

public interface ForgetPasswordView extends Viewable<ForgetPasswordPresenter> {
    void hideKeyboard(View view);

    void successForgotPassword(BasicResponse output);
}