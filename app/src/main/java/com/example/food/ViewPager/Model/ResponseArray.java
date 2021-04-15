package com.example.food.ViewPager.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseArray {

    @SerializedName("banner_images")
    @Expose
    private List<String> bannerImages = null;
    @SerializedName("android_app_version")
    @Expose
    private String androidAppVersion;
    @SerializedName("ios_app_version")
    @Expose
    private String iosAppVersion;
    @SerializedName("android_app_link")
    @Expose
    private String androidAppLink;
    @SerializedName("ios_app_link")
    @Expose
    private String iosAppLink;

    public List<String> getBannerImages() {
        return bannerImages;
    }

    public void setBannerImages(List<String> bannerImages) {
        this.bannerImages = bannerImages;
    }

    public String getAndroidAppVersion() {
        return androidAppVersion;
    }

    public void setAndroidAppVersion(String androidAppVersion) {
        this.androidAppVersion = androidAppVersion;
    }

    public String getIosAppVersion() {
        return iosAppVersion;
    }

    public void setIosAppVersion(String iosAppVersion) {
        this.iosAppVersion = iosAppVersion;
    }

    public String getAndroidAppLink() {
        return androidAppLink;
    }

    public void setAndroidAppLink(String androidAppLink) {
        this.androidAppLink = androidAppLink;
    }

    public String getIosAppLink() {
        return iosAppLink;
    }

    public void setIosAppLink(String iosAppLink) {
        this.iosAppLink = iosAppLink;
    }

}
