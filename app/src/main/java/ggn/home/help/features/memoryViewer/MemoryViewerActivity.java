package ggn.home.help.features.memoryViewer;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.MediaController;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityViewerBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.FullLifeAlbumResponse;

public class MemoryViewerActivity extends BaseActivity<ActivityViewerBinding, MemoryViewerPresenter> implements MemoryViewerView {

    private MediaController mediaController;
    private MediaPlayer mediaPlayer;

    private int currentPlaybackPosition = 0;
    private boolean isVideoPlaying = true;

    public static void start(Context context) {
        Intent starter = new Intent(context, MemoryViewerActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_viewer;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new MemoryViewerPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return MemoryViewerActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar("");
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        FullLifeAlbumResponse.Datum dataObj;
        if (getIntent().hasExtra(Constants.Extras.DATA)) {
            dataObj = (FullLifeAlbumResponse.Datum) getIntent().getSerializableExtra(Constants.Extras.DATA);
            getDataBinder().textViewTitle.setText(dataObj.title);
            getDataBinder().textViewCategory.setText(dataObj.categoryName + " > " + dataObj.subCategoryName);
        }

        getDataBinder().videoPreview.setOnTouchListener(new View.OnTouchListener() {
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

        if (getIntent().getBooleanExtra(Constants.Extras.IS_IMAGE, true)) {
            ImageLoader.loadFullImage(getDataBinder().imageViewPicture, getIntent().getStringExtra(Constants.Extras.MEDIA_URL));
            getDataBinder().previewAspectFrameLayout.setVisibility(View.GONE);
        } else {
            getDataBinder().imageViewPicture.setVisibility(View.GONE);
            displayVideo();
        }

    }

    private void displayVideo() {
        getDataBinder().videoPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
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
            mediaPlayer.setDataSource(getIntent().getStringExtra(Constants.Extras.MEDIA_URL));
            mediaPlayer.setDisplay(holder);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaController = new MediaController(getActivityG());
                    mediaController.setAnchorView(getDataBinder().videoPreview);
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

                    getDataBinder().previewAspectFrameLayout.setAspectRatio((double) videoWidth / videoHeight);

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
            Log.e("MemoryViewerActivity", "Error media player playing video.");
            finish();
        }
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
}
