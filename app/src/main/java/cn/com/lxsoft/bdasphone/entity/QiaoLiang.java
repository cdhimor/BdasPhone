package cn.com.lxsoft.bdasphone.entity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;

import org.greenrobot.greendao.annotation.*;

import com.google.gson.annotations.SerializedName;
import org.greenrobot.greendao.DaoException;

import java.util.Date;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.greendao.DaoSession;
import cn.com.lxsoft.bdasphone.database.greendao.LuXianDao;
import cn.com.lxsoft.bdasphone.database.greendao.DanWeiDao;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;
import cn.com.lxsoft.bdasphone.database.greendao.PatrolDao;
import cn.com.lxsoft.bdasphone.database.greendao.UserDao;
import cn.com.lxsoft.bdasphone.database.greendao.CheckDao;
import cn.com.lxsoft.bdasphone.database.greendao.YearTestDao;



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
public class QiaoLiang {//当前状态
    @Id
    @SerializedName("DM")
    private String daiMa;

    @SerializedName("MC")
    private String mingCheng;

    @SerializedName("LX")
    private String luXianID;
    @ToOne(joinProperty ="luXianID")
    private LuXian luXian;

    @SerializedName("DW")
    private String danWeiID;
    @ToOne(joinProperty ="danWeiID")
    private DanWei danWei;

    @SerializedName("LEX")
    private String leiXing;

    @SerializedName("JG")
    private String jieGou;

    @SerializedName("ZH")
    private float zhuangHao;
    @SerializedName("QC")
    private float qiaoChang;
    @SerializedName("QK")
    private float qiaoKuan;
    @SerializedName("QG")
    private float qiaoGao;

    @SerializedName("PJ")
    private int pingJi;

    @SerializedName("WK")
    private String workerID;
    @ToOne(joinProperty ="workerID")
    private User worker;

    @SerializedName("PI")
    public String patrolID;
    @ToOne(joinProperty ="patrolID")
    private Patrol patrol;

    @SerializedName("TI")
    public String yearTestID;
    @ToOne(joinProperty ="yearTestID")
    private YearTest yearTest;

    @SerializedName("CK")
    public String checkID;
    @ToOne(joinProperty ="checkID")
    private Check check;

    @SerializedName("LT")
    private double lat;

    @SerializedName("LG")
    private double lng;

    @SerializedName("NY")
    private Date jianQiaoNianYue;

    @SerializedName("JT")
    private float jiaoTongLiuLiang;

    @SerializedName("HZ")
    private String heZaiDengJi;

    @SerializedName("KD")
    private String kuaYueDiWu;

    @SerializedName("YT")
    private String yongTu;

    public String getleiXingInfo(){
        return DataDict.getDict("1.1",leiXing);
    }

    public String getjieGouInfo(){
        return DataDict.getMultDict("1.2",jieGou);
    }

    public String getjieGouPJInfo(){
        return DataDict.getMultDict("1.2x",jieGou);
    }


