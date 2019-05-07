package com.study.sysu.photo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class classcification_page extends AppCompatActivity {

    private int position;
    private ArrayList<SpacePhoto> human_face;
    private ArrayList<SpacePhoto> scenery;
    private ArrayList<SpacePhoto> thing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classcification_page);
        if (getSupportActionBar() != null){  // 去掉标题栏
            getSupportActionBar().hide();
        }

        inialize();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        position = bundle.getInt("position");

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_images_4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        if(position == 1){
            ImageGalleryAdapter adapter = new ImageGalleryAdapter(this, human_face);
            recyclerView.setAdapter(adapter);
        } else if(position == 2){
            ImageGalleryAdapter adapter = new ImageGalleryAdapter(this, scenery);
            recyclerView.setAdapter(adapter);
        } else{
            ImageGalleryAdapter adapter = new ImageGalleryAdapter(this, thing);
            recyclerView.setAdapter(adapter);
        }
    }

    public void inialize(){
        human_face = new ArrayList<>();
        human_face.add(new SpacePhoto("https://raw.githubusercontent.com/zhengxq27/intelligent-album/master/picture/h1.jpg","h1"));
        human_face.add(new SpacePhoto("https://raw.githubusercontent.com/zhengxq27/intelligent-album/master/picture/h2.jpg","h2"));
        human_face.add(new SpacePhoto("https://raw.githubusercontent.com/zhengxq27/intelligent-album/master/picture/h3.jpg","h3"));
        human_face.add(new SpacePhoto("https://raw.githubusercontent.com/zhengxq27/intelligent-album/master/picture/h4.jpg","h4"));
        human_face.add(new SpacePhoto("https://raw.githubusercontent.com/zhengxq27/intelligent-album/master/picture/h5.jpg","h5"));

        scenery = new ArrayList<>();
        scenery.add(new SpacePhoto("https://raw.githubusercontent.com/zhengxq27/intelligent-album/master/picture/s1.jpg","s1"));
        scenery.add(new SpacePhoto("https://raw.githubusercontent.com/zhengxq27/intelligent-album/master/picture/s2.jpg","s2"));
        scenery.add(new SpacePhoto("https://raw.githubusercontent.com/zhengxq27/intelligent-album/master/picture/s3.jpg","s3"));
        scenery.add(new SpacePhoto("https://raw.githubusercontent.com/zhengxq27/intelligent-album/master/picture/s4.jpg","s4"));
        scenery.add(new SpacePhoto("https://raw.githubusercontent.com/zhengxq27/intelligent-album/master/picture/s5.jpg","s5"));

        thing = new ArrayList<>();
        thing.add(new SpacePhoto("https://raw.githubusercontent.com/zhengxq27/intelligent-album/master/picture/c1.jpg","c1"));
        thing.add(new SpacePhoto("https://raw.githubusercontent.com/zhengxq27/intelligent-album/master/picture/c2.jpeg","c2"));
        thing.add(new SpacePhoto("https://raw.githubusercontent.com/zhengxq27/intelligent-album/master/picture/c3.jpeg","c3"));
        thing.add(new SpacePhoto("https://raw.githubusercontent.com/zhengxq27/intelligent-album/master/picture/c4.jpg","c4"));
        thing.add(new SpacePhoto("https://raw.githubusercontent.com/zhengxq27/intelligent-album/master/picture/c5.jpg","c5"));
    }
}
