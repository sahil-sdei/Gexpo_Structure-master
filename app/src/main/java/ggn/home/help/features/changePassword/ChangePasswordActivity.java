package ggn.home.help.features.changePassword;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityChangePasswordBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.utils.UtillsG;

public class ChangePasswordActivity extends BaseActivity<ActivityChangePasswordBinding, ChangePasswordPresenter> implements ChangePasswordView {

    public static void start(Context context) {
        Intent starter = new Intent(context, ChangePasswordActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new ChangePasswordPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return ChangePasswordActivity.this;
    }

    @Override
    public void initViews() {
        getDataBinder().setBinder(getPresenter());
    }

    @Override
    public void hideKeyboard(View view) {
        UtillsG.hideKeyboard(getActivityG(), view);
    }
}
