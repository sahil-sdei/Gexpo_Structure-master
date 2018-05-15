package ggn.home.help.features.pickMedia;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.usemedia.Matisse;
import com.example.usemedia.MimeType;
import com.example.usemedia.engine.impl.GlideEngine;
import com.example.usemedia.filter.Filter;
import com.example.usemedia.internal.entity.Album;
import com.example.usemedia.internal.entity.CaptureStrategy;
import com.example.usemedia.internal.entity.Item;
import com.example.usemedia.internal.model.SelectedItemCollection;
import com.example.usemedia.internal.ui.MediaSelectionFragment;
import com.example.usemedia.internal.ui.adapter.AlbumMediaAdapter;
import com.example.usemedia.ui.MatisseFragment;
import com.github.florent37.camerafragment.PreviewActivity;

import ggn.home.help.R;
import ggn.home.help.features.addMemories.fragments.AddDescriptionFragment;
import ggn.home.help.features.pickMedia.adapters.PagerAdapter;
import ggn.home.help.features.pickMedia.fragments.PhotoFragment;
import ggn.home.help.features.pickMedia.fragments.VideoFragment;
import ggn.home.help.utils.Constants;

public class AddMediaActivity extends AppCompatActivity implements AlbumMediaAdapter.CheckStateListener, AlbumMediaAdapter.OnMediaClickListener,
        MediaSelectionFragment.SelectionProvider {

    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;
    private PagerAdapter adapter;
    private ViewPager viewPager;

    public static void start(Context context) {
        Intent starter = new Intent(context, AddMediaActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_media);

        this.getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.black));

        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);

        if (ActivityCompat.checkSelfPermission(AddMediaActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(AddMediaActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(AddMediaActivity.this);
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs storage permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(AddMediaActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(AddMediaActivity.this);
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs storage permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(AddMediaActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
            }


            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, true);
            editor.commit();

        } else {
            //You already have the permission, just go ahead.
            setUpViewPager();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
                setUpViewPager();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(AddMediaActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddMediaActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();


                            ActivityCompat.requestPermissions(AddMediaActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);


                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(), "Unable to get Permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(AddMediaActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                setUpViewPager();
            }
        } else if (requestCode == Constants.RequestCode.IMAGE_PREVIEW) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getIntExtra(Constants.Extras.RESPONSE_CODE_ARG, 0) == PreviewActivity.ACTION_CONFIRM) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(Constants.Extras.RESPONSE_CODE_ARG, PreviewActivity.ACTION_CONFIRM).putExtra(Constants.Extras.FILE_PATH_ARG, data.getStringExtra(Constants.Extras.FILE_PATH_ARG)).putExtra(AddDescriptionFragment.MEDIA_ACTION_ARG, data.getIntExtra(AddDescriptionFragment.MEDIA_ACTION_ARG, 0));
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(AddMediaActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                setUpViewPager();
            }
        }
    }

    private void setUpViewPager() {
        viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);

        setUpMatisse();

        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFrag(MatisseFragment.newInstance(), "Gallery");
        adapter.addFrag(PhotoFragment.newInstance(), "Photo");
        adapter.addFrag(VideoFragment.newInstance(), "Video");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOffscreenPageLimit(3);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (ActivityCompat.checkSelfPermission(AddMediaActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddMediaActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(AddMediaActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                    return;
                }
                if (position == 1) {
                    PhotoFragment photoFragment = (PhotoFragment) adapter.getItem(1);
                    photoFragment.addCamera();
                } else if (position == 2) {
                    VideoFragment videoFragment = (VideoFragment) adapter.getItem(2);
                    videoFragment.addCamera();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setUpMatisse() {
        int maxSelectable = 5;
//        if (getIntent().getBooleanExtra(Constants.Extras.IS_MEMORY, false)) {
//            maxSelectable = 1;
//        }

        Matisse.from(AddMediaActivity.this)
                .choose(MimeType.ofAll(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "ggn.home.help.fileprovider"))
                .maxSelectable(maxSelectable)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(
                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine());
    }

    @Override
    public void onUpdate() {
        ((MatisseFragment) adapter.getItem(0)).updateBottomToolbar();
    }

    @Override
    public void onMediaClick(Album album, Item item, int adapterPosition) {
        ((MatisseFragment) adapter.getItem(0)).onMediaClick(album, item, adapterPosition);
    }

    @Override
    public SelectedItemCollection provideSelectedItemCollection() {
        return ((MatisseFragment) adapter.getItem(0)).getSelectedItemCollection();
    }
}
