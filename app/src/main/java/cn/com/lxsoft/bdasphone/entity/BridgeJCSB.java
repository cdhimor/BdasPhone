package cn.com.lxsoft.bdasphone.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BridgeJCSB {
    @SerializedName("A")
    @Id
    private String qiaoLiangDaiMa;

    @SerializedName("B")
    private String qiaoLiangMingCheng;

    @SerializedName("C")
    private String luXianHao;

    @SerializedName("D")
    private String luXianMingCheng;

    @SerializedName("E")
    private String luXianLeiXing;

    @SerializedName("F")
    private String shunXuHao;

    @SerializedName("G")
    private String suoZaiDi;

    @SerializedName("H")
    private String  zhongXinZhuangHao;

    @SerializedName("I")
    private String guanYangDanWei;

    @SerializedName("J")
    private String kuaYueDiWuLeiXing;

    @SerializedName("K")
    private String kuaYueDiWuMingCheng;

    @SerializedName("L")
    private String gongLuJiShuDengJi;

    @SerializedName("M")
    private String qiaoLiangXingZhi;

    @SerializedName("N")
    private String qiaoLiangFenLei;

    @SerializedName("O")
    private String qiaoLiangZhuangTai;

    @SerializedName("P")
    private String qiaoLiangYongtu;

    @SerializedName("Q")
    private String sheJiHeZaiDengJi;

    @SerializedName("R")
    private String muQianHeZai;

    @SerializedName("S")
    private String zhuQiaoShangBuJieGou;

    @SerializedName("T")
    private String qiaoDunLeiXing;

    @SerializedName("U")
    private String qiaoTaiLeiXing;

    @SerializedName("V")
    private String zhiZuoLeiXing;

    @SerializedName("W")
    private String qiaoDunJiChuLeiXing;

    @SerializedName("X")
    private String qiaoTaiJiChuLeiXing;

    @SerializedName("Y")
    private String qiaoMianPuZhuangLeiXing;

    @SerializedName("Z")
    private String shenShuoFengLeiXing;

    @SerializedName("AA")
    private String yinQiaoShangBuJieGou;

    @SerializedName("AB")
    private String  siGongZhuangHao;

    @SerializedName("AC")
    private String guanLiXingShiMa;

    @SerializedName("AD")
    private String muQiaoDaiMa;

    @Generated(hash = 1553517806)
    public BridgeJCSB(String qiaoLiangDaiMa, String qiaoLiangMingCheng,
            String luXianHao, String luXianMingCheng, String luXianLeiXing,
            String shunXuHao, String suoZaiDi, String zhongXinZhuangHao,
            String guanYangDanWei, String kuaYueDiWuLeiXing,
            String kuaYueDiWuMingCheng, String gongLuJiShuDengJi,
            String qiaoLiangXingZhi, String qiaoLiangFenLei,
            String qiaoLiangZhuangTai, String qiaoLiangYongtu,
            String sheJiHeZaiDengJi, String muQianHeZai,
            String zhuQiaoShangBuJieGou, String qiaoDunLeiXing,
            String qiaoTaiLeiXing, String zhiZuoLeiXing, String qiaoDunJiChuLeiXing,
            String qiaoTaiJiChuLeiXing, String qiaoMianPuZhuangLeiXing,
            String shenShuoFengLeiXing, String yinQiaoShangBuJieGou,
            String siGongZhuangHao, String guanLiXingShiMa, String muQiaoDaiMa) {
        this.qiaoLiangDaiMa = qiaoLiangDaiMa;
        this.qiaoLiangMingCheng = qiaoLiangMingCheng;
        this.luXianHao = luXianHao;
        this.luXianMingCheng = luXianMingCheng;
        this.luXianLeiXing = luXianLeiXing;
        this.shunXuHao = shunXuHao;
        this.suoZaiDi = suoZaiDi;
        this.zhongXinZhuangHao = zhongXinZhuangHao;
        this.guanYangDanWei = guanYangDanWei;
        this.kuaYueDiWuLeiXing = kuaYueDiWuLeiXing;
        this.kuaYueDiWuMingCheng = kuaYueDiWuMingCheng;
        this.gongLuJiShuDengJi = gongLuJiShuDengJi;
        this.qiaoLiangXingZhi = qiaoLiangXingZhi;
        this.qiaoLiangFenLei = qiaoLiangFenLei;
        this.qiaoLiangZhuangTai = qiaoLiangZhuangTai;
        this.qiaoLiangYongtu = qiaoLiangYongtu;
        this.sheJiHeZaiDengJi = sheJiHeZaiDengJi;
        this.muQianHeZai = muQianHeZai;
        this.zhuQiaoShangBuJieGou = zhuQiaoShangBuJieGou;
        this.qiaoDunLeiXing = qiaoDunLeiXing;
        this.qiaoTaiLeiXing = qiaoTaiLeiXing;
        this.zhiZuoLeiXing = zhiZuoLeiXing;
        this.qiaoDunJiChuLeiXing = qiaoDunJiChuLeiXing;
        this.qiaoTaiJiChuLeiXing = qiaoTaiJiChuLeiXing;
        this.qiaoMianPuZhuangLeiXing = qiaoMianPuZhuangLeiXing;
        this.shenShuoFengLeiXing = shenShuoFengLeiXing;
        this.yinQiaoShangBuJieGou = yinQiaoShangBuJieGou;
        this.siGongZhuangHao = siGongZhuangHao;
        this.guanLiXingShiMa = guanLiXingShiMa;
        this.muQiaoDaiMa = muQiaoDaiMa;
    }

    @Generated(hash = 612018682)
    public BridgeJCSB() {
    }

    public String getQiaoLiangDaiMa() {
        return this.qiaoLiangDaiMa;
    }

    public void setQiaoLiangDaiMa(String qiaoLiangDaiMa) {
        this.qiaoLiangDaiMa = qiaoLiangDaiMa;
    }

    public String getQiaoLiangMingCheng() {
        return this.qiaoLiangMingCheng;
    }

    public void setQiaoLiangMingCheng(String qiaoLiangMingCheng) {
        this.qiaoLiangMingCheng = qiaoLiangMingCheng;
    }

    public String getLuXianHao() {
        return this.luXianHao;
    }

    public void setLuXianHao(String luXianHao) {
        this.luXianHao = luXianHao;
    }

    public String getLuXianMingCheng() {
        return this.luXianMingCheng;
    }

    public void setLuXianMingCheng(String luXianMingCheng) {
        this.luXianMingCheng = luXianMingCheng;
    }

    public String getLuXianLeiXing() {
        return this.luXianLeiXing;
    }

    public void setLuXianLeiXing(String luXianLeiXing) {
        this.luXianLeiXing = luXianLeiXing;
    }

    public String getShunXuHao() {
        return this.shunXuHao;
    }

    public void setShunXuHao(String shunXuHao) {
        this.shunXuHao = shunXuHao;
    }

    public String getSuoZaiDi() {
        return this.suoZaiDi;
    }

    public void setSuoZaiDi(String suoZaiDi) {
        this.suoZaiDi = suoZaiDi;
    }

    public String getZhongXinZhuangHao() {
        return this.zhongXinZhuangHao;
    }

    public void setZhongXinZhuangHao(String zhongXinZhuangHao) {
        this.zhongXinZhuangHao = zhongXinZhuangHao;
    }

    public String getGuanYangDanWei() {
        return this.guanYangDanWei;
    }

    public void setGuanYangDanWei(String guanYangDanWei) {
        this.guanYangDanWei = guanYangDanWei;
    }

    public String getKuaYueDiWuLeiXing() {
        return this.kuaYueDiWuLeiXing;
    }

    public void setKuaYueDiWuLeiXing(String kuaYueDiWuLeiXing) {
        this.kuaYueDiWuLeiXing = kuaYueDiWuLeiXing;
    }

    public String getKuaYueDiWuMingCheng() {
        return this.kuaYueDiWuMingCheng;
    }

    public void setKuaYueDiWuMingCheng(String kuaYueDiWuMingCheng) {
        this.kuaYueDiWuMingCheng = kuaYueDiWuMingCheng;
    }

    public String getGongLuJiShuDengJi() {
        return this.gongLuJiShuDengJi;
    }

    public void setGongLuJiShuDengJi(String gongLuJiShuDengJi) {
        this.gongLuJiShuDengJi = gongLuJiShuDengJi;
    }

    public String getQiaoLiangXingZhi() {
        return this.qiaoLiangXingZhi;
    }

    public void setQiaoLiangXingZhi(String qiaoLiangXingZhi) {
        this.qiaoLiangXingZhi = qiaoLiangXingZhi;
    }

    public String getQiaoLiangFenLei() {
        return this.qiaoLiangFenLei;
    }

    public void setQiaoLiangFenLei(String qiaoLiangFenLei) {
        this.qiaoLiangFenLei = qiaoLiangFenLei;
    }

    public String getQiaoLiangZhuangTai() {
        return this.qiaoLiangZhuangTai;
    }

    public void setQiaoLiangZhuangTai(String qiaoLiangZhuangTai) {
        this.qiaoLiangZhuangTai = qiaoLiangZhuangTai;
    }

    public String getQiaoLiangYongtu() {
        return this.qiaoLiangYongtu;
    }

    public void setQiaoLiangYongtu(String qiaoLiangYongtu) {
        this.qiaoLiangYongtu = qiaoLiangYongtu;
    }

    public String getSheJiHeZaiDengJi() {
        return this.sheJiHeZaiDengJi;
    }

    public void setSheJiHeZaiDengJi(String sheJiHeZaiDengJi) {
        this.sheJiHeZaiDengJi = sheJiHeZaiDengJi;
    }

    public String getMuQianHeZai() {
        return this.muQianHeZai;
    }

    public void setMuQianHeZai(String muQianHeZai) {
        this.muQianHeZai = muQianHeZai;
    }

    public String getZhuQiaoShangBuJieGou() {
        return this.zhuQiaoShangBuJieGou;
    }

    public void setZhuQiaoShangBuJieGou(String zhuQiaoShangBuJieGou) {
        this.zhuQiaoShangBuJieGou = zhuQiaoShangBuJieGou;
    }

    public String getQiaoDunLeiXing() {
        return this.qiaoDunLeiXing;
    }

    public void setQiaoDunLeiXing(String qiaoDunLeiXing) {
        this.qiaoDunLeiXing = qiaoDunLeiXing;
    }

    public String getQiaoTaiLeiXing() {
        return this.qiaoTaiLeiXing;
    }

    public void setQiaoTaiLeiXing(String qiaoTaiLeiXing) {
        this.qiaoTaiLeiXing = qiaoTaiLeiXing;
    }

    public String getZhiZuoLeiXing() {
        return this.zhiZuoLeiXing;
    }

    public void setZhiZuoLeiXing(String zhiZuoLeiXing) {
        this.zhiZuoLeiXing = zhiZuoLeiXing;
    }

    public String getQiaoDunJiChuLeiXing() {
        return this.qiaoDunJiChuLeiXing;
    }

    public void setQiaoDunJiChuLeiXing(String qiaoDunJiChuLeiXing) {
        this.qiaoDunJiChuLeiXing = qiaoDunJiChuLeiXing;
    }

    public String getQiaoTaiJiChuLeiXing() {
        return this.qiaoTaiJiChuLeiXing;
    }

    public void setQiaoTaiJiChuLeiXing(String qiaoTaiJiChuLeiXing) {
        this.qiaoTaiJiChuLeiXing = qiaoTaiJiChuLeiXing;
    }

    public String getQiaoMianPuZhuangLeiXing() {
        return this.qiaoMianPuZhuangLeiXing;
    }

    public void setQiaoMianPuZhuangLeiXing(String qiaoMianPuZhuangLeiXing) {
        this.qiaoMianPuZhuangLeiXing = qiaoMianPuZhuangLeiXing;
    }

    public String getShenShuoFengLeiXing() {
        return this.shenShuoFengLeiXing;
    }

    public void setShenShuoFengLeiXing(String shenShuoFengLeiXing) {
        this.shenShuoFengLeiXing = shenShuoFengLeiXing;
    }

    public String getYinQiaoShangBuJieGou() {
        return this.yinQiaoShangBuJieGou;
    }

    public void setYinQiaoShangBuJieGou(String yinQiaoShangBuJieGou) {
        this.yinQiaoShangBuJieGou = yinQiaoShangBuJieGou;
    }

    public String getSiGongZhuangHao() {
        return this.siGongZhuangHao;
    }

    public void setSiGongZhuangHao(String siGongZhuangHao) {
        this.siGongZhuangHao = siGongZhuangHao;
    }

    public String getGuanLiXingShiMa() {
        return this.guanLiXingShiMa;
    }

    public void setGuanLiXingShiMa(String guanLiXingShiMa) {
        this.guanLiXingShiMa = guanLiXingShiMa;
    }

    public String getMuQiaoDaiMa() {
        return this.muQiaoDaiMa;
    }

    public void setMuQiaoDaiMa(String muQiaoDaiMa) {
        this.muQiaoDaiMa = muQiaoDaiMa;
    }
}
