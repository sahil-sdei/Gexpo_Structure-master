package ggn.home.help.features.changePassword;

import android.databinding.ObservableField;
import android.view.View;

import com.google.gson.Gson;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.LoginSignUpAPI;
import ggn.home.help.web.request.ChangePasswordRequest;
import ggn.home.help.web.response.BasicResponse;

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
            ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
            changePasswordRequest.token = getView().getLocalData().getAuthToken();
            changePasswordRequest.oldPassword = oldPassword.get();
            changePasswordRequest.password = newPassword.get();
            changePasswordRequest.confirmPassword = confirmPassword.get();
            changePasswordRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
            Gson gson = new Gson();

            getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
            createApiRequest(getRetrofitInstance(LoginSignUpAPI.class)
                    .changePassword(gson.toJson(changePasswordRequest)), new CallBackG<BasicResponse>() {
                @Override
                public void callBack(BasicResponse output) {
                    getView().hideLoading();
                    if (output.status == 1) {
                        getOldPassword().set("");
                        getNewPassword().set("");
                        getConfirmPassword().set("");
                        getView().successChangePassword(output);
                    }
                    else
                        getView().displayError(output.message);
                }
            });
        }
    }
}
