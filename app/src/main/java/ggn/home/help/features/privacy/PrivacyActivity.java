package ggn.home.help.features.privacy;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.github.florent37.camerafragment.PreviewActivity;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityPrivacyBinding;
import ggn.home.help.databinding.ActivityShareBinding;
import ggn.home.help.features.addMemories.fragments.AddDescriptionFragment;
import ggn.home.help.features.familyMembers.FamilyMembersActivity;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.utils.Constants;

public class PrivacyActivity extends BaseActivity<ActivityPrivacyBinding, PrivacyPresenter> implements PrivacyView, View.OnClickListener {

    public static void start(Context context) {
        Intent starter = new Intent(context, PrivacyActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_privacy;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new PrivacyPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return PrivacyActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar("Privacy");
        getDataBinder().relativeLayoutEveryone.setOnClickListener(this);
        getDataBinder().relativeLayoutFriends.setOnClickListener(this);
        getDataBinder().relativeLayoutFamily.setOnClickListener(this);
        getDataBinder().relativeLayoutOnlyMe.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relativeLayoutEveryone:
                returnResult(0);
                break;
            case R.id.relativeLayoutFriends:
                returnResult(1);
                break;
            case R.id.relativeLayoutFamily:
                startActivityForResult(new Intent(getActivityG(), FamilyMembersActivity.class), Constants.RequestCode.SELECT_FAMILY_MEMBERS);
                break;
            case R.id.relativeLayoutOnlyMe:
                returnResult(3);
                break;
        }
    }

    private void returnResult(int type){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Constants.Extras.PRIVACY, type);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.RequestCode.SELECT_FAMILY_MEMBERS){
            if(resultCode == RESULT_OK){
                returnResult(2);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
