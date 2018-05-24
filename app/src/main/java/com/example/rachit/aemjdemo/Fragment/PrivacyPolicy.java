package com.example.rachit.aemjdemo.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.rachit.aemjdemo.Model.StaticData;
import com.example.rachit.aemjdemo.R;
import com.example.rachit.aemjdemo.Utility.Utility;
import com.example.rachit.aemjdemo.Utility.Volley;
import com.example.rachit.aemjdemo.Utility.VolleyNetworkRequest;
import com.example.rachit.aemjdemo.databinding.FragmentPrivacyPolicyBinding;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PrivacyPolicy extends Fragment {

    FragmentPrivacyPolicyBinding mBinding;
    private static final String TAG = "PrivacyPolicy";
    Context mContext;
//    private OnFragmentInteractionListener mListener;


    public PrivacyPolicy() {
        // Required empty public constructor
    }

    public static PrivacyPolicy newInstance() {
        PrivacyPolicy fragment = new PrivacyPolicy();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*if (mListener != null) {
            mListener.onFragmentInteraction("Privacy Policy");
        }*/
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_privacy_policy, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getPageDetailWebservice();

    }

    private void getPageDetailWebservice() {
        if (!Utility.isConnected(mContext)) {
            Utility.showToast(mContext, "No Internet Connection");
            return;
        }

        Map<String, String> mBodyParams = new HashMap<>();
        mBodyParams.put("pageTitle", "privacyPolicy");

        String requestString = new JSONObject(mBodyParams).toString();
        VolleyNetworkRequest mVolleyNetworkRequest = new VolleyNetworkRequest(
                Utility.NetworkUtility.WS_GET_STATIC_PAGE_DETAIL
                , mGetPageDetailErrorListener
                , mGetPageDetailResponseListener
                , null
                , requestString);

        Volley.getInstance(mContext).addToRequestQueue(mVolleyNetworkRequest, Utility.NetworkUtility.WS_GET_STATIC_PAGE_DETAIL);
    }

    private final Response.Listener mGetPageDetailResponseListener = new Response.Listener() {
        @Override
        public void onResponse(Object response) {

            StaticData staticData = (StaticData) Utility.getObjectFromJsonString(response.toString(), StaticData.class);

            TextView textView = mBinding.getRoot().findViewById(R.id.privacy);

            Spanned spanned;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                spanned = Html.fromHtml(staticData.getData().getText(), Html.FROM_HTML_MODE_LEGACY);
                textView.setText(spanned);
            } else {
                spanned = Html.fromHtml(staticData.getData().getText());
                textView.setText(spanned);
            }
        }
    };

    private final Response.ErrorListener mGetPageDetailErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            Log.d(TAG, "onErrorResponse() called with: error = [" + error + "]");

            // Show Toast
            Utility.showToast(mContext, "Something went wrong.");

        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        /*try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

}
