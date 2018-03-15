package ggn.home.help.features.profile;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemProfileGalleryBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;

public class ProfileGalleryAdapter extends InfiniteAdapterG<ItemProfileGalleryBinding> {

    private List<String> dataList;
    private Context context;
    DisplayMetrics displayMetrics;

    public ProfileGalleryAdapter(List<String> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
        displayMetrics = new DisplayMetrics();
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_profile_gallery;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(int position, BaseViewHolder baseViewHolder) {
        baseViewHolder.binding.setData(dataList.get(position));
//        baseViewHolder.binding.setBinder(playlistAdapterBinder);

        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        baseViewHolder.binding.linearLayout.getLayoutParams().height = (width / 3);
        baseViewHolder.binding.linearLayout.getLayoutParams().width = (width / 3) - 2;

        baseViewHolder.binding.executePendingBindings();
    }
}
