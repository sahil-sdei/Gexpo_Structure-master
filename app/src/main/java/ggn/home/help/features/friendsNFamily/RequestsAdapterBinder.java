package ggn.home.help.features.friendsNFamily;

import ggn.home.help.web.response.FriendRequestsResponse;

public interface RequestsAdapterBinder {
    void onAccept(FriendRequestsResponse.Datum data);

    void onReject(FriendRequestsResponse.Datum data);

}