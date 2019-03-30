package ggn.home.help.features.accounts;

import ggn.home.help.web.response.Child;

public interface ManageAccountsAdapterBinder {
    void onTransfer(Child child);

    void onDelete(Child child);

}