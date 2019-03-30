package ggn.home.help.features.settings;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivitySettingsBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.web.response.UserPrivacyResponse;

public class SettingsActivity extends BaseActivity<ActivitySettingsBinding, SettingsPresenter> implements SettingsView {

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingsActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new SettingsPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return SettingsActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar("Settings");

        getDataBinder().switchPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getDataBinder().switchPrivacy.isChecked())
                    getPresenter().setPrivacy("1");
                else
                    getPresenter().setPrivacy("0");
            }
        });

        getPresenter().getPrivacy();
    }

    @Override
    public void showUserPrivacy(UserPrivacyResponse output) {
        if (output.privacy.equalsIgnoreCase("1"))
            getDataBinder().switchPrivacy.setChecked(true);
        else
            getDataBinder().switchPrivacy.setChecked(false);
    }
}
