package cn.com.lxsoft.bdasphone.entity;

import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.lxsoft.bdasphone.database.greendao.DaoSession;
import cn.com.lxsoft.bdasphone.database.greendao.DectectDao;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;
import cn.com.lxsoft.bdasphone.database.greendao.UserDao;
import cn.com.lxsoft.bdasphone.database.greendao.YearTestDao;

//@InverseBindingMethods({@InverseBindingMethod(type = YearTest.class, attribute = "pingJia",event = "progressAttrChanged"),@InverseBindingMethod(type = YearTest.class, attribute = "pingFen",event = "progressAttrChanged")})


@Entity
public class YearTest {
    @SerializedName("F_Id")
    public String examineID;

    @Id
    @SerializedName("桥梁代码")
    private String BridgeID;

    @ToOne(joinProperty ="BridgeID")
    private QiaoLiang bridge;

    @SerializedName("日期")
    private Date date;

    @SerializedName("下次检查日期")
    private Date nextDate;

    @SerializedName("记录人")
    private String workerID;

    @ToOne(joinProperty ="workerID")
    private User worker;

    @SerializedName("负责人")
    private String ownerID;

    @ToOne(joinProperty ="ownerID")
    private User owner;

    @SerializedName("技术状况评级")
    private int pingJia=-1;

    @SerializedName("技术状况评分")
    private int pingFen=-1;

    @SerializedName("上部结构评分")
    private int shangBuJieGouPingFen=-1;

    @SerializedName("下部机构评分")
    private int xiaBuJieGouPingFen=-1;

    @SerializedName("桥面系评分")
    private int qiaoMianXiPingFen=-1;

    @SerializedName("部件评分")
    private String gouJianPingFen;


    /*
    @Transient
    private InverseBindingListener mInverseBindingListener;
    public void setProgressAttrChanged(InverseBindingListener inverseBindingListener) {
        if (inverseBindingListener != null) {
            mInverseBindingListener = inverseBindingListener;
        }
    }
    */

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 726318525)
    private transient YearTestDao myDao;

    @Generated(hash = 761162677)
    private transient String bridge__resolvedKey;

    @Generated(hash = 1791517005)
    private transient String worker__resolvedKey;

    @Generated(hash = 1407767798)
    private transient String owner__resolvedKey;

    @Generated(hash = 1906039815)
    public YearTest(String examineID, String BridgeID, Date date, Date nextDate, String workerID, String ownerID, int pingJia, int pingFen, int shangBuJieGouPingFen, int xiaBuJieGouPingFen, int qiaoMianXiPingFen,
            String gouJianPingFen) {
        this.examineID = examineID;
        this.BridgeID = BridgeID;
        this.date = date;
        this.nextDate = nextDate;
        this.workerID = workerID;
        this.ownerID = ownerID;
        this.pingJia = pingJia;
        this.pingFen = pingFen;
        this.shangBuJieGouPingFen = shangBuJieGouPingFen;
        this.xiaBuJieGouPingFen = xiaBuJieGouPingFen;
        this.qiaoMianXiPingFen = qiaoMianXiPingFen;
        this.gouJianPingFen = gouJianPingFen;
    }

    @Generated(hash = 1217033837)
    public YearTest() {
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

    public String getOwnerID() {
        return this.ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public int getPingJia() {
        return this.pingJia;
    }

    public void setPingJia(int pingJia) {
        this.pingJia = pingJia;
    }

    public int getPingFen() {
        return this.pingFen;
    }

    public void setPingFen(int pingFen) {
        this.pingFen = pingFen;
    }

    public int getShangBuJieGouPingFen() {
        return this.shangBuJieGouPingFen;
    }

    public void setShangBuJieGouPingFen(int shangBuJieGouPingFen) {
        this.shangBuJieGouPingFen = shangBuJieGouPingFen;
    }

    public int getXiaBuJieGouPingFen() {
        return this.xiaBuJieGouPingFen;
    }

    public void setXiaBuJieGouPingFen(int xiaBuJieGouPingFen) {
        this.xiaBuJieGouPingFen = xiaBuJieGouPingFen;
    }

    public int getQiaoMianXiPingFen() {
        return this.qiaoMianXiPingFen;
    }

    public void setQiaoMianXiPingFen(int qiaoMianXiPingFen) {
        this.qiaoMianXiPingFen = qiaoMianXiPingFen;
    }

    public String getGouJianPingFen() {
        return this.gouJianPingFen;
    }

    public void setGouJianPingFen(String gouJianPingFen) {
        this.gouJianPingFen = gouJianPingFen;
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
    @Generated(hash = 1367179777)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getYearTestDao() : null;
    }

    public Date getNextDate() {
        return this.nextDate;
    }

    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }

}
