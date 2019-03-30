package ggn.home.help.features.userProfile.model;

import ggn.home.help.web.response.Gallery;

public class HeaderItem extends Gallery {

    public HeaderItem(String title) {
        super(title);
    }

    @Override
    public int getItemType() {
        return HEADER_ITEM_TYPE;
    }
}