package ggn.home.help.features.addMemories.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.usemedia.Matisse;
import com.example.usemedia.ui.MatisseFragment;
import com.github.florent37.camerafragment.PreviewActivity;
import com.github.florent37.camerafragment.configuration.Configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentAddMemoryDescriptionBinding;
import ggn.home.help.features.addMemories.AddMemoryPresenter;
import ggn.home.help.features.addMemories.AddMemoryView;
import ggn.home.help.features.addMemories.adapetrs.ShowMediaAdapter;
import ggn.home.help.features.dashboard.DashboardActivity;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.pickMedia.AddMediaActivity;
import ggn.home.help.features.previewMedia.PreviewMediaActivity;
import ggn.home.help.features.privacy.PrivacyActivity;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.UtillsG;
import ggn.home.help.web.request.AddPostRequest;
import ggn.home.help.web.response.FullLifeAlbumResponse;
import ggn.home.help.web.response.SubCategory;

import static android.app.Activity.RESULT_OK;


public class AddDescriptionFragment extends BaseFragment<FragmentAddMemoryDescriptionBinding, AddMemoryPresenter> implements AddMemoryView {

    private DisplayMetrics displayMetrics;
    public final static String MEDIA_ACTION_ARG = "media_action_arg";
    private List<String> listPictures;
    private ShowMediaAdapter showMediaAdapter;

    public static AddDescriptionFragment newInstance(List<FullLifeAlbumResponse.Datum> pictures) {
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
        final List<FullLifeAlbumResponse.Datum> pictures = (List<FullLifeAlbumResponse.Datum>) getArguments().getSerializable(Constants.Extras.SELECTED_MEDIA);

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
//                Intent resultIntent = getActivity().getIntent();
//                getActivity().setResult(RESULT_OK, resultIntent);
//                getActivity().finish();
                if (TextUtils.isEmpty(getDataBinder().editTextTitle.getText().toString())) {
                    displayError("Please enter title for your memory.");
                } /*else if (TextUtils.isEmpty(getDataBinder().editTextDescription.getText().toString())) {
                    displayError("Please enter description for your memory.");
                }*/ else {
                    AddPostRequest addPostRequest = new AddPostRequest();
                    addPostRequest.userId = Integer.parseInt(getLocalData().getUserId());
                    addPostRequest.token = getLocalData().getAuthToken();
                    addPostRequest.title = getDataBinder().editTextTitle.getText().toString();
                    addPostRequest.description = getDataBinder().editTextDescription.getText().toString();
                    addPostRequest.privacy = "1";
                    List<Integer> listGallery = new ArrayList<>();
                    for (FullLifeAlbumResponse.Datum p : pictures) {
                        listGallery.add(Integer.parseInt(p.id));
                    }
                    addPostRequest.galleries = listGallery;
                    getPresenter().addPost(addPostRequest);
                }
            }
        });

        getDataBinder().relativeLayoutPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivityG(), PrivacyActivity.class);
                startActivityForResult(intent, Constants.RequestCode.PRIVACY);
            }
        });

        listPictures = new ArrayList<>();

        getDataBinder().recyclerViewMedia.setHasFixedSize(true);
        getDataBinder().recyclerViewMedia.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.HORIZONTAL, false));
        showMediaAdapter = new ShowMediaAdapter(pictures, getActivityG(), null);
        showMediaAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewMedia.setAdapter(showMediaAdapter);

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
//                    for (String paths : pathList) {
//                        addImageToView(paths);
//                    }

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
//                addImageToView(data.getStringExtra(Constants.Extras.FILE_PATH_ARG));
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

    @Override
    public void memoryPostedSuccessfully() {
        UtillsG.finishAll(getActivityG());
        DashboardActivity.start(getActivityG());
    }
}
