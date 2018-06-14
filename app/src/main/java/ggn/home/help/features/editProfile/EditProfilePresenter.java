package ggn.home.help.features.editProfile;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import com.google.gson.Gson;

import java.io.File;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.utils.DateHelper;
import ggn.home.help.web.apiInterfaces.UserAPI;
import ggn.home.help.web.request.BasicRequest;
import ggn.home.help.web.request.EditProfileRequest;
import ggn.home.help.web.request.ProfileRequest;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.ProfileResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfilePresenter extends BasePresenter<EditProfileView> implements EditProfileBinder, RadioButtonHandler {
    private String imagePath;

    public ObservableField<String> name =
            new ObservableField<>();
    public ObservableField<String> address =
            new ObservableField<>();
    public ObservableField<String> lifeLesson =
            new ObservableField<>();
    public ObservableField<String> dob =
            new ObservableField<>();
    public ObservableField<String> gender =
            new ObservableField<>();

    @Override
    public ObservableField<String> getName() {
        return name;
    }

    @Override
    public ObservableField<String> getGender() {
        return gender;
    }

    @Override
    public ObservableField<String> getDOB() {
        return dob;
    }

    @Override
    public ObservableField<String> getAddress() {
        return address;
    }

    @Override
    public ObservableField<String> getLifeLesson() {
        return lifeLesson;
    }

    public EditProfilePresenter() {
        name.set("");
        dob.set("");
        gender.set("Male");
        address.set("");
        lifeLesson.set("");
    }

    @Override
    public void updateClicked(final View view) {
        if (name.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_full_name));
        } else if (dob.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_select_dob));
        } else if (address.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_address));
        } else if (lifeLesson.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_life_lesson));
        } else {
            getView().hideKeyboard(view);
            EditProfileRequest editProfileRequest = new EditProfileRequest();
            editProfileRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
            editProfileRequest.token = getView().getLocalData().getAuthToken();
            editProfileRequest.name = name.get();

            editProfileRequest.dob = DateHelper.getInstance().getMyDateFormat(dob.get(), DateHelper.DOB, DateHelper.ServerBasic);
            editProfileRequest.gender = gender.get();
            editProfileRequest.address = address.get();
            editProfileRequest.lifeLession = lifeLesson.get();

            MultipartBody.Part imageFileBody = null;
            if (!TextUtils.isEmpty(imagePath)) {
                File file = new File(imagePath);
                RequestBody fbody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                imageFileBody = MultipartBody.Part.createFormData("image", file.getName(), fbody);
            }
            Gson gson = new Gson();
            RequestBody details = RequestBody.create(MediaType.parse("text/plain"), gson.toJson(editProfileRequest));

            getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
            createApiRequest(getRetrofitInstance(UserAPI.class)
                    .editProfile(imageFileBody, details), new CallBackG<BasicResponse>() {
                @Override
                public void callBack(BasicResponse output) {
                    getView().hideLoading();
                    if(output.status == 1){
                        getView().editProfileSuccessful();
                    }else {
                        getView().displayError(output.message);
                    }
                }
            });
        }
    }


    public void getProfile(ProfileRequest profileRequest) {
        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        Gson gson = new Gson();
        createApiRequest(getRetrofitInstance(UserAPI.class)
                .getProfile(gson.toJson(profileRequest)), new CallBackG<ProfileResponse>() {
            @Override
            public void callBack(ProfileResponse output) {
                getView().hideLoading();
                if (output.status == 1)
                    getView().showProfileData(output);
                else
                    getView().displayError(output.message);
            }
        });
    }

    public void setProfile(ProfileResponse profile) {
        name.set(profile.about.name);
        dob.set(profile.about.dob);
        address.set(profile.about.address);
        lifeLesson.set(profile.about.lifeLession);
    }

    @Override
    public void onGenderChanged(RadioGroup radioGroup, int id) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        switch (selectedId) {
            case R.id.radioButtonMale:
                gender.set("Male");
                break;
            case R.id.radioButtonFemale:
                gender.set("Female");
                break;
        }
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
