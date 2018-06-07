package ggn.home.help.features.forgotPassword;

import android.databinding.ObservableField;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.LoginSignUpAPI;
import ggn.home.help.web.request.ForgotPasswordRequest;
import ggn.home.help.web.request.LoginRequest;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.LoginResponse;

public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordView> implements ForgetPasswordBinder {
    public ObservableField<String> email =
            new ObservableField<>();

    public ForgetPasswordPresenter() {
        email.set("");
    }

    @Override
    public ObservableField<String> getEmail() {
        return email;
    }

    @Override
    public void requestPassword(View view) {
        if (email.get().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.get()).matches()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_valid_email));
        } else {
            getView().hideKeyboard(view);
            ForgotPasswordRequest forgotPasswordRequest= new ForgotPasswordRequest();
            forgotPasswordRequest.email = getEmail().get();

            Gson gson = new Gson();

            getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
            createApiRequest(getRetrofitInstance(LoginSignUpAPI.class)
                    .getPassword(gson.toJson(forgotPasswordRequest)), new CallBackG<BasicResponse>() {
                @Override
                public void callBack(BasicResponse output) {
                    getView().hideLoading();
                    if (output.status == 1)
                        getView().successForgotPassword(output);
                    else
                        getView().displayError(output.message);
                    email.set("");
                }
            });
        }
    }
}
