package cn.com.lxsoft.bdasphone.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Chart {
    @Id(autoincrement = true)
    public Long ID;

    private int cid;  //

    private String type;  //

    private String name;  //

    private int num; //

    private float len; //

    @Generated(hash = 1056181815)
    public Chart(Long ID, int cid, String type, String name, int num, float len) {
        this.ID = ID;
        this.cid = cid;
        this.type = type;
        this.name = name;
        this.num = num;
        this.len = len;
    }

    @Generated(hash = 845533582)
    public Chart() {
    }

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public int getCid() {
        return this.cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public float getLen() {
        return this.len;
    }

    public void setLen(float len) {
        this.len = len;
    }
}
