package com.study.sysu.photo;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.study.sysu.photo.MyApplication.getMyApplication;

public class PageFragment2 extends Fragment {

    private listViewAdapter albumAdapter;
    private ListView listView;
    private MyApplication app;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, // 系统会在Fragment首次绘制其用户界面时调用此方法
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_page2,container,false);

        app = (MyApplication)getMyApplication();

        albumAdapter = new listViewAdapter(app.get_all_album(),getActivity());
        listView = root.findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),album_show_page.class);
                Bundle bundle = new Bundle();
                bundle.putInt("index",position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        listView.setAdapter(albumAdapter);

        return root;
    }

}
