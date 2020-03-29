package cn.com.lxsoft.bdasphone.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;

import cn.com.lxsoft.bdasphone.database.greendao.DaoSession;
import cn.com.lxsoft.bdasphone.database.greendao.PatrolDao;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;
import cn.com.lxsoft.bdasphone.database.greendao.UserDao;
import cn.com.lxsoft.bdasphone.database.greendao.CheckDao;

@Entity(nameInDb="WEEKCHECK")
public class Check {
    @Id
    @SerializedName("F_Id")
    public String examineID;

    @SerializedName("桥梁代码")
    private String BridgeID;

    @ToOne(joinProperty ="BridgeID")
    private QiaoLiang bridge;

    @SerializedName("桥梁名称")
    @Transient
    private String bridgeName;

    @SerializedName("日期")
    private Date date;

    @SerializedName("记录人")
    private String workerID;
    @ToOne(joinProperty ="workerID")
    private User worker;

    @SerializedName("负责人")
    private String ownerID;
    @ToOne(joinProperty ="ownerID")
    private User owner;

    private String beiZhu;//备注;

    @Expose(serialize = false, deserialize = false)
    private Boolean bHistory=false;

    @SerializedName("bh101")
    private String qiaoLiangJieGou;//桥梁结构
    @SerializedName("bh102")
    private String qiaoLiangWaiGuan;//桥梁外观
    @SerializedName("bh103")
    private String zhuLiang;//主梁
    @SerializedName("bh104")
    private String xieLaSuo;//斜拉索/杆
    @SerializedName("bh105")
    private String qiaoMianPuZhuang;//桥面铺装
    @SerializedName("bh106")
    private String shenSuoFeng;//伸缩缝;
    @SerializedName("bh107")
    private String renXingDao;//人行道;
    @SerializedName("bh108")
    private String lanGanHuLan;// 栏杆护栏;
    @SerializedName("bh109")
    private String paiShuiSheShi;//排水设施
    @SerializedName("bh110")
    private String dunTai;//墩台;
    @SerializedName("bh111")
    private String yiQiangHuPo;//翼墙护坡;
    @SerializedName("bh112")
    private String jiaoTongSheShi;//交通设施;
    @SerializedName("bh113")
    private String guanCeDian;//观测点;

    @SerializedName("下次日期")
    private Date nextDate;

    public Boolean beNewCheck(){
        if(new Date().getTime()<nextDate.getTime()){
            return false;
        }
        else
            return true;
    }


