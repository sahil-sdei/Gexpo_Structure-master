package ggn.home.help.utils.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.utils.RelationSelectedListener;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.RelationsResponse;

public class RelationsAdapter extends ArrayAdapter<RelationsResponse.Datum> {
    private RelationSelectedListener relationSelectedListener;

    public void setRelationSelectedListener(RelationSelectedListener relationSelectedListener) {
        this.relationSelectedListener = relationSelectedListener;
    }

    public RelationsAdapter(Context context, List<RelationsResponse.Datum> test) {
        super(context, R.layout.item_relations_drop_down, test);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customRow = convertView;
        if (customRow == null) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            customRow = inflater.inflate(R.layout.item_relations_drop_down, null);
        }
        final RelationsResponse.Datum dataObj = getItem(position);

        RelativeLayout relativeLayoutParent = customRow.findViewById(R.id.relativeLayoutParent);
        TextView textViewTitle = customRow.findViewById(R.id.textViewTitle);
        ImageView imageViewProfilePic = customRow.findViewById(R.id.imageViewProfilePic);

        String drawable = dataObj.relation.name.toLowerCase();
        ImageLoader.loadImageVerySmall(imageViewProfilePic, drawable);

        if(dataObj.relation.name.equalsIgnoreCase("Aunt")){
            ImageLoader.loadImageVerySmall(imageViewProfilePic, "aunty");
        }else if(dataObj.relation.name.equalsIgnoreCase("Brother")){
            ImageLoader.loadImageVerySmall(imageViewProfilePic, "cousin");
        }else if(dataObj.relation.name.equalsIgnoreCase("Sister")){
            ImageLoader.loadImageVerySmall(imageViewProfilePic, "daughter");
        }else if(dataObj.relation.name.equalsIgnoreCase("brother-in-law")){
            ImageLoader.loadImageVerySmall(imageViewProfilePic, "husband");
        }else if(dataObj.relation.name.equalsIgnoreCase("sister-in-law")){
            ImageLoader.loadImageVerySmall(imageViewProfilePic, "wife");
        }else if(dataObj.relation.name.equalsIgnoreCase("father-in-law")){
            ImageLoader.loadImageVerySmall(imageViewProfilePic, "father");
        }else if(dataObj.relation.name.equalsIgnoreCase("mother-in-law")){
            ImageLoader.loadImageVerySmall(imageViewProfilePic, "mother");
        }else if(dataObj.relation.name.equalsIgnoreCase("son-in-law")){
            ImageLoader.loadImageVerySmall(imageViewProfilePic, "husband");
        }else if(dataObj.relation.name.equalsIgnoreCase("daughter-in-law")){
            ImageLoader.loadImageVerySmall(imageViewProfilePic, "mother");
        }else if(dataObj.relation.name.equalsIgnoreCase("parents-in-law")){
            ImageLoader.loadImageVerySmall(imageViewProfilePic, "father");
        }else if(dataObj.relation.name.equalsIgnoreCase("grandfather")){
            ImageLoader.loadImageVerySmall(imageViewProfilePic, "father");
        }else if(dataObj.relation.name.equalsIgnoreCase("grandmother")){
            ImageLoader.loadImageVerySmall(imageViewProfilePic, "mother");
        }else if(dataObj.relation.name.equalsIgnoreCase("grandson")){
            ImageLoader.loadImageVerySmall(imageViewProfilePic, "son");
        }else if(dataObj.relation.name.equalsIgnoreCase("granddaughter")){
            ImageLoader.loadImageVerySmall(imageViewProfilePic, "daughter");
        }else if(dataObj.relation.name.equalsIgnoreCase("grandparents")){
            ImageLoader.loadImageVerySmall(imageViewProfilePic, "father");
        }else if(dataObj.relation.name.equalsIgnoreCase("grandchildren")){
            ImageLoader.loadImageVerySmall(imageViewProfilePic, "cousin");
        }

        RadioButton radioButtonSelected = customRow.findViewById(R.id.radioButtonSelected);

        textViewTitle.setText(dataObj.relation.name);
        radioButtonSelected.setChecked(dataObj.relation.isSelected);

        relativeLayoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataObj.relation.isSelected = !dataObj.relation.isSelected;
                notifyDataSetChanged();
                relationSelectedListener.onRelationSelected(dataObj);
            }
        });

        radioButtonSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataObj.relation.isSelected = !dataObj.relation.isSelected;
                notifyDataSetChanged();
                relationSelectedListener.onRelationSelected(dataObj);
            }
        });

        return customRow;
    }
}