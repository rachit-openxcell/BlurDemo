package com.example.rachit.aemjdemo.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.NestedScrollView.OnScrollChangeListener;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.rachit.aemjdemo.Adapter.RecyclerAdapter;
import com.example.rachit.aemjdemo.Model.ServerData;
import com.example.rachit.aemjdemo.R;
import com.example.rachit.aemjdemo.Utility.BlurBuilder;
import com.example.rachit.aemjdemo.Utility.GlideApp;
import com.example.rachit.aemjdemo.Utility.Utility;
import com.example.rachit.aemjdemo.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class HomeFragment extends Fragment {

    FragmentHomeBinding mBinding;
    Context mContext;
    private static final String TAG = "HomeFragment";
    //    MainActivity.ContentPagerAdapter contentPagerAdapter;
    ServerData serverData;
    int position;
    RecyclerAdapter recyclerAdapter;
    private int height = 0;
    private int rootHeight = 0;
    //    Bitmap bitmapUp;
    Bitmap bit;
    private Disposable d;
    List<Disposable> disposed = new ArrayList<>();
//    Bitmap bitmapDown;
//    BlurKit blurKit;
//    ViewPager viewPager;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String serverData, int pos) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(Utility.NetworkUtility.Jsondata, serverData);
        args.putInt("POS", pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            String data = getArguments().getString(Utility.NetworkUtility.Jsondata);
            serverData = (ServerData) Utility.getObjectFromJsonString(data, ServerData.class);
            position = getArguments().getInt("POS");
        }
//        BlurKit.init(mContext);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return mBinding.getRoot();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        viewPager = getActivity().findViewById(R.id.view_pager);
//        blurKit = BlurKit.getInstance();
        recyclerAdapter = new RecyclerAdapter();
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.setAdapter(recyclerAdapter);
        mBinding.recyclerView.setNestedScrollingEnabled(false);

        mBinding.title.postDelayed(new Runnable() {
            @Override
            public void run() {
                height = mBinding.authorName.getHeight() + mBinding.title.getHeight() + mBinding.categoryName.getHeight();
                rootHeight = mBinding.getRoot().getHeight();
                Log.e(TAG, "onActivityCreated: height " + height);
                Log.e(TAG, "onActivityCreated: full height " + mBinding.getRoot().getHeight());
                int padd = mBinding.getRoot().getHeight() - height;
                Log.e(TAG, "onActivityCreated: padd " + padd);

                mBinding.bottomSheet.setPadding(0, padd, 0, 0);
                Log.e(TAG, "onActivityCreated: getPaddingTop()  " + mBinding.bottomSheet.getPaddingTop());


            }
        }, 100);

        mBinding.scrollView.setOnScrollChangeListener(new OnScrollChangeListener() {


            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {


                if (bit == null) {
                    Log.e(TAG, "onScrollChange: bitmap is null ");
                    return;
                }

                Log.e(TAG, "----------------------------On Scroll Log Starts----------------------------");

                final float perScroll = (float) (scrollY + height) / rootHeight * 100;

//                int scrollDirection = scrollY - oldScrollY;

                if (perScroll >= 13) {
                    if (perScroll / 4 > 25) {
                        loadBitmap(25);
//                        Bitmap temp = BlurBuilder.blur(bit, 25, mContext);
//                        GlideApp.with(mContext)
//                                .load(temp)
//                                .into(mBinding.imgData);
                        Log.e(TAG, "onScrollChange: full scrolll ------- radius 25");
                    } else {
//                        if (scrollDirection > 0) {
                        loadBitmap(perScroll / 4);
//                            Bitmap temp = BlurBuilder.blur(bit, perScroll / 4, mContext);
//                            GlideApp.with(mContext)
//                                    .load(temp)
//                                    .into(mBinding.imgData);
                        Log.e(TAG, "onScrollChange: scroll up scrolll ------- " + perScroll / 4);
                    /*} else if (scrollDirection < 0) {

                            Log.e(TAG, "onScrollChange: scroll down scrolll ------- " + perScroll / 4);
                            loadBitmap(perScroll / 4);
//                            Bitmap temp = BlurBuilder.blur(bit, perScroll / 4, mContext);
//                            GlideApp.with(mContext)
//                                    .load(temp)
//                                    .into(mBinding.imgData);
                        }*/
                    }


                } else {

                    Log.e(TAG, "onScrollChange: origin full scroll down ------- ");
//
//                    GlideApp.with(mContext)
//                            .load(bit)
//                            .into(mBinding.imgData);

                    /*if (d!=null)
                        d.dispose();*/
                    mBinding.imgData.setImageBitmap(bit);

//                    bitmapUp = bit.copy(bit.getConfig(), true);
//                    bitmapDown = bit.copy(bit.getConfig(), true);

                }
            }
        });

        GlideApp.with(mContext).asBitmap()
                .load(serverData.getData().get(position).getImage())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
//                        bitmapUp = resource.copy(resource.getConfig(), true);
//                        bitmapDown = resource.copy(resource.getConfig(), true);
                        bit = resource.copy(resource.getConfig(), true);

//                        Log.e(TAG, "SameBitmap:bitmapDown and bit " + bitmapDown.sameAs(bit));
//                        Log.e(TAG, "SameBitmap:bitmapUp and bit " + bitmapUp.sameAs(bit));
//                        Log.e(TAG, "SameBitmap:bitmapUp and bitmapDown " + bitmapUp.sameAs(bitmapDown));

                        return false;
                    }
                }).into(mBinding.imgData);
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

    private void loadBitmap(double scroll) {

        /*if (d != null)
            d.dispose();
        d = Observable.fromCallable(() ->

//            ImageFilter.applyFilter(bit,ImageFilter.Filter.GAUSSIAN_BLUR,scroll*20))
        BlurBuilder.blur(bit, (float) scroll, mContext))
              .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap1 -> {
//                                showProgress(false);
                    mBinding.imgData.setImageBitmap(bitmap1);
                });*/

       /* new Thread(new Runnable() {
            @Override
            public void run() {*/
                mBinding.imgData.setImageBitmap(BlurBuilder.blur(bit, (float) scroll, mContext));
            /*}
        });*/
    }
}
