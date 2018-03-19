package ggn.home.help.features.profile.model;


public abstract class Item {

    public static final int HEADER_ITEM_TYPE = 0;
    public static final int GRID_ITEM_TYPE = 1;
    private String mItemTitle;
    private boolean isSelected;

    public Item(String title) {
        mItemTitle = title;
    }

    public String getItemTitle() {
        return mItemTitle;
    }

    public abstract int getItemType();

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
