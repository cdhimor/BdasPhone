package cn.com.lxsoft.bdasphone.entity;

import org.greenrobot.greendao.annotation.*;

import com.google.gson.annotations.SerializedName;
import org.greenrobot.greendao.DaoException;
import cn.com.lxsoft.bdasphone.database.greendao.DaoSession;
import cn.com.lxsoft.bdasphone.database.greendao.LuXianDao;
import cn.com.lxsoft.bdasphone.database.greendao.DanWeiDao;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;



/**

 * Created by wangjitao on 2017/2/13 0013.

 * E-Mail：543441727@qq.com

 *

 * Bean 对象注释的解释

 *

 /* @Entity：告诉GreenDao该对象为实体，只有被@Entity注释的Bean类才能被dao类操作

 /* @Id：对象的Id，使用Long类型作为EntityId，否则会报错。(autoincrement = true)表示主键会自增，如果false就会使用旧值

 /*  @Property：可以自定义字段名，注意外键不能使用该属性

 /*  @NotNull：属性不能为空

 /*  @Transient：使用该注释的属性不会被存入数据库的字段中

 /* @Unique：该属性值必须在数据库中是唯一值

 /* @Generated：编译后自动生成的构造函数、方法等的注释，提示构造函数、方法等不能被修改

 */

@Entity
public class QiaoLiang {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    @Unique
    private String daiMa;

    @SerializedName("mC")
    private String mingCheng;

    private String leiXing;

    private String jieGou;

    private String danWeiID;
    @ToOne(joinProperty ="danWeiID")
    private DanWei danWei;

    private String luXianID;
    @ToOne(joinProperty ="luXianID")
    private LuXian luXian;

    @SerializedName("zHao")
    private float zhuangHao;
    @SerializedName("qC")
    private float qiaoChang;
    @SerializedName("qK")
    private float qiaoKuan;
    @SerializedName("qG")
    private float qiaoGao;

    public String getleiXingInfo(){
        return DataDict.getDict("1.1",leiXing);
    }

    public String getjieGouInfo(){
        return DataDict.getDict("1.2",jieGou.substring(jieGou.length()-3));
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLeiXing() {
        return this.leiXing;
    }

    public void setLeiXing(String leiXing) {
        this.leiXing = leiXing;
    }

    public String getJieGou() {
        return this.jieGou;
    }

    public void setJieGou(String jieGou) {
        this.jieGou = jieGou;
    }

    public String getDanWeiID() {
        return this.danWeiID;
    }

    public void setDanWeiID(String danWeiID) {
        this.danWeiID = danWeiID;
    }

    public String getLuXianID() {
        return this.luXianID;
    }

    public void setLuXianID(String luXianID) {
        this.luXianID = luXianID;
    }

    public float getZhuangHao() {
        return this.zhuangHao;
    }

    public void setZhuangHao(float zhuangHao) {
        this.zhuangHao = zhuangHao;
    }

    public float getQiaoChang() {
        return this.qiaoChang;
    }

    public void setQiaoChang(float qiaoChang) {
        this.qiaoChang = qiaoChang;
    }

    public float getQiaoKuan() {
        return this.qiaoKuan;
    }

    public void setQiaoKuan(float qiaoKuan) {
        this.qiaoKuan = qiaoKuan;
    }

    public float getQiaoGao() {
        return this.qiaoGao;
    }

    public void setQiaoGao(float qiaoGao) {
        this.qiaoGao = qiaoGao;
    }

    public int getPingJi() {
        return this.pingJi;
    }

    public void setPingJi(int pingJi) {
        this.pingJi = pingJi;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 790888800)
    public DanWei getDanWei() {
        String __key = this.danWeiID;
        if (danWei__resolvedKey == null || danWei__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DanWeiDao targetDao = daoSession.getDanWeiDao();
            DanWei danWeiNew = targetDao.load(__key);
            synchronized (this) {
                danWei = danWeiNew;
                danWei__resolvedKey = __key;
            }
        }
        return danWei;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 642733689)
    public void setDanWei(DanWei danWei) {
        synchronized (this) {
            this.danWei = danWei;
            danWeiID = danWei == null ? null : danWei.getDaiMa();
            danWei__resolvedKey = danWeiID;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1943572567)
    public LuXian getLuXian() {
        String __key = this.luXianID;
        if (luXian__resolvedKey == null || luXian__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LuXianDao targetDao = daoSession.getLuXianDao();
            LuXian luXianNew = targetDao.load(__key);
            synchronized (this) {
                luXian = luXianNew;
                luXian__resolvedKey = __key;
            }
        }
        return luXian;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1278722307)
    public void setLuXian(LuXian luXian) {
        synchronized (this) {
            this.luXian = luXian;
            luXianID = luXian == null ? null : luXian.getBianHao();
            luXian__resolvedKey = luXianID;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 212535093)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getQiaoLiangDao() : null;
    }


    private int pingJi;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 164110102)
    private transient QiaoLiangDao myDao;

    @Generated(hash = 19433828)
    public QiaoLiang(Long id, @NotNull String daiMa, String mingCheng,
            String leiXing, String jieGou, String danWeiID, String luXianID,
            float zhuangHao, float qiaoChang, float qiaoKuan, float qiaoGao,
            int pingJi) {
        this.id = id;
        this.daiMa = daiMa;
        this.mingCheng = mingCheng;
        this.leiXing = leiXing;
        this.jieGou = jieGou;
        this.danWeiID = danWeiID;
        this.luXianID = luXianID;
        this.zhuangHao = zhuangHao;
        this.qiaoChang = qiaoChang;
        this.qiaoKuan = qiaoKuan;
        this.qiaoGao = qiaoGao;
        this.pingJi = pingJi;
    }

    @Generated(hash = 1604867976)
    public QiaoLiang() {
    }


    @Generated(hash = 1456009866)
    private transient String danWei__resolvedKey;

    @Generated(hash = 1233672073)
    private transient String luXian__resolvedKey;
}
