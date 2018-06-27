package ggn.home.help.utils.bitmapUtils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import ggn.home.help.R;

public class ImageLoader {
    private static final ImageLoader ourInstance = new ImageLoader();

    public static ImageLoader getInstance() {
        return ourInstance;
    }

    private ImageLoader() {
    }

    @BindingAdapter({"bind:urlSquare"})
    public static void loadImageSmall(View imageView, String url) {
        if (url == null || url.isEmpty()) {
            return;
        }
        if (!url.contains("http")) {
            int id = (imageView).getContext().getResources().getIdentifier(url, "drawable", imageView.getContext().getPackageName());
            ((ImageView) imageView).setImageResource(id);
        } else {

            Glide
                    .with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).override(50, 50)
                    )
                    .into(((ImageView) imageView));
        }
    }

    @BindingAdapter({"bind:urlUser"})
    public static void loadUserImageSmall(View imageView, String url) {
        if (url == null || url.isEmpty()) {
            return;
        }
        if (!url.contains("http")) {
            int id = (imageView).getContext().getResources().getIdentifier(url, "drawable", imageView.getContext().getPackageName());
            ((ImageView) imageView).setImageResource(id);
        } else {

            Glide
                    .with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_user_placeholder).error(R.drawable.ic_user_placeholder).override(50, 50)
                    )
                    .into(((ImageView) imageView));
        }
    }

    @BindingAdapter({"bind:small"})
    public static void loadImageVerySmall(View imageView, String url) {
        if (url == null || url.isEmpty()) {
            return;
        }

        if (!url.contains("http")) {
            int id = (imageView).getContext().getResources().getIdentifier(url, "drawable", imageView.getContext().getPackageName());
            ((ImageView) imageView).setImageResource(id);
        } else {
            Glide
                    .with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions().centerInside().diskCacheStrategy(DiskCacheStrategy.ALL).override(100, 100))
                    .into(((ImageView) imageView));
        }
    }


    @BindingAdapter({"bind:urlHome"})
    public static void loadHomeImage(View imageView, String url) {

        if (url == null || url.isEmpty()) {
            return;
        }
        if (!url.contains("http")) {
            int id = (imageView).getContext().getResources().getIdentifier(url, "drawable", imageView.getContext().getPackageName());
            ((ImageView) imageView).setImageResource(id);
        } else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.override(200, 200);
            requestOptions.transforms(new CenterCrop());
            Glide
                    .with(imageView.getContext())
                    .load(url)
                    .apply(requestOptions)
                    .into(((ImageView) imageView));
        }

    }

    @BindingAdapter({"bind:artSmall"})
    public static void artSmall(View imageView, String url) {

        if (url == null || url.isEmpty()) {
            return;
        }
        if (!url.contains("http")) {
            int id = (imageView).getContext().getResources().getIdentifier(url, "drawable", imageView.getContext().getPackageName());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.override(100, 100);
            requestOptions.transforms(new CenterCrop(), new RoundedCorners(10));
            Glide.with(imageView.getContext()).load(id).apply(requestOptions).into((ImageView) imageView);
        } else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.override(100, 100);
            requestOptions.transforms(new CenterCrop(), new RoundedCorners(10));
            Glide
                    .with(imageView.getContext())
                    .load(url)
                    .apply(requestOptions)
                    .into(((ImageView) imageView));
        }

    }

    @BindingAdapter({"bind:urlFullWidth"})
    public static void loadFullWidthImage(View imageView, String url) {

        if (url == null || url.isEmpty()) {
            return;
        }
        if (!url.contains("http")) {
            int id = (imageView).getContext().getResources().getIdentifier(url, "drawable", imageView.getContext().getPackageName());
            ((ImageView) imageView).setImageResource(id);
        } else {

            Glide
                    .with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(((ImageView) imageView));
        }

    }

    public static void loadFullImage(View imageView, String url) {

        if (url == null || url.isEmpty()) {
            return;
        }
        if (!url.contains("http")) {
            int id = (imageView).getContext().getResources().getIdentifier(url, "drawable", imageView.getContext().getPackageName());
            ((ImageView) imageView).setImageResource(id);
        } else {

            Glide
                    .with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions().centerInside().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(((ImageView) imageView));
        }

    }

    @BindingAdapter({"bind:fullWidthBG"})
    public static void loadFullWidthImageBG(View background, String image) {

        if (image == null || image.isEmpty()) {
            return;
        }
        int id = (background).getContext().getResources().getIdentifier(image, "drawable", background.getContext().getPackageName());
        background.setBackgroundResource(id);
    }


    @BindingAdapter({"android:src"})
    public static void loadResImage(ImageView view, Drawable resDrawable) {
        view.setImageDrawable(resDrawable);
    }

    @BindingAdapter({"android:src"})
    public static void loadResImage(ImageView view, int resDrawable) {
        view.setImageResource(resDrawable);
    }

    public static void loadFullImageSDCard(View imageView, String path) {
        Glide
                .with(imageView.getContext())
                .load(path)
                .apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(((ImageView) imageView));

    }

}
