package com.uh4t.imotocareapp.Api;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;


public class PersonelService {
    private static SyncHttpClient client = new SyncHttpClient();

    public static void get(String url, ResponseHandlerInterface responseHandler) {
        client.get(getAbsoluteUrl(url), responseHandler);
    }


    private static String getAbsoluteUrl(String relativeUrl) {
        Log.d("url ===============", Urls.Personel_Base_Url + relativeUrl);
        return Urls.Personel_Base_Url + relativeUrl;
    }
}
