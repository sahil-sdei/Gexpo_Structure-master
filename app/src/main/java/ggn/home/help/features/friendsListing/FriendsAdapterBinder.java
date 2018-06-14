package ggn.home.help.features.friendsListing;

import ggn.home.help.web.response.AllFriendsResponse;

public interface FriendsAdapterBinder {
    void onItemClicked(AllFriendsResponse.Datum datum);
}