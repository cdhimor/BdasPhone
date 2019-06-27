package cn.com.lxsoft.bdasphone.entity;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;

import cn.com.lxsoft.bdasphone.database.greendao.DaoSession;
import cn.com.lxsoft.bdasphone.database.greendao.PatrolDao;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;
import cn.com.lxsoft.bdasphone.database.greendao.UserDao;
import cn.com.lxsoft.bdasphone.database.greendao.CheckDao;

@Entity
public class Check {
    @Id
    public String examineID;

    private String BridgeID;
    @ToOne(joinProperty ="BridgeID")
    private QiaoLiang bridge;

    private Date date;

    private String workerID;
    @ToOne(joinProperty ="workerID")
    private User worker;

    private String ownerID;
    @ToOne(joinProperty ="ownerID")
    private User owner;

    private String qiaoLiangJieGou;//桥梁结构
    private String qiaoLiangWaiGuan;//桥梁外观
    private String zhuLiang;//主梁
    private String xieLaSuo;//斜拉索/杆
    private String qiaoMianPuZhuang;//桥面铺装
    private String shenSuoFeng;//伸缩缝;
    private String renXingDao;//人行道;
    private String lanGanHuLan;// 栏杆护栏;
    private String paiShuiSheShi;//排水设施
    private String dunTai;//墩台;
    private String yiQiangHuPo;//翼墙护坡;
    private String jiaoTongSheShi;//交通设施;
    private String guanCeDian;//观测点;
    private String beiZhu;//备注;

