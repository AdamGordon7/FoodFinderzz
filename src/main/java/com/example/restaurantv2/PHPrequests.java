package com.example.restaurantv2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PHPrequests {

    OkHttpClient client=new OkHttpClient();
    String urlPrefrix;
    public PHPrequests(String urlPrefix)
    {
        this.urlPrefrix=urlPrefix;
    }

    public void doPostRequest(Activity activity, String fileName, ContentValues params, RequestHandler requestHandler)
    {

        HttpUrl.Builder url= Objects.requireNonNull(HttpUrl.parse(urlPrefrix+fileName)).newBuilder();

        for(String key: params.keySet())
        {
            url.addQueryParameter(key,params.getAsString(key));
        }

        Request request=new Request.Builder()
                .url(String.valueOf(url))
                .build();

        Log.d("URL", "doPostRequest: "+request);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                if(response.isSuccessful())
                {
                    String responseInfo=response.body().string();
                    activity.runOnUiThread(() ->
                    {
                        requestHandler.processResponse(responseInfo);
                    });
                }
            }
        });
    }

}
