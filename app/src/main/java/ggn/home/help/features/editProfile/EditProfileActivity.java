package ggn.home.help.features.editProfile;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityChangePasswordBinding;
import ggn.home.help.databinding.ActivityEditProfileBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.utils.UtillsG;

public class EditProfileActivity extends BaseActivity<ActivityEditProfileBinding, EditProfilePresenter> implements EditProfileView {

    public static void start(Context context) {
        Intent starter = new Intent(context, EditProfileActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new EditProfilePresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return EditProfileActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar(getString(R.string.change_password));
        getDataBinder().setBinder(getPresenter());
    }

    @Override
    public void hideKeyboard(View view) {
        UtillsG.hideKeyboard(getActivityG(), view);
    }
}
