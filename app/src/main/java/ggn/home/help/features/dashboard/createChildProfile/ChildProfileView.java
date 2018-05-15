package ggn.home.help.features.dashboard.createChildProfile;

import android.view.View;

import ggn.home.help.features.internal.base.contract.Viewable;

interface ChildProfileView extends Viewable<ChildProfilePresenter> {
    void hideKeyboard(View view);

    void onCreatedSuccessfully();
}
