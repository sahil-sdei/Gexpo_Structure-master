package ggn.home.help.features.searchUser;

import ggn.home.help.web.response.SearchUserResponse;

public interface SearchUserAdapterBinder {
    void onItemClicked(SearchUserResponse.Datum datum);
}