package ggn.home.help.features.editProfile;

import android.view.View;

import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.ProfileResponse;

public interface EditProfileView extends Viewable<EditProfilePresenter> {
    void hideKeyboard(View view);

    void showProfileData(ProfileResponse output);

    void editProfileSuccessful();
}