package ggn.home.help.features.dashboard.myMemories;

import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.ChildAccountsResponse;

public interface MemoriesView extends Viewable<MemoriesPresenter> {
    void onMemoryLiked(Memory memory);

    void showChildAccounts(ChildAccountsResponse output);
}
