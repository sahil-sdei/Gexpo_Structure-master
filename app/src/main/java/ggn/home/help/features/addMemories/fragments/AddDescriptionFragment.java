package ggn.home.help.features.addMemories.fragments;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.github.florent37.camerafragment.PreviewActivity;
import com.nex3z.flowlayout.FlowLayout;

import java.io.File;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentAddMemoryDescriptionBinding;
import ggn.home.help.features.addMemories.AddMemoryPresenter;
import ggn.home.help.features.addMemories.AddMemoryView;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.memoryCategories.SubCategories;
import ggn.home.help.features.pickMedia.AddMediaActivity;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.UtillsG;

import static android.app.Activity.RESULT_OK;


public class AddDescriptionFragment extends BaseFragment<FragmentAddMemoryDescriptionBinding, AddMemoryPresenter> implements AddMemoryView {

    private DisplayMetrics displayMetrics;

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

        displayMetrics = new DisplayMetrics();
    }

    @Override
    public void initViews() {
        getDataBinder().flowLayoutAttachments.removeAllViews();

        getDataBinder().textViewAddMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivityG(), AddMediaActivity.class);
                startActivityForResult(intent, Constants.RequestCode.IMAGE_SEARCH);
            }
        });

        getDataBinder().buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = getActivity().getIntent();
                getActivity().setResult(RESULT_OK, resultIntent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void showDescriptionFragment(SubCategories subCategories) {

    }

    private void addImageToView(String imagePath) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View inflatedLayout = inflater.inflate(R.layout.flow_item_image, null, false);

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        ImageView imageView = new ImageView(getActivity());
        File file = new File(imagePath);
        Uri imageUri = Uri.fromFile(file);
        imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
//        Glide.with(getActivity()).load(imageUri).override(300,300).centerCrop().into(imageView);
        inflatedLayout.setLayoutParams(new FlowLayout.LayoutParams((width / 3) - UtillsG.dipToPixels(getActivity(), 15),
                UtillsG.dipToPixels(getActivity(), 140)));
        getDataBinder().flowLayoutAttachments.addView(inflatedLayout);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RequestCode.IMAGE_SEARCH) {
            if(resultCode == RESULT_OK){
                if(data.getIntExtra(Constants.Extras.RESPONSE_CODE_ARG, 0) == PreviewActivity.ACTION_CONFIRM){
                    addImageToView(data.getStringExtra(Constants.Extras.FILE_PATH_ARG));
                }
            }
        }
    }
}
