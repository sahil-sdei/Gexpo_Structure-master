package ggn.home.help.features.addMemoryPreview;


import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.MemoryAPI;
import ggn.home.help.web.request.AddMemoryRequest;
import ggn.home.help.web.response.BasicResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MemoryPreviewPresenter extends BasePresenter<MemoryPreviewView> {

    public void addMemory(AddMemoryRequest addMemoryRequest, ArrayList<String> listImages) {
        MultipartBody.Part[] fileParts = null;
        if (listImages.size() > 0) {
            fileParts = new MultipartBody.Part[listImages.size()];
            for (int index = 0; index < listImages.size(); index++) {
                Log.d("PostPreviewPresenter", "requestUploadSurvey: " + index + "  " + listImages.get(index));
                File file = null;
                RequestBody surveyBody = null;
                if (isImageFile(listImages.get(index))) {
                    file = new File(listImages.get(index));
                    surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
                    Log.d("PostPreviewPresenter", "Image " + listImages.get(index));
                } else if (isVideoFile(listImages.get(index))) {
                    file = new File(listImages.get(index));
                    surveyBody = RequestBody.create(MediaType.parse("video/*"), file);
                    Log.d("PostPreviewPresenter", "Video " + listImages.get(index));
                }
                fileParts[index] = MultipartBody.Part.createFormData("galleries[]", file.getName(), surveyBody);
            }
        }
        Gson gson = new Gson();
        RequestBody details = RequestBody.create(MediaType.parse("text/plain"), gson.toJson(addMemoryRequest));

        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        createApiRequest(getRetrofitInstance(MemoryAPI.class)
                .addMemory(fileParts, details), new CallBackG<BasicResponse>() {
            @Override
            public void callBack(BasicResponse output) {
                getView().hideLoading();
                if (output.status == 1) {
                    getView().memoryPostedSuccessfully();
                    Toast.makeText(getView().getActivityG(), output.message, Toast.LENGTH_LONG).show();
                } else
                    getView().displayError(output.message);
            }
        });
    }

    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    public static boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }
}
