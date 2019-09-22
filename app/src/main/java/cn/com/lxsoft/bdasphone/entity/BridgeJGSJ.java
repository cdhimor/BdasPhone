package cn.com.lxsoft.bdasphone.entity;

import com.google.gson.annotations.SerializedName;


public class BridgeJGSJ {
    @SerializedName("桥梁代码")
    public String qiaoLiangDaiMa;

    @SerializedName("桥梁全长")
    public String qiaoLiangQuanChang;

    @SerializedName("跨径组合")
    public String qiaoKuaZuHe;

    @SerializedName("最大跨径")
    public float zuiDaKuaJing;

    @SerializedName("桥宽组合")
    public String qiaoKuanZuHe;

    @SerializedName("桥面净宽")
    public float qiaoMianJingKuan;

    @SerializedName("桥面全宽")
    public float  qiaoMianQuanKuan;

    @SerializedName("桥面标高")
    public float qiaoGao;

    @SerializedName("桥下净高")
    public float jingGao;

    @SerializedName("桥上净高")
    public float qiaoLiangXianGao;

    @SerializedName("是否宽路窄桥")
    public String  shiFouKuanLuZaiQiao;

    @SerializedName("栏杆或防撞护栏")  //1.12
    public String  lanGanHuoFangZhuangHuLan;

    @SerializedName("护栏或防撞栏高度")
    public float  lanGanHuoFangZhuangHuLanGaoDu;

    @SerializedName("中央分隔带")  //1.14
    public String zhongYangFenGeDai;

    @SerializedName("中央或分离带宽度")
    public float zhongYangFenGeDaiGaoDu;

    @SerializedName("引道总宽")
    public float yinDaoZongKuan;

    @SerializedName("引道路面宽")
    public float yinDaoLuMianKuan;

    @SerializedName("引道线性")
    public String yinDaoXianXing;

    @SerializedName("伸缩缝类型")  //2.2
    public String shenSuoFengLeiXing;

    @SerializedName("支座类型")  //2.3
    public String zhiZuoLeiXing;

    @SerializedName("桥台护坡类型") //2.4
    public String qiaoTaiHuPoLeiXing;

    @SerializedName("调治构造物")
    public String tiaoZhiGouZaoWu;

    @SerializedName("常水位")
    public float changShuiWei;

    @SerializedName("设计水位")
    public float sheJiShuiWei;

    @SerializedName("历史洪水位")
    public float liShiHongShuiWei;

    @SerializedName("墩台防撞设施类型")  //2.5
    public String dunTaiFangZhuangLeiXing;

    @SerializedName("是否互通立交")
    public String shiFouHuTongLiJiao;

    @SerializedName("收费性质") //1.13
    public String souFeiXingZhi;

    public String getQiaoLiangDaiMa() {
        return qiaoLiangDaiMa;
    }

    public void setQiaoLiangDaiMa(String qiaoLiangDaiMa) {
        this.qiaoLiangDaiMa = qiaoLiangDaiMa;
    }

    public String getQiaoLiangQuanChang() {
        return qiaoLiangQuanChang;
    }

    public void setQiaoLiangQuanChang(String qiaoLiangQuanChang) {
        this.qiaoLiangQuanChang = qiaoLiangQuanChang;
    }

    public String getQiaoKuaZuHe() {
        return qiaoKuaZuHe;
    }

    public void setQiaoKuaZuHe(String qiaoKuaZuHe) {
        this.qiaoKuaZuHe = qiaoKuaZuHe;
    }

    public float getZuiDaKuaJing() {
        return zuiDaKuaJing;
    }

    public String getZuiDaKuaJingInfo() {
        return String.valueOf(zuiDaKuaJing);
    }


    public void setZuiDaKuaJing(float zuiDaKuaJing) {
        this.zuiDaKuaJing = zuiDaKuaJing;
    }

    public String getQiaoKuanZuHe() {
        return qiaoKuanZuHe;
    }

    public void setQiaoKuanZuHe(String qiaoKuanZuHe) {
        this.qiaoKuanZuHe = qiaoKuanZuHe;
    }

    public float getQiaoMianJingKuan() {
        return qiaoMianJingKuan;
    }

    public String getQiaoMianJingKuanInfo() {
        return String.valueOf(qiaoMianJingKuan);
    }


    public void setQiaoMianJingKuan(float qiaoMianJingKuan) {
        this.qiaoMianJingKuan = qiaoMianJingKuan;
    }

    public float getQiaoMianQuanKuan() {
        return qiaoMianQuanKuan;
    }

    public String getQiaoMianQuanKuanInfo() {
        return String.valueOf(qiaoMianQuanKuan);
    }

    public void setQiaoMianQuanKuan(float qiaoMianQuanKuan) {
        this.qiaoMianQuanKuan = qiaoMianQuanKuan;
    }

    public float getQiaoGao() {
        return qiaoGao;
    }

    public String getQiaoGaoInfo() {
        return String.valueOf(qiaoGao);
    }

    public void setQiaoGao(float qiaoGao) {
        this.qiaoGao = qiaoGao;
    }

    public float getJingGao() {
        return jingGao;
    }

    public String getJingGaoInfo() {
        return String.valueOf(jingGao);
    }

    public void setJingGao(float jingGao) {
        this.jingGao = jingGao;
    }

    public float getQiaoLiangXianGao() {
        return qiaoLiangXianGao;
    }

    public String getQiaoLiangXianGaoInfo() {
        return String.valueOf(qiaoLiangXianGao);
    }

    public void setQiaoLiangXianGao(float qiaoLiangXianGao) {
        this.qiaoLiangXianGao = qiaoLiangXianGao;
    }

    public String getShiFouKuanLuZaiQiao() {
        return shiFouKuanLuZaiQiao;
    }