    public String getDiseaseInfo(){
        String res="";
        if(!qiaoLiangJieGou.equals("A"))
            res=res.concat("桥梁结构 ∙ ");
        if(!qiaoLiangWaiGuan.equals("A"))
            res=res.concat("桥梁外观 ∙ ");
        if(!zhuLiang.equals("A"))
            res=res.concat("主梁 ∙ ");
        if(!xieLaSuo.equals("A"))
            res=res.concat("斜拉索 ∙ ");
        if(!qiaoMianPuZhuang.equals("A"))
            res=res.concat("桥面铺装 ∙ ");
        if(!shenSuoFeng.equals("A"))
            res=res.concat("伸缩缝 ∙ ");
        if(!renXingDao.equals("A"))
            res=res.concat("人行道 ∙ ");
        if(!lanGanHuLan.equals("A"))
            res=res.concat("栏杆护栏 ∙ ");
        if(!paiShuiSheShi.equals("A"))
            res=res.concat("排水设施 ∙ ");
        if(!dunTai.equals("A"))
            res=res.concat("墩台 ∙ ");
        if(!yiQiangHuPo.equals("A"))
            res=res.concat("翼墙护坡 ∙ ");
        if(!jiaoTongSheShi.equals("A"))
            res=res.concat("交通设施 ∙ ");
        if(!guanCeDian.equals("A"))
            res=res.concat("观测点 ∙ ");
        return res;
    }


    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1858097680)
    private transient CheckDao myDao;
    @Generated(hash = 565278369)
    public Check(String examineID, String BridgeID, Date date, String workerID,
            String ownerID, String qiaoLiangJieGou, String qiaoLiangWaiGuan,
            String zhuLiang, String xieLaSuo, String qiaoMianPuZhuang,
            String shenSuoFeng, String renXingDao, String lanGanHuLan,
            String paiShuiSheShi, String dunTai, String yiQiangHuPo,
            String jiaoTongSheShi, String guanCeDian, String beiZhu) {
        this.examineID = examineID;
        this.BridgeID = BridgeID;
        this.date = date;
        this.workerID = workerID;
        this.ownerID = ownerID;
        this.qiaoLiangJieGou = qiaoLiangJieGou;
        this.qiaoLiangWaiGuan = qiaoLiangWaiGuan;
        this.zhuLiang = zhuLiang;
        this.xieLaSuo = xieLaSuo;
        this.qiaoMianPuZhuang = qiaoMianPuZhuang;
        this.shenSuoFeng = shenSuoFeng;
        this.renXingDao = renXingDao;
        this.lanGanHuLan = lanGanHuLan;
        this.paiShuiSheShi = paiShuiSheShi;
        this.dunTai = dunTai;
        this.yiQiangHuPo = yiQiangHuPo;
        this.jiaoTongSheShi = jiaoTongSheShi;
        this.guanCeDian = guanCeDian;
        this.beiZhu = beiZhu;
    }
    @Generated(hash = 1080183208)
    public Check() {
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
    public String getQiaoLiangJieGou() {
        return this.qiaoLiangJieGou;
    }
    public void setQiaoLiangJieGou(String qiaoLiangJieGou) {
        this.qiaoLiangJieGou = qiaoLiangJieGou;
    }
    public String getQiaoLiangWaiGuan() {
        return this.qiaoLiangWaiGuan;
    }
    public void setQiaoLiangWaiGuan(String qiaoLiangWaiGuan) {
        this.qiaoLiangWaiGuan = qiaoLiangWaiGuan;
    }
    public String getZhuLiang() {
        return this.zhuLiang;
    }
    public void setZhuLiang(String zhuLiang) {
        this.zhuLiang = zhuLiang;
    }
    public String getXieLaSuo() {
        return this.xieLaSuo;
    }
    public void setXieLaSuo(String xieLaSuo) {
        this.xieLaSuo = xieLaSuo;
    }
    public String getQiaoMianPuZhuang() {
        return this.qiaoMianPuZhuang;
    }
    public void setQiaoMianPuZhuang(String qiaoMianPuZhuang) {
        this.qiaoMianPuZhuang = qiaoMianPuZhuang;
    }
    public String getShenSuoFeng() {
        return this.shenSuoFeng;
    }
    public void setShenSuoFeng(String shenSuoFeng) {
        this.shenSuoFeng = shenSuoFeng;
    }
    public String getRenXingDao() {
        return this.renXingDao;
    }
    public void setRenXingDao(String renXingDao) {
        this.renXingDao = renXingDao;
    }
    public String getLanGanHuLan() {
        return this.lanGanHuLan;
    }
    public void setLanGanHuLan(String lanGanHuLan) {
        this.lanGanHuLan = lanGanHuLan;
    }
    public String getPaiShuiSheShi() {
        return this.paiShuiSheShi;
    }
    public void setPaiShuiSheShi(String paiShuiSheShi) {
        this.paiShuiSheShi = paiShuiSheShi;
    }
    public String getDunTai() {
        return this.dunTai;
    }
    public void setDunTai(String dunTai) {
        this.dunTai = dunTai;
    }
    public String getYiQiangHuPo() {
        return this.yiQiangHuPo;
    }
    public void setYiQiangHuPo(String yiQiangHuPo) {
        this.yiQiangHuPo = yiQiangHuPo;
    }
    public String getJiaoTongSheShi() {
        return this.jiaoTongSheShi;
    }
    public void setJiaoTongSheShi(String jiaoTongSheShi) {
        this.jiaoTongSheShi = jiaoTongSheShi;
    }
    public String getGuanCeDian() {
        return this.guanCeDian;
    }
    public void setGuanCeDian(String guanCeDian) {
        this.guanCeDian = guanCeDian;
    }
    public String getBeiZhu() {
        return this.beiZhu;
    }
    public void setBeiZhu(String beiZhu) {
        this.beiZhu = beiZhu;
    }
    @Generated(hash = 761162677)
    private transient String bridge__resolvedKey;
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
    @Generated(hash = 1791517005)
    private transient String worker__resolvedKey;
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
    @Generated(hash = 1407767798)
    private transient String owner__resolvedKey;
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
    @Generated(hash = 1380373164)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCheckDao() : null;
    }


}
