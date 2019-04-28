package com.study.sysu.photo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SpacePhoto implements Parcelable {

    private String mUrl;
    private String mTitle;

    public SpacePhoto(String url, String title) {
        mUrl = url;
        mTitle = title;
    }

    protected SpacePhoto(Parcel in) {
        mUrl = in.readString();
        mTitle = in.readString();
    }

    public static final Creator<SpacePhoto> CREATOR = new Creator<SpacePhoto>() {
        @Override
        public SpacePhoto createFromParcel(Parcel in) {
            return new SpacePhoto(in);
        }

        @Override
        public SpacePhoto[] newArray(int size) {
            return new SpacePhoto[size];
        }
    };

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public static ArrayList<SpacePhoto> getSpacePhotos() {  // 在这里请求图片，或者到时候专门写一个请求图片的函数

        ArrayList<SpacePhoto> result = new ArrayList<>();
        result.add(new SpacePhoto("http://b-ssl.duitang.com/uploads/item/201208/24/20120824153617_hTHeL.thumb.700_0.jpeg", "路飞"));
        result.add(new SpacePhoto("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554874470534&di=f8457882cab256a9092505b061f21716&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F61d36ffd764c6552b4cc8ff43b96e2a4a4dfcd9611757-R7wICR_fw658", "索隆"));
        result.add(new SpacePhoto("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555469265&di=ff5b2a998fdfef133dca091059fd0e7b&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201609%2F01%2F20160901202609_xsuGA.thumb.700_0.png", "娜美"));
        result.add(new SpacePhoto("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1547184935,29541481&fm=26&gp=0.jpg", "乔巴"));
        result.add(new SpacePhoto("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4275965090,4293778523&fm=26&gp=0.jpg", "乌索普"));
        result.add(new SpacePhoto("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2833416705,2183784600&fm=26&gp=0.jpg", "妮可罗宾"));
        result.add(new SpacePhoto("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=551854351,2721443377&fm=26&gp=0.jpg", "山治"));
        result.add(new SpacePhoto("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2923558887,1638786592&fm=26&gp=0.jpg", "弗兰奇"));
        result.add(new SpacePhoto("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3404756489,4113061136&fm=26&gp=0.jpg", "布鲁克"));
        result.add(new SpacePhoto("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2880437780,74327985&fm=26&gp=0.jpg", "甚平"));
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUrl);
        parcel.writeString(mTitle);
    }
}