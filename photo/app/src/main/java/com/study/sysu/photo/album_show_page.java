package com.study.sysu.photo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import static com.study.sysu.photo.MyApplication.getMyApplication;

public class album_show_page extends AppCompatActivity {

    private int index;
    private MyApplication app;
    private album showing_album;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_show_page);
        if (getSupportActionBar() != null){  // 去掉标题栏
            getSupportActionBar().hide();
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        index = bundle.getInt("index");

        app = (MyApplication)getMyApplication();
        showing_album = app.get_all_album().get(index);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_images_3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ImageGalleryAdapter adapter = new ImageGalleryAdapter(this, showing_album.getPhotoList());
        recyclerView.setAdapter(adapter);

    }
}
