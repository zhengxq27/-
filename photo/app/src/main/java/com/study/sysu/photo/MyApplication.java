package com.study.sysu.photo;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

public class MyApplication extends Application{
    private static MyApplication instance;
    private static ArrayList<SpacePhoto> all_photo_set = new ArrayList<>(); // 存放所有图片的路径
    private static ArrayList<album> all_album = new ArrayList<>(); //按系统相册所属分开照片
    private static boolean firstIn = true;

    // 获取Application
    public static Context getMyApplication() {
        return instance;
    }

    public static ArrayList<SpacePhoto> get_all_photo_set(){
        return all_photo_set;
    }

    public static ArrayList<album> get_all_album(){
        return all_album;
    }

    public static void set_all_photo_set(ArrayList<SpacePhoto> copy){
        all_photo_set = copy;
    }

    public static void set_all_album(ArrayList<album> copy){
        all_album = copy;
    }

    public static void set_firdtIn_var(boolean label) { firstIn = label;}

    public static boolean get_firstIn_var() { return firstIn; }

}
