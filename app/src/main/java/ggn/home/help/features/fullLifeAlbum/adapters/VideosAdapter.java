package ggn.home.help.features.fullLifeAlbum.adapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemPhotosVideosBinding;
import ggn.home.help.features.fullLifeAlbum.PhotoVideoAdapterBinder;
import ggn.home.help.features.internal.base.InfiniteAdapterG;

public class VideosAdapter extends InfiniteAdapterG<ItemPhotosVideosBinding> {

    private List<String> dataList;
    private Context context;
    private PhotoVideoAdapterBinder photoVideoAdapterBinder;
    private DisplayMetrics displayMetrics;


    public VideosAdapter(List<String> dataList, Context context, PhotoVideoAdapterBinder photoVideoAdapterBinder) {
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
        baseViewHolder.binding.imageViewVideoIcon.setVisibility(View.VISIBLE);
        baseViewHolder.binding.executePendingBindings();
    }
}
