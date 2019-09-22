package cn.com.lxsoft.bdasphone.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MaintainBuilder {

    public BridgeMaintain build(QiaoLiang bridge,YearTest yearTest){
        BridgeMaintain bm=new BridgeMaintain();

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
        bm.setJiaoTongLiang(resll);

        int day=new Date().getYear()-bridge.getJianQiaoNianYue().getYear();
        float resday=1;
        if(day>=5 && day<10)
            resday=0.96f;
        else if(day>=10 && day<20)
            resday=0.92f;
        else if(day>=20 && day<30)
            resday=0.88f;
        else if(day>=30)
            resday=0.85f;
        bm.setShiYongNianXian(resday);

        float[] hz={0.92f,0.93f,0.92f,0.93f,0.95f,0.96f,0.97f,0.98f};
        float reshz=1;
        if(bridge.getHeZaiDengJi()!=null && !bridge.getHeZaiDengJi().isEmpty())
            reshz=hz[Integer.valueOf(bridge.getHeZaiDengJi())];
        bm.setHeZaiDengJi(reshz);

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
        bm.setZhongYaoBuJian(resbj);


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
        bm.setJingJiJiShu(resjj);

        return bm;
    }
}
