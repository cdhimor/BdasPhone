package cn.com.lxsoft.bdasphone.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Transient;

import cn.com.lxsoft.bdasphone.database.greendao.DaoSession;
import cn.com.lxsoft.bdasphone.database.greendao.UserDao;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;
import cn.com.lxsoft.bdasphone.database.greendao.PatrolDao;

@Entity
public class Patrol{
    @Id
    @SerializedName("桥梁代码")
    protected String bridgeCode;
    @ToOne(joinProperty ="bridgeCode")
    @Expose(serialize = false, deserialize = false)
    protected QiaoLiang bridge;

    @SerializedName("F_Id")
    protected String examineID;

    @SerializedName("日期")
    protected Date date;

    @SerializedName("负责人")
    private String ownerID;


    @SerializedName("桥梁名称")
    @Transient
    private String bridgeName;


    @ToOne(joinProperty ="ownerID")
    @Expose(serialize = false, deserialize = false)
    private User owner;

    @SerializedName("记录人")
    private String workerID;

    @ToOne(joinProperty ="workerID")
    @Expose(serialize = false, deserialize = false)
    private User worker;


    @SerializedName("桥路链接处")
    private String qiaoLuLianJie;//桥路连接处

    @SerializedName("桥面铺装及伸缩缝")
    private String puZhuangSSF;//桥面铺装或伸缩缝

    @SerializedName("栏杆或护栏")
    private String lanGan;//栏杆或护栏

    @SerializedName("标志标牌")
    private String biaoZhi;//标志标牌

    @SerializedName("桥梁线形")
    private String xianXing;//桥梁线性

    @SerializedName("下次日期")
    private Date nextDate;

    private String remark;

    @SerializedName("照片")
    private String images;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1148119580)
    private transient PatrolDao myDao;


    @Generated(hash = 272757962)
    public Patrol(String bridgeCode, String examineID, Date date, String ownerID,
            String workerID, String qiaoLuLianJie, String puZhuangSSF,
            String lanGan, String biaoZhi, String xianXing, Date nextDate,
            String remark, String images) {
        this.bridgeCode = bridgeCode;
        this.examineID = examineID;
        this.date = date;
        this.ownerID = ownerID;
        this.workerID = workerID;
        this.qiaoLuLianJie = qiaoLuLianJie;
        this.puZhuangSSF = puZhuangSSF;
        this.lanGan = lanGan;
        this.biaoZhi = biaoZhi;
        this.xianXing = xianXing;
        this.nextDate = nextDate;
        this.remark = remark;
        this.images = images;
    }

    @Generated(hash = 743822884)
    public Patrol() {
    }

    @Generated(hash = 761162677)
    private transient String bridge__resolvedKey;
    @Generated(hash = 1407767798)
    private transient String owner__resolvedKey;
    @Generated(hash = 1791517005)
    private transient String worker__resolvedKey;


    public Boolean beNewPatrol(){
        if(new Date().getTime()<nextDate.getTime()){
            return false;
        }
        else
            return true;
    }

    public String getDiseaseInfo(){
        String res="";
        if(qiaoLuLianJie!=null && !qiaoLuLianJie.equals("1"))
            res=res.concat("桥路连接▪");
        if(puZhuangSSF!=null && !puZhuangSSF.equals("1"))
            res=res.concat("桥面铺装/伸缩缝▪");
        if(lanGan!=null && !lanGan.equals("1"))
            res=res.concat("栏杆▪");
        if(biaoZhi!=null && !biaoZhi.equals("1"))
            res=res.concat("标志标牌▪");
        if(xianXing!=null && !xianXing.equals("1"))
            res=res.concat("线性▪");
        if(res.equals(""))
            return res;
        else
            return res.substring(0,res.length()-1);
    }

    public String getBridgeCode() {
        return this.bridgeCode;
    }

    public void setBridgeCode(String bridgeCode) {
        this.bridgeCode = bridgeCode;
    }

    public String getExamineID() {
        return this.examineID;
    }

    public void setExamineID(String examineID) {
        this.examineID = examineID;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOwnerID() {
        return this.ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getWorkerID() {
        return this.workerID;
    }

    public void setWorkerID(String workerID) {
        this.workerID = workerID;
    }

    public String getQiaoLuLianJie() {
        return this.qiaoLuLianJie;
    }

    public void setQiaoLuLianJie(String qiaoLuLianJie) {
        this.qiaoLuLianJie = qiaoLuLianJie;
    }

    public String getPuZhuangSSF() {
        return this.puZhuangSSF;
    }

    public void setPuZhuangSSF(String puZhuangSSF) {
        this.puZhuangSSF = puZhuangSSF;
    }

    public String getLanGan() {
        return this.lanGan;
    }

    public void setLanGan(String lanGan) {
        this.lanGan = lanGan;
    }

    public String getBiaoZhi() {
        return this.biaoZhi;
    }

    public void setBiaoZhi(String biaoZhi) {
        this.biaoZhi = biaoZhi;
    }

    public String getXianXing() {
        return this.xianXing;
    }

    public void setXianXing(String xianXing) {
        this.xianXing = xianXing;
    }

    public Date getNextDate() {
        return this.nextDate;
    }

    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImages() {
        return this.images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1277994889)
    public QiaoLiang getBridge() {
        String __key = this.bridgeCode;
        if (bridge__resolvedKey == null || bridge__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            QiaoLiangDao targetDao = daoSession.getQiaoLiangDao();
            QiaoLiang bridgeNew = targetDao.load(__key);
            synchronized (this) {
                bridge = bridgeNew;
                bridge__resolvedKey = __key;
            }
        }
        return bridge;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1593399174)
    public void setBridge(QiaoLiang bridge) {
        synchronized (this) {
            this.bridge = bridge;
            bridgeCode = bridge == null ? null : bridge.getDaiMa();
            bridge__resolvedKey = bridgeCode;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1377745454)
    public User getOwner() {
        String __key = this.ownerID;
        if (owner__resolvedKey == null || owner__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User ownerNew = targetDao.load(__key);
            synchronized (this) {
                owner = ownerNew;
                owner__resolvedKey = __key;
            }
        }
        return owner;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1708933489)
    public void setOwner(User owner) {
        synchronized (this) {
            this.owner = owner;
            ownerID = owner == null ? null : owner.getLoginName();
            owner__resolvedKey = ownerID;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 741428595)
    public User getWorker() {
        String __key = this.workerID;
        if (worker__resolvedKey == null || worker__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User workerNew = targetDao.load(__key);
            synchronized (this) {
                worker = workerNew;
                worker__resolvedKey = __key;
            }
        }
        return worker;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1849089738)
    public void setWorker(User worker) {
        synchronized (this) {
            this.worker = worker;
            workerID = worker == null ? null : worker.getLoginName();
            worker__resolvedKey = workerID;
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
    @Generated(hash = 2083577010)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPatrolDao() : null;
    }

    public String getBridgeName() {
        return bridgeName;
    }

    public void setBridgeName(String bridgeName) {
        this.bridgeName = bridgeName;
    }

}
