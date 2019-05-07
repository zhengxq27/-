package com.study.sysu.photo;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.io.File;
import java.util.ArrayList;

import static com.study.sysu.photo.MyApplication.getMyApplication;

public class PageFragment1 extends Fragment {

    private MyApplication app;
    private ArrayList<SpacePhoto> all_photo_set = new ArrayList<>(); // 存放所有图片的路径


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, // 系统会在Fragment首次绘制其用户界面时调用此方法
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_page1,container,false);

        app = (MyApplication)getMyApplication();
        all_photo_set = app.get_all_photo_set();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rv_images_1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        if(all_photo_set != null){
            ImageGalleryAdapter adapter = new ImageGalleryAdapter(getActivity(), all_photo_set);
            //调用这个函数的时候SpacePhoto并不是空的
            recyclerView.setAdapter(adapter);
        }
        return root;
    }

}
