package ggn.home.help.features.forgotPassword;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityForotPasswordBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.signIn.SignInActivity;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.utils.DialogHelper;
import ggn.home.help.utils.UtillsG;
import ggn.home.help.web.response.BasicResponse;

public class ForgetPasswordActivity extends BaseActivity<ActivityForotPasswordBinding, ForgetPasswordPresenter> implements ForgetPasswordView {

    public static void start(Context context) {
        Intent starter = new Intent(context, ForgetPasswordActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_forot_password;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new ForgetPasswordPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return ForgetPasswordActivity.this;
    }

    @Override
    public void initViews() {
        getDataBinder().setBinder(getPresenter());
    }

    public void finish(View view) {
        this.finish();
    }

    @Override
    public void hideKeyboard(View view) {
        UtillsG.hideKeyboard(getActivityG(), view);
    }

    @Override
    public void successForgotPassword(BasicResponse output) {
        DialogHelper.getInstance().showInformation(getActivityG(), output.message, new CallBackG<String>() {
            @Override
            public void callBack(String output) {
                finish();
            }
        });
    }
}
