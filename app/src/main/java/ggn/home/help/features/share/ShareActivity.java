package ggn.home.help.features.share;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityShareBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.privacy.PrivacyActivity;
import ggn.home.help.utils.Constants;

public class ShareActivity extends BaseActivity<ActivityShareBinding, SharePresenter> implements ShareView, View.OnClickListener {

    public static void start(Context context) {
        Intent starter = new Intent(context, ShareActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new SharePresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return ShareActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar("Share");
        getDataBinder().textViewPrivacy.setOnClickListener(this);
        getDataBinder().buttonShare.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewPrivacy:
                Intent intent = new Intent(getActivityG(), PrivacyActivity.class);
                startActivityForResult(intent, Constants.RequestCode.PRIVACY);
                break;
            case R.id.button:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.RequestCode.PRIVACY){
            if(resultCode == RESULT_OK){
                int privacyType = data.getIntExtra(Constants.Extras.PRIVACY, 0);
                updatePrivacy(privacyType);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updatePrivacy(int privacyType){
        if(privacyType == 0){
            getDataBinder().textViewPrivacy.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_everyone_in_share_screen, 0, 0, 0);
            getDataBinder().textViewPrivacy.setText("Everyone");
        }else if(privacyType == 1){
            getDataBinder().textViewPrivacy.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_friends_small, 0, 0, 0);
            getDataBinder().textViewPrivacy.setText("Friends");
        }else if(privacyType == 2){
            getDataBinder().textViewPrivacy.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_family_small, 0, 0, 0);
            getDataBinder().textViewPrivacy.setText("Family");
        }else if(privacyType == 3){
            getDataBinder().textViewPrivacy.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_only_me_small, 0, 0, 0);
            getDataBinder().textViewPrivacy.setText("Only Me");
        }
    }
}
