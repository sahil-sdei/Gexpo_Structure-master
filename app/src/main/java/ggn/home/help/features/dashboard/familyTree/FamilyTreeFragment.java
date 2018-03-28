package ggn.home.help.features.dashboard.familyTree;

import android.view.View;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentFamilyTreeBinding;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.profile.ProfilePresenter;
import ggn.home.help.features.profile.ProfileView;

public class FamilyTreeFragment extends BaseFragment<FragmentFamilyTreeBinding, ProfilePresenter> implements ProfileView, View.OnClickListener {

    public static FamilyTreeFragment newInstance() {
        FamilyTreeFragment profileFragment = new FamilyTreeFragment();
        return profileFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_family_tree;
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
