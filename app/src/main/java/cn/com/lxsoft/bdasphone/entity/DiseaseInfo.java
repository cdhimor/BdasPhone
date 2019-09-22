package cn.com.lxsoft.bdasphone.entity;

import android.content.res.XmlResourceParser;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

public class DiseaseInfo {
    private Integer biaoDu;

    private String bianMa;

    private String dingLiangMiaoShu="";

    private String dingXingMiaoShu="";

    public DiseaseInfo() {
    }

    public DiseaseInfo(String disCode,XmlResourceParser parser)
    {
        setBiaoDu(parser.getAttributeIntValue(null,"b",1));
        setBianMa(disCode.concat(parser.getAttributeValue(null,"b")));
        setDingXingMiaoShu(parser.getAttributeValue(null,"n"));
        if(parser.getAttributeCount()>2)
            setDingLiangMiaoShu(parser.getAttributeValue(null,"s"));
    }

    public Integer getBiaoDu() {
        return biaoDu;
    }

    public void setBiaoDu(Integer biaoDu) {
        this.biaoDu = biaoDu;
    }

    public String getDingLiangMiaoShu() {
        return dingLiangMiaoShu;
    }

    public void setDingLiangMiaoShu(String dingLiangMiaoShu) {
        this.dingLiangMiaoShu = "["+getBiaoDu().toString()+"]"+dingLiangMiaoShu;
    }

    public String getDingXingMiaoShu() {
        return dingXingMiaoShu;
    }

    public void setDingXingMiaoShu(String dingXingMiaoShu) {
        this.dingXingMiaoShu = "["+getBiaoDu().toString()+"]"+dingXingMiaoShu;
    }

    public String getBianMa() {
        return bianMa;
    }

    public void setBianMa(String bianMa) {
        this.bianMa = bianMa;
    }
}
