package ggn.home.help.utils.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.utils.CategorySelectedListener;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.Category;
import ggn.home.help.web.response.CategoryResponse;

public class CategoryAdapter extends ArrayAdapter<CategoryResponse.Datum> {

    private CategorySelectedListener categorySelectedListener;

    public void setCategorySelectedListener(CategorySelectedListener categorySelectedListener) {
        this.categorySelectedListener = categorySelectedListener;
    }

    public CategoryAdapter(Context context, List<CategoryResponse.Datum> test) {
        super(context, R.layout.item_category_sub_category, test);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View customRow = inflater.inflate(R.layout.item_category_sub_category, parent, false);
        final CategoryResponse.Datum categoryObj = getItem(position);

        RelativeLayout relativeLayoutParent = customRow.findViewById(R.id.relativeLayoutParent);
        TextView textViewTitle = customRow.findViewById(R.id.textViewTitle);
        ImageView imageViewBg = customRow.findViewById(R.id.imageViewBg);
        RadioButton radioButtonSelected = customRow.findViewById(R.id.radioButtonSelected);

        String drawable = "";
        switch (position) {
            case 0:
                drawable = "pre_birth";
                break;
            case 1:
                drawable = "birth";
                break;
            case 2:
                drawable = "infancy";
                break;
            case 3:
                drawable = "early_childhood";
                break;
            case 4:
                drawable = "middle_childhood";
                break;
            case 5:
                drawable = "late_childhood";
                break;
            case 6:
                drawable = "adolescence";
                break;
            case 7:
                drawable = "early_adulthood";
                break;
            case 8:
                drawable = "midlife";
                break;
            case 9:
                drawable = "mature_adulthood";
                break;
            case 10:
                drawable = "late_adulthood";
                break;
        }
//        ImageLoader.loadImageVerySmall(imageViewBg, "http://18.216.102.186/memoreeta/files/categories/" + categoryObj.category.image);

        ImageLoader.loadImageVerySmall(imageViewBg, drawable);

        if (!TextUtils.isEmpty(categoryObj.category.years))
            textViewTitle.setText(categoryObj.category.name + " (" + categoryObj.category.years + " y)");
        else
            textViewTitle.setText(categoryObj.category.name);

        radioButtonSelected.setChecked(categoryObj.category.isSelected);

        relativeLayoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryObj.category.isSelected = !categoryObj.category.isSelected;
                notifyDataSetChanged();
                categorySelectedListener.onCategorySelected(categoryObj);
            }
        });

        radioButtonSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                categoryObj.category.isSelected = !categoryObj.category.isSelected;
                notifyDataSetChanged();
                categorySelectedListener.onCategorySelected(categoryObj);
            }
        });

        return customRow;
    }
}