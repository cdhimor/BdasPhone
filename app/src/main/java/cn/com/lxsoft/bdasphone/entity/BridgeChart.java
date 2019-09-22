package cn.com.lxsoft.bdasphone.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class BridgeChart {
    @Id(autoincrement = true)
    @Expose(serialize = false, deserialize = false)
    public Long ID;

    @SerializedName("单位编号")
    private String name;  //

    @SerializedName("名称")
    private String info;  //

    private String type;

    @SerializedName("数量a")
    private int num_a;  //

    @SerializedName("延米a")
    private float len_a;

    @SerializedName("数量b")
    private int num_b;  //

    @SerializedName("延米b")
    private float len_b;

    @SerializedName("数量c")
    private int num_c;  //

    @SerializedName("延米c")
    private float len_c;  //

    @SerializedName("延米d")
    private float len_d;

    @SerializedName("数量d")
    private int num_d;  //

    @SerializedName("延米e")
    private float len_e;

    @SerializedName("数量e")
    private int num_e;  //

    @SerializedName("延米合计")
    private float len_all;

    @SerializedName("数量合计")
    private int num_all;  //

    @Generated(hash = 1129417074)
    public BridgeChart(Long ID, String name, String info, String type, int num_a,
            float len_a, int num_b, float len_b, int num_c, float len_c,
            float len_d, int num_d, float len_e, int num_e, float len_all,
            int num_all) {
        this.ID = ID;
        this.name = name;
        this.info = info;
        this.type = type;
        this.num_a = num_a;
        this.len_a = len_a;
        this.num_b = num_b;
        this.len_b = len_b;
        this.num_c = num_c;
        this.len_c = len_c;
        this.len_d = len_d;
        this.num_d = num_d;
        this.len_e = len_e;
        this.num_e = num_e;
        this.len_all = len_all;
        this.num_all = num_all;
    }

    @Generated(hash = 855139307)
    public BridgeChart() {
    }

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNum_a() {
        return this.num_a;
    }

    public void setNum_a(int num_a) {
        this.num_a = num_a;
    }

    public float getLen_a() {
        return this.len_a;
    }

    public void setLen_a(float len_a) {
        this.len_a = len_a;
    }

    public int getNum_b() {
        return this.num_b;
    }

    public void setNum_b(int num_b) {
        this.num_b = num_b;
    }

    public float getLen_b() {
        return this.len_b;
    }

    public void setLen_b(float len_b) {
        this.len_b = len_b;
    }

    public int getNum_c() {
        return this.num_c;
    }

    public void setNum_c(int num_c) {
        this.num_c = num_c;
    }

    public float getLen_c() {
        return this.len_c;
    }

    public void setLen_c(float len_c) {
        this.len_c = len_c;
    }

    public float getLen_d() {
        return this.len_d;
    }

    public void setLen_d(float len_d) {
        this.len_d = len_d;
    }

    public int getNum_d() {
        return this.num_d;
    }

    public void setNum_d(int num_d) {
        this.num_d = num_d;
    }

    public float getLen_e() {
        return this.len_e;
    }

    public void setLen_e(float len_e) {
        this.len_e = len_e;
    }

    public int getNum_e() {
        return this.num_e;
    }

    public void setNum_e(int num_e) {
        this.num_e = num_e;
    }

    public float getLen_all() {
        return this.len_all;
    }

    public void setLen_all(float len_all) {
        this.len_all = len_all;
    }

    public int getNum_all() {
        return this.num_all;
    }

    public void setNum_all(int num_all) {
        this.num_all = num_all;
    }

}
