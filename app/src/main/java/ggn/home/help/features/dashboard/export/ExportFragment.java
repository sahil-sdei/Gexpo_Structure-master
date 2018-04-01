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

    }

    @Override
    public void onClick(View view) {

    }
}
