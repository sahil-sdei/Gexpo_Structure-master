package ggn.home.help.features.dashboard.myMemories;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.utils.bitmapUtils.ImageLoader;


public class SlidingImageAdapter extends PagerAdapter {


    private List<String> listImages;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImageAdapter(Context context, List<String> listImages) {
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
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.item_image, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout
                .findViewById(R.id.imageViewPicture);
        ImageLoader.loadFullWidthImage(imageView, listImages.get(position));

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