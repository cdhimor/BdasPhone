package cn.com.lxsoft.bdasphone.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import cn.com.lxsoft.bdasphone.database.greendao.DaoSession;
import cn.com.lxsoft.bdasphone.database.greendao.DanWeiDao;
import cn.com.lxsoft.bdasphone.database.greendao.UserDao;

@Entity
public class User {
    @Id
    @SerializedName("loginName")
    private String loginName;

    @SerializedName("realName")
    private String name;

    @SerializedName("PH")
    private String phoneNumber;

    @SerializedName("department")
    private String danWeiID;
    @ToOne(joinProperty ="danWeiID")
    private DanWei danWei;

    @SerializedName("role")
    private int roleID;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;

    @Generated(hash = 792468687)
    public User(String loginName, String name, String phoneNumber, String danWeiID,
            int roleID) {
        this.loginName = loginName;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.danWeiID = danWeiID;
        this.roleID = roleID;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDanWeiID() {
        return this.danWeiID;
    }

    public void setDanWeiID(String danWeiID) {
        this.danWeiID = danWeiID;
    }

    public int getRoleID() {
        return this.roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    @Generated(hash = 1456009866)
    private transient String danWei__resolvedKey;

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
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }

}
