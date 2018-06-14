package ggn.home.help.features.friendsNFamily;


import com.google.gson.Gson;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.UserAPI;
import ggn.home.help.web.request.AcceptRequest;
import ggn.home.help.web.request.BasicPageRequest;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.FriendRequestsResponse;

public class FriendsFamilyPresenter extends BasePresenter<FriendsFamilyView> implements RequestsAdapterBinder {
    public void getRequests(int page) {
        BasicPageRequest basicPageRequest = new BasicPageRequest();
        basicPageRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        basicPageRequest.token = getView().getLocalData().getAuthToken();
        basicPageRequest.page = page;

        Gson gson = new Gson();

        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        createApiRequest(getRetrofitInstance(UserAPI.class)
                .getRequests(gson.toJson(basicPageRequest)), new CallBackG<FriendRequestsResponse>() {
            @Override
            public void callBack(FriendRequestsResponse output) {
                getView().hideLoading();
                if (output.status == 1)
                    getView().showRequests(output);
                else
                    getView().noRequestsFound();
            }
        });
    }

    @Override
    public void onAccept(FriendRequestsResponse.Datum data) {
        acceptRejectRequest("1", data);
    }

    @Override
    public void onReject(FriendRequestsResponse.Datum data) {
        acceptRejectRequest("0", data);
    }

    public void acceptRejectRequest(String action, final FriendRequestsResponse.Datum data) {
        AcceptRequest acceptRequest = new AcceptRequest();
        acceptRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        acceptRequest.token = getView().getLocalData().getAuthToken();
        acceptRequest.senderUserId = Integer.parseInt(data.id);
        acceptRequest.action = action;

        Gson gson = new Gson();

        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        createApiRequest(getRetrofitInstance(UserAPI.class)
                .acceptRejectRequest(gson.toJson(acceptRequest)), new CallBackG<BasicResponse>() {
            @Override
            public void callBack(BasicResponse output) {
                getView().hideLoading();
                if (output.status == 1) {
                    getView().requestAcceptedRejectedSuccessFully(data);
                }
                getView().displayError(output.message);
            }
        });
    }
}
