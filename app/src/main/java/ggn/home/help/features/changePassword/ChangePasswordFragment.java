package ggn.home.help.features.changePassword;

import android.view.View;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentChangePasswordBinding;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.utils.UtillsG;


public class ChangePasswordFragment extends BaseFragment<FragmentChangePasswordBinding, ChangePasswordPresenter> implements ChangePasswordView {

    public static ChangePasswordFragment newInstance() {
        ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
        return changePasswordFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_change_password;
    }

    @Override
    protected void onCreateFragmentG() {
        setHasOptionsMenu(true);
        injectPresenter(new ChangePasswordPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void hideKeyboard(View view) {
        UtillsG.hideKeyboard(getActivityG(), view);
    }
}
