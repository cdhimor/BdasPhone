package cn.com.lxsoft.bdasphone.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.*;
import org.greenrobot.greendao.DaoException;
import cn.com.lxsoft.bdasphone.database.greendao.DaoSession;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;
import cn.com.lxsoft.bdasphone.database.greendao.ConstructDao;


@Entity
public class Construct {
    @Id(autoincrement = true)
    private Long id;

    @SerializedName("桥梁代码")
    private String BridgeID;

    @SerializedName("序号")
    private String xuHao;

    @SerializedName("桥梁部件")
    private String buJian;

    @SerializedName("部件材质")
    private String caiZhi;

    @SerializedName("桥梁构件")
    private String gouJian;

    @Generated(hash = 623566956)
    public Construct(Long id, String BridgeID, String xuHao, String buJian,
            String caiZhi, String gouJian) {
        this.id = id;
        this.BridgeID = BridgeID;
        this.xuHao = xuHao;
        this.buJian = buJian;
        this.caiZhi = caiZhi;
        this.gouJian = gouJian;
    }

    @Generated(hash = 1220110346)
    public Construct() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBridgeID() {
        return this.BridgeID;
    }

    public void setBridgeID(String BridgeID) {
        this.BridgeID = BridgeID;
    }

    public String getXuHao() {
        return this.xuHao;
    }

    public void setXuHao(String xuHao) {
        this.xuHao = xuHao;
    }

    public String getBuJian() {
        return this.buJian;
    }

    public void setBuJian(String buJian) {
        this.buJian = buJian;
    }

    public String getCaiZhi() {
        return this.caiZhi;
    }

    public void setCaiZhi(String caiZhi) {
        this.caiZhi = caiZhi;
    }

    public String getGouJian() {
        return this.gouJian;
    }

    public void setGouJian(String gouJian) {
        this.gouJian = gouJian;
    }


}
