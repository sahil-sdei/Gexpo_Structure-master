package ggn.home.help.features.addMemories.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentAddMemoryDescriptionBinding;
import ggn.home.help.features.addMemories.AddMemoryActivity;
import ggn.home.help.features.addMemories.AddMemoryPresenter;
import ggn.home.help.features.addMemories.AddMemoryView;
import ggn.home.help.features.addMemories.adapetrs.ShowMediaAdapter;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.memoryCategories.SubCategories;
import ggn.home.help.features.pickMedia.AddMediaActivity;
import ggn.home.help.features.previewMedia.PreviewMediaActivity;
import ggn.home.help.features.privacy.PrivacyActivity;
import ggn.home.help.features.searchUser.SearchUserAdapter;
import ggn.home.help.features.selectPictures.Pictures;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.UtillsG;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.SubCategory;

import static android.app.Activity.RESULT_OK;


public class AddDescriptionFragment extends BaseFragment<FragmentAddMemoryDescriptionBinding, AddMemoryPresenter> implements AddMemoryView {

    private DisplayMetrics displayMetrics;
    public final static String MEDIA_ACTION_ARG = "media_action_arg";
    private List<String> listPictures;
    private ShowMediaAdapter showMediaAdapter;

    public static AddDescriptionFragment newInstance(List<Pictures> pictures) {
        AddDescriptionFragment addDescriptionFragment = new AddDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.Extras.SELECTED_MEDIA, (Serializable) pictures);
        addDescriptionFragment.setArguments(bundle);
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
//        getDataBinder().flowLayoutAttachments.removeAllViews();

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
//                Toast.makeText(getActivityG(), "Your memory has been posted successfully.", Toast.LENGTH_LONG).show();
                Intent resultIntent = getActivity().getIntent();
                getActivity().setResult(RESULT_OK, resultIntent);
                getActivity().finish();
            }
        });

        getDataBinder().relativeLayoutPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivityG(), PrivacyActivity.class);
                startActivityForResult(intent, Constants.RequestCode.PRIVACY);
            }
        });

        List<Pictures> pictures = (List<Pictures>) getArguments().getSerializable(Constants.Extras.SELECTED_MEDIA);
        listPictures = new ArrayList<>();

        getDataBinder().recyclerViewMedia.setHasFixedSize(true);
        getDataBinder().recyclerViewMedia.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.HORIZONTAL, false));
        showMediaAdapter= new ShowMediaAdapter(listPictures, getActivityG(), null);
        showMediaAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewMedia.setAdapter(showMediaAdapter);

        for (Pictures picturesObj : pictures) {
            addImageToView(picturesObj.picture);
        }
    }

    private void addImageToView(String imagePath) {
        listPictures.add(imagePath);
        showMediaAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RequestCode.IMAGE_SEARCH) {
            if (resultCode == RESULT_OK) {
                if (data.getBooleanExtra(MatisseFragment.EXTRA_IS_GALLERY, false)) {
                    List<Uri> uriList = Matisse.obtainResult(data);
                    List<String> pathList = Matisse.obtainPathResult(data);
//                    Toast.makeText(getActivityG(), "Got the data " + uriList.size() + " - - " + pathList.size(), Toast.LENGTH_SHORT).show();
                    for (String paths : pathList) {
                        addImageToView(paths);
                    }

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
        } else if (requestCode == Constants.RequestCode.PRIVACY) {
            if (resultCode == RESULT_OK) {
                int privacyType = data.getIntExtra(Constants.Extras.PRIVACY, 0);
                updatePrivacy(privacyType);
            }
        }
    }

    private void updatePrivacy(int privacyType) {
        if (privacyType == 0) {
            getDataBinder().textViewPrivacy.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_everyone_in_share_screen, 0, 0, 0);
            getDataBinder().textViewPrivacy.setText("Everyone");
        } else if (privacyType == 1) {
            getDataBinder().textViewPrivacy.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_friends_small, 0, 0, 0);
            getDataBinder().textViewPrivacy.setText("Friends");
        } else if (privacyType == 2) {
            getDataBinder().textViewPrivacy.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_family_small, 0, 0, 0);
            getDataBinder().textViewPrivacy.setText("Family");
        } else if (privacyType == 3) {
            getDataBinder().textViewPrivacy.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_only_me_small, 0, 0, 0);
            getDataBinder().textViewPrivacy.setText("Only Me");
        }
    }

    @Override
    public void showDescriptionFragment(SubCategory subCategory) {

    }
}
