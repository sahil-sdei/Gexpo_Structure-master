package ggn.home.help.features.accounts;


import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.ChildAccountsResponse;

public interface ManageAccountsView extends Viewable<ManageAccountsPresenter> {

    void showChildAccounts(ChildAccountsResponse output);
}