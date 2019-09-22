package cn.com.lxsoft.bdasphone.database;

import android.content.Context;

import com.amap.api.maps2d.model.LatLng;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.HashMap;
import java.util.List;

import cn.com.lxsoft.bdasphone.database.greendao.DaoSession;
import cn.com.lxsoft.bdasphone.entity.BridgeChart;
import cn.com.lxsoft.bdasphone.entity.BridgeGisData;
import cn.com.lxsoft.bdasphone.entity.BridgeMaintain;
import cn.com.lxsoft.bdasphone.entity.Chart;
import cn.com.lxsoft.bdasphone.entity.Check;
import cn.com.lxsoft.bdasphone.entity.Construct;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.Disease;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.entity.Patrol;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.entity.User;
import cn.com.lxsoft.bdasphone.entity.YearTest;

public interface DataBase {
    void init(Context context);

    void initData();

    List<LuXian> getLuXianData(String searchText);

    List<QiaoLiang> getQiaoLiangData(WhereCondition whereCondition, Property property,boolean bUp,int pageIndex);


    List<QiaoLiang> getBridgePageData(int pageIndex);

    Patrol getPatrolData(String sExamineID);

    Check getCheckData(String sExamineID);

    void insertOrReplacePatrolData(Patrol patrol);

    void insertOrReplaceBridgeData(QiaoLiang bridge);

    Long setExamineSearchData(int examType,int searchType);

    List<DanWei> getDeptData(String searchText);

    BridgeChart getChartData(String cid, String ctype);

    void setBridgeSearchData(String searchRes,String key,boolean bUp);

    QiaoLiang getBridge(String tpqldm);

    String getDanWeiName(String key);

    void addSearchHistoryData(String name,String data);

    String[] getSearchDataName();

    String[] getSearchHistoryData();

    List<Construct> getConstructData(String tpql);
    List<Construct> getConstructData(String tpql,int pos);

    void insertDiseaseData(List<Disease> dList);

    YearTest getYearTestData(String sExamineID);

    void insertOrReplaceYearTestData(YearTest test);

    void insertOrReplaceMaintainData(BridgeMaintain maintain);

    List<Disease> getDiseaseData(String examineID);

    void insertOrReplaceCheckData(Check check);

    List<User> getLeaderUser();

    DaoSession getDaoSession();

    User getUser(String id);

    List<QiaoLiang> getBridgeGisData();

    void insertDeptData(List<DanWei> dwList);

    void insertBridgeData(List<QiaoLiang> qlList);

    Long getBridgeNum();

    void insertRoadData(List<LuXian> qlList);

    DanWei getDepartment(String id);
}
