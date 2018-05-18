package com.example.rachit.aemjdemo.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rachit.aemjdemo.R;
import com.example.rachit.aemjdemo.databinding.FragmentPrivacyPolicyBinding;

public class PrivacyPolicy extends Fragment {

    FragmentPrivacyPolicyBinding mBinding;
    private static final String TAG = "PrivacyPolicy";
    public PrivacyPolicy() {
        // Required empty public constructor
    }

    public static PrivacyPolicy newInstance() {
        PrivacyPolicy fragment = new PrivacyPolicy();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_privacy_policy, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
