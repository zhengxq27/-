package com.study.sysu.photo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class listViewAdapter extends BaseAdapter
{

    private ArrayList<album> list;
    private Context context;


    public listViewAdapter(ArrayList<album>list, Context context)
    {
        this.list = list;
        this.context = context;
    }


    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        if (list == null) {
            return null;
        }
        return list.get(i);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // 通过inflate的方法加载布局，context需要在使用这个Adapter的Activity中传入。
        if( view == null )
        {
            view = LayoutInflater.from(context).inflate(R.layout.album, null);
        }
        ImageView image = view.findViewById(R.id.cover);
        TextView album_name = view.findViewById(R.id.album_name);
        TextView picture_num = view.findViewById(R.id.picture_num);

        Glide.with(context) //传递上下文
                .load(list.get(i).getPhotoList().get(0).getUrl()) // 目录路径或者URI或者URL
                .centerCrop() // 图片有可能被裁剪
                .placeholder(R.drawable.error) //一个本地APP资源id，在图片被加载前作为占位的图片
                .into(image); // 要放置图片的目标imageView控件

        picture_num.setText(list.get(i).size() + "张图片");
        album_name.setText(list.get(i).getAlbum_name());

        return view; // 将这个处理好的view返回
    }
}