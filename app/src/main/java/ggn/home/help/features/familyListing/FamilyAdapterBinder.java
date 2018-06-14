package ggn.home.help.features.familyListing;

import ggn.home.help.web.response.AllFamilyResponse;

public interface FamilyAdapterBinder {
    void onItemClicked(AllFamilyResponse.Datum datum);
}