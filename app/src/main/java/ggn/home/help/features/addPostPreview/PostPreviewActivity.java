package ggn.home.help.features.addPostPreview;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import java.io.Serializable;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityPostPreviewBinding;
import ggn.home.help.features.addMemories.AddMemoryActivity;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.utils.Constants;
import ggn.home.help.web.response.FullLifeAlbumResponse;

public class PostPreviewActivity extends BaseActivity<ActivityPostPreviewBinding, PostPreviewPresenter> implements PostPreviewView, View.OnClickListener {

    public static void start(Context context) {
        Intent starter = new Intent(context, PostPreviewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_post_preview;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new PostPreviewPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return PostPreviewActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar("Post Preview");

        List<FullLifeAlbumResponse.Datum> listAlbum = (List<FullLifeAlbumResponse.Datum>) getIntent().getSerializableExtra(Constants.Extras.DATA);
        SlidingPostPreviewAdapter slidingPostPreviewAdapter = new SlidingPostPreviewAdapter(getActivityG(), listAlbum);
        getDataBinder().viewPagerMedia.setAdapter(slidingPostPreviewAdapter);
        getDataBinder().indicator.setViewPager(getDataBinder().viewPagerMedia);

        if (listAlbum.size() == 1) {
            getDataBinder().indicator.setVisibility(View.INVISIBLE);
        }

        getDataBinder().buttonNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonNext:
                Intent intent = new Intent(getActivityG(), AddMemoryActivity.class);
                intent.putExtra(Constants.Extras.BASE_URL_IMAGE, getIntent().getStringExtra(Constants.Extras.BASE_URL_IMAGE));
                intent.putExtra(Constants.Extras.POST_FROM_ALBUM, getIntent().getBooleanExtra(Constants.Extras.POST_FROM_ALBUM, false));
                intent.putExtra(Constants.Extras.IS_MEMORY, false);
                intent.putExtra(Constants.Extras.DATA, getIntent().getSerializableExtra(Constants.Extras.DATA));
                if (getIntent().hasExtra(Constants.Extras.CATEGORY_DATA))
                    intent.putExtra(Constants.Extras.CATEGORY_DATA, getIntent().getSerializableExtra(Constants.Extras.CATEGORY_DATA));
                if (getIntent().hasExtra(Constants.Extras.SUB_CATEGORY_DATA))
                    intent.putExtra(Constants.Extras.SUB_CATEGORY_DATA, getIntent().getSerializableExtra(Constants.Extras.SUB_CATEGORY_DATA));
                startActivity(intent);
                break;
        }
    }
}
