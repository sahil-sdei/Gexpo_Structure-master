package ggn.home.help.features.memoryCategories;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ItemAddMemoryBinding;
import ggn.home.help.features.internal.base.InfiniteAdapterG;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.CategoryResponse;

public class MemoryCategoriesAdapter extends InfiniteAdapterG<ItemAddMemoryBinding> {

    private List<CategoryResponse.Datum> dataList;
    private Context context;
    private MemoryCategoriesAdapterBinder memoryCategoriesAdapterBinder;
    private String baseUrl;

    public MemoryCategoriesAdapter(List<CategoryResponse.Datum> dataList, Context context, MemoryCategoriesAdapterBinder memoryCategoriesAdapterBinder) {
        this.dataList = dataList;
        this.context = context;
        this.memoryCategoriesAdapterBinder = memoryCategoriesAdapterBinder;
    }

    @Override
    public int getInflateLayout() {
        return R.layout.item_add_memory;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    protected void bindData(int position, BaseViewHolder baseViewHolder) {
        baseViewHolder.binding.setData(dataList.get(position));

        ImageLoader.loadFullWidthImage(baseViewHolder.binding.imageViewBg, baseUrl+dataList.get(position).category.image);
        if(!TextUtils.isEmpty(dataList.get(position).category.years))
            baseViewHolder.binding.relativeLayoutYears.setVisibility(View.VISIBLE);
        else
            baseViewHolder.binding.relativeLayoutYears.setVisibility(View.GONE);

        baseViewHolder.binding.setBinder(memoryCategoriesAdapterBinder);
        baseViewHolder.binding.executePendingBindings();
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl(){
        return baseUrl;
    }
}
