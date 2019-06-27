package cn.com.lxsoft.bdasphone.entity;

import org.greenrobot.greendao.annotation.*;


@Entity
public class Disease {
    //@SerializedName("A")

    @Id(autoincrement = true)
    private Long id;

    private String BridgeID;

    private String gouJian;

    private String caiZhi;

    private String buJian;

    private String bingHai;


    @Generated(hash = 896750286)
    public Disease(Long id, String BridgeID, String gouJian, String caiZhi,
            String buJian, String bingHai) {
        this.id = id;
        this.BridgeID = BridgeID;
        this.gouJian = gouJian;
        this.caiZhi = caiZhi;
        this.buJian = buJian;
        this.bingHai = bingHai;
    }

    @Generated(hash = 1596955631)
    public Disease() {
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

    public String getBingHai() {
        return this.bingHai;
    }

    public void setBingHai(String bingHai) {
        this.bingHai = bingHai;
    }
}
