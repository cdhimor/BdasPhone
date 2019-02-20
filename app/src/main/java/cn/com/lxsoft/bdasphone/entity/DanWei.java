package cn.com.lxsoft.bdasphone.entity;

/**
 * Created by Administrator on 2018/12/20.
 */
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DanWei {
    @Id
    private String daiMa;

    private String mingCheng;

    @Generated(hash = 1132150992)
    public DanWei(String daiMa, String mingCheng) {
        this.daiMa = daiMa;
        this.mingCheng = mingCheng;
    }

    @Generated(hash = 856826978)
    public DanWei() {
    }

    public String getDaiMa() {
        return this.daiMa;
    }

    public void setDaiMa(String daiMa) {
        this.daiMa = daiMa;
    }

    public String getMingCheng() {
        return this.mingCheng;
    }

    public void setMingCheng(String mingCheng) {
        this.mingCheng = mingCheng;
    }

}
