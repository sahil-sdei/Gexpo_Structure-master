package ggn.home.help.features.dashboard.myMemories;


import com.google.gson.Gson;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.features.profile.FamilyFriendsAdapterBinder;
import ggn.home.help.features.userProfile.UserProfileActivity;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.UserAPI;
import ggn.home.help.web.request.ChildAccountsRequest;
import ggn.home.help.web.response.ChildAccountsResponse;

public class MemoriesPresenter extends BasePresenter<MemoriesView> implements MemoriesAdapterBinder, FamilyFriendsAdapterBinder {

    @Override
    public void onProfileClicked(Memory memory) {
        UserProfileActivity.start(getView().getActivityG());
    }

    @Override
    public void onLikeClicked(Memory memory) {
        memory.isLiked = true;
        getView().onMemoryLiked(memory);
    }

    public void getChildAccounts() {
        ChildAccountsRequest childAccountsRequest = new ChildAccountsRequest();
        childAccountsRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        childAccountsRequest.token = getView().getLocalData().getAuthToken();
        childAccountsRequest.page = 1;

        Gson gson = new Gson();

        createApiRequest(getRetrofitInstance(UserAPI.class)
                .getChildAccounts(gson.toJson(childAccountsRequest)), new CallBackG<ChildAccountsResponse>() {
            @Override
            public void callBack(ChildAccountsResponse output) {
                if (output.status == 1)
                    getView().showChildAccounts(output);
                else
                    getView().displayError(output.message);
            }
        });
    }
}
