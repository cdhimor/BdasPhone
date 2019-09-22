package cn.com.lxsoft.bdasphone.entity;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.*;
import org.greenrobot.greendao.DaoException;

import java.util.ArrayList;
import java.util.List;

import cn.com.lxsoft.bdasphone.database.greendao.DaoSession;
import cn.com.lxsoft.bdasphone.database.greendao.YearTestDao;
import cn.com.lxsoft.bdasphone.database.greendao.DiseaseDao;


@Entity
public class Disease {
    @Id(autoincrement = true)
    private Long id;

    @SerializedName("检查ID")
    private String examineID;
    @ToOne(joinProperty ="examineID")
    private YearTest yearTest;

    @SerializedName("序号")
    private String xuHao;

    @SerializedName("构件编号")
    private String gouJian="";

    @SerializedName("部件材质")
    private String caiZhi="";

    @SerializedName("部件编号")
    private String buJian="";

    @SerializedName("病害")
    private String bingHai="";

    @SerializedName("照片")
    private String images="";

    @SerializedName("备注")
    private String remark="";


    public int getBiaoDu(){
        if(bingHai.length()==9)
            return Integer.valueOf(bingHai.substring(8));
        else{
            if(getBuJian().equals("401"))
                return 5;
        }
        return 0;
    }

    public boolean checkEmpty(){
        if(gouJian.isEmpty() || bingHai.isEmpty())
            return true;
        else
            return false;
    }



    public List<ImageData> getImageList() {
        if(imageList==null)
            imageList=ImageData.parseImageData(images);
        return imageList;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExamineID() {
        return this.examineID;
    }

    public void setExamineID(String examineID) {
        this.examineID = examineID;
    }

    public String getXuHao() {
        return this.xuHao;
    }

    public void setXuHao(String xuHao) {
        this.xuHao = xuHao;
    }

    public String getGouJian() {
        return this.gouJian;
    }

    public void setGouJian(String gouJian) {
        this.gouJian = gouJian;
    }

    public String getCaiZhi() {
        return this.caiZhi;
    }

    public void setCaiZhi(String caiZhi) {
        this.caiZhi = caiZhi;
    }

    public String getBuJian() {
        return this.buJian;
    }

    public void setBuJian(String buJian) {
        this.buJian = buJian;
    }

    public String getBingHai() {
        return this.bingHai;
    }

    public void setBingHai(String bingHai) {
        this.bingHai = bingHai;
    }

    public String getImages() {
        return this.images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1033886417)
    public YearTest getYearTest() {
        String __key = this.examineID;
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
    @Generated(hash = 1410881344)
    public void setYearTest(YearTest yearTest) {
        synchronized (this) {
            this.yearTest = yearTest;
            examineID = yearTest == null ? null : yearTest.getBridgeID();
            yearTest__resolvedKey = examineID;
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
    @Generated(hash = 109044434)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDiseaseDao() : null;
    }

    @Transient
    private List<ImageData> imageList=null;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1041835162)
    private transient DiseaseDao myDao;


    @Generated(hash = 407020118)
    public Disease(Long id, String examineID, String xuHao, String gouJian,
            String caiZhi, String buJian, String bingHai, String images,
            String remark) {
        this.id = id;
        this.examineID = examineID;
        this.xuHao = xuHao;
        this.gouJian = gouJian;
        this.caiZhi = caiZhi;
        this.buJian = buJian;
        this.bingHai = bingHai;
        this.images = images;
        this.remark = remark;
    }

    @Generated(hash = 1596955631)
    public Disease() {
    }

    @Generated(hash = 1464487730)
    private transient String yearTest__resolvedKey;


}
