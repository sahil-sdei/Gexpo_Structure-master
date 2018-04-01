package ggn.home.help.features.previewMedia;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;

import com.github.florent37.camerafragment.PreviewActivity;
import com.github.florent37.camerafragment.configuration.Configuration;
import com.github.florent37.camerafragment.internal.ui.view.AspectFrameLayout;
import com.github.florent37.camerafragment.internal.utils.ImageLoader;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityPreviewMediaBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.utils.PagerAdapter;

public class PreviewMediaActivity extends BaseActivity<ActivityPreviewMediaBinding, PreviewMediaPresenter> implements PreviewMediaView {

    private PagerAdapter adapter;
    private final static String MEDIA_ACTION_ARG = "media_action_arg";
    private final static String FILE_PATH_ARG = "file_path_arg";
    private final static String VIDEO_POSITION_ARG = "current_video_position";
    private final static String VIDEO_IS_PLAYED_ARG = "is_played";

    private SurfaceView surfaceView;
    private FrameLayout photoPreviewContainer;
    private ImageView imagePreview;
    private AspectFrameLayout videoPreviewContainer;

    private int mediaAction;
    private String previewFilePath;

    private MediaController mediaController;
    private MediaPlayer mediaPlayer;

    private int currentPlaybackPosition = 0;
    private boolean isVideoPlaying = true;


    public static void start(Context context) {
        Intent starter = new Intent(context, PreviewMediaActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_preview_media;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new PreviewMediaPresenter());
        getPresenter().attachView(this);

    }

    @Override
    public Context getActivityG() {
        return PreviewMediaActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar("");
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        surfaceView = findViewById(com.github.florent37.camerafragment.R.id.video_preview);
        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mediaController == null) return false;
                if (mediaController.isShowing()) {
                    mediaController.hide();
                } else {
                    mediaController.show();
                }
                return false;
            }
        });

        videoPreviewContainer = findViewById(com.github.florent37.camerafragment.R.id.previewAspectFrameLayout);
        photoPreviewContainer = findViewById(com.github.florent37.camerafragment.R.id.photo_preview_container);

        Bundle args = getIntent().getExtras();

        mediaAction = args.getInt(MEDIA_ACTION_ARG);
        previewFilePath = args.getString(FILE_PATH_ARG);

        if (mediaAction == Configuration.MEDIA_ACTION_VIDEO) {
            displayVideo(savedInstanceState);
        } else if (mediaAction == Configuration.MEDIA_ACTION_PHOTO) {
            displayImage();
        }

        getDataBinder().buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(FILE_PATH_ARG, previewFilePath);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveVideoParams(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (mediaController != null) {
            mediaController.hide();
            mediaController = null;
        }
    }

    private void displayImage() {
        videoPreviewContainer.setVisibility(View.GONE);
        surfaceView.setVisibility(View.GONE);
        showImagePreview();
    }

    private void showImagePreview() {
        imagePreview = new ImageView(this);
        imagePreview.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        imagePreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageLoader.Builder builder = new ImageLoader.Builder(this);
        builder.load(previewFilePath).build().into(imagePreview);
        photoPreviewContainer.removeAllViews();
        photoPreviewContainer.addView(imagePreview);
    }

    private void displayVideo(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            loadVideoParams(savedInstanceState);
        }
        photoPreviewContainer.setVisibility(View.GONE);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                showVideoPreview(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    private void showVideoPreview(SurfaceHolder holder) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(previewFilePath);
            mediaPlayer.setDisplay(holder);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaController = new MediaController(PreviewMediaActivity.this);
                    mediaController.setAnchorView(surfaceView);
                    mediaController.setMediaPlayer(new MediaController.MediaPlayerControl() {
                        @Override
                        public void start() {
                            mediaPlayer.start();
                        }

                        @Override
                        public void pause() {
                            mediaPlayer.pause();
                        }

                        @Override
                        public int getDuration() {
                            return mediaPlayer.getDuration();
                        }

                        @Override
                        public int getCurrentPosition() {
                            return mediaPlayer.getCurrentPosition();
                        }

                        @Override
                        public void seekTo(int pos) {
                            mediaPlayer.seekTo(pos);
                        }

                        @Override
                        public boolean isPlaying() {
                            return mediaPlayer.isPlaying();
                        }

                        @Override
                        public int getBufferPercentage() {
                            return 0;
                        }

                        @Override
                        public boolean canPause() {
                            return true;
                        }

                        @Override
                        public boolean canSeekBackward() {
                            return true;
                        }

                        @Override
                        public boolean canSeekForward() {
                            return true;
                        }

                        @Override
                        public int getAudioSessionId() {
                            return mediaPlayer.getAudioSessionId();
                        }
                    });

                    int videoWidth = mp.getVideoWidth();
                    int videoHeight = mp.getVideoHeight();

                    videoPreviewContainer.setAspectRatio((double) videoWidth / videoHeight);

                    mediaPlayer.start();
                    mediaPlayer.seekTo(currentPlaybackPosition);

                    if (!isVideoPlaying)
                        mediaPlayer.pause();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    finish();
                    return true;
                }
            });
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            Log.e(PreviewMediaActivity.class.getName(), "Error media player playing video.");
            finish();
        }
    }

    private void saveVideoParams(Bundle outState) {
        if (mediaPlayer != null) {
            outState.putInt(VIDEO_POSITION_ARG, mediaPlayer.getCurrentPosition());
            outState.putBoolean(VIDEO_IS_PLAYED_ARG, mediaPlayer.isPlaying());
        }
    }

    private void loadVideoParams(Bundle savedInstanceState) {
        currentPlaybackPosition = savedInstanceState.getInt(VIDEO_POSITION_ARG, 0);
        isVideoPlaying = savedInstanceState.getBoolean(VIDEO_IS_PLAYED_ARG, true);
    }

    public static Intent newIntentPhoto(Context context, String filePath) {
        return new Intent(context, PreviewMediaActivity.class)
                .putExtra(MEDIA_ACTION_ARG, Configuration.MEDIA_ACTION_PHOTO)
                .putExtra(FILE_PATH_ARG, filePath);
    }

    public static Intent newIntentVideo(Context context, String filePath) {
        return new Intent(context, PreviewMediaActivity.class)
                .putExtra(MEDIA_ACTION_ARG, Configuration.MEDIA_ACTION_VIDEO)
                .putExtra(FILE_PATH_ARG, filePath);
    }
}
