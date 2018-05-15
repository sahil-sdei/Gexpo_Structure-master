package ggn.home.help.features.accounts;


import com.google.gson.Gson;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.UserAPI;
import ggn.home.help.web.request.ChildAccountsRequest;
import ggn.home.help.web.response.ChildAccountsResponse;

public class ManageAccountsPresenter extends BasePresenter<ManageAccountsView> implements ManageAccountsAdapterBinder {

    public void getChildAccounts() {
        ChildAccountsRequest childAccountsRequest = new ChildAccountsRequest();
        childAccountsRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        childAccountsRequest.token = getView().getLocalData().getAuthToken();
        childAccountsRequest.page = 1;

        Gson gson = new Gson();

        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        createApiRequest(getRetrofitInstance(UserAPI.class)
                .getChildAccounts(gson.toJson(childAccountsRequest)), new CallBackG<ChildAccountsResponse>() {
            @Override
            public void callBack(ChildAccountsResponse output) {
                getView().hideLoading();
                if (output.status == 1)
                    getView().showChildAccounts(output);
                else
                    getView().displayError(output.message);
            }
        });
    }
}
