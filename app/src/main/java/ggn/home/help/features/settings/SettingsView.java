package ggn.home.help.features.settings;


import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.UserPrivacyResponse;

public interface SettingsView extends Viewable<SettingsPresenter> {

    void showUserPrivacy(UserPrivacyResponse output);
}