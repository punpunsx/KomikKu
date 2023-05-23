package com.komikatow.komiku.utils;

import android.app.Activity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public final class Networking {

    public static void HttpsRequest(final String url, final Activity activity, Response responseListener){

        new Thread(()->{

            AndroidNetworking.get(url)
                    .setPriority(Priority.IMMEDIATE)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            responseListener.onHttpsResponse(response);
                        }

                        @Override
                        public void onError(ANError anError) {
                            activity.runOnUiThread(()->{
                                responseListener.onHttpsError(anError);
                            });
                        }
                    });
        }).start();
    }

    public interface Response{
        void onHttpsResponse(JSONObject jsonObject);
        void onHttpsError(ANError anError);
    }
}
