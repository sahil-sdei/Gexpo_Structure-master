package ggn.home.help.features.friendsListing;


import com.google.gson.Gson;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.features.userProfile.UserProfileActivity;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.UserAPI;
import ggn.home.help.web.request.BasicPageRequest;
import ggn.home.help.web.response.AllFriendsResponse;

public class FriendsPresenter extends BasePresenter<FriendsView> implements FriendsAdapterBinder {

    public void getFriends(int pageNo) {
        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        BasicPageRequest basicPageRequest = new BasicPageRequest();
        basicPageRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        basicPageRequest.token = getView().getLocalData().getAuthToken();
        basicPageRequest.page = pageNo;

        Gson gson = new Gson();
        createApiRequest(getRetrofitInstance(UserAPI.class)
                .getFriendsList(gson.toJson(basicPageRequest)), new CallBackG<AllFriendsResponse>() {
            @Override
            public void callBack(AllFriendsResponse output) {
                getView().hideLoading();
                if (output.status == 1)
                    getView().showFriends(output);
                else {
                    getView().noDataFound();
                }
            }
        });
    }

    @Override
    public void onItemClicked(AllFriendsResponse.Datum datum) {
        UserProfileActivity.start(getView().getActivityG(), datum.id);
    }
}
