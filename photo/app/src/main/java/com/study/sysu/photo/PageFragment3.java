package com.study.sysu.photo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PageFragment3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, // 系统会在Fragment首次绘制其用户界面时调用此方法
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_page2,container,false);


        return root;
    }
}
