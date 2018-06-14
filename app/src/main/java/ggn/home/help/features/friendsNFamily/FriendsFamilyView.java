package ggn.home.help.features.friendsNFamily;

import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.FriendRequestsResponse;

public interface FriendsFamilyView extends Viewable<FriendsFamilyPresenter> {
    void showRequests(FriendRequestsResponse output);

    void noRequestsFound();

    void requestAcceptedRejectedSuccessFully(FriendRequestsResponse.Datum datum);
}
