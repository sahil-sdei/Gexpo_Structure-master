package ggn.home.help.features.internal.base;

import ggn.home.help.web.Web;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
 
 
public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Web.Path.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}