package ggn.home.help.features.dashboard.export;

import android.view.View;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentExportBinding;
import ggn.home.help.features.delivery.DeliveryActivity;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.profile.ProfilePresenter;
import ggn.home.help.features.profile.ProfileView;
import ggn.home.help.features.selectPictures.SelectPicturesActivity;

public class ExportFragment extends BaseFragment<FragmentExportBinding, ProfilePresenter> implements ProfileView, View.OnClickListener {

    public static ExportFragment newInstance() {
        ExportFragment profileFragment = new ExportFragment();
        return profileFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_export;
    }

    @Override
    protected void onCreateFragmentG() {
        injectPresenter(new ProfilePresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        getDataBinder().buttonSelectPhotosMemoreeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectPicturesActivity.start(getActivityG());
            }
        });

        getDataBinder().buttonSelectPhotosPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectPicturesActivity.start(getActivityG());
            }
        });

        getDataBinder().buttonFullAlbumMemoreeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExportData exportData = new ExportData();
                exportData.type = 1;
                exportData.numberPhotos = 0;
                exportData.title = "";
                DeliveryActivity.start(getActivityG(), exportData);
            }
        });

        getDataBinder().buttonFullAlbumPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExportData exportData = new ExportData();
                exportData.type = 1;
                exportData.numberPhotos = 0;
                exportData.title = "";
                DeliveryActivity.start(getActivityG(), exportData);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
