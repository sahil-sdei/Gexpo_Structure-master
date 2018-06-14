package ggn.home.help.features.selectMediaPost;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivitySelectMediaPostBinding;
import ggn.home.help.features.addPostPreview.PostPreviewActivity;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.selectMediaPost.fragments.SelectImagesFragment;
import ggn.home.help.features.selectMediaPost.fragments.SelectVideosFragment;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.PagerAdapter;
import ggn.home.help.web.response.FullLifeAlbumResponse;

public class SelectMediaPostActivity extends BaseActivity<ActivitySelectMediaPostBinding, SelectMediaPostPresenter> implements SelectMediaPostView {

    private PagerAdapter adapter;
    private List<FullLifeAlbumResponse.Datum> listMedia;

    public static void start(Context context) {
        Intent starter = new Intent(context, SelectMediaPostActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_select_media_post;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new SelectMediaPostPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return SelectMediaPostActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar("Select Your Memories");

        setupViewPager(getDataBinder().viewPager);
        getDataBinder().tabs.setupWithViewPager(getDataBinder().viewPager);
        getDataBinder().viewPager.setOffscreenPageLimit(2);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFrag(SelectImagesFragment.newInstance(getIntent().getStringExtra(Constants.Extras.CATEGORY_ID), getIntent().getStringExtra(Constants.Extras.SUB_CATEGORY_ID)), getString(R.string.photos));
        adapter.addFrag(SelectVideosFragment.newInstance(getIntent().getStringExtra(Constants.Extras.CATEGORY_ID), getIntent().getStringExtra(Constants.Extras.SUB_CATEGORY_ID)), getString(R.string.videos));
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_next, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next:
                List<FullLifeAlbumResponse.Datum> listPictures = ((SelectImagesFragment) adapter.getItem(0)).getListPictures();
                List<FullLifeAlbumResponse.Datum> listVideos = ((SelectVideosFragment) adapter.getItem(1)).getListVideos();

                listMedia = new ArrayList<>();
                for (FullLifeAlbumResponse.Datum img : listPictures) {
                    if (img.isSelected) {
                        listMedia.add(img);
                    }
                }
                for (FullLifeAlbumResponse.Datum vid : listVideos) {
                    if (vid.isSelected) {
                        listMedia.add(vid);
                    }
                }

                boolean isSelected = false;
                for (FullLifeAlbumResponse.Datum data : listMedia) {
                    if (data.isSelected) {
                        isSelected = true;
                        break;
                    }
                }

                if (isSelected) {
                    Intent intent = new Intent(getActivityG(), PostPreviewActivity.class);
                    intent.putExtra(Constants.Extras.BASE_URL_IMAGE, getIntent().getStringExtra(Constants.Extras.BASE_URL_IMAGE));
                    intent.putExtra(Constants.Extras.DATA, (Serializable) listMedia);
                    intent.putExtra(Constants.Extras.POST_FROM_ALBUM, false);
                    intent.putExtra(Constants.Extras.CATEGORY_DATA, getIntent().getSerializableExtra(Constants.Extras.CATEGORY_DATA));
                    intent.putExtra(Constants.Extras.SUB_CATEGORY_DATA, getIntent().getSerializableExtra(Constants.Extras.SUB_CATEGORY_DATA));

                    startActivity(intent);
                } else {
                    Toast.makeText(getActivityG(), "Please select atleast 1 image or video to post your memory.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void showAlbum(FullLifeAlbumResponse output) {

    }

    @Override
    public void noDataFound() {

    }
}
