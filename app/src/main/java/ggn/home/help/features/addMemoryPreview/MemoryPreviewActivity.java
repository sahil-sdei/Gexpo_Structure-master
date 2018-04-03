package ggn.home.help.features.addMemoryPreview;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityAddMemoryPreviewBinding;
import ggn.home.help.databinding.ActivityShareBinding;
import ggn.home.help.features.dashboard.DashboardActivity;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.privacy.PrivacyActivity;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.UtillsG;

public class MemoryPreviewActivity extends BaseActivity<ActivityAddMemoryPreviewBinding, MemoryPreviewPresenter> implements MemoryPreviewView, View.OnClickListener {

    public static void start(Context context) {
        Intent starter = new Intent(context, MemoryPreviewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_memory_preview;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new MemoryPreviewPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return MemoryPreviewActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar("Add Memory");
        getDataBinder().buttonShare.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonShare:
                Toast.makeText(getActivityG(), "Memory Added Successfully.", Toast.LENGTH_LONG).show();
                UtillsG.finishAll(getActivityG());
                DashboardActivity.start(getActivityG(), 1);
                break;
        }
    }
}
