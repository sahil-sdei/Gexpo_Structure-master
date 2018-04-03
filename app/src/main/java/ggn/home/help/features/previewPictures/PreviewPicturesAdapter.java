package ggn.home.help.features.previewPictures;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemSelectPicturesBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.features.profile.interfaces.GalleryItemSelected;
import ggn.home.help.features.selectPictures.Pictures;

public class PreviewPicturesAdapter extends InfiniteAdapterG<ItemSelectPicturesBinding> {

    private List<Pictures> dataList;
    private Context context;
    private PreviewPicturesAdapterBinder previewPicturesAdapterBinder;
    private DisplayMetrics displayMetrics;
    private GalleryItemSelected galleryItemSelected;

    public PreviewPicturesAdapter(List<Pictures> dataList, Context context, PreviewPicturesAdapterBinder previewPicturesAdapterBinder) {
        this.dataList = dataList;
        this.context = context;
        this.previewPicturesAdapterBinder = previewPicturesAdapterBinder;
        displayMetrics = new DisplayMetrics();
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_select_pictures;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(final int position, BaseViewHolder baseViewHolder) {
        final Pictures pictures = dataList.get(position);
        baseViewHolder.binding.setData(dataList.get(position));
//        baseViewHolder.binding.setBinder(previewPicturesAdapterBinder);

        if (pictures.isSelected) {
            baseViewHolder.binding.relativeLayoutSelected.setVisibility(View.VISIBLE);
        } else {
            baseViewHolder.binding.relativeLayoutSelected.setVisibility(View.GONE);
        }

        baseViewHolder.binding.relativeLayoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pictures.isSelected) {
                    pictures.isSelected = false;
                    galleryItemSelected.onGalleryItemSelected();
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
                    galleryItemSelected.onGalleryItemSelected();
                    notifyDataSetChanged();
                } else {

                }
            }
        });


        baseViewHolder.binding.relativeLayoutParent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                pictures.isSelected = !pictures.isSelected;
                galleryItemSelected.onGalleryItemSelected();
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

    public void setGalleryItemSelectedListener(GalleryItemSelected galleryItemSelected) {
        this.galleryItemSelected = galleryItemSelected;
    }
}
