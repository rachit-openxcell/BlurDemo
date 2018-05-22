package com.example.rachit.aemjdemo.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.rachit.aemjdemo.Model.ServerData;
import com.example.rachit.aemjdemo.R;
import com.example.rachit.aemjdemo.Utility.Utility;
import com.example.rachit.aemjdemo.Utility.Volley;
import com.example.rachit.aemjdemo.Utility.VolleyNetworkRequest;
import com.example.rachit.aemjdemo.databinding.FragmentContentContainerBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentContainer extends Fragment {

    ContentPagerAdapter contentPagerAdapter;
    private static final String TAG = "ContentContainer";
    Context mContext;
    FragmentContentContainerBinding mBinding;

    public ContentContainer() {
        // Required empty public constructor
    }

    public static ContentContainer newInstance() {
        ContentContainer fragment = new ContentContainer();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_content_container, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contentPagerAdapter = new ContentPagerAdapter(getChildFragmentManager());
        mBinding.viewPager.setAdapter(contentPagerAdapter);


        getPageDetailWebservice();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private final Response.Listener mGetPageDetailResponseListener = new Response.Listener() {
        @Override
        public void onResponse(Object response) {

            ServerData serverData = (ServerData) Utility.getObjectFromJsonString(response.toString(), ServerData.class);

            for(int i = 0; i < serverData.getData().size(); i++ ){
                contentPagerAdapter.addFrag(HomeFragment.newInstance(response.toString(), i));
            }
            mBinding.viewPager.setOffscreenPageLimit(serverData.getData().size());
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

    private void getPageDetailWebservice() {

        if (!Utility.isConnected(mContext)) {
            Utility.showToast(mContext, "No Internet Connection");
            return;
        }

        // Add Header parameters
//        Map<String, String> mHeaderParams = new HashMap<>();
//        mHeaderParams.put("Content-Type", "application/json");
        //Add Params
        Map<String, String> mBodyParams = new HashMap<>();
        mBodyParams.put("page", "1");

        /*Map<String, JSONObject> bodyParam = new HashMap<>();
        bodyParam.put(Utility.NetworkUtility.Jsondata, new JSONObject(mBodyParams));*/
        String requestString = new JSONObject(mBodyParams).toString();
        VolleyNetworkRequest mVolleyNetworkRequest = new VolleyNetworkRequest(
                Utility.NetworkUtility.WS_GET_BLOG_DETAIL
                , mGetPageDetailErrorListener
                , mGetPageDetailResponseListener
                , null
                , requestString);

        Volley.getInstance(mContext).addToRequestQueue(mVolleyNetworkRequest, Utility.NetworkUtility.WS_GET_BLOG_DETAIL);
    }

    public class ContentPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment){
            mFragmentList.add(fragment);
            notifyDataSetChanged();
//            mBinding.viewPager.setOffscreenPageLimit(mFragmentList.size());
        }
    }
}