    public void setShiFouKuanLuZaiQiao(String shiFouKuanLuZaiQiao) {
        this.shiFouKuanLuZaiQiao = shiFouKuanLuZaiQiao;
    }

    public String getLanGanHuoFangZhuangHuLan() {
        return lanGanHuoFangZhuangHuLan;
    }

    public void setLanGanHuoFangZhuangHuLan(String lanGanHuoFangZhuangHuLan) {
        this.lanGanHuoFangZhuangHuLan = lanGanHuoFangZhuangHuLan;
    }

    public float getLanGanHuoFangZhuangHuLanGaoDu() {
        return lanGanHuoFangZhuangHuLanGaoDu;
    }

    public String getLanGanHuoFangZhuangHuLanGaoDuInfo() {
        return String.valueOf(lanGanHuoFangZhuangHuLanGaoDu);
    }

    public void setLanGanHuoFangZhuangHuLanGaoDu(float lanGanHuoFangZhuangHuLanGaoDu) {
        this.lanGanHuoFangZhuangHuLanGaoDu = lanGanHuoFangZhuangHuLanGaoDu;
    }

    public String getZhongYangFenGeDai() {
        return zhongYangFenGeDai;
    }

    public void setZhongYangFenGeDai(String zhongYangFenGeDai) {
        this.zhongYangFenGeDai = zhongYangFenGeDai;
    }

    public float getZhongYangFenGeDaiGaoDu() {
        return zhongYangFenGeDaiGaoDu;
    }

    public String getZhongYangFenGeDaiGaoDuInfo() {
        return String.valueOf(zhongYangFenGeDaiGaoDu);
    }


    public void setZhongYangFenGeDaiGaoDu(float zhongYangFenGeDaiGaoDu) {
        this.zhongYangFenGeDaiGaoDu = zhongYangFenGeDaiGaoDu;
    }

    public float getYinDaoZongKuan() {
        return yinDaoZongKuan;
    }

    public String getYinDaoZongKuanInfo() {
        return String.valueOf(yinDaoZongKuan);
    }

    public void setYinDaoZongKuan(float yinDaoZongKuan) {
        this.yinDaoZongKuan = yinDaoZongKuan;
    }

    public float getYinDaoLuMianKuan() {
        return yinDaoLuMianKuan;
    }

    public String getYinDaoLuMianKuanInfo() {
        return String.valueOf(yinDaoLuMianKuan);
    }

    public void setYinDaoLuMianKuan(float yinDaoLuMianKuan) {
        this.yinDaoLuMianKuan = yinDaoLuMianKuan;
    }

    public String getYinDaoXianXing() {
        return yinDaoXianXing;
    }

    public void setYinDaoXianXing(String yinDaoXianXing) {
        this.yinDaoXianXing = yinDaoXianXing;
    }

    public String getShenSuoFengLeiXing() {
        return shenSuoFengLeiXing;
    }

    public void setShenSuoFengLeiXing(String shenSuoFengLeiXing) {
        this.shenSuoFengLeiXing = shenSuoFengLeiXing;
    }

    public String getZhiZuoLeiXing() {
        return zhiZuoLeiXing;
    }

    public void setZhiZuoLeiXing(String zhiZuoLeiXing) {
        this.zhiZuoLeiXing = zhiZuoLeiXing;
    }

    public String getQiaoTaiHuPoLeiXing() {
        return qiaoTaiHuPoLeiXing;
    }

    public void setQiaoTaiHuPoLeiXing(String qiaoTaiHuPoLeiXing) {
        this.qiaoTaiHuPoLeiXing = qiaoTaiHuPoLeiXing;
    }

    public String getTiaoZhiGouZaoWu() {
        return tiaoZhiGouZaoWu;
    }

    public void setTiaoZhiGouZaoWu(String tiaoZhiGouZaoWu) {
        this.tiaoZhiGouZaoWu = tiaoZhiGouZaoWu;
    }

    public float getChangShuiWei() {
        return changShuiWei;
    }

    public String getChangShuiWeiInfo() {
        return String.valueOf(changShuiWei);
    }

    public void setChangShuiWei(float changShuiWei) {
        this.changShuiWei = changShuiWei;
    }

    public float getSheJiShuiWei() {
        return sheJiShuiWei;
    }

    public String getSheJiShuiWeiInfo() {
        return String.valueOf(sheJiShuiWei);
    }

    public void setSheJiShuiWei(float sheJiShuiWei) {
        this.sheJiShuiWei = sheJiShuiWei;
    }

    public float getLiShiHongShuiWei() {
        return liShiHongShuiWei;
    }

    public String getLiShiHongShuiWeiInfo() {
        return String.valueOf(liShiHongShuiWei);
    }


    public void setLiShiHongShuiWei(float liShiHongShuiWei) {
        this.liShiHongShuiWei = liShiHongShuiWei;
    }

    public String getDunTaiFangZhuangLeiXing() {
        return dunTaiFangZhuangLeiXing;
    }

    public void setDunTaiFangZhuangLeiXing(String dunTaiFangZhuangLeiXing) {
        this.dunTaiFangZhuangLeiXing = dunTaiFangZhuangLeiXing;
    }

    public String getShiFouHuTongLiJiao() {
        return shiFouHuTongLiJiao;
    }

    public void setShiFouHuTongLiJiao(String shiFouHuTongLiJiao) {
        this.shiFouHuTongLiJiao = shiFouHuTongLiJiao;
    }

    public String getSouFeiXingZhi() {
        return souFeiXingZhi;
    }

    public void setSouFeiXingZhi(String souFeiXingZhi) {
        this.souFeiXingZhi = souFeiXingZhi;
    }
}
