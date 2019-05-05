package com.study.sysu.photo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.io.File;
import java.util.ArrayList;

import static com.study.sysu.photo.MyApplication.getMyApplication;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    private MyApplication app;
    private ArrayList<SpacePhoto> all_photo_set = new ArrayList<>(); // 存放所有图片的路径
    private ArrayList<album> all_album = new ArrayList<>(); //按系统相册所属分开照片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null){  // 去掉标题栏
            getSupportActionBar().hide();
        }
        verifyStoragePermissions(MainActivity.this);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("照片", PageFragment1.class)
                .add("相册", PageFragment2.class)
                .add("发现", PageFragment3.class)
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);

        search_all_picture();
        app = (MyApplication)getMyApplication();
        app.set_all_photo_set(all_photo_set); //放到Application中
        app.set_all_album(all_album);
    }

    public static void verifyStoragePermissions(Activity activity) {
        try {

            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void search_all_picture() { // 获取系统中所有图片的路径
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projImage = { MediaStore.Images.Media._ID
                , MediaStore.Images.Media.DATA
                ,MediaStore.Images.Media.SIZE
                ,MediaStore.Images.Media.DISPLAY_NAME};
        Cursor mCursor = this.getContentResolver().query(mImageUri,
                projImage,
                MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED+" desc");

        if( mCursor != null )
        {
            while(mCursor.moveToNext()){
                String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                all_photo_set.add(new SpacePhoto(path,displayName)); //将获取到的路径加入路径集合

                String dirPath = new File(path).getParentFile().getAbsolutePath(); // 获取该图片的父路径名

                int pdf = 0; //判断此图片所属的相册是否已存在，pdf为0表示图片所属的相册还不存在
                for(int i = 0; i < all_album.size(); i++){
                    if( all_album.get(i).getDirpath().equals(dirPath)){
                        pdf = 1;
                        all_album.get(i).add(new SpacePhoto(path,displayName));
                    }
                }
                if( pdf == 0){
                    ArrayList<SpacePhoto> new_list = new ArrayList<>();
                    new_list.add(new SpacePhoto(path,displayName));
                    String Str[] = dirPath.split("/");
                    String album_name = Str[Str.length - 1];
                    all_album.add(new album(new_list,dirPath,album_name));
                }

            }

        }

    }
}



