package ggn.home.help.features.editProfile;

import android.databinding.ObservableField;
import android.view.View;

import com.google.gson.Gson;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.UserAPI;
import ggn.home.help.web.request.BasicRequest;
import ggn.home.help.web.request.ChildAccountsRequest;
import ggn.home.help.web.response.ChildAccountsResponse;
import ggn.home.help.web.response.ProfileResponse;

public class EditProfilePresenter extends BasePresenter<EditProfileView> implements EditProfileBinder {
    public ObservableField<String> name =
            new ObservableField<>();
    public ObservableField<String> address =
            new ObservableField<>();
    public ObservableField<String> lifeLesson =
            new ObservableField<>();

    public EditProfilePresenter() {
        name.set("");
        address.set("");
        lifeLesson.set("");
    }

    @Override
    public ObservableField<String> getName() {
        return name;
    }

    @Override
    public ObservableField<String> getGender() {
        return null;
    }

    @Override
    public ObservableField<String> getDOB() {
        return null;
    }

    @Override
    public ObservableField<String> getAddress() {
        return address;
    }

    @Override
    public ObservableField<String> getLifeLesson() {
        return lifeLesson;
    }

    @Override
    public void updateClicked(View view) {
        if (name.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_full_name));
        } else if (address.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_address));
        } else if (lifeLesson.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_life_lesson));
        }  else {
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

    public void getProfile(BasicRequest basicRequest) {
        Gson gson = new Gson();
        createApiRequest(getRetrofitInstance(UserAPI.class)
                .getProfile(gson.toJson(basicRequest)), new CallBackG<ProfileResponse>() {
            @Override
            public void callBack(ProfileResponse output) {
                if (output.status == 1)
                    setProfile(output);
                else
                    getView().displayError(output.message);
            }
        });
    }

    public void setProfile(ProfileResponse profile) {
    }
}
