package ggn.home.help.features.editProfile;

import android.databinding.ObservableField;
import android.view.View;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;

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
}
