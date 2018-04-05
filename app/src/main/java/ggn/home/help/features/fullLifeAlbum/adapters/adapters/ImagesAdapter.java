package ggn.home.help.features.fullLifeAlbum.adapters.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemPhotosVideosBinding;
import ggn.home.help.features.fullLifeAlbum.PhotoVideoAdapterBinder;
import ggn.home.help.features.internal.base.InfiniteAdapterG;

public class ImagesAdapter extends InfiniteAdapterG<ItemPhotosVideosBinding> {

    private List<String> dataList;
    private Context context;
    private PhotoVideoAdapterBinder photoVideoAdapterBinder;
    private DisplayMetrics displayMetrics;

    public ImagesAdapter(List<String> dataList, Context context, PhotoVideoAdapterBinder photoVideoAdapterBinder) {
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
        baseViewHolder.binding.setData(dataList.get(position));
        baseViewHolder.binding.setBinder(photoVideoAdapterBinder);

        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        baseViewHolder.binding.relativeLayoutParent.getLayoutParams().height = (width / 3);
        baseViewHolder.binding.relativeLayoutParent.getLayoutParams().width = (width / 3) - 2;

        baseViewHolder.binding.executePendingBindings();
    }
}
