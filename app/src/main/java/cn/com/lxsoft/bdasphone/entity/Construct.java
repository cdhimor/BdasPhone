package cn.com.lxsoft.bdasphone.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.*;
import org.greenrobot.greendao.DaoException;
import cn.com.lxsoft.bdasphone.database.greendao.DaoSession;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;
import cn.com.lxsoft.bdasphone.database.greendao.ConstructDao;


@Entity
public class Construct {
    @Id
    private Long id;

    @SerializedName("ql")
    private String BridgeID;

    @SerializedName("bj")
    private String buJian;

    @SerializedName("cz")
    private String caiZhi;

    @SerializedName("gj")
    private String gouJian;



    @Generated(hash = 753413919)
    public Construct(Long id, String BridgeID, String buJian, String caiZhi,
            String gouJian) {
        this.id = id;
        this.BridgeID = BridgeID;
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

    public String getGouJian() {
        return this.gouJian;
    }

    public void setGouJian(String gouJian) {
        this.gouJian = gouJian;
    }

    public String getCaiZhi() {
        return this.caiZhi;
    }

    public void setCaiZhi(String caiZhi) {
        this.caiZhi = caiZhi;
    }

    public String getBuJian() {
        return this.buJian;
    }

    public void setBuJian(String buJian) {
        this.buJian = buJian;
    }
    
}
