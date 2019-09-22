package cn.com.lxsoft.bdasphone.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

import cn.com.lxsoft.bdasphone.utils.ConvertUtils;

public class BridgeDAZL {
    @SerializedName("桥梁代码")
    private String qiaoLiangDaiMa;

    @SerializedName("总体照片")
    private String zongTiZhaoPian;

    @SerializedName("正面照片")
    private String zhengMianZhaoPian;

    public String getQiaoLiangDaiMa() {
        return qiaoLiangDaiMa;
    }

    public void setQiaoLiangDaiMa(String qiaoLiangDaiMa) {
        this.qiaoLiangDaiMa = qiaoLiangDaiMa;
    }

    public String getZongTiZhaoPian() {
        return zongTiZhaoPian;
    }

    public void setZongTiZhaoPian(String zongTiZhaoPian) {
        this.zongTiZhaoPian = zongTiZhaoPian;
    }

    public String getZhengMianZhaoPian() {
        return zhengMianZhaoPian;
    }

    public void setZhengMianZhaoPian(String zhengMianZhaoPian) {
        this.zhengMianZhaoPian = zhengMianZhaoPian;
    }
}
