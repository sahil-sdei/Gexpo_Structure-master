package ggn.home.help.features.changePassword;

import android.view.View;

import ggn.home.help.features.internal.base.contract.Viewable;

public interface ChangePasswordView extends Viewable<ChangePasswordPresenter> {
    void hideKeyboard(View view);
}