package ggn.home.help.utils;

import ggn.home.help.web.response.RelationsResponse;

/**
 * Created by maverick on 28/04/18.
 */

public interface RelationSelectedListener {
    void onRelationSelected(RelationsResponse.Datum datum);
}