    public String getDiseaseInfo(){
        String res="";
        if(qiaoLiangJieGou!=null && !qiaoLiangJieGou.equals("1"))
            res=res.concat("结构▪");
        if(qiaoLiangWaiGuan!=null && !qiaoLiangWaiGuan.equals("1"))
            res=res.concat("外观▪");
        if(zhuLiang!=null && !zhuLiang.equals("1"))
            res=res.concat("主梁▪");
        if(xieLaSuo!=null && !xieLaSuo.equals("1"))
            res=res.concat("斜拉索▪");
        if(qiaoMianPuZhuang!=null && !qiaoMianPuZhuang.equals("1"))
            res=res.concat("铺装▪");
        if(shenSuoFeng!=null && !shenSuoFeng.equals("1"))
            res=res.concat("伸缩缝▪");
        if(renXingDao!=null && !renXingDao.equals("1"))
            res=res.concat("人行道▪");
        if(lanGanHuLan!=null && !lanGanHuLan.equals("1"))
            res=res.concat("栏杆▪");
        if(paiShuiSheShi!=null && !paiShuiSheShi.equals("1"))
            res=res.concat("排水▪");
        if(dunTai!=null && !dunTai.equals("1"))
            res=res.concat("墩台▪");
        if(yiQiangHuPo!=null && !yiQiangHuPo.equals("1"))
            res=res.concat("翼墙护坡▪");
        if(jiaoTongSheShi!=null && !jiaoTongSheShi.equals("1"))
            res=res.concat("交通设施▪");
        if(guanCeDian!=null && !guanCeDian.equals("1"))
            res=res.concat("观测点▪");
        return res.equals("")?res:res.substring(0,res.length()-1);
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
    public String getBeiZhu() {
        return this.beiZhu;
    }
    public void setBeiZhu(String beiZhu) {
        this.beiZhu = beiZhu;
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
    public Date getNextDate() {
        return this.nextDate;
    }
    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }
    public String getQiaoLiangJieGouImg() {
        return this.qiaoLiangJieGouImg;
    }
    public void setQiaoLiangJieGouImg(String qiaoLiangJieGouImg) {
        this.qiaoLiangJieGouImg = qiaoLiangJieGouImg;
    }
    public String getQiaoLiangWaiGuanImg() {
        return this.qiaoLiangWaiGuanImg;
    }
    public void setQiaoLiangWaiGuanImg(String qiaoLiangWaiGuanImg) {
        this.qiaoLiangWaiGuanImg = qiaoLiangWaiGuanImg;
    }
    public String getZhuLiangImg() {
        return this.zhuLiangImg;
    }
    public void setZhuLiangImg(String zhuLiangImg) {
        this.zhuLiangImg = zhuLiangImg;
    }
    public String getXieLaSuoImg() {
        return this.xieLaSuoImg;
    }
    public void setXieLaSuoImg(String xieLaSuoImg) {
        this.xieLaSuoImg = xieLaSuoImg;
    }
    public String getQiaoMianPuZhuangImg() {
        return this.qiaoMianPuZhuangImg;
    }
    public void setQiaoMianPuZhuangImg(String qiaoMianPuZhuangImg) {
        this.qiaoMianPuZhuangImg = qiaoMianPuZhuangImg;
    }
    public String getShenSuoFengImg() {
        return this.shenSuoFengImg;
    }
    public void setShenSuoFengImg(String shenSuoFengImg) {
        this.shenSuoFengImg = shenSuoFengImg;
    }
    public String getRenXingDaoImg() {
        return this.renXingDaoImg;
    }
    public void setRenXingDaoImg(String renXingDaoImg) {
        this.renXingDaoImg = renXingDaoImg;
    }
    public String getLanGanHuLanImg() {
        return this.lanGanHuLanImg;
    }
    public void setLanGanHuLanImg(String lanGanHuLanImg) {
        this.lanGanHuLanImg = lanGanHuLanImg;
    }
    public String getPaiShuiSheShiImg() {
        return this.paiShuiSheShiImg;
    }
    public void setPaiShuiSheShiImg(String paiShuiSheShiImg) {
        this.paiShuiSheShiImg = paiShuiSheShiImg;
    }
    public String getDunTaiImg() {
        return this.dunTaiImg;
    }
    public void setDunTaiImg(String dunTaiImg) {
        this.dunTaiImg = dunTaiImg;
    }
    public String getYiQiangHuPoImg() {
        return this.yiQiangHuPoImg;
    }
    public void setYiQiangHuPoImg(String yiQiangHuPoImg) {
        this.yiQiangHuPoImg = yiQiangHuPoImg;
    }
    public String getJiaoTongSheShiImg() {
        return this.jiaoTongSheShiImg;
    }
    public void setJiaoTongSheShiImg(String jiaoTongSheShiImg) {
        this.jiaoTongSheShiImg = jiaoTongSheShiImg;
    }
    public String getGuanCeDianImg() {
        return this.guanCeDianImg;
    }
    public void setGuanCeDianImg(String guanCeDianImg) {
        this.guanCeDianImg = guanCeDianImg;
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
    @Generated(hash = 1380373164)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCheckDao() : null;
    }

    @SerializedName("bh101img")
    private String qiaoLiangJieGouImg;//桥梁结构

    @SerializedName("bh102img")
    private String qiaoLiangWaiGuanImg;//桥梁外观

    @SerializedName("bh103img")
    private String zhuLiangImg;//主梁

    @SerializedName("bh104img")
    private String xieLaSuoImg;//斜拉索/杆

    @SerializedName("bh105img")
    private String qiaoMianPuZhuangImg;//桥面铺装

    @SerializedName("bh106img")
    private String shenSuoFengImg;//伸缩缝;

    @SerializedName("bh107img")
    private String renXingDaoImg;//人行道;

    @SerializedName("bh108img")
    private String lanGanHuLanImg;// 栏杆护栏;

    @SerializedName("bh109img")
    private String paiShuiSheShiImg;//排水设施

    @SerializedName("bh110img")
    private String dunTaiImg;//墩台;

    @SerializedName("bh111img")
    private String yiQiangHuPoImg;//翼墙护坡;

    @SerializedName("bh112img")
    private String jiaoTongSheShiImg;//交通设施;

    @SerializedName("bh113img")
    private String guanCeDianImg;//观测点;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1858097680)
    private transient CheckDao myDao;


    @Generated(hash = 1386618912)
    public Check(String examineID, String BridgeID, Date date, String workerID,
            String ownerID, String beiZhu, Boolean bHistory, String qiaoLiangJieGou,
            String qiaoLiangWaiGuan, String zhuLiang, String xieLaSuo,
            String qiaoMianPuZhuang, String shenSuoFeng, String renXingDao,
            String lanGanHuLan, String paiShuiSheShi, String dunTai, String yiQiangHuPo,
            String jiaoTongSheShi, String guanCeDian, Date nextDate,
            String qiaoLiangJieGouImg, String qiaoLiangWaiGuanImg, String zhuLiangImg,
            String xieLaSuoImg, String qiaoMianPuZhuangImg, String shenSuoFengImg,
            String renXingDaoImg, String lanGanHuLanImg, String paiShuiSheShiImg,
            String dunTaiImg, String yiQiangHuPoImg, String jiaoTongSheShiImg,
            String guanCeDianImg) {
        this.examineID = examineID;
        this.BridgeID = BridgeID;
        this.date = date;
        this.workerID = workerID;
        this.ownerID = ownerID;
        this.beiZhu = beiZhu;
        this.bHistory = bHistory;
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
        this.nextDate = nextDate;
        this.qiaoLiangJieGouImg = qiaoLiangJieGouImg;
        this.qiaoLiangWaiGuanImg = qiaoLiangWaiGuanImg;
        this.zhuLiangImg = zhuLiangImg;
        this.xieLaSuoImg = xieLaSuoImg;
        this.qiaoMianPuZhuangImg = qiaoMianPuZhuangImg;
        this.shenSuoFengImg = shenSuoFengImg;
        this.renXingDaoImg = renXingDaoImg;
        this.lanGanHuLanImg = lanGanHuLanImg;
        this.paiShuiSheShiImg = paiShuiSheShiImg;
        this.dunTaiImg = dunTaiImg;
        this.yiQiangHuPoImg = yiQiangHuPoImg;
        this.jiaoTongSheShiImg = jiaoTongSheShiImg;
        this.guanCeDianImg = guanCeDianImg;
    }

    public Check(CheckTemp checkt) {
        this.examineID = checkt.getExamineID();
        this.BridgeID = checkt.getBridgeID();
        this.date = checkt.getDate();
        this.workerID = checkt.getWorkerID();
        this.ownerID = checkt.getOwnerID();
        this.beiZhu = checkt.getBeiZhu();
        this.qiaoLiangJieGou = checkt.getQiaoLiangJieGou();
        this.qiaoLiangWaiGuan = checkt.getQiaoLiangWaiGuan();
        this.zhuLiang = checkt.getZhuLiang();
        this.xieLaSuo = checkt.getXieLaSuo();
        this.qiaoMianPuZhuang = checkt.getQiaoMianPuZhuang();
        this.shenSuoFeng = checkt.getShenSuoFeng();
        this.renXingDao = checkt.getRenXingDao();
        this.lanGanHuLan = checkt.getLanGanHuLan();
        this.paiShuiSheShi = checkt.getPaiShuiSheShi();
        this.dunTai = checkt.getDunTai();
        this.yiQiangHuPo = checkt.getYiQiangHuPo();
        this.jiaoTongSheShi = checkt.getJiaoTongSheShi();
        this.guanCeDian = checkt.getGuanCeDian();
        this.nextDate = checkt.getNextDate();
        this.qiaoLiangJieGouImg = checkt.getQiaoLiangJieGouImg();
        this.qiaoLiangWaiGuanImg = checkt.getQiaoLiangWaiGuanImg();
        this.zhuLiangImg = checkt.getZhuLiangImg();
        this.xieLaSuoImg = checkt.getXieLaSuoImg();
        this.qiaoMianPuZhuangImg = checkt.getQiaoMianPuZhuangImg();
        this.shenSuoFengImg = checkt.getShenSuoFengImg();
        this.renXingDaoImg = checkt.getRenXingDaoImg();
        this.lanGanHuLanImg = checkt.getLanGanHuLanImg();
        this.paiShuiSheShiImg = checkt.getPaiShuiSheShiImg();
        this.dunTaiImg = checkt.getDunTaiImg();
        this.yiQiangHuPoImg = checkt.getYiQiangHuPoImg();
        this.jiaoTongSheShiImg = checkt.getJiaoTongSheShiImg();
        this.guanCeDianImg = checkt.getGuanCeDianImg();
    }

    @Generated(hash = 1080183208)
    public Check() {
    }

    @Generated(hash = 761162677)
    private transient String bridge__resolvedKey;

    @Generated(hash = 1791517005)
    private transient String worker__resolvedKey;

    @Generated(hash = 1407767798)
    private transient String owner__resolvedKey;

    public String getBridgeName() {
        return bridgeName;
    }

    public void setBridgeName(String bridgeName) {
        this.bridgeName = bridgeName;
    }


    public Boolean getBHistory() {
        return this.bHistory;
    }


    public void setBHistory(Boolean bHistory) {
        this.bHistory = bHistory;
    }

}
