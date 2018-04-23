package ggn.home.help.features.changePassword;

import android.view.View;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentChangePasswordBinding;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.utils.DialogHelper;
import ggn.home.help.utils.UtillsG;
import ggn.home.help.web.response.BasicResponse;


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
        getDataBinder().setBinder(getPresenter());
    }

    @Override
    public void hideKeyboard(View view) {
        UtillsG.hideKeyboard(getActivityG(), view);
    }

    @Override
    public void successChangePassword(BasicResponse output) {
        DialogHelper.getInstance().showInformation(getActivityG(), output.message, new CallBackG<String>() {
            @Override
            public void callBack(String output) {

            }
        });
    }
}
