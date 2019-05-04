package com.study.sysu.photo;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import static com.study.sysu.photo.MyApplication.getMyApplication;

public class PageFragment1 extends Fragment {

    private ArrayList<SpacePhoto> all_photo_set = new ArrayList<>(); // 存放所有图片的路径
    private ArrayList<album> all_album = new ArrayList<>(); //按系统相册所属分开照片
    private MyApplication app;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, // 系统会在Fragment首次绘制其用户界面时调用此方法
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_page1,container,false);

        search_all_picture();
        app = (MyApplication)getMyApplication();
        app.set_all_photo_set(all_photo_set); //放到Application中
        app.set_all_album(all_album);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rv_images_1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ImageGalleryAdapter adapter = new ImageGalleryAdapter(getActivity(), all_photo_set);
        //调用这个函数的时候SpacePhoto并不是空的
        recyclerView.setAdapter(adapter);
        return root;
    }

    public void search_all_picture() { // 获取系统中所有图片的路径
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projImage = { MediaStore.Images.Media._ID
                , MediaStore.Images.Media.DATA
                ,MediaStore.Images.Media.SIZE
                ,MediaStore.Images.Media.DISPLAY_NAME};
        Cursor mCursor = getActivity().getContentResolver().query(mImageUri,
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
