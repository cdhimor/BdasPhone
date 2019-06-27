package cn.com.lxsoft.bdasphone.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import cn.com.lxsoft.bdasphone.database.greendao.DaoSession;
import cn.com.lxsoft.bdasphone.database.greendao.UserDao;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;
import cn.com.lxsoft.bdasphone.database.greendao.PatrolDao;

@Entity
public class Patrol{
    @SerializedName("A")

    @Id
    public String examineID;

    private String BridgeID;
    @ToOne(joinProperty ="BridgeID")
    private QiaoLiang bridge;

    private Date date;

    private String ownerID;
    @ToOne(joinProperty ="ownerID")
    private User owner;

    private String workerID;
    @ToOne(joinProperty ="workerID")
    private User worker;


    private String qiaoLuLianJie;//桥路连接处
    private String puZhuangSSF;//桥面铺装或伸缩缝
    private String lanGan;//栏杆或护栏
    private String biaoZhi;//标志标牌
    private String xianXing;//桥梁线性

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1148119580)
    private transient PatrolDao myDao;

    @Generated(hash = 1894889110)
    public Patrol(String examineID, String BridgeID, Date date, String ownerID,
            String workerID, String qiaoLuLianJie, String puZhuangSSF, String lanGan,
            String biaoZhi, String xianXing) {
        this.examineID = examineID;
        this.BridgeID = BridgeID;
        this.date = date;
        this.ownerID = ownerID;
        this.workerID = workerID;
        this.qiaoLuLianJie = qiaoLuLianJie;
        this.puZhuangSSF = puZhuangSSF;
        this.lanGan = lanGan;
        this.biaoZhi = biaoZhi;
        this.xianXing = xianXing;
    }
    @Generated(hash = 743822884)
    public Patrol() {
    }

    @Generated(hash = 761162677)
    private transient String bridge__resolvedKey;

    @Generated(hash = 1791517005)
    private transient String worker__resolvedKey;

    @Generated(hash = 1407767798)
    private transient String owner__resolvedKey;

    public String getDiseaseInfo(){
        String res="";
        if(!qiaoLuLianJie.equals("A"))
            res=res.concat("桥路连接处 ∙ ");
        if(!puZhuangSSF.equals("A"))
            res=res.concat("桥面铺装或伸缩缝 ∙ ");
        if(!lanGan.equals("A"))
            res=res.concat("栏杆或护栏 ∙ ");
        if(!biaoZhi.equals("A"))
            res=res.concat("标志标牌 ∙ ");
        if(!xianXing.equals("A"))
            res=res.concat("桥梁线性 ∙ ");
        return res;
    }

    public String getExamineID() {
        return this.examineID;
    }
    public void setExamineID(String examineID) {
        this.examineID = examineID;
    }
    public String getBridgeID() {
        return this.BridgeID;
    }
    public void setBridgeID(String BridgeID) {
        this.BridgeID = BridgeID;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
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

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1078728531)
    public QiaoLiang getBridge() {
        String __key = this.BridgeID;
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
    @Generated(hash = 27211433)
    public void setBridge(QiaoLiang bridge) {
        synchronized (this) {
            this.bridge = bridge;
            BridgeID = bridge == null ? null : bridge.getDaiMa();
            bridge__resolvedKey = BridgeID;
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

}
