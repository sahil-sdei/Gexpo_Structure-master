package ggn.home.help.features.signIn;

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

public class SignInPresenter extends BasePresenter<SignInView> implements SignInBinder {
    public ObservableField<String> email =
            new ObservableField<>();
    public ObservableField<String> password =
            new ObservableField<>();

    public SignInPresenter() {
        email.set("");
        password.set("");
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
    public void signInClicked(View view) {
        DashboardActivity.start(getView().getActivityG());

//        if (email.get().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.get()).matches()) {
//            getView().displayError(getView().getActivityG().getString(R.string.please_enter_valid_email));
//        } else if (password.get().isEmpty()) {
//            getView().displayError(getView().getActivityG().getString(R.string.please_enter_password));
//        } else {
//            getView().hideKeyboard(view);
//            DashboardActivity.start(getView().getActivityG());
////                getView().showLoading(getView().getActivityG().getString(R.string.validating_user), getView().getActivityG().getString(R.string.please_wait));
////                createApiRequest(getRetrofitInstance(LoginSignUpAPI.class)
////                        .login(getEmail().get(), getPassword().get()), new CallBackG<UserModel>() {
////                    @Override
////                    public void callBack(UserModel output) {
////                        getView().saveDataLocally(output);
////                        DashboardActivity.start(getView().getActivityG());
////                    }
////                });
////        }
//        }
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
