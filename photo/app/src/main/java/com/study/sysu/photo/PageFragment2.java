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

public class PageFragment2 extends Fragment {

    private SpacePhoto[] path_set;
    private int index = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, // 系统会在Fragment首次绘制其用户界面时调用此方法
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_page2,container,false);

        search_all_picture(); //查找手机存储中的所有图片
//        path_set = new SpacePhoto[1];
//        path_set[index] = new SpacePhoto("/storage/emulated/0/DCIM/Camera/IMG_20190410_032031.jpg","tesing");

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rv_images_2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ImageGalleryAdapter adapter = new ImageGalleryAdapter(getActivity(), path_set);
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
            int picture_num = mCursor.getColumnCount();
            path_set = new SpacePhoto[picture_num];
            while(mCursor.moveToNext()){
                String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                path_set[index] = new SpacePhoto(path,displayName);
                index++;
            }
        }

    }

}
