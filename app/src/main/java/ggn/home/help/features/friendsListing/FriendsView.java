package ggn.home.help.features.friendsListing;


import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.AllFriendsResponse;

public interface FriendsView extends Viewable<FriendsPresenter> {
    void noDataFound();

    void showFriends(AllFriendsResponse output);
}