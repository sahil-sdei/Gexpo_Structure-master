package ggn.home.help.features.fullLifeAlbum.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CompoundButton;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemFamilyBinding;
import ggn.home.help.databinding.ItemPhotosVideosBinding;
import ggn.home.help.databinding.ItemPhotosVideosFullLifeBinding;
import ggn.home.help.features.fullLifeAlbum.PhotoVideoAdapterBinder;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.features.memoryViewer.MemoryViewerActivity;
import ggn.home.help.features.profile.FamilyFriendsAdapterBinder;
import ggn.home.help.features.selectPictures.Pictures;
import ggn.home.help.utils.Constants;
import ggn.home.help.web.response.FullLifeAlbumResponse;

public class ImagesAdapter extends InfiniteAdapterG<ItemPhotosVideosFullLifeBinding> {

    private List<FullLifeAlbumResponse.Datum> dataList;
    private Context context;
    private PhotoVideoAdapterBinder photoVideoAdapterBinder;
    private DisplayMetrics displayMetrics;

    public ImagesAdapter(List<FullLifeAlbumResponse.Datum> dataList, Context context, PhotoVideoAdapterBinder photoVideoAdapterBinder) {
        this.dataList = dataList;
        this.context = context;
        this.photoVideoAdapterBinder = photoVideoAdapterBinder;
        displayMetrics = new DisplayMetrics();
    }

    public void setPhotoVideoAdapterBinder(PhotoVideoAdapterBinder photoVideoAdapterBinder) {
        this.photoVideoAdapterBinder = photoVideoAdapterBinder;
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_photos_videos_full_life;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(int position, BaseViewHolder baseViewHolder) {
        final FullLifeAlbumResponse.Datum pictures = dataList.get(position);
        baseViewHolder.binding.setData(pictures);
        baseViewHolder.binding.setBinder(photoVideoAdapterBinder);

        if (pictures.isSelected)
            baseViewHolder.binding.relativeLayoutSelected.setVisibility(View.VISIBLE);
        else
            baseViewHolder.binding.relativeLayoutSelected.setVisibility(View.GONE);

        baseViewHolder.binding.relativeLayoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pictures.isSelected) {
                    pictures.isSelected = false;
                    photoVideoAdapterBinder.onPictureVideoSelected(false);
                    notifyDataSetChanged();
                    return;
                }

                boolean isSelected = false;
                for (FullLifeAlbumResponse.Datum iObj : dataList) {
                    if (iObj.isSelected) {
                        isSelected = true;
                        break;
                    }
                }

                if (isSelected) {
                    pictures.isSelected = !pictures.isSelected;
                    notifyDataSetChanged();
                } else {
                    Intent intent = new Intent(context, MemoryViewerActivity.class);
                    intent.putExtra(Constants.Extras.IS_IMAGE, true);
                    intent.putExtra(Constants.Extras.DATA, pictures);
                    intent.putExtra(Constants.Extras.MEDIA_URL, pictures.gallery);
                    context.startActivity(intent);
                }

                photoVideoAdapterBinder.onPictureVideoSelected(isSelected);
            }
        });

        baseViewHolder.binding.relativeLayoutParent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                pictures.isSelected = !pictures.isSelected;
                notifyDataSetChanged();
                photoVideoAdapterBinder.onPictureVideoSelected(pictures.isSelected);
                return false;
            }
        });


        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        baseViewHolder.binding.relativeLayoutParent.getLayoutParams().height = (width / 3);
        baseViewHolder.binding.relativeLayoutParent.getLayoutParams().width = (width / 3) - 2;

        baseViewHolder.binding.executePendingBindings();
    }
}
