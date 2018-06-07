package ggn.home.help.features.searchUser;


import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.SearchUserResponse;

public interface SearchUserView extends Viewable<SearchUserPresenter> {

    void showUsers(SearchUserResponse output);
}