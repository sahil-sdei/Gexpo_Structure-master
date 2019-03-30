package ggn.home.help.features.addMemories.adapetrs;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FlowItemImageBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.features.searchUser.SearchUserAdapterBinder;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.FullLifeAlbumResponse;

public class ShowMediaAdapter extends InfiniteAdapterG<FlowItemImageBinding> {

    private List<FullLifeAlbumResponse.Datum> dataList;
    private SearchUserAdapterBinder searchUserAdapterBinder;
    private Context context;
    private DisplayMetrics displayMetrics;


    public ShowMediaAdapter(List<FullLifeAlbumResponse.Datum> dataList, Context context, SearchUserAdapterBinder searchUserAdapterBinder) {
        this.dataList = dataList;
        this.searchUserAdapterBinder = searchUserAdapterBinder;
        this.context = context;
        displayMetrics = new DisplayMetrics();

    }

    @Override
    public int getInflateLayout() {
        return R.layout.flow_item_image;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(final int position, BaseViewHolder baseViewHolder) {
        baseViewHolder.binding.setData(dataList.get(position));
        ImageLoader.loadFullWidthImage(baseViewHolder.binding.imageViewPicture, dataList.get(position).gallery);

//        if (position == 0) {
//            baseViewHolder.binding.imageViewDelete.setVisibility(View.GONE);
//        } else {
            baseViewHolder.binding.imageViewDelete.setVisibility(View.VISIBLE);
//        }

        if (dataList.get(position).memoryType.equalsIgnoreCase("image")) {
            baseViewHolder.binding.imageViewVideoIcon.setVisibility(View.GONE);
        } else {
            baseViewHolder.binding.imageViewVideoIcon.setVisibility(View.VISIBLE);
        }
//        baseViewHolder.binding.setBinder(searchUserAdapterBinder);

        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        baseViewHolder.binding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataList.remove(position);
                notifyDataSetChanged();
            }
        });

        baseViewHolder.binding.frameLayoutParent.getLayoutParams().height = (width / 3);
        baseViewHolder.binding.frameLayoutParent.getLayoutParams().width = (width / 4) - 2;

        baseViewHolder.binding.executePendingBindings();
    }
}
