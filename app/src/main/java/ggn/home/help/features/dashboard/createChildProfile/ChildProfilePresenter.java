package ggn.home.help.features.dashboard.createChildProfile;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import com.google.gson.Gson;

import java.io.File;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.MemoryAPI;
import ggn.home.help.web.apiInterfaces.UserAPI;
import ggn.home.help.web.request.AddChildRequest;
import ggn.home.help.web.request.BasicRequest;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.CategoryResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

class ChildProfilePresenter extends BasePresenter<ChildProfileView> implements CreateChildProfileBinder, RadioButtonHandler {

    private String imagePath;

    public ObservableField<String> name =
            new ObservableField<>();
    public ObservableField<String> nickName =
            new ObservableField<>();
    public ObservableField<String> dob =
            new ObservableField<>();
    public ObservableField<String> gender =
            new ObservableField<>();
    public ObservableField<String> address =
            new ObservableField<>();
    public ObservableField<String> lifeLesson =
            new ObservableField<>();

    public ChildProfilePresenter() {
        name.set("");
        nickName.set("");
        dob.set("");
        gender.set("Male");
        address.set("");
        lifeLesson.set("");
    }

    @Override
    public ObservableField<String> getName() {
        return name;
    }

    @Override
    public ObservableField<String> getNickName() {
        return nickName;
    }

    @Override
    public ObservableField<String> getDOB() {
        return dob;
    }

    @Override
    public ObservableField<String> getGender() {
        return gender;
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
    public void saveClicked(final View view) {
        if (name.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_child_name));
        } else if (nickName.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_nick_name));
        } else if (dob.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_select_dob));
        } else if (address.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_address));
        } else if (lifeLesson.get().isEmpty()) {
            getView().displayError(getView().getActivityG().getString(R.string.please_enter_life_lesson));
        } else {
            AddChildRequest addChildRequest = new AddChildRequest();
            addChildRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
            addChildRequest.token = getView().getLocalData().getAuthToken();
            addChildRequest.name = name.get();
            addChildRequest.nickname = nickName.get();
            addChildRequest.dob = dob.get();
            addChildRequest.gender = gender.get();
            addChildRequest.address = address.get();
            addChildRequest.lifeLession = lifeLesson.get();

            MultipartBody.Part imageFileBody = null;
            if (!TextUtils.isEmpty(imagePath)) {
                File file = new File(imagePath);
                RequestBody fbody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                imageFileBody = MultipartBody.Part.createFormData("image", file.getName(), fbody);
            }
            Gson gson = new Gson();
            RequestBody details = RequestBody.create(MediaType.parse("text/plain"), gson.toJson(addChildRequest));

            getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
            createApiRequest(getRetrofitInstance(UserAPI.class)
                    .addChildProfile(imageFileBody, details), new CallBackG<BasicResponse>() {
                @Override
                public void callBack(BasicResponse output) {
                    getView().hideLoading();
                    if (output.status == 1) {
                        getView().hideKeyboard(view);
                        getView().displayError(output.message);
                        name.set("");
                        nickName.set("");
                        dob.set("");
                        gender.set("Male");
                        address.set("");
                        lifeLesson.set("");
                        getView().onCreatedSuccessfully();
                    } else
                        getView().displayError(output.message);
                }
            });
        }
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
