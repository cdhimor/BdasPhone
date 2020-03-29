package cn.com.lxsoft.bdasphone.database.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import cn.com.lxsoft.bdasphone.entity.BridgeChart;
import cn.com.lxsoft.bdasphone.entity.BridgeMaintain;
import cn.com.lxsoft.bdasphone.entity.Chart;
import cn.com.lxsoft.bdasphone.entity.Check;
import cn.com.lxsoft.bdasphone.entity.CheckTemp;
import cn.com.lxsoft.bdasphone.entity.Construct;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.Dectect;
import cn.com.lxsoft.bdasphone.entity.Disease;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.entity.Patrol;
import cn.com.lxsoft.bdasphone.entity.PatrolTemp;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.entity.SearchHistory;
import cn.com.lxsoft.bdasphone.entity.User;
import cn.com.lxsoft.bdasphone.entity.YearTest;

import cn.com.lxsoft.bdasphone.database.greendao.BridgeChartDao;
import cn.com.lxsoft.bdasphone.database.greendao.BridgeMaintainDao;
import cn.com.lxsoft.bdasphone.database.greendao.ChartDao;
import cn.com.lxsoft.bdasphone.database.greendao.CheckDao;
import cn.com.lxsoft.bdasphone.database.greendao.CheckTempDao;
import cn.com.lxsoft.bdasphone.database.greendao.ConstructDao;
import cn.com.lxsoft.bdasphone.database.greendao.DanWeiDao;
import cn.com.lxsoft.bdasphone.database.greendao.DectectDao;
import cn.com.lxsoft.bdasphone.database.greendao.DiseaseDao;
import cn.com.lxsoft.bdasphone.database.greendao.LuXianDao;
import cn.com.lxsoft.bdasphone.database.greendao.PatrolDao;
import cn.com.lxsoft.bdasphone.database.greendao.PatrolTempDao;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;
import cn.com.lxsoft.bdasphone.database.greendao.SearchHistoryDao;
import cn.com.lxsoft.bdasphone.database.greendao.UserDao;
import cn.com.lxsoft.bdasphone.database.greendao.YearTestDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig bridgeChartDaoConfig;
    private final DaoConfig bridgeMaintainDaoConfig;
    private final DaoConfig chartDaoConfig;
    private final DaoConfig checkDaoConfig;
    private final DaoConfig checkTempDaoConfig;
    private final DaoConfig constructDaoConfig;
    private final DaoConfig danWeiDaoConfig;
    private final DaoConfig dectectDaoConfig;
    private final DaoConfig diseaseDaoConfig;
    private final DaoConfig luXianDaoConfig;
    private final DaoConfig patrolDaoConfig;
    private final DaoConfig patrolTempDaoConfig;
    private final DaoConfig qiaoLiangDaoConfig;
    private final DaoConfig searchHistoryDaoConfig;
    private final DaoConfig userDaoConfig;
    private final DaoConfig yearTestDaoConfig;

    private final BridgeChartDao bridgeChartDao;
    private final BridgeMaintainDao bridgeMaintainDao;
    private final ChartDao chartDao;
    private final CheckDao checkDao;
    private final CheckTempDao checkTempDao;
    private final ConstructDao constructDao;
    private final DanWeiDao danWeiDao;
    private final DectectDao dectectDao;
    private final DiseaseDao diseaseDao;
    private final LuXianDao luXianDao;
    private final PatrolDao patrolDao;
    private final PatrolTempDao patrolTempDao;
    private final QiaoLiangDao qiaoLiangDao;
    private final SearchHistoryDao searchHistoryDao;
    private final UserDao userDao;
    private final YearTestDao yearTestDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        bridgeChartDaoConfig = daoConfigMap.get(BridgeChartDao.class).clone();
        bridgeChartDaoConfig.initIdentityScope(type);

        bridgeMaintainDaoConfig = daoConfigMap.get(BridgeMaintainDao.class).clone();
        bridgeMaintainDaoConfig.initIdentityScope(type);

        chartDaoConfig = daoConfigMap.get(ChartDao.class).clone();
        chartDaoConfig.initIdentityScope(type);

        checkDaoConfig = daoConfigMap.get(CheckDao.class).clone();
        checkDaoConfig.initIdentityScope(type);

        checkTempDaoConfig = daoConfigMap.get(CheckTempDao.class).clone();
        checkTempDaoConfig.initIdentityScope(type);

        constructDaoConfig = daoConfigMap.get(ConstructDao.class).clone();
        constructDaoConfig.initIdentityScope(type);

        danWeiDaoConfig = daoConfigMap.get(DanWeiDao.class).clone();
        danWeiDaoConfig.initIdentityScope(type);

        dectectDaoConfig = daoConfigMap.get(DectectDao.class).clone();
        dectectDaoConfig.initIdentityScope(type);

        diseaseDaoConfig = daoConfigMap.get(DiseaseDao.class).clone();
        diseaseDaoConfig.initIdentityScope(type);

        luXianDaoConfig = daoConfigMap.get(LuXianDao.class).clone();
        luXianDaoConfig.initIdentityScope(type);

        patrolDaoConfig = daoConfigMap.get(PatrolDao.class).clone();
        patrolDaoConfig.initIdentityScope(type);

        patrolTempDaoConfig = daoConfigMap.get(PatrolTempDao.class).clone();
        patrolTempDaoConfig.initIdentityScope(type);

        qiaoLiangDaoConfig = daoConfigMap.get(QiaoLiangDao.class).clone();
        qiaoLiangDaoConfig.initIdentityScope(type);

        searchHistoryDaoConfig = daoConfigMap.get(SearchHistoryDao.class).clone();
        searchHistoryDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        yearTestDaoConfig = daoConfigMap.get(YearTestDao.class).clone();
        yearTestDaoConfig.initIdentityScope(type);

        bridgeChartDao = new BridgeChartDao(bridgeChartDaoConfig, this);
        bridgeMaintainDao = new BridgeMaintainDao(bridgeMaintainDaoConfig, this);
        chartDao = new ChartDao(chartDaoConfig, this);
        checkDao = new CheckDao(checkDaoConfig, this);
        checkTempDao = new CheckTempDao(checkTempDaoConfig, this);
        constructDao = new ConstructDao(constructDaoConfig, this);
        danWeiDao = new DanWeiDao(danWeiDaoConfig, this);
        dectectDao = new DectectDao(dectectDaoConfig, this);
        diseaseDao = new DiseaseDao(diseaseDaoConfig, this);
        luXianDao = new LuXianDao(luXianDaoConfig, this);
        patrolDao = new PatrolDao(patrolDaoConfig, this);
        patrolTempDao = new PatrolTempDao(patrolTempDaoConfig, this);
        qiaoLiangDao = new QiaoLiangDao(qiaoLiangDaoConfig, this);
        searchHistoryDao = new SearchHistoryDao(searchHistoryDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);
        yearTestDao = new YearTestDao(yearTestDaoConfig, this);

        registerDao(BridgeChart.class, bridgeChartDao);
        registerDao(BridgeMaintain.class, bridgeMaintainDao);
        registerDao(Chart.class, chartDao);
        registerDao(Check.class, checkDao);
        registerDao(CheckTemp.class, checkTempDao);
        registerDao(Construct.class, constructDao);
        registerDao(DanWei.class, danWeiDao);
        registerDao(Dectect.class, dectectDao);
        registerDao(Disease.class, diseaseDao);
        registerDao(LuXian.class, luXianDao);
        registerDao(Patrol.class, patrolDao);
        registerDao(PatrolTemp.class, patrolTempDao);
        registerDao(QiaoLiang.class, qiaoLiangDao);
        registerDao(SearchHistory.class, searchHistoryDao);
        registerDao(User.class, userDao);
        registerDao(YearTest.class, yearTestDao);
    }
    
    public void clear() {
        bridgeChartDaoConfig.clearIdentityScope();
        bridgeMaintainDaoConfig.clearIdentityScope();
        chartDaoConfig.clearIdentityScope();
        checkDaoConfig.clearIdentityScope();
        checkTempDaoConfig.clearIdentityScope();
        constructDaoConfig.clearIdentityScope();
        danWeiDaoConfig.clearIdentityScope();
        dectectDaoConfig.clearIdentityScope();
        diseaseDaoConfig.clearIdentityScope();
        luXianDaoConfig.clearIdentityScope();
        patrolDaoConfig.clearIdentityScope();
        patrolTempDaoConfig.clearIdentityScope();
        qiaoLiangDaoConfig.clearIdentityScope();
        searchHistoryDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
        yearTestDaoConfig.clearIdentityScope();
    }

    public BridgeChartDao getBridgeChartDao() {
        return bridgeChartDao;
    }

    public BridgeMaintainDao getBridgeMaintainDao() {
        return bridgeMaintainDao;
    }

    public ChartDao getChartDao() {
        return chartDao;
    }

    public CheckDao getCheckDao() {
        return checkDao;
    }

    public CheckTempDao getCheckTempDao() {
        return checkTempDao;
    }

    public ConstructDao getConstructDao() {
        return constructDao;
    }

    public DanWeiDao getDanWeiDao() {
        return danWeiDao;
    }

    public DectectDao getDectectDao() {
        return dectectDao;
    }

    public DiseaseDao getDiseaseDao() {
        return diseaseDao;
    }

    public LuXianDao getLuXianDao() {
        return luXianDao;
    }

    public PatrolDao getPatrolDao() {
        return patrolDao;
    }

    public PatrolTempDao getPatrolTempDao() {
        return patrolTempDao;
    }

    public QiaoLiangDao getQiaoLiangDao() {
        return qiaoLiangDao;
    }

    public SearchHistoryDao getSearchHistoryDao() {
        return searchHistoryDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public YearTestDao getYearTestDao() {
        return yearTestDao;
    }

}
