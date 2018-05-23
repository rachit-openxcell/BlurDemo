package com.example.rachit.aemjdemo.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class Utility {

    /**
     * Checking whether there is internet connection or not
     *
     * @param mContext The Context
     * @return Whether there is internet connection or not
     */
    public static boolean isConnected(Context mContext) {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public  static int position=0;

    /**
     * GSON UTILITY Methods
     */
    private static Gson gson = new GsonBuilder().create();

    public static Object getObjectFromJsonString(String jsonData, Class modelClass) {
        return gson.fromJson(jsonData, modelClass);
    }

    public static String getJsonStringFromObject(Object modelClass) {
        return gson.toJson(modelClass);
    }

    public static <T> ArrayList<T> getObjectListFromJsonString(String jsonData, Class myclass) {
        return new ArrayList<>(Arrays.asList((T[]) gson.fromJson(jsonData, myclass)));
    }

    public static void showSnackBar(String message, View view) {
        Snackbar.make(view, message, 3000).show();
    }

    public static void showToast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    public interface NetworkUtility {
//        public static final String BASE_URL = "http://nforceapps.com:1234/";
        public static final String BASE_URL = "http://tokbox.orderhive.plus:1234/";

        public static final String WS_GET_STATIC_PAGE_DETAIL = BASE_URL + "staticPage";
        public static final String WS_GET_BLOG_DETAIL = BASE_URL + "blog";
        public static final String WS_GET_SINGLE_BLOGDETAIL = BASE_URL + "blogById";
        public static final String WS_GET_DEVICVE_TOKEN = BASE_URL + "addDeviceToken";
        public static final String WS_INVITE_FRIENDS = BASE_URL + "inviteFriends";

        public static final String Jsondata = "Jsondata";
    }

}
