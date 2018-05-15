package ggn.home.help.features.signUp;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import ggn.home.help.R;
import ggn.home.help.features.dashboard.DashboardActivity;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.LoginSignUpAPI;
import ggn.home.help.web.request.LoginRequest;
import ggn.home.help.web.request.SignUpRequest;
import ggn.home.help.web.request.SocialLoginRequest;
import ggn.home.help.web.response.LoginResponse;

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
        return fullName;
    }

    @Override
    public void signUpClicked(View view) {
        if (fullName.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_full_name));
        } else if (email.get().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.get()).matches()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_valid_email));
        } else if (password.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_password));
        } else {
            getView().hideKeyboard(view);
            SignUpRequest signUpRequest = new SignUpRequest();
            signUpRequest.name = getFullName().get();
            signUpRequest.email = getEmail().get();
            signUpRequest.password = getPassword().get();
            signUpRequest.result = true;
            signUpRequest.registrationType = 1;
            signUpRequest.deviceToken = "121212121";
            Gson gson = new Gson();

            getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
            createApiRequest(getRetrofitInstance(LoginSignUpAPI.class)
                    .register(gson.toJson(signUpRequest)), new CallBackG<LoginResponse>() {
                @Override
                public void callBack(LoginResponse output) {
                    getView().hideLoading();
                    if (output.status == 1)
                        getView().goToSignIn(output);
                    else
                        getView().displayError(output.message);
                }
            });
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
//                                    getView().hideLoading();
//                                    AccessToken token = AccessToken.getCurrentAccessToken();
                                    facebookLogin(response.getJSONObject());
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

    private void facebookLogin(JSONObject jsonObject) {
        SocialLoginRequest socialLoginRequest = new SocialLoginRequest();
        try {
            socialLoginRequest.socialId = jsonObject.getString("id");
            socialLoginRequest.name = jsonObject.getString("name");
            if (jsonObject.getString("email") != null)
                socialLoginRequest.email = jsonObject.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socialLoginRequest.deviceToken = "1212121212";
        socialLoginRequest.loginType = "1";
        socialLoginRequest.registrationType = 1;

        Gson gson = new Gson();
        createApiRequest(getRetrofitInstance(LoginSignUpAPI.class)
                .socialLogin(gson.toJson(socialLoginRequest)), new CallBackG<LoginResponse>() {
            @Override
            public void callBack(LoginResponse output) {
                getView().hideLoading();
                if (output.status == 1)
                    getView().saveDataLocally(output);
                else
                    getView().displayError(output.message);
            }
        });
    }
}

