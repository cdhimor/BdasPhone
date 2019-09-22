package cn.com.lxsoft.bdasphone.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

import cn.com.lxsoft.bdasphone.utils.ConvertUtils;

public class BridgeJCSB {
    @SerializedName("桥梁代码")
    @Id
    private String qiaoLiangDaiMa;

    @SerializedName("桥梁名称")
    private String qiaoLiangMingCheng;

    @SerializedName("路线号")
    private String luXianHao;

    @SerializedName("路线名称")
    private String luXianMingCheng;

    @SerializedName("路线类型")
    private String luXianLeiXing;

    @SerializedName("路线等级")
    private String luXianDengJi;

    @SerializedName("行政等级")
    private String xingZhengDengJi;

    @SerializedName("顺序号")
    private String shunXuHao;

    @SerializedName("所在地")
    private String suoZaiDi;

    @SerializedName("中心桩号")
    private float  zhongXinZhuangHao;

    @SerializedName("管养单位")
    private String guanYangDanWei;

    @SerializedName("跨越地物类型")
    private String kuaYueDiWuLeiXing;

    @SerializedName("跨越地物名称")
    private String kuaYueDiWuMingCheng;

    @SerializedName("桥梁性质")
    private String qiaoLiangXingZhi;

    @SerializedName("所在位置")
    private String suoZaiWeiZhi;

    @SerializedName("桥梁分类")
    private String qiaoLiangFenLei;

    @SerializedName("桥梁类型")
    private String qiaoLiangLeiXing;

    @SerializedName("抗震等级")
    private String kangZhengDengJi;

    @SerializedName("通航等级")
    private String tongHangDengJi;

    @SerializedName("设计载荷")
    private String sheJiHeZaiDengJi;

    @SerializedName("通行荷载")
    private String muQianHeZai;

    @SerializedName("弯斜坡度")
    private String wanPoXieDu;


    @SerializedName("修建年度")
    private Date xiuJianNianDu;

    @SerializedName("建成通车日期")
    private Date tongCheRiQi;

    @SerializedName("业主单位")
    private String yeZhuDanWei;

    @SerializedName("设计单位")
    private String sheJiDanWei;

    @SerializedName("施工单位")
    private String shiGongDanWei;

    @SerializedName("监理单位")
    private String jianLiDanWei;


    @Generated(hash = 744236622)
    public BridgeJCSB(String qiaoLiangDaiMa, String qiaoLiangMingCheng,
            String luXianHao, String luXianMingCheng, String luXianLeiXing,
            String luXianDengJi, String xingZhengDengJi, String shunXuHao,
            String suoZaiDi, float zhongXinZhuangHao, String guanYangDanWei,
            String kuaYueDiWuLeiXing, String kuaYueDiWuMingCheng,
            String qiaoLiangXingZhi, String suoZaiWeiZhi, String qiaoLiangFenLei,
            String qiaoLiangLeiXing, String kangZhengDengJi, String tongHangDengJi,
            String sheJiHeZaiDengJi, String muQianHeZai, String wanPoXieDu,
            Date xiuJianNianDu, Date tongCheRiQi, String yeZhuDanWei,
            String sheJiDanWei, String shiGongDanWei, String jianLiDanWei) {
        this.qiaoLiangDaiMa = qiaoLiangDaiMa;
        this.qiaoLiangMingCheng = qiaoLiangMingCheng;
        this.luXianHao = luXianHao;
        this.luXianMingCheng = luXianMingCheng;
        this.luXianLeiXing = luXianLeiXing;
        this.luXianDengJi = luXianDengJi;
        this.xingZhengDengJi = xingZhengDengJi;
        this.shunXuHao = shunXuHao;
        this.suoZaiDi = suoZaiDi;
        this.zhongXinZhuangHao = zhongXinZhuangHao;
        this.guanYangDanWei = guanYangDanWei;
        this.kuaYueDiWuLeiXing = kuaYueDiWuLeiXing;
        this.kuaYueDiWuMingCheng = kuaYueDiWuMingCheng;
        this.qiaoLiangXingZhi = qiaoLiangXingZhi;
        this.suoZaiWeiZhi = suoZaiWeiZhi;
        this.qiaoLiangFenLei = qiaoLiangFenLei;
        this.qiaoLiangLeiXing = qiaoLiangLeiXing;
        this.kangZhengDengJi = kangZhengDengJi;
        this.tongHangDengJi = tongHangDengJi;
        this.sheJiHeZaiDengJi = sheJiHeZaiDengJi;
        this.muQianHeZai = muQianHeZai;
        this.wanPoXieDu = wanPoXieDu;
        this.xiuJianNianDu = xiuJianNianDu;
        this.tongCheRiQi = tongCheRiQi;
        this.yeZhuDanWei = yeZhuDanWei;
        this.sheJiDanWei = sheJiDanWei;
        this.shiGongDanWei = shiGongDanWei;
        this.jianLiDanWei = jianLiDanWei;
    }

