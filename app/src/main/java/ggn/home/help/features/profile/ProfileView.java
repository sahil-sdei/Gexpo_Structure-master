package ggn.home.help.features.profile;


import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.ProfileResponse;

public interface ProfileView extends Viewable<ProfilePresenter> {

    void showProfileData(ProfileResponse output);
}