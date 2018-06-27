package ggn.home.help.features.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.net.URLConnection;
import java.util.List;

import ggn.home.help.features.internal.base.ApiClient;
import ggn.home.help.utils.Constants;
import ggn.home.help.web.request.AddMemoryRequest;
import ggn.home.help.web.response.BasicResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadService extends IntentService implements ProgressRequestBody.UploadCallbacks {

    public static final String RESULT = "result";
    public static final String NOTIFICATION = "ggn.home.help.service.receiver";
    public static final String PROGRESS = "progress";

    public UploadService() {
        super("UploadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AddMemoryRequest addMemoryRequest = (AddMemoryRequest) intent.getSerializableExtra(Constants.Extras.DATA);
        List<String> listImages = intent.getStringArrayListExtra(Constants.Extras.GALLERY_DATA);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        MultipartBody.Part[] fileParts = null;
        if (listImages.size() > 0) {
            fileParts = new MultipartBody.Part[listImages.size()];
            for (int index = 0; index < listImages.size(); index++) {
                Log.d("PostPreviewPresenter", "requestUploadSurvey: " + index + "  " + listImages.get(index));
                File file = null;
                ProgressRequestBody surveyBody = null;
                if (isImageFile(listImages.get(index))) {
                    file = new File(listImages.get(index));
//                    surveyBody = ProgressRequestBody.create(MediaType.parse("image/*"), file);
                    surveyBody = new ProgressRequestBody(file, "image/*", this);
                    Log.d("PostPreviewPresenter", "Image " + listImages.get(index));
                } else if (isVideoFile(listImages.get(index))) {
                    file = new File(listImages.get(index));
//                    surveyBody = ProgressRequestBody.create(MediaType.parse("video/*"), file);
                    surveyBody = new ProgressRequestBody(file, "video/*", this);
                    Log.d("PostPreviewPresenter", "Video " + listImages.get(index));
                }
                fileParts[index] = MultipartBody.Part.createFormData("galleries[]", file.getName(), surveyBody);
            }
        }
        Gson gson = new Gson();
        RequestBody details = RequestBody.create(MediaType.parse("text/plain"), gson.toJson(addMemoryRequest));

        Call<BasicResponse> call = apiService.addMemory(fileParts, details);
        call.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                publishResults(Activity.RESULT_OK);
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                publishResults(Activity.RESULT_CANCELED);
            }
        });

    }

    private void publishResults(int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }

    private void publishResults(int result, int progress) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result);
        intent.putExtra(PROGRESS, progress);
        sendBroadcast(intent);
    }

    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    public static boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }

    @Override
    public void onProgressUpdate(int percentage) {
        publishResults(Activity.RESULT_FIRST_USER, percentage);
    }

    @Override
    public void onError() {
        publishResults(Activity.RESULT_CANCELED);
    }

    @Override
    public void onFinish() {
        publishResults(Activity.RESULT_OK);
    }
}