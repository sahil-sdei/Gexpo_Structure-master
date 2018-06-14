package ggn.home.help.features.familyListing;


import com.google.gson.Gson;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.features.userProfile.UserProfileActivity;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.UserAPI;
import ggn.home.help.web.request.BasicPageRequest;
import ggn.home.help.web.response.AllFamilyResponse;
import ggn.home.help.web.response.AllFriendsResponse;

public class FamilyPresenter extends BasePresenter<FamilyView> implements FamilyAdapterBinder {

    public void getFamily(int pageNo) {
        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        BasicPageRequest basicPageRequest = new BasicPageRequest();
        basicPageRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        basicPageRequest.token = getView().getLocalData().getAuthToken();
        basicPageRequest.page = pageNo;

        Gson gson = new Gson();
        createApiRequest(getRetrofitInstance(UserAPI.class)
                .getFamilyList(gson.toJson(basicPageRequest)), new CallBackG<AllFamilyResponse>() {
            @Override
            public void callBack(AllFamilyResponse output) {
                getView().hideLoading();
                if (output.status == 1)
                    getView().showFamily(output);
                else {
                    getView().noDataFound();
                }
            }
        });
    }

    @Override
    public void onItemClicked(AllFamilyResponse.Datum datum) {
        UserProfileActivity.start(getView().getActivityG(), datum.id);
    }
}
