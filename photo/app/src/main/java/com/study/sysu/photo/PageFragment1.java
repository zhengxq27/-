package com.study.sysu.photo;


import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.HTTP;

import static android.content.Context.POWER_SERVICE;
import static com.android.volley.VolleyLog.TAG;
import static com.study.sysu.photo.MyApplication.getMyApplication;

public class PageFragment1 extends Fragment {

    private MyApplication app;
    private CircleImageView camera_button;
    private ArrayList<SpacePhoto> all_photo_set = new ArrayList<>(); // 存放所有图片的路径
    final private int CROP_PHOTO = 1;
    final private int RESULT_OK = -1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, // 系统会在Fragment首次绘制其用户界面时调用此方法
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_page1,container,false);
        verifyStoragePermissions(getActivity());
        camera_button = root.findViewById(R.id.camear_button);
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(){ //开启一个新线程来完成这个工作，避免主线程负担太大
                    @Override
                    public void run() {
                        takePhoto();
                    }
                }.start();
            }
        });

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

    public void takePhoto(){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, CROP_PHOTO);
    }

    @Override
    public void onActivityResult(int req, int res, Intent data){
        switch (req){
            case CROP_PHOTO:
                final Intent Data = data;
                new Thread(){
                    @Override
                    public void run() {
                        UploadCameraImage(Data);
                    }
                }.start();

                break;
            default:
                break;

        }
    }

    public void UploadCameraImage(Intent data) {

        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.i(TAG, "sd card is not avaiable/writeable right now.");
            return;
        }

        Bitmap bmp = (Bitmap) data.getExtras().get("data");// 解析返回的图片成bitmap

        FileOutputStream Image = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
        String imageName = sdf.format(Calendar.getInstance().getTime()) + ".jpg";

        File file = new File("/mnt/sdcard/intelligentAlbum/");
        file.mkdirs(); //创建文件夹
        imageName = "/mnt/sdcard/intelligentAlbum/" + imageName;//完整的路径

        try {// 写入SD card
            Image = new FileOutputStream(imageName);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, Image);
            if (Image != null) {
                Log.d("Image", "saveCameraImage: Image is not null");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                Image.flush();
                Image.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("imageName", "UploadCameraImage: " + imageName);
        File file1 = new File(imageName);
        if(file1.exists()){
            Log.d("success", "UploadCameraImage: success");
            Log.d("success", "UploadCameraImage: " + file1.getName());
        }
        else{
            Log.d("fail", "UploadCameraImage: fail");
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file1);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file1.getName(), requestFile);

        String descriptionString = "this is photo description";
        //RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);

        OkHttpClient build = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.2.183:8080")
                // 本次实验不需要自定义Gson
                .addConverterFactory(GsonConverterFactory.create())
               // .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                // build 即为okhttp声明的变量，下文会讲
                .client(build)
                .build();

        MyService myService = retrofit.create(MyService.class);
        Call<Object> myCall = myService.upload(descriptionString,body);
        myCall.enqueue(new Callback<Object>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<Object> call, final Response<Object> response) {
                // 步骤7：处理返回的数据结果
                if( response.body() != null){
                    Log.d("reply", "onResponse: " + response.toString());
                    Toast.makeText(getActivity(),"图片分类成功，分类结果为：XXX",Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("reply", "onResponse: null");
                    Toast.makeText(getActivity(),"图片分类失败",Toast.LENGTH_SHORT).show();
                }
            }
            //请求失败时回调
            @Override
            public void onFailure(Call<Object> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
