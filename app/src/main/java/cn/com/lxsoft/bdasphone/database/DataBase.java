package cn.com.lxsoft.bdasphone.database;

import android.content.Context;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.HashMap;
import java.util.List;

import cn.com.lxsoft.bdasphone.entity.Chart;
import cn.com.lxsoft.bdasphone.entity.Check;
import cn.com.lxsoft.bdasphone.entity.Construct;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.Disease;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.entity.Patrol;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.entity.YearTest;

public interface DataBase {
    void init(Context context);

    void initTestData();

    List<LuXian> getLuXianData(String searchText);

    List<QiaoLiang> getQiaoLiangData(WhereCondition whereCondition, Property property,boolean bUp,int pageIndex);

    List<QiaoLiang> getQiaoLiangDataAdv(HashMap<String,List<List<WhereCondition>>> hList, Property property, boolean bUp, int pageIndex);

    List<QiaoLiang> getBridgePageData(int pageIndex);

    Patrol getPatrolData(String sExamineID);

    Check getCheckData(String sExamineID);

    void insertOrReplacePatrolData(Patrol patrol);

    void insertOrReplaceBridgeData(QiaoLiang bridge);

    List<QiaoLiang> getBridgePatrolDiseaseData(int pageIndex);

    List<DanWei> getDeptData(String searchText);

    List<Chart> getChartData(int cid, String ctype);

    void setBridgeSearchData(String searchRes);

    void setBridgeOrderData(String key,boolean bUp);

    String getDanWeiName(String key);

    void addSearchHistoryData(String name,String data);

    String[] getSearchDataName();

    String[] getSearchHistoryData();

    List<Construct> getConstructData(String tpql);

    void insertOrReplaceDiseaseData(List<Disease> dList);

    YearTest getYearTestData(String sExamineID);

    void insertOrReplaceYearTestData(YearTest test);

    List<Disease> getDiseaseData(String bridgeID);
}
