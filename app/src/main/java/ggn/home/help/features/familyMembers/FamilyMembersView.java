package ggn.home.help.features.familyMembers;


import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.AllFamilyResponse;

public interface FamilyMembersView extends Viewable<FamilyMembersPresenter> {

    void showFamily(AllFamilyResponse output);

    void noDataFound();
}