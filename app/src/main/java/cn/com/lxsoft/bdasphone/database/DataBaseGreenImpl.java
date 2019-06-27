package cn.com.lxsoft.bdasphone.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.greendao.BridgeJCSBDao;
import cn.com.lxsoft.bdasphone.database.greendao.ChartDao;
import cn.com.lxsoft.bdasphone.database.greendao.CheckDao;
import cn.com.lxsoft.bdasphone.database.greendao.ConstructDao;
import cn.com.lxsoft.bdasphone.database.greendao.DanWeiDao;
import cn.com.lxsoft.bdasphone.database.greendao.DaoMaster;
import cn.com.lxsoft.bdasphone.database.greendao.DaoSession;
import cn.com.lxsoft.bdasphone.database.greendao.DiseaseDao;
import cn.com.lxsoft.bdasphone.database.greendao.LuXianDao;
import cn.com.lxsoft.bdasphone.database.greendao.PatrolDao;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;
import cn.com.lxsoft.bdasphone.database.greendao.SearchHistoryDao;
import cn.com.lxsoft.bdasphone.entity.BridgeJCSB;
import cn.com.lxsoft.bdasphone.entity.Chart;
import cn.com.lxsoft.bdasphone.entity.Check;
import cn.com.lxsoft.bdasphone.entity.Construct;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.Disease;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.entity.Patrol;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.entity.SearchHistory;
import cn.com.lxsoft.bdasphone.entity.YearTest;


public class DataBaseGreenImpl implements DataBase {
    private static volatile DataBaseGreenImpl instance=null;

    DaoMaster.OpenHelper helper;
    SQLiteDatabase db=null;
    DaoMaster daoMaster=null;
    DaoSession daoSession=null;
    Context context;

    QueryBuilder bridgeQB;

    HashMap<String, SearchData> searchDictList = null;
    class SearchData {
        public String name;
        public Property property;

        public SearchData(String nm, Property pt) {
            name = nm;
            property = pt;
        }
    }

    @Override
    public void init(Context ct) {
        helper = new DaoMaster.DevOpenHelper(ct, SystemConfig.DBName, null);
        context=ct;

        searchDictList = new HashMap<>();
        searchDictList.put("m1.1", new SearchData("BASE", QiaoLiangDao.Properties.LeiXing));
        searchDictList.put("m1.2", new SearchData("BASE", QiaoLiangDao.Properties.JieGou));
        searchDictList.put("m1.3", new SearchData("JCSB", BridgeJCSBDao.Properties.KuaYueDiWuLeiXing));
        searchDictList.put("m1.4", new SearchData("JCSB", BridgeJCSBDao.Properties.GongLuJiShuDengJi));
        searchDictList.put("m0.1", new SearchData("BASE", QiaoLiangDao.Properties.DaiMa));
        searchDictList.put("m0.2", new SearchData("BASE", QiaoLiangDao.Properties.MingCheng));
        searchDictList.put("m0.3", new SearchData("BASE", QiaoLiangDao.Properties.DanWeiID));
        searchDictList.put("m0.4", new SearchData("BASE", QiaoLiangDao.Properties.LuXianID));
        searchDictList.put("m6.0", new SearchData("BASE", QiaoLiangDao.Properties.PingJi));


    }

    public static  DataBaseGreenImpl getInstance(){
        if(instance==null){
            synchronized(DataBaseGreenImpl .class){
                if(instance==null){
                    instance=new DataBaseGreenImpl ();
                }
            }
        }
        return instance;
    }

