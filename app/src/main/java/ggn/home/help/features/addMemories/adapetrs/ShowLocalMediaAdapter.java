package ggn.home.help.features.addMemories.adapetrs;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;

import java.net.URLConnection;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FlowItemImageBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.features.searchUser.SearchUserAdapterBinder;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.FullLifeAlbumResponse;

public class ShowLocalMediaAdapter extends InfiniteAdapterG<FlowItemImageBinding> {

    private List<String> dataList;
    private Context context;
    private DisplayMetrics displayMetrics;


    public ShowLocalMediaAdapter(List<String> dataList, Context context) {
        this.dataList = dataList;
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
        baseViewHolder.binding.setDataLocal(dataList.get(position));
        ImageLoader.loadFullImageSDCard(baseViewHolder.binding.imageViewPicture, dataList.get(position));

//        if (position == 0) {
//            baseViewHolder.binding.imageViewDelete.setVisibility(View.GONE);
//        } else {
        baseViewHolder.binding.imageViewDelete.setVisibility(View.VISIBLE);
//        }

        if (isImageFile(dataList.get(position))) {
            baseViewHolder.binding.imageViewVideoIcon.setVisibility(View.GONE);
        } else if (isVideoFile(dataList.get(position))) {
            baseViewHolder.binding.imageViewVideoIcon.setVisibility(View.VISIBLE);
        }

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

    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    public static boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }
}
