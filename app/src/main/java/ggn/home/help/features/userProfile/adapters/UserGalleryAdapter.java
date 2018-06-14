package ggn.home.help.features.userProfile.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemUserGalleryBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.features.memoryViewer.MemoryViewerActivity;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.UtillsG;
import ggn.home.help.web.response.FullLifeAlbumResponse;
import ggn.home.help.web.response.Gallery;

public class UserGalleryAdapter extends InfiniteAdapterG<ItemUserGalleryBinding> {

    private List<Gallery> dataList;
    private Context context;
    private DisplayMetrics displayMetrics;

    public UserGalleryAdapter(List<Gallery> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
        displayMetrics = new DisplayMetrics();
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_user_gallery;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(int position, BaseViewHolder baseViewHolder) {
        final Gallery gallery = dataList.get(position);
        baseViewHolder.binding.setData(gallery);

        if(gallery.memoryType.equalsIgnoreCase("image")){
            baseViewHolder.binding.imageViewVideoIcon.setVisibility(View.GONE);
        }else{
            baseViewHolder.binding.imageViewVideoIcon.setVisibility(View.VISIBLE);
        }

        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        baseViewHolder.binding.relativeLayoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MemoryViewerActivity.class);
                if (!TextUtils.isEmpty(gallery.memoryType))
                    if (gallery.memoryType.equalsIgnoreCase("image"))
                        intent.putExtra(Constants.Extras.IS_IMAGE, true);
                    else
                        intent.putExtra(Constants.Extras.IS_IMAGE, false);
                intent.putExtra(Constants.Extras.GALLERY_DATA, gallery);
                intent.putExtra(Constants.Extras.MEDIA_URL, gallery.gallery);
                context.startActivity(intent);
            }
        });

        baseViewHolder.binding.relativeLayoutParent.getLayoutParams().height = (width / 3);
        baseViewHolder.binding.relativeLayoutParent.getLayoutParams().width = (width / 3) - UtillsG.dpToPx(1);

        baseViewHolder.binding.executePendingBindings();
    }
}
