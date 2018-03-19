package ggn.home.help.features.profile.model;


public class HeaderItem extends Item {

    public HeaderItem(String title) {
        super(title);
    }

    @Override
    public int getItemType() {
        return HEADER_ITEM_TYPE;
    }
}
