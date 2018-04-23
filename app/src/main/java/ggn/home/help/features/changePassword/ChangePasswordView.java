package ggn.home.help.features.changePassword;

import android.view.View;

import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.BasicResponse;

public interface ChangePasswordView extends Viewable<ChangePasswordPresenter> {
    void hideKeyboard(View view);

    void successChangePassword(BasicResponse output);
}