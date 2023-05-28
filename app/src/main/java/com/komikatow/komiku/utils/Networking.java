package com.komikatow.komiku.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.komikatow.komiku.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

public final class Networking {

    public static void HttpsRequest(final String url, final Activity activity, Response responseListener){

        new Thread(()-> AndroidNetworking.get(url)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseListener.onHttpsResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        activity.runOnUiThread(()-> responseListener.onHttpsError(anError));
                    }
                })).start();
    }

    public interface Response{
        void onHttpsResponse(JSONObject jsonObject);
        void onHttpsError(ANError anError);
    }

    public static void getUpdate(Activity activity, Context context) {

        new Thread(() -> AndroidNetworking.get(Endpoints.BASE_UPDATE_API)
                .addHeaders("X-Master-Key",Endpoints.API_KEY)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONObject jsonObject = response.getJSONObject("record");
                            final String appVersion = jsonObject.getString("appVersion");
                            final String deskripsiUpdate = jsonObject.getString("deskripsiUpdate");
                            final boolean isServerOff = jsonObject.getBoolean("serverOff");

                            if (BuildConfig.VERSION_NAME != appVersion){
                                activity.runOnUiThread(()-> DialogsKt.setAlertDialog(context, "Update", "Update tersedia apakah anda ingin mengUpdate terlebih dahulu\nNew Feature : " + deskripsiUpdate, false, new OnDialogListener() {
                                    @Override
                                    public void onOkeButton() {

                                    }

                                    @Override
                                    public void onCencleButton() {

                                    }
                                }));
                            }

                            if (isServerOff){

                                activity.runOnUiThread(()->{
                                    Toast.makeText(activity, "Saat ini server sedang off silahkan coba lagi nanti", Toast.LENGTH_SHORT).show();
                                    activity.finish();
                                });

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        activity.runOnUiThread(()-> Toast.makeText(activity, "Error saat mencoba menghubungi server Update ", Toast.LENGTH_SHORT).show());
                    }
                })).start();
    }

}
