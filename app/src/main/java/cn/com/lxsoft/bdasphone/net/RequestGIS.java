package cn.com.lxsoft.bdasphone.net;


public class RequestGIS {
    public String CODE="bridge_update_gis";

    public RequestGIS(String DM, Double LAT, Double LNG) {
        this.DM = DM;
        this.LAT = LAT;
        this.LNG = LNG;
    }

    public String DM;

    public Double LAT;

    public Double LNG;

}
