package cn.com.lxsoft.bdasphone.entity;

import com.amap.api.maps2d.model.LatLng;

import java.util.List;

public class BridgeGisData{
    private String name;
    private String code;
    //private double latitue;
    //private double langitute;

    public BridgeGisData(QiaoLiang bridge){
        name=bridge.getMingCheng();
        code=bridge.getDaiMa();
        //latitue=bridge.getLat();
        //langitute=bridge.getLng();
    }

    /*
    public double getLatitue() {
        return latitue;
    }

    public double getLangitute() {
        return langitute;
    }
    */

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }


}
