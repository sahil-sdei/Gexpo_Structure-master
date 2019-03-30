package ggn.home.help.features.userProfile.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.URLConnection;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.features.memoryViewer.MemoryViewerActivity;
import ggn.home.help.features.profile.interfaces.GalleryItemSelected;
import ggn.home.help.features.profile.view.Holder;
import ggn.home.help.features.userProfile.model.GridItem;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.Gallery;

public class UserProfileGalleryAdapter extends RecyclerView.Adapter<Holder> {

    private final int mDefaultSpanCount;
    private List<Gallery> mItemList;
    private Context context;
    private DisplayMetrics displayMetrics;
    private GalleryItemSelected galleryItemSelected;

    public UserProfileGalleryAdapter(Context context, List<Gallery> itemList, GridLayoutManager gridLayoutManager, int defaultSpanCount) {
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
        return mItemList.get(position).getItemType() == Gallery.HEADER_ITEM_TYPE;
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
        ImageView imageViewVideoIcon = container.findViewById(R.id.imageViewVideoIcon);

        final GridItem item = (GridItem) mItemList.get(position);

        ImageLoader.loadFullWidthImage(imageViewImage, item.mItemTitle);

        if (isImageFile(item.mItemTitle)) {
            imageViewVideoIcon.setVisibility(View.GONE);
        } else {
            imageViewVideoIcon.setVisibility(View.VISIBLE);
        }

        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        relativeLayoutParent.getLayoutParams().height = (width / 3);
        relativeLayoutParent.getLayoutParams().width = (width / 3) - 2;

        relativeLayoutSelected.setVisibility(View.GONE);

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MemoryViewerActivity.class);
                intent.putExtra(Constants.Extras.IS_IMAGE, isImageFile(item.mItemTitle));
                intent.putExtra(Constants.Extras.MEDIA_URL, item.mItemTitle);
                context.startActivity(intent);
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
        title.setText(mItemList.get(position).mItemTitle);
    }

    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position).getItemType() == Gallery.HEADER_ITEM_TYPE ? 0 : 1;
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
    public void addItem(Gallery item) {
        mItemList.add(item);
        notifyDataSetChanged();
    }

    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }
}
