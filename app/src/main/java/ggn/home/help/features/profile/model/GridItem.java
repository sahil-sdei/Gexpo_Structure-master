package ggn.home.help.features.profile.model;

public class GridItem extends Item {

    private int mPosition;
    private String mSubTitle;

    public GridItem(String title, int position) {
        super(title);
        mPosition = position;
    }

    public String getSubTitle() {
        return mSubTitle;
    }

    public void setSubTitle(String subTitle) {
        mSubTitle = subTitle;
    }

    public int getPosition() {
        return mPosition;
    }

    @Override
    public int getItemType() {
        return GRID_ITEM_TYPE;
    }
}