package ggn.home.help.features.addPostPreview;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.FullLifeAlbumResponse;


public class SlidingPostPreviewAdapter extends PagerAdapter {


    private List<FullLifeAlbumResponse.Datum> listImages;
    private LayoutInflater inflater;
    private Context context;


    public SlidingPostPreviewAdapter(Context context, List<FullLifeAlbumResponse.Datum> listImages) {
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
        View imageLayout = inflater.inflate(R.layout.item_post_preview, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout
                .findViewById(R.id.imageViewPicture);
        final TextView textViewTitle= imageLayout
                .findViewById(R.id.textViewTitle);
        final TextView textViewCategory= imageLayout
                .findViewById(R.id.textViewCategory);
        final ImageView imageViewVideoIcon = imageLayout
                .findViewById(R.id.imageViewVideoIcon);

        ImageLoader.loadFullWidthImage(imageView, listImages.get(position).gallery);
        textViewTitle.setText(listImages.get(position).title);
        textViewCategory.setText(listImages.get(position).categoryName+" > "+listImages.get(position).subCategoryName);


        if(listImages.get(position).memoryType.equalsIgnoreCase("image")){
            imageViewVideoIcon.setVisibility(View.GONE);
        }else{
            imageViewVideoIcon.setVisibility(View.VISIBLE);
        }

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