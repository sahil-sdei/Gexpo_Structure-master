package ggn.home.help.features.dashboard.export;

import android.view.View;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentExportBinding;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.profile.ProfilePresenter;
import ggn.home.help.features.profile.ProfileView;

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
        getDataBinder().relativeLayoutExport.setOnClickListener(this);
        getDataBinder().relativeLayoutReceive.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relativeLayoutExport:
                getDataBinder().imageViewExportSelected.setImageResource(R.drawable.ic_check_black_24dp);
                getDataBinder().imageViewReceiveSelected.setImageResource(android.R.color.transparent);
                break;
            case R.id.relativeLayoutReceive:
                getDataBinder().imageViewExportSelected.setImageResource(android.R.color.transparent);
                getDataBinder().imageViewReceiveSelected.setImageResource(R.drawable.ic_check_black_24dp);
                break;
        }
    }
}
