package com.study.sysu.photo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class PageFragment3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, // 系统会在Fragment首次绘制其用户界面时调用此方法
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_page3,container,false);

        ImageView imageView1 = root.findViewById(R.id.imageview1);
        ImageView imageView2 = root.findViewById(R.id.imageview2);
        ImageView imageView3 = root.findViewById(R.id.imageview3);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),classcification_page.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position",1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),classcification_page.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position",2);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),classcification_page.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position",3);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return root;
    }
}
