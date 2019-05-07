package com.study.sysu.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import static com.study.sysu.photo.MyApplication.getMyApplication;

public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    private MyApplication app;
    private ArrayList<SpacePhoto> all_photo_set = new ArrayList<>(); // 存放所有图片的路径
    private ArrayList<album> all_album = new ArrayList<>(); //按系统相册所属分开照片
    public static int MODE = Context.MODE_PRIVATE;
    private boolean isFirstIn;
    private SharedPreferences preferences;
    private Button enter_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getSupportActionBar() != null){  // 去掉标题栏
            getSupportActionBar().hide();
        }

        preferences = getSharedPreferences("first_pref", MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", true);

        if(isFirstIn){
            enter_button = findViewById(R.id.enter_button);
            enter_button.setVisibility(View.VISIBLE);

            verifyStoragePermissions(SplashActivity.this);

            SharedPreferences preferences = getSharedPreferences("first_pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstIn", false);
            editor.commit();
        }
        else{
            enter_button = findViewById(R.id.enter_button);
            enter_button.setVisibility(View.VISIBLE);
            startActivity(new Intent(this,MainActivity.class));
            finish();
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

    public void gotoMainAct(View view){
        search_all_picture();
        app = (MyApplication)getMyApplication();
        app.set_all_album(all_album);
        app.set_all_photo_set(all_photo_set);
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
