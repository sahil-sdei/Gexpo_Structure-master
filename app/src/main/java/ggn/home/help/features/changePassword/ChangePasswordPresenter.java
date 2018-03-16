package ggn.home.help.features.changePassword;

import android.databinding.ObservableField;
import android.view.View;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;

public class ChangePasswordPresenter extends BasePresenter<ChangePasswordView> implements ChangePasswordBinder {
    public ObservableField<String> oldPassword =
            new ObservableField<>();
    public ObservableField<String> newPassword =
            new ObservableField<>();
    public ObservableField<String> confirmPassword =
            new ObservableField<>();

    public ChangePasswordPresenter() {
        oldPassword.set("");
        newPassword.set("");
        confirmPassword.set("");
    }

    @Override
    public ObservableField<String> getOldPassword() {
        return oldPassword;
    }

    @Override
    public ObservableField<String> getNewPassword() {
        return newPassword;
    }

    @Override
    public ObservableField<String> getConfirmPassword() {
        return confirmPassword;
    }

    @Override
    public void saveClicked(View view) {
        if (oldPassword.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_old_password));
        } else if (newPassword.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_new_password));
        } else if (confirmPassword.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_confirm_password));
        } else if (!newPassword.get().equalsIgnoreCase(confirmPassword.get())) {
            getView().displayError(getView().getActivityG().getString(R.string.passwords_does_not_match));
        } else {
            getView().hideKeyboard(view);
//                getView().showLoading(getView().getActivityG().getString(R.string.validating_user), getView().getActivityG().getString(R.string.please_wait));
//                createApiRequest(getRetrofitInstance(LoginSignUpAPI.class)
//                        .login(getEmail().get(), getPassword().get()), new CallBackG<UserModel>() {
//                    @Override
//                    public void callBack(UserModel output) {
//                        getView().saveDataLocally(output);
//                        DashboardActivity.start(getView().getActivityG());
//                    }
//                });
//        }
        }
    }
}
