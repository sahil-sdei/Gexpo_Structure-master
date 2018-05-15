package ggn.home.help.utils.widgets;

import android.content.Context;
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
import ggn.home.help.utils.SubCategorySelectedListener;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.SubCategory;

public class SubCategoryAdapter extends ArrayAdapter<SubCategory> {
    private SubCategorySelectedListener subCategorySelectedListener;

    public void setSubCategorySelectedListener(SubCategorySelectedListener subCategorySelectedListener) {
        this.subCategorySelectedListener = subCategorySelectedListener;
    }

    public SubCategoryAdapter(Context context, List<SubCategory> test) {
        super(context, R.layout.item_category_sub_category, test);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customRow = convertView;
        if (customRow == null) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            customRow = inflater.inflate(R.layout.item_sub_category_drop_down, null);
        }
        final SubCategory subCategoryObj = getItem(position);

        RelativeLayout relativeLayoutParent = customRow.findViewById(R.id.relativeLayoutParent);
        TextView textViewTitle = customRow.findViewById(R.id.textViewTitle);
        ImageView imageViewProfilePic= customRow.findViewById(R.id.imageViewProfilePic);

        RadioButton radioButtonSelected = customRow.findViewById(R.id.radioButtonSelected);


        ImageLoader.loadImageVerySmall(imageViewProfilePic, "http://18.216.102.186/memoreeta/files/categories/" + subCategoryObj.image);


        textViewTitle.setText(subCategoryObj.name);
        radioButtonSelected.setChecked(subCategoryObj.isSelected);

        relativeLayoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subCategoryObj.isSelected = !subCategoryObj.isSelected;
                notifyDataSetChanged();
                subCategorySelectedListener.onSubCategorySelected(subCategoryObj);
            }
        });

//        radioButtonSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                subCategoryObj.isSelected = !subCategoryObj.isSelected;
//                notifyDataSetChanged();
//            }
//        });

        radioButtonSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subCategoryObj.isSelected = !subCategoryObj.isSelected;
                notifyDataSetChanged();
                subCategorySelectedListener.onSubCategorySelected(subCategoryObj);
            }
        });

        return customRow;
    }
}