package ggn.home.help.features.editProfile;

import android.view.View;

import ggn.home.help.features.internal.base.contract.Viewable;

public interface EditProfileView extends Viewable<EditProfilePresenter> {
    void hideKeyboard(View view);
}