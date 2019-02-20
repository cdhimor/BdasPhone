package cn.com.lxsoft.bdasphone.entity;

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
    private String bianHao;

    private String mingCheng;

    @Generated(hash = 1744651780)
    public LuXian(String bianHao, String mingCheng) {
        this.bianHao = bianHao;
        this.mingCheng = mingCheng;
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

}
