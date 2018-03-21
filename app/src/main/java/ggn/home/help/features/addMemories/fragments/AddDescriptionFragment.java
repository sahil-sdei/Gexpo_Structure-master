package ggn.home.help.features.addMemories.fragments;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentAddMemoryDescriptionBinding;
import ggn.home.help.databinding.FragmentRecyclerViewBinding;
import ggn.home.help.features.addMemories.AddMemoryPresenter;
import ggn.home.help.features.addMemories.AddMemoryView;
import ggn.home.help.features.addMemories.SubCategoriesAdapter;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.pickMedia.AddMediaActivity;
import ggn.home.help.utils.UtillsG;


public class AddDescriptionFragment extends BaseFragment<FragmentAddMemoryDescriptionBinding, AddMemoryPresenter> implements AddMemoryView {

    public static AddDescriptionFragment newInstance() {
        AddDescriptionFragment addDescriptionFragment = new AddDescriptionFragment();
        return addDescriptionFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_memory_description;
    }

    @Override
    protected void onCreateFragmentG() {
        injectPresenter(new AddMemoryPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        getDataBinder().flowLayoutAttachments.removeAllViews();

        TextView textView = new TextView(getActivityG());
        textView.setText("Add Media");
        Drawable top = getResources().getDrawable(R.drawable.ic_add_image_large);
        textView.setCompoundDrawables(null, top, null, null);
//        textView.setCompoundDrawablePadding(UtillsG.dipToPixels(getActivityG(), 5));
        textView.setTextColor(ContextCompat.getColor(getActivityG(), R.color.greyDark));
//        textView.setTextSize9(UtillsG.dipToPixels(getActivityG(), 13));

        getDataBinder().flowLayoutAttachments.addView(textView);

//        getDataBinder().flowLayoutAttachments.addView(button);
//        getDataBinder().flowLayoutAttachments.addView(button);
//        getDataBinder().flowLayoutAttachments.addView(button);
//        getDataBinder().flowLayoutAttachments.addView(button);
//        getDataBinder().flowLayoutAttachments.addView(button);
//        getDataBinder().flowLayoutAttachments.addView(button);

        getDataBinder().buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = new Button(getActivityG());
                button.setText("ABCD");
                button.setBackgroundResource(R.color.buttonBlue);

                getDataBinder().flowLayoutAttachments.addView(button);

                AddMediaActivity.start(getActivityG());
            }
        });
    }

    @Override
    public void showDescriptionFragment() {


    }
}
