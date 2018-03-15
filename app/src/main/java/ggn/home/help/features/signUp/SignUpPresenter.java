package ggn.home.help.features.signUp;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import ggn.home.help.R;
import ggn.home.help.features.dashboard.DashboardActivity;
import ggn.home.help.features.internal.base.BasePresenter;

public class SignUpPresenter extends BasePresenter<SignUpView> implements SignUpBinder {
    public ObservableField<String> email =
            new ObservableField<>();
    public ObservableField<String> password =
            new ObservableField<>();
    public ObservableField<String> fullName =
            new ObservableField<>();

    public SignUpPresenter() {
        email.set("");
        password.set("");
        fullName.set("");
    }

    @Override
    public ObservableField<String> getEmail() {
        return email;
    }

    @Override
    public ObservableField<String> getPassword() {
        return password;
    }

    @Override
    public ObservableField<String> getFullName() {
        return null;
    }

    @Override
    public void signUpClicked(View view) {
        if (email.get().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.get()).matches()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_valid_email));
        } else if (password.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_password));
        } else if (fullName.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_full_name));
        } else {
            getView().hideKeyboard(view);
            DashboardActivity.start(getView().getActivityG());
//                    getView().showLoading(getView().getActivityG().getString(R.string.registering_user), getView().getActivityG().getString(R.string.please_wait));
//                    createApiRequest(getRetrofitInstance(LoginSignUpAPI.class)
//                            .register(getEmail().get(), getPassword().get(), getUserName().get()), new CallBackG<UserModel>() {
//                        @Override
//                        public void callBack(UserModel output) {
//                            getView().saveDataLocally(output);
//                            DashboardActivity.start(getView().getActivityG());
//                        }
//                    });
        }
    }

    public FacebookCallback<LoginResult> getFbCallback() {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getView().showLoading(getView().getActivityG().getString(R.string.fetching_facebook_information), getView().getActivityG().getString(R.string.please_wait));
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    getView().hideLoading();
//                                    AccessToken token = AccessToken.getCurrentAccessToken();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                getView().displayError(error.getMessage());
            }
        };
    }
}

