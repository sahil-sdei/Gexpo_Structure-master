package ggn.home.help.features.familyListing;


import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.AllFamilyResponse;
import ggn.home.help.web.response.AllFriendsResponse;

public interface FamilyView extends Viewable<FamilyPresenter> {
    void noDataFound();

    void showFamily(AllFamilyResponse output);
}