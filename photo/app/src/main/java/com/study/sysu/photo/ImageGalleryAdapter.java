package com.study.sysu.photo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>  {

    private ArrayList<SpacePhoto> mSpacePhotos;
    private Context mContext;

    @Override
    public ImageGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.item_photo, parent, false);
        ImageGalleryAdapter.MyViewHolder viewHolder = new ImageGalleryAdapter.MyViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageGalleryAdapter.MyViewHolder holder, int position) {

        SpacePhoto spacePhoto = mSpacePhotos.get(position);
        ImageView imageView = holder.mPhotoImageView;
        Glide.with(mContext) //传递上下文
                .load(spacePhoto.getUrl()) // 目录路径或者URI或者URL
                .centerCrop() // 图片有可能被裁剪
                .placeholder(R.drawable.error) //一个本地APP资源id，在图片被加载前作为占位的图片
                .into(imageView); // 要放置图片的目标imageView控件
    }

    @Override
    public int getItemCount() {
        return (mSpacePhotos.size());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mPhotoImageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mPhotoImageView = (ImageView) itemView.findViewById(R.id.iv_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION) {
                SpacePhoto spacePhoto = mSpacePhotos.get(position);
                String url = spacePhoto.getUrl();
                Intent intent = new Intent(mContext, SpacePhotoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url",url);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        }
    }

    public ImageGalleryAdapter(Context context, ArrayList<SpacePhoto> spacePhotos) {
        mSpacePhotos = new ArrayList<>();
        mContext = context;
        mSpacePhotos = spacePhotos;
    }
}