    @Generated(hash = 612018682)
    public BridgeJCSB() {
    }


    public String getZhongXinZhuangHaoInfo() {
        return String.valueOf(this.zhongXinZhuangHao);
    }

    public void setZhongXinZhuangHaoInfo(String zhuanghao) {
        //return "";//String.valueOf(this.zhongXinZhuangHao);
    }

    public String getXiuJianNianDuInfo() {
        return ConvertUtils.getDateName(this.xiuJianNianDu);
    }

    public void setXiuJianNianDuInfo(String xiuJianNianDu) {
        //this.xiuJianNianDu = xiuJianNianDu;
    }

    public String getTongCheRiQiInfo() {
        return ConvertUtils.getDateName(this.tongCheRiQi);
    }

    public void setTongCheRiQiInfo(String tongCheRiQi) {
        //this.tongCheRiQi = tongCheRiQi;
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

    public String getLuXianDengJi() {
        return this.luXianDengJi;
    }

    public void setLuXianDengJi(String luXianDengJi) {
        this.luXianDengJi = luXianDengJi;
    }

    public String getXingZhengDengJi() {
        return this.xingZhengDengJi;
    }

    public void setXingZhengDengJi(String xingZhengDengJi) {
        this.xingZhengDengJi = xingZhengDengJi;
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

    public float getZhongXinZhuangHao() {
        return this.zhongXinZhuangHao;
    }

    public void setZhongXinZhuangHao(float zhongXinZhuangHao) {
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

    public String getQiaoLiangLeiXing() {
        return this.qiaoLiangLeiXing;
    }

    public void setQiaoLiangLeiXing(String qiaoLiangLeiXing) {
        this.qiaoLiangLeiXing = qiaoLiangLeiXing;
    }

    public String getKangZhengDengJi() {
        return this.kangZhengDengJi;
    }

    public void setKangZhengDengJi(String kangZhengDengJi) {
        this.kangZhengDengJi = kangZhengDengJi;
    }

    public String getTongHangDengJi() {
        return this.tongHangDengJi;
    }

    public void setTongHangDengJi(String tongHangDengJi) {
        this.tongHangDengJi = tongHangDengJi;
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

    public Date getXiuJianNianDu() {
        return this.xiuJianNianDu;
    }

    public void setXiuJianNianDu(Date xiuJianNianDu) {
        this.xiuJianNianDu = xiuJianNianDu;
    }

    public Date getTongCheRiQi() {
        return this.tongCheRiQi;
    }

    public void setTongCheRiQi(Date tongCheRiQi) {
        this.tongCheRiQi = tongCheRiQi;
    }

    public String getYeZhuDanWei() {
        return this.yeZhuDanWei;
    }

    public void setYeZhuDanWei(String yeZhuDanWei) {
        this.yeZhuDanWei = yeZhuDanWei;
    }

    public String getSheJiDanWei() {
        return this.sheJiDanWei;
    }

    public void setSheJiDanWei(String sheJiDanWei) {
        this.sheJiDanWei = sheJiDanWei;
    }

    public String getShiGongDanWei() {
        return this.shiGongDanWei;
    }

    public void setShiGongDanWei(String shiGongDanWei) {
        this.shiGongDanWei = shiGongDanWei;
    }

    public String getJianLiDanWei() {
        return this.jianLiDanWei;
    }

    public void setJianLiDanWei(String jianLiDanWei) {
        this.jianLiDanWei = jianLiDanWei;
    }

    public String getSuoZaiWeiZhi() {
        return this.suoZaiWeiZhi;
    }

    public void setSuoZaiWeiZhi(String suoZaiWeiZhi) {
        this.suoZaiWeiZhi = suoZaiWeiZhi;
    }

    public String getWanPoXieDu() {
        return this.wanPoXieDu;
    }

    public void setWanPoXieDu(String wanPoXieDu) {
        this.wanPoXieDu = wanPoXieDu;
    }


}
