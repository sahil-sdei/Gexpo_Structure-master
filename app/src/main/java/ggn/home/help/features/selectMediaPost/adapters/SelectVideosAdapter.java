package ggn.home.help.features.selectMediaPost.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemPhotosVideosBinding;
import ggn.home.help.features.fullLifeAlbum.PhotoVideoAdapterBinder;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.features.selectMediaPost.PicturesSelectedListener;
import ggn.home.help.features.selectPictures.Pictures;

public class SelectVideosAdapter extends InfiniteAdapterG<ItemPhotosVideosBinding> {

    private List<Pictures> dataList;
    private Context context;
    private PhotoVideoAdapterBinder photoVideoAdapterBinder;
    private DisplayMetrics displayMetrics;
    private PicturesSelectedListener picturesSelectedListener;


    public SelectVideosAdapter(List<Pictures> dataList, Context context, PhotoVideoAdapterBinder photoVideoAdapterBinder) {
        this.dataList = dataList;
        this.context = context;
        this.photoVideoAdapterBinder = photoVideoAdapterBinder;
        displayMetrics = new DisplayMetrics();
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_photos_videos;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(int position, BaseViewHolder baseViewHolder) {
        final Pictures pictures = dataList.get(position);

        baseViewHolder.binding.setPictures(dataList.get(position));
        baseViewHolder.binding.setBinder(photoVideoAdapterBinder);
        baseViewHolder.binding.imageViewVideoIcon.setVisibility(View.VISIBLE);

        if (pictures.isSelected)
            baseViewHolder.binding.relativeLayoutSelected.setVisibility(View.VISIBLE);
        else
            baseViewHolder.binding.relativeLayoutSelected.setVisibility(View.GONE);

        baseViewHolder.binding.relativeLayoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pictures.isSelected) {
                    pictures.isSelected = false;
                    notifyDataSetChanged();
                    return;
                }

                boolean isSelected = false;
                for (Pictures iObj : dataList) {
                    if (iObj.isSelected) {
                        isSelected = true;
                        break;
                    }
                }
                if (isSelected) {
                    pictures.isSelected = !pictures.isSelected;
                    notifyDataSetChanged();
                } else {

                }
            }
        });

        baseViewHolder.binding.relativeLayoutParent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                pictures.isSelected = !pictures.isSelected;
                notifyDataSetChanged();
                return false;
            }
        });

        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        baseViewHolder.binding.relativeLayoutParent.getLayoutParams().height = (width / 3);
        baseViewHolder.binding.relativeLayoutParent.getLayoutParams().width = (width / 3) - 2;

        baseViewHolder.binding.executePendingBindings();
    }

    public void setPicturesSelectedListener(PicturesSelectedListener picturesSelectedListener) {
        this.picturesSelectedListener = picturesSelectedListener;
    }
}
