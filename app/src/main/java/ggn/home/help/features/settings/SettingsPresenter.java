package ggn.home.help.features.settings;


import com.google.gson.Gson;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.UserAPI;
import ggn.home.help.web.request.BasicRequest;
import ggn.home.help.web.request.UserPrivacyRequest;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.UserPrivacyResponse;

public class SettingsPresenter extends BasePresenter<SettingsView> {
    public void getPrivacy() {
        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        BasicRequest basicRequest = new BasicRequest();
        basicRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        basicRequest.token = getView().getLocalData().getAuthToken();

        Gson gson = new Gson();
        createApiRequest(getRetrofitInstance(UserAPI.class)
                .getPrivacy(gson.toJson(basicRequest)), new CallBackG<UserPrivacyResponse>() {
            @Override
            public void callBack(UserPrivacyResponse output) {
                getView().hideLoading();
                if (output.status == 1)
                    getView().showUserPrivacy(output);
            }
        });
    }

    public void setPrivacy(String privacy) {
        UserPrivacyRequest userPrivacyRequest= new UserPrivacyRequest();
        userPrivacyRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        userPrivacyRequest.token = getView().getLocalData().getAuthToken();
        userPrivacyRequest.privacy = privacy;

        Gson gson = new Gson();
        createApiRequest(getRetrofitInstance(UserAPI.class)
                .setPrivacy(gson.toJson(userPrivacyRequest)), new CallBackG<BasicResponse>() {
            @Override
            public void callBack(BasicResponse output) {
                getView().hideLoading();
                if (output.status == 1)
                    getView().displayError(output.message);
            }
        });
    }
}