    public int getExamineLevel(){
        int iQ=Integer.parseInt(getLeiXing())-1;
        int iL=0;
        switch (getLuXianID()){
            case "S":
               iL=1;
               break;
            case "X":
                iL=3;
                break;
        }
        return SystemConfig.ExamineLevel[iL][iQ];
    }


    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 164110102)
    private transient QiaoLiangDao myDao;


    @Generated(hash = 1961210338)
    public QiaoLiang(String daiMa, String mingCheng, String luXianID, String danWeiID, String leiXing, String jieGou,
            float zhuangHao, float qiaoChang, float qiaoKuan, float qiaoGao, int pingJi, String workerID, String patrolID,
            String yearTestID, String checkID, double lat, double lng, Date jianQiaoNianYue, float jiaoTongLiuLiang,
            String heZaiDengJi, String kuaYueDiWu, String yongTu) {
        this.daiMa = daiMa;
        this.mingCheng = mingCheng;
        this.luXianID = luXianID;
        this.danWeiID = danWeiID;
        this.leiXing = leiXing;
        this.jieGou = jieGou;
        this.zhuangHao = zhuangHao;
        this.qiaoChang = qiaoChang;
        this.qiaoKuan = qiaoKuan;
        this.qiaoGao = qiaoGao;
        this.pingJi = pingJi;
        this.workerID = workerID;
        this.patrolID = patrolID;
        this.yearTestID = yearTestID;
        this.checkID = checkID;
        this.lat = lat;
        this.lng = lng;
        this.jianQiaoNianYue = jianQiaoNianYue;
        this.jiaoTongLiuLiang = jiaoTongLiuLiang;
        this.heZaiDengJi = heZaiDengJi;
        this.kuaYueDiWu = kuaYueDiWu;
        this.yongTu = yongTu;
    }

    @Generated(hash = 1604867976)
    public QiaoLiang() {
    }

    @Generated(hash = 1456009866)
    private transient String danWei__resolvedKey;

    @Generated(hash = 1233672073)
    private transient String luXian__resolvedKey;

    @Generated(hash = 1791517005)
    private transient String worker__resolvedKey;

    @Generated(hash = 1788412596)
    private transient String patrol__resolvedKey;

    @Generated(hash = 1464487730)
    private transient String yearTest__resolvedKey;

    @Generated(hash = 693484506)
    private transient String check__resolvedKey;


    public Drawable getBridgeDrawable(){
        int color=0xFF00CC00;
        switch (getPingJi()) {
            case 2:
                color=0xff0000CC;
                break;
            case 3:
                color=0xffFFFF00;
                break;
            case 4:
                color=0xffff6600;
                break;
            case 5:
                color=0xffFF3300;
                break;
        }
        GradientDrawable grad = new GradientDrawable(GradientDrawable.Orientation.TL_BR,new int[]{color, Color.WHITE});
        grad.setCornerRadius(24);
        return grad;
    }

    public int getIconNum(){
        int iconID=R.mipmap.ic_qiaoxing_liang;
        switch (Float.floatToIntBits(getZhuangHao())%5){
            case 1:
                iconID=R.mipmap.ic_qiaoxing_gong;
                break;
            case 2:
                iconID=R.mipmap.ic_qiaoxing_xiela;
                break;
            case 3:
                iconID=R.mipmap.ic_qiaoxing_xuansuo;
                break;
            case 4:
                iconID=R.mipmap.ic_qiaoxing_zhuhe;
                break;
        }
        return iconID;
        //drawableIcon=ContextCompat.getDrawable(viewModel.getApplication(),iconID);
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

    public String getWorkerID() {
        return this.workerID;
    }

    public void setWorkerID(String workerID) {
        this.workerID = workerID;
    }

    public String getPatrolID() {
        return this.patrolID;
    }

    public void setPatrolID(String patrolID) {
        this.patrolID = patrolID;
    }

    public String getYearTestID() {
        return this.yearTestID;
    }

    public void setYearTestID(String yearTestID) {
        this.yearTestID = yearTestID;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return this.lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getCheckID() {
        return this.checkID;
    }

    public void setCheckID(String checkID) {
        this.checkID = checkID;
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
    @Generated(hash = 718638389)
    public Patrol getPatrol() {
        String __key = this.patrolID;
        if (patrol__resolvedKey == null || patrol__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PatrolDao targetDao = daoSession.getPatrolDao();
            Patrol patrolNew = targetDao.load(__key);
            synchronized (this) {
                patrol = patrolNew;
                patrol__resolvedKey = __key;
            }
        }
        return patrol;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1266084540)
    public void setPatrol(Patrol patrol) {
        synchronized (this) {
            this.patrol = patrol;
            patrolID = patrol == null ? null : patrol.getBridgeCode();
            patrol__resolvedKey = patrolID;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 20364898)
    public YearTest getYearTest() {
        String __key = this.yearTestID;
        if (yearTest__resolvedKey == null || yearTest__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            YearTestDao targetDao = daoSession.getYearTestDao();
            YearTest yearTestNew = targetDao.load(__key);
            synchronized (this) {
                yearTest = yearTestNew;
                yearTest__resolvedKey = __key;
            }
        }
        return yearTest;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1283386284)
    public void setYearTest(YearTest yearTest) {
        synchronized (this) {
            this.yearTest = yearTest;
            yearTestID = yearTest == null ? null : yearTest.getBridgeID();
            yearTest__resolvedKey = yearTestID;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2076208756)
    public Check getCheck() {
        String __key = this.checkID;
        if (check__resolvedKey == null || check__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CheckDao targetDao = daoSession.getCheckDao();
            Check checkNew = targetDao.load(__key);
            synchronized (this) {
                check = checkNew;
                check__resolvedKey = __key;
            }
        }
        return check;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1253036697)
    public void setCheck(Check check) {
        synchronized (this) {
            this.check = check;
            checkID = check == null ? null : check.getExamineID();
            check__resolvedKey = checkID;
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

    public Date getJianQiaoNianYue() {
        return this.jianQiaoNianYue;
    }

    public void setJianQiaoNianYue(Date jianQiaoNianYue) {
        this.jianQiaoNianYue = jianQiaoNianYue;
    }

    public float getJiaoTongLiuLiang() {
        return this.jiaoTongLiuLiang;
    }

    public void setJiaoTongLiuLiang(float jiaoTongLiuLiang) {
        this.jiaoTongLiuLiang = jiaoTongLiuLiang;
    }

    public String getHeZaiDengJi() {
        return this.heZaiDengJi;
    }

    public void setHeZaiDengJi(String heZaiDengJi) {
        this.heZaiDengJi = heZaiDengJi;
    }

    public String getKuaYueDiWu() {
        return this.kuaYueDiWu;
    }

    public void setKuaYueDiWu(String kuaYueDiWu) {
        this.kuaYueDiWu = kuaYueDiWu;
    }

    public String getYongTu() {
        return this.yongTu;
    }

    public void setYongTu(String yongTu) {
        this.yongTu = yongTu;
    }


}