    @Override
    public void initTestData(){
        File baseFile = new File("/data/data/cn.com.lxsoft.bdas/databases/"+SystemConfig.DBName);
        if(baseFile.exists())
            return;

        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession tpdaoSession = daoMaster.newSession();

        daoMaster.dropAllTables(daoMaster.getDatabase(),true);
        daoMaster.createAllTables(daoMaster.getDatabase(),true);

        LuXianDao lxDao = tpdaoSession.getLuXianDao();
        lxDao.deleteAll();
        DanWeiDao danWeiDao=tpdaoSession.getDanWeiDao();
        danWeiDao.deleteAll();
        QiaoLiangDao qiaoLiangDao=tpdaoSession.getQiaoLiangDao();
        qiaoLiangDao.deleteAll();
        ChartDao chartDao=tpdaoSession.getChartDao();
        chartDao.deleteAll();
        ConstructDao bjDao=tpdaoSession.getConstructDao();
        bjDao.deleteAll();


        Gson gson = new Gson();
        InputStream stream;
        BufferedReader reader;
        String jsonStr="",line="";

        stream=context.getResources().openRawResource(R.raw.lx);
        reader=new BufferedReader(new InputStreamReader(stream));
        try {
            while ((line=reader.readLine())!=null){
                jsonStr+=line;
            }
            reader.close();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<LuXian> luXianList = gson.fromJson(jsonStr, new TypeToken<List<LuXian>>() {
        }.getType());
        lxDao.insertInTx(luXianList);

        stream=context.getResources().openRawResource(R.raw.dw);
        reader=new BufferedReader(new InputStreamReader(stream));
        jsonStr="";
        line="";
        try {
            while ((line=reader.readLine())!=null){
                jsonStr+=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<DanWei> listDanWei = gson.fromJson(jsonStr, new TypeToken<List<DanWei>>() {
        }.getType());
        danWeiDao.insertInTx(listDanWei);

        stream=context.getResources().openRawResource(R.raw.ql);
        reader=new BufferedReader(new InputStreamReader(stream));
        jsonStr="";
        line="";
        try {
            while ((line=reader.readLine())!=null){
                jsonStr+=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<QiaoLiang> listQL = gson.fromJson(jsonStr, new TypeToken<List<QiaoLiang>>() {}.getType());
        qiaoLiangDao.insertInTx(listQL);

        stream=context.getResources().openRawResource(R.raw.bj);
        reader=new BufferedReader(new InputStreamReader(stream));
        jsonStr="";
        line="";
        try {
            while ((line=reader.readLine())!=null){
                jsonStr+=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Construct> listBJ = gson.fromJson(jsonStr, new TypeToken<List<Construct>>() {}.getType());
        bjDao.insertInTx(listBJ);

        stream=context.getResources().openRawResource(R.raw.bb);
        reader=new BufferedReader(new InputStreamReader(stream));
        jsonStr="";
        line="";
        try {
            while ((line=reader.readLine())!=null){
                jsonStr+=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Chart> listChart = gson.fromJson(jsonStr, new TypeToken<List<Chart>>() {}.getType());
        chartDao.insertInTx(listChart);



        db.close();
        tpdaoSession.clear();


    }

    public List<LuXian> getLuXianData(String searchText) {
        openDataBase();
        QueryBuilder qb=daoSession.getLuXianDao().queryBuilder();
        qb.where(qb.or(LuXianDao.Properties.BianHao.like("%" + searchText + "%"),LuXianDao.Properties.MingCheng.like("%" + searchText + "%")));
        qb.orderAsc(LuXianDao.Properties.BianHao);
        return qb.list();
    }

    public List<DanWei> getDeptData(String searchText) {
        openDataBase();
        QueryBuilder qb=daoSession.getDanWeiDao().queryBuilder();
        qb.where(DanWeiDao.Properties.DaiMa.like("%" + searchText + "%"));
        qb.orderAsc(DanWeiDao.Properties.DaiMa);
        return qb.list();
    }


    public List<QiaoLiang> getQiaoLiangData(WhereCondition whereCondition, Property property,boolean bUp,int pageIndex) {
        openDataBase();
        QueryBuilder qb=daoSession.getQiaoLiangDao().queryBuilder();
        qb.where((whereCondition==null)?QiaoLiangDao.Properties.DaiMa.isNotNull():whereCondition);
        if(bUp)
            qb.orderAsc(property==null?QiaoLiangDao.Properties.DaiMa:property);
        else
            qb.orderDesc(property==null?QiaoLiangDao.Properties.DaiMa:property);
        return qb.offset(pageIndex * SystemConfig.PageSizeBridge).limit(SystemConfig.PageSizeBridge)
            .list();
    }

    private Join dealWhereConditionList(Join join,HashMap<String,List<WhereCondition>> andHash)
    {
        for(String andkey:andHash.keySet()){
            List<WhereCondition> orList=andHash.get(andkey);
            if(orList.size()>1) {
                WhereCondition wc1=orList.get(0);
                WhereCondition wc2=orList.get(1);
                if(orList.size()>2) {
                    WhereCondition[] orArray = orList.subList(2, orList.size() - 1).toArray(new WhereCondition[orList.size()]);
                    bridgeQB.whereOr(wc1,wc2,orArray);
                }
                else
                    bridgeQB.whereOr(wc1,wc2);
            }
            else
                bridgeQB.where(orList.get(0));
        }
        return join;
    }

    public List<QiaoLiang> getBridgePageData(int pageIndex){
        if(bridgeQB==null)
            return null;
        return bridgeQB.offset(pageIndex * SystemConfig.PageSizeBridge).limit(SystemConfig.PageSizeBridge).list();
    }

    //m1.1┬1┼m1.2┬01
    public void setBridgeSearchData(String searchRes) {
        HashMap<String,HashMap<String,List<WhereCondition>>> advSearchCondition=new HashMap<>();
        String[] searchList=searchRes.split("┼");

        bridgeQB=daoSession.getQiaoLiangDao().queryBuilder();

        for(int i=0;i<searchList.length;i++) {
            String[] tplist = searchList[i].split("┬");
            if (tplist.length > 1) {
                String dict=tplist[0];
                String res = tplist[1];
                SearchData sdata=searchDictList.get(tplist[0]);
                WhereCondition wc=null;
                if(res=="NOTNULL")
                    wc=sdata.property.isNull();
                else {
                    switch (dict) {
                        case "m0.1"://桥梁代码
                        case "m0.2"://桥梁名称
                            wc = sdata.property.like("%" + tplist[1] + "%");
                            break;
                        default:
                            wc = sdata.property.eq(tplist[1]);
                            break;
                    }
                }

                if(wc!=null) {
                    HashMap<String, List<WhereCondition>> andMap = advSearchCondition.get(sdata.name);
                    if (andMap == null) {
                        andMap = new HashMap<>();
                        advSearchCondition.put(sdata.name, andMap);
                    }

                    List<WhereCondition> orList = andMap.get(res);
                    if (orList == null) {
                        orList = new ArrayList<>();
                        andMap.put(res, orList);
                    }
                    orList.add(wc);
                }
            }
        }

        for(String key:advSearchCondition.keySet())
        {
            switch (key){
                case "BASE":
                    HashMap<String,List<WhereCondition>> andHash=advSearchCondition.get(key);
                    for(String andkey:andHash.keySet()){
                        List<WhereCondition> orList=andHash.get(andkey);
                        if(orList.size()>1) {
                            WhereCondition wc1=orList.get(0);
                            WhereCondition wc2=orList.get(1);
                            if(orList.size()>2) {
                                WhereCondition[] orArray = orList.subList(2, orList.size() - 1).toArray(new WhereCondition[orList.size()]);
                                bridgeQB.whereOr(wc1,wc2,orArray);
                            }
                            else
                                bridgeQB.whereOr(wc1,wc2);
                        }
                        else
                            bridgeQB.where(orList.get(0));
                    }
                    break;
                case "JCSB":
                    Join join=bridgeQB.join(QiaoLiangDao.Properties.DaiMa,BridgeJCSB.class,BridgeJCSBDao.Properties.QiaoLiangDaiMa);
                    dealWhereConditionList(join,advSearchCondition.get(key));
                    break;
            }
        }

    }

    public void setBridgeOrderData(String key,boolean bUp)
    {
        if(bridgeQB==null)
            bridgeQB=daoSession.getQiaoLiangDao().queryBuilder();
        Property property=searchDictList.get(key).property;
        if(bUp)
            bridgeQB.orderAsc(property==null?QiaoLiangDao.Properties.DaiMa:property);
        else
            bridgeQB.orderDesc(property==null?QiaoLiangDao.Properties.DaiMa:property);
    }

    public List<QiaoLiang> getQiaoLiangDataAdv(HashMap<String,List<List<WhereCondition>>> hList,Property property, boolean bUp, int pageIndex) {

        return getBridgePageData(pageIndex);
    }

    public List<QiaoLiang> getBridgePatrolDiseaseData(int pageIndex) {
        openDataBase();
        QueryBuilder qb=daoSession.getQiaoLiangDao().queryBuilder();
        qb.where(QiaoLiangDao.Properties.PatrolID.isNotNull());
        Join join=qb.join(QiaoLiangDao.Properties.PatrolID,Patrol.class,PatrolDao.Properties.ExamineID);
        join.where(join.or(PatrolDao.Properties.PuZhuangSSF.notEq("A"),
                        PatrolDao.Properties.XianXing.notEq("A"),
                        PatrolDao.Properties.LanGan.notEq("A"),
                        PatrolDao.Properties.BiaoZhi.notEq("A"),
                        PatrolDao.Properties.QiaoLuLianJie.notEq("A")));

        return qb.offset(pageIndex * SystemConfig.PageSizeBridge).limit(SystemConfig.PageSizeBridge)
                .list();
    }

    public Patrol getPatrolData(String sExamineID) {
        openDataBase();
        QueryBuilder qb=daoSession.getPatrolDao().queryBuilder();
        qb.where(PatrolDao.Properties.ExamineID.eq(sExamineID));
        List<Patrol> list=qb.limit(1).list();
        if(list.size()>0)
            return list.get(0);
        else
            return null;
    }

    public Check getCheckData(String sExamineID) {
        openDataBase();
        QueryBuilder qb=daoSession.getCheckDao().queryBuilder();
        qb.where(CheckDao.Properties.ExamineID.eq(sExamineID));
        List<Check> list=qb.limit(1).list();
        if(list.size()>0)
            return list.get(0);
        else
            return null;
    }

    public YearTest getYearTestData(String sExamineID) {
        openDataBase();
        QueryBuilder qb=daoSession.getYearTestDao().queryBuilder();
        qb.where(CheckDao.Properties.ExamineID.eq(sExamineID));
        List<YearTest> list=qb.limit(1).list();
        if(list.size()>0)
            return list.get(0);
        else
            return null;
    }

    public List<Disease> getDiseaseData(String bridgeID)
    {
        openDataBase();
        QueryBuilder qb=daoSession.getDiseaseDao().queryBuilder();
        qb.where(DiseaseDao.Properties.BridgeID.eq(bridgeID));
        return qb.list();
    }

    public List<Chart> getChartData(int cid,String ctype)
    {
        openDataBase();
        QueryBuilder qb=daoSession.getChartDao().queryBuilder();
        qb.where(qb.and(ChartDao.Properties.Cid.eq(cid),
                ChartDao.Properties.Type.eq(ctype)));
        return qb.list();
    }

    public void insertOrReplaceBridgeData(QiaoLiang bridge)
    {
        openDataBase();
        daoSession.getQiaoLiangDao().insertOrReplace(bridge);
    }

    public void insertOrReplacePatrolData(Patrol patrol)
    {
        openDataBase();
        daoSession.getPatrolDao().insertOrReplace(patrol);
    }

    public void insertOrReplaceYearTestData(YearTest test)
    {
        openDataBase();
        daoSession.getYearTestDao().insertOrReplace(test);
    }

    public String getDanWeiName(String key) {
        openDataBase();
        QueryBuilder qb=daoSession.getDanWeiDao().queryBuilder();
        qb.where(DanWeiDao.Properties.DaiMa.eq(key));
        List<DanWei> dwl=qb.limit(1).list();
        if(dwl.size()>=1)
            return dwl.get(0).getMingCheng();
        else
            return "";
    }

    public void addSearchHistoryData(String name,String data)
    {
        SearchHistory searchData=new SearchHistory(data,name,System.currentTimeMillis());
        openDataBase();
        daoSession.getSearchHistoryDao().insertOrReplace(searchData);
    }

    public String[] getSearchDataName(){
        QueryBuilder qb=daoSession.getSearchHistoryDao().queryBuilder();
        qb.orderDesc(SearchHistoryDao.Properties.Time);
        List<SearchHistory> list=qb.limit(20).list();
        int count=list.size();
        if(count>0) {
            String[] resList = new String[list.size()];
            for (int i = 0; i < list.size(); i++)
                resList[i] = list.get(i).getName();
            return resList;
        }
        else
            return null;
    }

    public String[] getSearchHistoryData(){
        QueryBuilder qb=daoSession.getSearchHistoryDao().queryBuilder();
        qb.orderDesc(SearchHistoryDao.Properties.Time);
        List<SearchHistory> list=qb.limit(20).list();
        int count=list.size();
        if(count>0) {
            String[] resList = new String[list.size()];
            for (int i = 0; i < list.size(); i++)
                resList[i] = list.get(i).getSearchData();
            return resList;
        }
        else
            return null;
    }

    public List<Construct> getConstructData(String tpql){
        openDataBase();
        QueryBuilder qb=daoSession.getConstructDao().queryBuilder();
        qb.where(ConstructDao.Properties.BridgeID.eq(tpql));
        return qb.list();
    }

    private void openDataBase(){
        if(daoSession==null) {
            db = helper.getReadableDatabase();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
            bridgeQB=daoSession.getQiaoLiangDao().queryBuilder();
        }
    }

    public void insertOrReplaceDiseaseData(List<Disease> dList)
    {
        openDataBase();
        for(int i=0;i<dList.size();i++){
            daoSession.getDiseaseDao().insertOrReplace(dList.get(i));
        }
        //daoSession.getPatrolDao().in;
    }


    private void openWriteDataBase(){
        if(daoSession==null) {
            db = helper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
        }
    }

    private void closeDataBase(){
        db.close();
        daoSession.clear();
    }
}
