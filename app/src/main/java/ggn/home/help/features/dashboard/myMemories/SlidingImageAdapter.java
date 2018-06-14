package ggn.home.help.features.dashboard.myMemories;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.features.memoryViewer.MemoryViewerActivity;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.Gallery;


public class SlidingImageAdapter extends PagerAdapter {


    private List<Gallery> listImages;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImageAdapter(Context context, List<Gallery> listImages) {
        this.context = context;
        this.listImages = listImages;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return listImages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.item_image, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout
                .findViewById(R.id.imageViewPicture);
        ImageView imageViewVideoIcon = imageLayout
                .findViewById(R.id.imageViewVideoIcon);
        ImageLoader.loadFullWidthImage(imageView, listImages.get(position).galleryImage);

        if(listImages.get(position).memoryType.equalsIgnoreCase("image")){
            imageViewVideoIcon.setVisibility(View.GONE);
        }else{
            imageViewVideoIcon.setVisibility(View.VISIBLE);
        }

        imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MemoryViewerActivity.class);
                if (!TextUtils.isEmpty(listImages.get(position).memoryType))
                    if (listImages.get(position).memoryType.equalsIgnoreCase("image"))
                        intent.putExtra(Constants.Extras.IS_IMAGE, true);
                    else
                        intent.putExtra(Constants.Extras.IS_IMAGE, false);
                intent.putExtra(Constants.Extras.GALLERY_DATA, listImages.get(position));
                intent.putExtra(Constants.Extras.MEDIA_URL, listImages.get(position).galleryImage);
                context.startActivity(intent);
            }
        });

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}