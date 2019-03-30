package ggn.home.help.features.accounts;


import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.ChildAccountsResponse;

public interface ManageAccountsView extends Viewable<ManageAccountsPresenter> {

    void showChildAccounts(ChildAccountsResponse output);

    void accountTransferSuccessfully(BasicResponse output);

    void accountDeletedSuccessfully(BasicResponse output);
}