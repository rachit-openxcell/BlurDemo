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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.commit451.nativestackblur.NativeStackBlur;
import com.example.rachit.aemjdemo.Adapter.RecyclerAdapter;
import com.example.rachit.aemjdemo.Model.ServerData;
import com.example.rachit.aemjdemo.R;
import com.example.rachit.aemjdemo.Utility.GlideApp;
import com.example.rachit.aemjdemo.Utility.Utility;
import com.example.rachit.aemjdemo.databinding.FragmentHomeBinding;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    FragmentHomeBinding mBinding;
    Context mContext;
    private static final String TAG = "HomeFragment";
    ServerData serverData;
    int position;
    RecyclerAdapter recyclerAdapter;
    private int height = 0;
    private int rootHeight = 0;
    Bitmap bit;
    private Disposable d;
    ImageView blurView;
    View view;

    public HomeFragment() {
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
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return mBinding.getRoot();
    }

     ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        public void onGlobalLayout() {
            // measure your views here
            Log.e(TAG, "onGlobalLayout: " + position + " ***********************");
            height = mBinding.authorName.getHeight() + mBinding.title.getHeight() + mBinding.categoryName.getHeight();
            rootHeight = mBinding.getRoot().getHeight();
            Log.e(TAG, "onActivityCreated: height " + height);
            Log.e(TAG, "onActivityCreated: full height " + mBinding.getRoot().getHeight());
            int padd = rootHeight - height;
            Log.e(TAG, "onActivityCreated: padd " + padd);

            if (getActivity() != null)
                mBinding.bottomSheet.setPadding(0, padd - (int) dpToPixel(16), 0, 0);
            Log.e(TAG, "onActivityCreated: getPaddingTop()  " + mBinding.bottomSheet.getPaddingTop());
            mBinding.constraint.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);

        }
    };
    @SuppressLint("CheckResult")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.e(TAG, "onActivityCreated: 1" + " ***********************");
        blurView = mBinding.getRoot().findViewById(R.id.blur_view);
        view = mBinding.getRoot().findViewById(R.id.trans_view);
        recyclerAdapter = new RecyclerAdapter(mContext);

        mBinding.constraint.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.setAdapter(recyclerAdapter);
        mBinding.recyclerView.setNestedScrollingEnabled(false);
        recyclerAdapter.addAll(serverData, position);
        Log.e(TAG, "onActivityCreated: 2" + " ***********************");
        initData();

        mBinding.scrollView.setOnScrollChangeListener(new OnScrollChangeListener() {


            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                Log.e(TAG, "onScrollChange:ScrollY is " + scrollY);

                Log.e(TAG, "----------------------------On Scroll Log Starts----------------------------");

                final float perScroll = (float) (scrollY + height) / rootHeight;

                if (perScroll > height/rootHeight) {
                    if (perScroll > 0.85f) {
                        loadBitmap(0.85f, 1f);
                        Log.e(TAG, "onScrollChange: full scrolll ------- max radius");
                    } else {
                        loadBitmap((perScroll/6)+perScroll, (perScroll/6)+perScroll);
                        Log.e(TAG, "onScrollChange: viewAlpha ------- " + perScroll);
                        Log.e(TAG, "onScrollChange: blurAlpha ------- " + ((perScroll/6)+perScroll));
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
//                    mBinding.imgData.setImageBitmap(bit);

                    blurView.setAlpha(0f);
                    view.setAlpha(0f);

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
                        bit = resource.copy(resource.getConfig(), true);
//                        NativeStackBlur.process(bit, 150))
                        //                                BlurBuilder.blur(bit, 25, mContext))
                        //        BlurBuilder.blur(bit, (float) scroll, mContext))
                        d = Observable.fromCallable(() -> NativeStackBlur.process(bit, 150)).subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(bitmap1 -> {
                                            mBinding.blurView.setImageBitmap(bitmap1);
                                        });


                        return false;
                    }
                }).into(mBinding.imgData);


    }

    private void initData() {
        mBinding.authorName.setText(serverData.getData().get(position).getAuthorName());
        mBinding.categoryName.setText(serverData.getData().get(position).getCategoryName());
        mBinding.title.setText(serverData.getData().get(position).getTitle());
        Log.e(TAG, "initData: " + " ***********************");
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

    @Override
    public void onResume() {
        super.onResume();

        if (recyclerAdapter != null) {
            Log.e(TAG, "onResume: " + " ***********************");
            recyclerAdapter.notifyDataSetChanged();
        }
    }

    private void loadBitmap(float viewAlpha, float blurAlpha) {

        /*if (d != null)
            d.dispose();*/
        /*d = Observable.fromCallable(() ->

            ImageFilter.applyFilter(bit,ImageFilter.Filter.GAUSSIAN_BLUR,30d))
//        BlurBuilder.blur(bit, (float) scroll, mContext))
              .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap1 -> {
//                                showProgress(false);
                    mBinding.imgData.setImageBitmap(bitmap1);
                });*/

        /*new Thread(new Runnable() {
            @Override
            public void run() {*/
//                mBinding.imgData.setImageBitmap(BlurBuilder.blur(bit, (float) scroll, mContext));
//        mBinding.imgData.setImageBitmap(ImageFilter.applyFilter(bit,ImageFilter.Filter.GAUSSIAN_BLUR,24d));
        Log.e(TAG, "onScrollChange: viewAlpha ------- " + viewAlpha);
        Log.e(TAG, "onScrollChange: blurAlpha ------- " + blurAlpha);

        blurView.setAlpha(blurAlpha);
        view.setAlpha(viewAlpha);

            /*}
        });*/
    }

    public float dpToPixel(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
