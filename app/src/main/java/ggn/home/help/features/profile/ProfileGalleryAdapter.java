package ggn.home.help.features.profile;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.features.profile.interfaces.GalleryItemSelected;
import ggn.home.help.features.profile.model.GridItem;
import ggn.home.help.features.profile.model.Item;
import ggn.home.help.features.profile.view.Holder;

public class ProfileGalleryAdapter extends RecyclerView.Adapter<Holder> {

    private final int mDefaultSpanCount;
    private List<Item> mItemList;
    private Context context;
    private DisplayMetrics displayMetrics;
    private GalleryItemSelected galleryItemSelected;

    public ProfileGalleryAdapter(Context context, List<Item> itemList, GridLayoutManager gridLayoutManager, int defaultSpanCount) {
        this.context = context;
        mItemList = itemList;
        mDefaultSpanCount = defaultSpanCount;
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return isHeaderType(position) ? mDefaultSpanCount : 1;
            }
        });
        displayMetrics = new DisplayMetrics();
    }

    private boolean isHeaderType(int position) {
        return mItemList.get(position).getItemType() == Item.HEADER_ITEM_TYPE ? true : false;
    }

    public void setGalleryItemSelectedListener(GalleryItemSelected galleryItemSelected) {
        this.galleryItemSelected = galleryItemSelected;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view;

        if (viewType == 0) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery_header, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_profile_gallery, viewGroup, false);
        }

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if (isHeaderType(position)) {
            bindHeaderItem(holder, position);
        } else {
            bindGridItem(holder, position);
        }
    }

    /**
     * This method is used to bind grid item value
     *
     * @param holder
     * @param position
     */
    private void bindGridItem(Holder holder, final int position) {

        View container = holder.itemView;

        RelativeLayout relativeLayoutParent = container.findViewById(R.id.relativeLayoutParent);
        RelativeLayout relativeLayoutSelected = container.findViewById(R.id.relativeLayoutSelected);
        ImageView imageViewImage = container.findViewById(R.id.imageViewPicture);

        final GridItem item = (GridItem) mItemList.get(position);

        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        relativeLayoutParent.getLayoutParams().height = (width / 3);
        relativeLayoutParent.getLayoutParams().width = (width / 3) - 2;

        relativeLayoutSelected.setVisibility(item.isSelected() ? View.VISIBLE : View.GONE);

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.isSelected()) {
                    item.setSelected(false);
                    notifyDataSetChanged();
                    galleryItemSelected.onGalleryItemSelected();
                    return;
                }

                boolean isSelected = false;
                for (Item iObj : mItemList) {
                    if (iObj.isSelected()) {
                        isSelected = true;
                        break;
                    }
                }
                if (isSelected) {
                    item.setSelected(!item.isSelected());
                    galleryItemSelected.onGalleryItemSelected();
                    notifyDataSetChanged();
                } else
                    Toast.makeText(view.getContext(), "You clicked on the " + item.getItemTitle() + " at Position -> " + item.getPosition() + "!", Toast.LENGTH_SHORT).show();
            }
        });


        container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                item.setSelected(!item.isSelected());
                galleryItemSelected.onGalleryItemSelected();
                notifyDataSetChanged();
                return false;
            }
        });
    }

    /**
     * This method is used to bind the header with the corresponding item position information
     *
     * @param holder
     * @param position
     */
    private void bindHeaderItem(Holder holder, int position) {
        TextView title = holder.itemView.findViewById(R.id.textViewTitle);
        title.setText(mItemList.get(position).getItemTitle());
    }

    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position).getItemType() == Item.HEADER_ITEM_TYPE ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    /**
     * This method is used to add an item into the recyclerview list
     *
     * @param item
     */
    public void addItem(Item item) {
        mItemList.add(item);
        notifyDataSetChanged();
    }

    /**
     * This method is used to remove items from the list
     *
     * @param item {@link Item}
     */
    public void removeItem(Item item) {
        mItemList.remove(item);
        notifyDataSetChanged();
    }
}
