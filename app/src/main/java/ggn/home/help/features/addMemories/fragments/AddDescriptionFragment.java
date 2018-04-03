package ggn.home.help.features.addMemories.fragments;

import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.usemedia.Matisse;
import com.example.usemedia.ui.MatisseFragment;
import com.github.florent37.camerafragment.PreviewActivity;
import com.github.florent37.camerafragment.configuration.Configuration;
import com.nex3z.flowlayout.FlowLayout;

import java.io.File;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentAddMemoryDescriptionBinding;
import ggn.home.help.features.addMemories.AddMemoryActivity;
import ggn.home.help.features.addMemories.AddMemoryPresenter;
import ggn.home.help.features.addMemories.AddMemoryView;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.memoryCategories.SubCategories;
import ggn.home.help.features.pickMedia.AddMediaActivity;
import ggn.home.help.features.previewMedia.PreviewMediaActivity;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.UtillsG;

import static android.app.Activity.RESULT_OK;


public class AddDescriptionFragment extends BaseFragment<FragmentAddMemoryDescriptionBinding, AddMemoryPresenter> implements AddMemoryView {

    private DisplayMetrics displayMetrics;
    public final static String MEDIA_ACTION_ARG = "media_action_arg";

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
        ((AddMemoryActivity) getActivity()).changeHeadingText(getString(R.string.life_event));
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
                Toast.makeText(getActivityG(), "Memory Posted Successfully.", Toast.LENGTH_LONG).show();
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
        final View inflatedLayout = inflater.inflate(R.layout.flow_item_image, null, false);

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        ImageView imageViewDelete = inflatedLayout.findViewById(R.id.imageViewDelete);
        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataBinder().flowLayoutAttachments.removeView(inflatedLayout);
            }
        });

        ImageView imageView = inflatedLayout.findViewById(R.id.imageViewPicture);
        File file = new File(imagePath);
        Uri imageUri = Uri.fromFile(file);
//        imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(100, 100);
        requestOptions.transforms(new CenterCrop(), new RoundedCorners(10));
        Glide
                .with(imageView.getContext())
                .load(imageUri)
                .apply(requestOptions)
                .into((imageView));
        inflatedLayout.setLayoutParams(new FlowLayout.LayoutParams((width / 4) - UtillsG.dipToPixels(getActivity(), 15),
                (width / 4) - UtillsG.dipToPixels(getActivity(), 15)));
        getDataBinder().flowLayoutAttachments.addView(inflatedLayout);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RequestCode.IMAGE_SEARCH) {
            if (resultCode == RESULT_OK) {
                if (data.getBooleanExtra(MatisseFragment.EXTRA_IS_GALLERY, false)) {
                    List<Uri> uriList = Matisse.obtainResult(data);
                    List<String> pathList = Matisse.obtainPathResult(data);
                    Toast.makeText(getActivityG(), "Got the data " + uriList.size() + " - - " + pathList.size(), Toast.LENGTH_SHORT).show();
                } else {
                    if (data.getIntExtra(Constants.Extras.RESPONSE_CODE_ARG, 0) == PreviewActivity.ACTION_CONFIRM) {
                        if (data.getIntExtra(MEDIA_ACTION_ARG, 0) == Configuration.MEDIA_ACTION_VIDEO) {
                            Intent intentVideo = PreviewMediaActivity.newIntentVideo(getActivity(), data.getStringExtra(Constants.Extras.FILE_PATH_ARG));
                            startActivityForResult(intentVideo, Constants.RequestCode.IMAGE_PREVIEW);
                        } else {
                            Intent intentPhoto = PreviewMediaActivity.newIntentPhoto(getActivity(), data.getStringExtra(Constants.Extras.FILE_PATH_ARG));
                            startActivityForResult(intentPhoto, Constants.RequestCode.IMAGE_PREVIEW);
                        }
                    }
                }
            }
        } else if (requestCode == Constants.RequestCode.IMAGE_PREVIEW) {
            if (resultCode == RESULT_OK) {
                addImageToView(data.getStringExtra(Constants.Extras.FILE_PATH_ARG));
            }
        }
    }
}
