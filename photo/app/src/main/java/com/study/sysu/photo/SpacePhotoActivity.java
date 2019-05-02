package com.study.sysu.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import static com.study.sysu.photo.MyApplication.getMyApplication;

public class SpacePhotoActivity extends AppCompatActivity {


    private ImageView mImageView;
    private MyApplication app;
    private ArrayList<SpacePhoto> Album;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_space_detail);
        if (getSupportActionBar() != null){  // 去掉标题栏
            getSupportActionBar().hide();
        }

        app = (MyApplication)getMyApplication();
        Album = app.get_all_photo_set();


        mImageView = (ImageView) findViewById(R.id.image);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String url = bundle.getString("url");


        Glide.with(this)
                .load(url)
                .asBitmap()
                .error(R.drawable.error)
                .into(mImageView);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.tool, menu);
//        return true;
//    }



}
