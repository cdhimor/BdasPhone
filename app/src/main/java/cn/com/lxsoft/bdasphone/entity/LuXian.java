package cn.com.lxsoft.bdasphone.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/12/20.
 */

@Entity
public class LuXian {
    @Id
    @SerializedName("BH")
    private String bianHao;

    @SerializedName("MC")
    private String mingCheng;

    @SerializedName("DJ")
    private String dengJi;

    @Generated(hash = 149713271)
    public LuXian(String bianHao, String mingCheng, String dengJi) {
        this.bianHao = bianHao;
        this.mingCheng = mingCheng;
        this.dengJi = dengJi;
    }

    @Generated(hash = 862110456)
    public LuXian() {
    }

    public String getBianHao() {
        return this.bianHao;
    }

    public void setBianHao(String bianHao) {
        this.bianHao = bianHao;
    }

    public String getMingCheng() {
        return this.mingCheng;
    }

    public void setMingCheng(String mingCheng) {
        this.mingCheng = mingCheng;
    }

    public String getDengJi() {
        return this.dengJi;
    }

    public void setDengJi(String dengJi) {
        this.dengJi = dengJi;
    }

}
