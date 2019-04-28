package com.study.sysu.photo;

import java.util.ArrayList;

public class album {
    private ArrayList<SpacePhoto> photo_set;
    private String album_name;
    private String dirpath;

    public album(ArrayList<SpacePhoto> list,String path,String str){
        photo_set = list;
        dirpath = path;
        album_name = str;
    }
    public void add(SpacePhoto item){
        photo_set.add(item);
    }

    public String getAlbum_name(){
        return album_name;
    }

    public String getDirpath(){
        return dirpath;
    }

    public int size(){
        return photo_set.size();
    }

    public ArrayList<SpacePhoto> getPhotoList(){
        return photo_set;
    }
}
