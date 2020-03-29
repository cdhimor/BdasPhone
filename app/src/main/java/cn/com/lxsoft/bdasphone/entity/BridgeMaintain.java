package cn.com.lxsoft.bdasphone.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import cn.com.lxsoft.bdasphone.utils.ConvertUtils;

@Entity
public class BridgeMaintain {
    @SerializedName("DM")
    @Id
    private String qiaoLiangDaiMa;

    @SerializedName("PF")
    int pingFen;

    @SerializedName("JJ")
    float jingJiJiShu;

    @SerializedName("JT")
    float jiaoTongLiang;

    @SerializedName("NX")
    float shiYongNianXian;

    @SerializedName("BJ")
    float zhongYaoBuJian;

    @SerializedName("YH")
    float yanghuDengJi;

    @SerializedName("HZ")
    float heZaiDengJi;

    @SerializedName("LX")
    String yangHuLeiXing;

    public BridgeMaintain (QiaoLiang bridge,YearTest yearTest){

        float ll=bridge.getJiaoTongLiuLiang();
        float resll=1f;
        if(ll>=400 && ll<600 )
            resll=0.96f;
        else if(ll>=600 && ll<800)
            resll=0.92f;
        else if(ll>=800 && ll<1000)
            resll=0.88f;
        else if(ll>=1000)
            resll=0.85f;
        setJiaoTongLiang(resll);

        int day=ConvertUtils.getYearSpace(new Date(),bridge.getJianQiaoNianYue());
        float resday=1;
        if(day>=5 && day<10)
            resday=0.96f;
        else if(day>=10 && day<20)
            resday=0.92f;
        else if(day>=20 && day<30)
            resday=0.88f;
        else if(day>=30)
            resday=0.85f;
        setShiYongNianXian(resday);

        float[] hz={0.92f,0.93f,0.92f,0.93f,0.95f,0.96f,0.97f,0.98f};
        float reshz=1;
        if(bridge.getHeZaiDengJi()!=null && !bridge.getHeZaiDengJi().isEmpty())
            reshz=hz[Integer.valueOf(bridge.getHeZaiDengJi())];
        setHeZaiDengJi(reshz);

        ArrayList<String> gjlist= (ArrayList<String>)Arrays.asList(yearTest.getGouJianPingFen().split(","));
        float resbj=1;

        if(gjlist.indexOf("101")!=-1 || gjlist.indexOf("102")!=-1 || gjlist.indexOf("103")!=-1 || gjlist.indexOf("104")!=-1
                || gjlist.indexOf("121")!=-1 || gjlist.indexOf("122")!=-1 || gjlist.indexOf("127")!=-1 || gjlist.indexOf("128")!=-1 || gjlist.indexOf("129")!=-1 || gjlist.indexOf("130")!=-1
                || gjlist.indexOf("141")!=-1 || gjlist.indexOf("142")!=-1
                || gjlist.indexOf("151")!=-1 || gjlist.indexOf("152")!=-1
                || gjlist.indexOf("203")!=-1 || gjlist.indexOf("204")!=-1
                || gjlist.indexOf("161")!=-1)
            resbj=0.90f;
        else if(gjlist.indexOf("105")!=-1 || gjlist.indexOf("106")!=-1 || gjlist.indexOf("107")!=-1 || gjlist.indexOf("108")!=-1
                || gjlist.indexOf("123")!=-1 || gjlist.indexOf("124")!=-1 || gjlist.indexOf("125")!=-1 || gjlist.indexOf("126")!=-1
                || gjlist.indexOf("144")!=-1 || gjlist.indexOf("145")!=-1 || gjlist.indexOf("146")!=-1
                || gjlist.indexOf("301")!=-1 || gjlist.indexOf("302")!=-1)
            resbj=0.95f;
        setZhongYaoBuJian(resbj);


        float resjj=1;
        switch (bridge.getLeiXing()){
            case "1":
                resjj=0.88f;
                break;
            case "2":
                resjj=0.92f;
                break;
            case "3":
                resjj=0.96f;
                break;
        }
        setJingJiJiShu(resjj);


    }

    @Generated(hash = 842360067)
    public BridgeMaintain(String qiaoLiangDaiMa, int pingFen, float jingJiJiShu,
            float jiaoTongLiang, float shiYongNianXian, float zhongYaoBuJian,
            float yanghuDengJi, float heZaiDengJi, String yangHuLeiXing) {
        this.qiaoLiangDaiMa = qiaoLiangDaiMa;
        this.pingFen = pingFen;
        this.jingJiJiShu = jingJiJiShu;
        this.jiaoTongLiang = jiaoTongLiang;
        this.shiYongNianXian = shiYongNianXian;
        this.zhongYaoBuJian = zhongYaoBuJian;
        this.yanghuDengJi = yanghuDengJi;
        this.heZaiDengJi = heZaiDengJi;
        this.yangHuLeiXing = yangHuLeiXing;
    }

    @Generated(hash = 643811150)
    public BridgeMaintain() {
    }

    public String getQiaoLiangDaiMa() {
        return this.qiaoLiangDaiMa;
    }

    public void setQiaoLiangDaiMa(String qiaoLiangDaiMa) {
        this.qiaoLiangDaiMa = qiaoLiangDaiMa;
    }

    public int getPingFen() {
        return this.pingFen;
    }

    public void setPingFen(int pingFen) {
        this.pingFen = pingFen;
    }

    public float getJingJiJiShu() {
        return this.jingJiJiShu;
    }

    public void setJingJiJiShu(float jingJiJiShu) {
        this.jingJiJiShu = jingJiJiShu;
    }

    public float getJiaoTongLiang() {
        return this.jiaoTongLiang;
    }

    public void setJiaoTongLiang(float jiaoTongLiang) {
        this.jiaoTongLiang = jiaoTongLiang;
    }

    public float getShiYongNianXian() {
        return this.shiYongNianXian;
    }

    public void setShiYongNianXian(float shiYongNianXian) {
        this.shiYongNianXian = shiYongNianXian;
    }

    public float getZhongYaoBuJian() {
        return this.zhongYaoBuJian;
    }

    public void setZhongYaoBuJian(float zhongYaoBuJian) {
        this.zhongYaoBuJian = zhongYaoBuJian;
    }

    public float getYanghuDengJi() {
        return this.yanghuDengJi;
    }

    public void setYanghuDengJi(float yanghuDengJi) {
        this.yanghuDengJi = yanghuDengJi;
    }

    public float getHeZaiDengJi() {
        return this.heZaiDengJi;
    }

    public void setHeZaiDengJi(float heZaiDengJi) {
        this.heZaiDengJi = heZaiDengJi;
    }

    public String getYangHuLeiXing() {
        return this.yangHuLeiXing;
    }

    public void setYangHuLeiXing(String yangHuLeiXing) {
        this.yangHuLeiXing = yangHuLeiXing;
    }
}
