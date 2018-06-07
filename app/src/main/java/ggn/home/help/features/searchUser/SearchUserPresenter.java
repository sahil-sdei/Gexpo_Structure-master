package ggn.home.help.features.searchUser;


import com.google.gson.Gson;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.features.userProfile.UserProfileActivity;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.MemoryAPI;
import ggn.home.help.web.apiInterfaces.UserAPI;
import ggn.home.help.web.request.BasicRequest;
import ggn.home.help.web.request.SearchUserRequest;
import ggn.home.help.web.response.CategoryResponse;
import ggn.home.help.web.response.SearchUserResponse;

public class SearchUserPresenter extends BasePresenter<SearchUserView> implements SearchUserAdapterBinder {

    @Override
    public void onItemClicked(SearchUserResponse.Datum datum) {
        UserProfileActivity.start(getView().getActivityG());
    }

    public void searchUser(String keyword) {
        SearchUserRequest searchUserRequest= new SearchUserRequest();
        searchUserRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        searchUserRequest.token = getView().getLocalData().getAuthToken();
        searchUserRequest.page = 1;
        searchUserRequest.keyword = keyword;

        Gson gson = new Gson();

        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        createApiRequest(getRetrofitInstance(UserAPI.class)
                .searchUser(gson.toJson(searchUserRequest)), new CallBackG<SearchUserResponse>() {
            @Override
            public void callBack(SearchUserResponse output) {
                getView().hideLoading();
                if (output.status == 1)
                    getView().showUsers(output);
                else
                    getView().displayError(output.message);
            }
        });
    }
}
