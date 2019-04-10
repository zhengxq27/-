package com.study.sysu.album;

import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import java.util.Locale;

public class Application extends android.app.Application {

    private static Application instance; // 自定义的Application类需要告知系统在实例化的时候是实例化我们自定义的
    //为了让系统实例化的时候找到，我们必须在manifest中修改application标签属性
    //android:name=".Application"

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null) {
            instance = this;

            Album.initialize(AlbumConfig.newBuilder(this) // 初始化全局变量
                    .setAlbumLoader(new MediaLoader())
                    .setLocale(Locale.getDefault())
                    .build() );
        }
    }

    public static Application getInstance() {
        return instance;
    }
}
