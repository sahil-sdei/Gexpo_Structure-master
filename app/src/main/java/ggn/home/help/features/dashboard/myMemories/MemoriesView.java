package ggn.home.help.features.dashboard.myMemories;

import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.ChildAccountsResponse;
import ggn.home.help.web.response.PostsResponse;

public interface MemoriesView extends Viewable<MemoriesPresenter> {
    void onMemoryLiked(PostsResponse.Datum memory);

    void showChildAccounts(ChildAccountsResponse output);

    void showUserPosts(PostsResponse output);

    void openComments(PostsResponse.Datum datum);

    void noDataFound();

}
