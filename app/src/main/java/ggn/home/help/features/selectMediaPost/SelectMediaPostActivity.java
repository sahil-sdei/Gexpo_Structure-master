package ggn.home.help.features.selectMediaPost;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.florent37.camerafragment.PreviewActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivitySelectMediaPostBinding;
import ggn.home.help.features.addMemories.fragments.AddDescriptionFragment;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.selectMediaPost.fragments.SelectImagesFragment;
import ggn.home.help.features.selectMediaPost.fragments.SelectVideosFragment;
import ggn.home.help.features.selectPictures.Pictures;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.PagerAdapter;

public class SelectMediaPostActivity extends BaseActivity<ActivitySelectMediaPostBinding, SelectMediaPostPresenter> implements SelectMediaPostView {

    private PagerAdapter adapter;

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
        adapter.addFrag(SelectImagesFragment.newInstance(), getString(R.string.photos));
        adapter.addFrag(SelectVideosFragment.newInstance(), getString(R.string.videos));
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
                boolean isFoundImages = false;
                for (Pictures pictures : ((SelectImagesFragment)adapter.getItem(0)).getPictures()) {
                    if (pictures.isSelected) {
                        isFoundImages = true;
                        break;
                    }
                }

                boolean isFoundVideos = false;
                for (Pictures pictures : ((SelectVideosFragment)adapter.getItem(1)).getVideos()) {
                    if (pictures.isSelected) {
                        isFoundVideos = true;
                        break;
                    }
                }

                if (isFoundImages || isFoundVideos) {
                    List<Pictures> listSelectedMedia = new ArrayList<>();
                    for (Pictures pictures : ((SelectImagesFragment)adapter.getItem(0)).getPictures()) {
                        if (pictures.isSelected) {
                            listSelectedMedia.add(pictures);
                        }
                    }

                    for (Pictures pictures : ((SelectVideosFragment)adapter.getItem(1)).getVideos()) {
                        if (pictures.isSelected) {
                            listSelectedMedia.add(pictures);
                        }
                    }

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(Constants.Extras.SELECTED_MEDIA, (Serializable) listSelectedMedia);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(getActivityG(), "Please select atleast one picture or video.", Toast.LENGTH_SHORT).show();
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
}
