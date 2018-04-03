package ggn.home.help.features.forgotPassword;

import android.databinding.ObservableField;
import android.view.View;
import android.widget.Toast;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;

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
            email.set("");
            Toast.makeText(getView().getActivityG(), "Reset password link has been sent to your registered email id. Please check your inbox.", Toast.LENGTH_LONG).show();
        }
    }
}
