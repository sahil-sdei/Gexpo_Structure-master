package ggn.home.help.features.addMemories;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import java.io.InputStream;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemSubCategoriesBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.utils.bitmapUtils.SvgSoftwareLayerSetter;
import ggn.home.help.web.response.SubCategory;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class SubCategoriesAdapter extends InfiniteAdapterG<ItemSubCategoriesBinding> {

    private List<SubCategory> dataList;
    private AddMemoryAdapterBinder addMemoryAdapterBinder;
    private Context context;
    private String baseUrl;
    private RequestBuilder<PictureDrawable> requestBuilder;

    public SubCategoriesAdapter(List<SubCategory> dataList, Context context, AddMemoryAdapterBinder addMemoryAdapterBinder) {
        this.dataList = dataList;
        this.addMemoryAdapterBinder = addMemoryAdapterBinder;
        this.context = context;

        requestBuilder = Glide.with(context)
                .as(PictureDrawable.class)
                .transition(withCrossFade())
                .listener(new SvgSoftwareLayerSetter());
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_sub_categories;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(int position, BaseViewHolder baseViewHolder) {
        baseViewHolder.binding.setData(dataList.get(position));

        baseViewHolder.binding.textViewUserName.setText(titleize(dataList.get(position).name));
        ImageLoader.loadImageSmall(baseViewHolder.binding.imageViewProfilePic, baseUrl + dataList.get(position).image);
//        Uri uri = Uri.parse(baseUrl + dataList.get(position).image);
//        requestBuilder.load(uri).into(baseViewHolder.binding.imageViewProfilePic);

        if(dataList.get(position).name.equalsIgnoreCase("Suggest Sub Category")){
            baseViewHolder.binding.relativeLayoutProfilePic.setVisibility(View.INVISIBLE);
        }else{
            baseViewHolder.binding.relativeLayoutProfilePic.setVisibility(View.VISIBLE);

        }

        baseViewHolder.binding.setBinder(addMemoryAdapterBinder);
        baseViewHolder.binding.executePendingBindings();
    }

    public static String titleize(final String input) {
        // Initialize the output to the length of the input since we know it and the
        // output and input will be the same size.
        StringBuilder output = new StringBuilder(input.length());
        // This defaults to true because we assume the beginning of the
        // string also counts as whitespace. This is to make sure the
        // first word gets capitalized as well.
        boolean lastCharacterWasWhitespace = true;

        for(int i = 0; i < input.length(); i++) {
            char currentCharacter = input.charAt(i);

            if(lastCharacterWasWhitespace && Character.isLowerCase(currentCharacter)) {
                currentCharacter = Character.toTitleCase(currentCharacter);
            }

            output.append(currentCharacter);

            lastCharacterWasWhitespace = Character.isWhitespace(currentCharacter);
        }

        return output.toString();
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
