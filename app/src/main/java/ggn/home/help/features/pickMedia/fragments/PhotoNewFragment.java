package ggn.home.help.features.pickMedia.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.camerafragment.PreviewActivity;
import com.github.florent37.camerafragment.widgets.CameraSwitchView;
import com.github.florent37.camerafragment.widgets.RecordButton;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraLogger;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.SessionType;
import com.otaliastudios.cameraview.Size;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

import ggn.home.help.R;
import ggn.home.help.utils.Constants;

public class PhotoNewFragment extends Fragment implements View.OnClickListener {
    RecordButton record_button;
    CameraSwitchView cameraSwitchView;
    private CameraView camera;
    private TextView textViewTimer;
    private boolean mCapturingPicture;
    private boolean mCapturingVideo;

    // To show stuff in the callback
    private Size mCaptureNativeSize;
    private long mCaptureTime;
    private boolean isPlaying = false;

    public static PhotoNewFragment newInstance() {
        PhotoNewFragment photoFragment = new PhotoNewFragment();
        return photoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        CameraLogger.setLogLevel(CameraLogger.LEVEL_VERBOSE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_camera, container, false);
        record_button = v.findViewById(R.id.record_button);
        cameraSwitchView = v.findViewById(R.id.front_back_camera_switcher);
        camera = v.findViewById(R.id.camera);
        textViewTimer = v.findViewById(R.id.textViewTimer);

        camera.addCameraListener(new CameraListener() {
            public void onCameraOpened(CameraOptions options) {
            }

            public void onPictureTaken(byte[] jpeg) {
                onPicture(jpeg);
            }

            @Override
            public void onVideoTaken(File video) {
                super.onVideoTaken(video);
                onVideo(video);
            }
        });

        record_button.setOnClickListener(this);
        cameraSwitchView.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_button:
                if (camera.getSessionType() == SessionType.PICTURE)
                    capturePhoto();
                else {
                    if (!isPlaying)
                        captureVideo();
                    else
                        camera.stopCapturingVideo();
                }
                break;

            case R.id.front_back_camera_switcher:
                toggleCamera();
                break;
        }
    }

    private void onPicture(byte[] jpeg) {
        mCapturingPicture = false;
        long callbackTime = System.currentTimeMillis();
        if (mCapturingVideo) {
            return;
        }

        // This can happen if picture was taken with a gesture.
        if (mCaptureTime == 0) mCaptureTime = callbackTime - 300;
        if (mCaptureNativeSize == null) mCaptureNativeSize = camera.getPictureSize();


        mCaptureTime = 0;
        mCaptureNativeSize = null;

//        Toast.makeText(getActivity(), "Picture Taken", Toast.LENGTH_SHORT).show();
        new SavePhotoTask().execute(jpeg);
    }

    class SavePhotoTask extends AsyncTask<byte[], String, String> {
        @Override
        protected String doInBackground(byte[]... jpeg) {
            File photo = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + "photo.jpg");

            if (photo.exists()) {
                photo.delete();
            }

            try {
                FileOutputStream fos = new FileOutputStream(photo.getPath());

                fos.write(jpeg[0]);
                fos.close();
            } catch (java.io.IOException e) {
                Log.e("PictureDemo", "Exception in photoCallback", e);
            }

            return photo.getAbsolutePath();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = PreviewActivity.newIntentPhoto(getActivity(), s);
            getActivity().startActivityForResult(intent, Constants.RequestCode.IMAGE_PREVIEW);
        }
    }

    private void onVideo(File video) {
        mCapturingVideo = false;
        isPlaying = false;
        textViewTimer.setVisibility(View.GONE);
//        Toast.makeText(getActivity(), "Video taken " + video.getAbsolutePath().toString(), Toast.LENGTH_SHORT).show();
        Intent intent = PreviewActivity.newIntentVideo(getActivity(), video.getAbsolutePath());
        getActivity().startActivityForResult(intent, Constants.RequestCode.IMAGE_PREVIEW);
    }

    private void capturePhoto() {
        if (mCapturingPicture) return;
        mCapturingPicture = true;
        mCaptureTime = System.currentTimeMillis();
        mCaptureNativeSize = camera.getPictureSize();
        camera.capturePicture();
    }

    private void captureVideo() {
        if (camera.getSessionType() != SessionType.VIDEO) {
            return;
        }
        if (mCapturingPicture || mCapturingVideo) return;
        mCapturingVideo = true;
        camera.startCapturingVideo(null, 50000); //50 Seconds
        startTimer();
        isPlaying = true;
    }

    private void toggleCamera() {
        if (mCapturingPicture) return;
        switch (camera.toggleFacing()) {
            case BACK:
                break;

            case FRONT:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        camera.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        camera.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        camera.destroy();
    }

    public void setVideoView() {
        camera.setSessionType(SessionType.VIDEO);
        Toast.makeText(getActivity(), "Video Mode", Toast.LENGTH_SHORT).show();
    }

    public void setPictureView() {
        textViewTimer.setVisibility(View.GONE);
        camera.setSessionType(SessionType.PICTURE);
        Toast.makeText(getActivity(), "Picture Mode", Toast.LENGTH_SHORT).show();
    }

    private void startTimer() {
        textViewTimer.setVisibility(View.VISIBLE);
        new CountDownTimer(50000, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText("" + String.format("%2d:%2d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                textViewTimer.setText("00:00");
                textViewTimer.setVisibility(View.GONE);
            }
        }.start();
    }
}



