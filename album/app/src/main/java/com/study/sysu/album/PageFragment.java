package com.study.sysu.album;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.yanzhenjie.album.impl.OnItemClickListener;
import com.yanzhenjie.album.widget.divider.Api21ItemDivider;
import com.yanzhenjie.album.widget.divider.Divider;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PageFragment extends Fragment {
    private Toolbar mToolbar;
    private TextView mTvMessage;

    private Adapter mAdapter;
    private ArrayList<AlbumFile> mAlbumFiles;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, // 系统会在Fragment首次绘制其用户界面时调用此方法
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_page,container,false);


        mTvMessage = root.findViewById(R.id.tv_message);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 3));
        Divider divider = new Api21ItemDivider(Color.TRANSPARENT, 10, 10);
        recyclerView.addItemDecoration(divider);

        mAdapter = new Adapter(root.getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                previewAlbum(position); //点击缩略图显示图像的具体部分
            }
        });
        recyclerView.setAdapter(mAdapter);
        //selectAlbum();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) { //在Fragment被绘制后，调用此方法，可以初始化控件资源。
        super.onViewCreated(view, savedInstanceState);

    }

    private void previewAlbum(int position) {
        if (mAlbumFiles == null || mAlbumFiles.size() == 0) {
            Toast.makeText(getActivity(), "Please select first", Toast.LENGTH_LONG).show();
        } else {
            Album.galleryAlbum(this)
                    .checkable(true)
                    .checkedList(mAlbumFiles)
                    .currentPosition(position)
                    .widget(
                            Widget.newDarkBuilder(getActivity())
                                    .title(mToolbar.getTitle().toString())
                                    .build()
                    )
                    .onResult(new Action<ArrayList<AlbumFile>>() {
                        @Override
                        public void onAction(@NonNull ArrayList<AlbumFile> result) {
                            mAlbumFiles = result;
                            mAdapter.notifyDataSetChanged(mAlbumFiles);
                            mTvMessage.setVisibility(result.size() > 0 ? View.VISIBLE : View.GONE);
                        }
                    })
                    .start();
        }
    }

    private void selectAlbum() {
        Album.album(this)
                .multipleChoice()
                .columnCount(2)
                .selectCount(9)
                .camera(true)
                .cameraVideoQuality(1)
                .cameraVideoLimitDuration(Long.MAX_VALUE)
                .cameraVideoLimitBytes(Long.MAX_VALUE)
                .checkedList(mAlbumFiles)
                .widget(
                        Widget.newDarkBuilder(getActivity())
                                .build()
                )
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles = result;
                        mAdapter.notifyDataSetChanged(mAlbumFiles);
                        mTvMessage.setVisibility(result.size() > 0 ? View.VISIBLE : View.GONE);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_LONG).show();
                    }
                })
                .start();
    }
}
