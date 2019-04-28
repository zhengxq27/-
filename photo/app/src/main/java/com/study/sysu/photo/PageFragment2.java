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
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PageFragment2 extends Fragment {
    private ArrayList<SpacePhoto> all_photo_set = new ArrayList<>(); // 存放所有图片的路径
    private ArrayList<album> all_album = new ArrayList<>(); //按系统相册所属分开照片
    private listViewAdapter albumAdapter;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, // 系统会在Fragment首次绘制其用户界面时调用此方法
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_page2,container,false);

        search_all_picture(); //查找手机存储中的所有图片，按相册分类
        albumAdapter = new listViewAdapter(all_album,getActivity());
        listView = root.findViewById(R.id.listview);
        listView.setAdapter(albumAdapter);

//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
//        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rv_images_2);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(layoutManager);
//
//        ImageGalleryAdapter adapter = new ImageGalleryAdapter(getActivity(), all_photo_set);
//        recyclerView.setAdapter(adapter);
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

                int pdf = 0; //判断此图片所属的相册是否已存在
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

            for(int i = 0; i < all_album.size(); i++){
                Log.d("分类3", "相册名称：" + all_album.get(i).getAlbum_name() + "  图片数量：" + all_album.get(i).size());
            }


        }

    }

}
