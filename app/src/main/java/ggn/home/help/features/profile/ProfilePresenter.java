package ggn.home.help.features.profile;

import com.google.gson.Gson;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.UserAPI;
import ggn.home.help.web.request.BasicRequest;
import ggn.home.help.web.response.ProfileResponse;

public class ProfilePresenter extends BasePresenter<ProfileView> implements FamilyFriendsAdapterBinder {
    public void getProfile(BasicRequest basicRequest) {
        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        Gson gson = new Gson();
        createApiRequest(getRetrofitInstance(UserAPI.class)
                .getProfile(gson.toJson(basicRequest)), new CallBackG<ProfileResponse>() {
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
}
