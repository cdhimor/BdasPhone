package cn.com.lxsoft.bdasphone.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.amap.api.maps2d.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.greendao.BridgeChartDao;
import cn.com.lxsoft.bdasphone.database.greendao.BridgeJCSBDao;
import cn.com.lxsoft.bdasphone.database.greendao.ChartDao;
import cn.com.lxsoft.bdasphone.database.greendao.CheckDao;
import cn.com.lxsoft.bdasphone.database.greendao.CheckTempDao;
import cn.com.lxsoft.bdasphone.database.greendao.ConstructDao;
import cn.com.lxsoft.bdasphone.database.greendao.DanWeiDao;
import cn.com.lxsoft.bdasphone.database.greendao.DaoMaster;
import cn.com.lxsoft.bdasphone.database.greendao.DaoSession;
import cn.com.lxsoft.bdasphone.database.greendao.DataSession;
import cn.com.lxsoft.bdasphone.database.greendao.DiseaseDao;
import cn.com.lxsoft.bdasphone.database.greendao.LuXianDao;
import cn.com.lxsoft.bdasphone.database.greendao.PatrolDao;
import cn.com.lxsoft.bdasphone.database.greendao.PatrolTempDao;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;
import cn.com.lxsoft.bdasphone.database.greendao.SearchHistoryDao;
import cn.com.lxsoft.bdasphone.database.greendao.UserDao;
import cn.com.lxsoft.bdasphone.database.greendao.YearTestDao;
import cn.com.lxsoft.bdasphone.entity.BridgeChart;
import cn.com.lxsoft.bdasphone.entity.BridgeGisData;
import cn.com.lxsoft.bdasphone.entity.BridgeJCSB;
import cn.com.lxsoft.bdasphone.entity.BridgeMaintain;
import cn.com.lxsoft.bdasphone.entity.Chart;
import cn.com.lxsoft.bdasphone.entity.Check;
import cn.com.lxsoft.bdasphone.entity.CheckTemp;
import cn.com.lxsoft.bdasphone.entity.Construct;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.Disease;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.entity.Patrol;
import cn.com.lxsoft.bdasphone.entity.PatrolTemp;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.entity.SearchHistory;
import cn.com.lxsoft.bdasphone.entity.User;
import cn.com.lxsoft.bdasphone.entity.YearTest;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import cn.com.lxsoft.bdasphone.utils.ConvertUtils;


public class DataBaseGreenImpl implements DataBase {
    private static volatile DataBaseGreenImpl instance=null;

    DaoMaster.OpenHelper helper;
    SQLiteDatabase db=null;
    DaoMaster daoMaster=null;
    DaoSession daoSession=null;
    Context context;

    QueryBuilder bridgeQB;
    QueryBuilder patrolTempQB;
    QueryBuilder checkTempQB;
    QueryBuilder yearTestTempQB;

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
        searchDictList.put("m1.4", new SearchData("LuXian", LuXianDao.Properties.DengJi));
        searchDictList.put("m0.1", new SearchData("BASE", QiaoLiangDao.Properties.DaiMa));
        searchDictList.put("m0.2", new SearchData("BASE", QiaoLiangDao.Properties.MingCheng));
        searchDictList.put("m0.3", new SearchData("BASE", QiaoLiangDao.Properties.DanWeiID));
        searchDictList.put("m0.4", new SearchData("BASE", QiaoLiangDao.Properties.LuXianID));
        searchDictList.put("m6.0", new SearchData("BASE", QiaoLiangDao.Properties.PingJi));
        searchDictList.put("m6.11", new SearchData("Patrol", PatrolDao.Properties.ExamineID));
        searchDictList.put("m1.7", new SearchData("BASE", QiaoLiangDao.Properties.HeZaiDengJi));
        searchDictList.put("m1.3", new SearchData("BASE", QiaoLiangDao.Properties.KuaYueDiWu));
        searchDictList.put("i1.1", new SearchData("BASE", QiaoLiangDao.Properties.QiaoChang));
        searchDictList.put("i1.2", new SearchData("BASE", QiaoLiangDao.Properties.JianQiaoNianYue));
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
    public void initData(){
        File baseFile = new File("/data/data/cn.com.lxsoft.bdas/databases/"+SystemConfig.DBName);
        if(baseFile.exists())
            return;

        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession tpdaoSession = daoMaster.newSession();

        daoMaster.dropAllTables(daoMaster.getDatabase(),true);
        daoMaster.createAllTables(daoMaster.getDatabase(),true);


        //LuXianDao lxDao = tpdaoSession.getLuXianDao();
        //lxDao.deleteAll();
        //DanWeiDao danWeiDao=tpdaoSession.getDanWeiDao();
        //danWeiDao.deleteAll();
        //QiaoLiangDao qiaoLiangDao=tpdaoSession.getQiaoLiangDao();
        //qiaoLiangDao.deleteAll();
        //ChartDao chartDao=tpdaoSession.getChartDao();
        //chartDao.deleteAll();
        //ConstructDao bjDao=tpdaoSession.getConstructDao();
        //bjDao.deleteAll();

        //UserDao userDao=tpdaoSession.getUserDao();
        //userDao.deleteAll();


/*
        Gson gson = new Gson();
        InputStream stream;
        BufferedReader reader;
        String jsonStr="",line="";
        //Map<String,String> luXianList = gson.fromJson(jsonStr, new TypeToken<Map<String,String>>().getType());

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


        stream=context.getResources().openRawResource(R.raw.us);
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
        List<User> listUser = gson.fromJson(jsonStr, new TypeToken<List<User>>() {}.getType());
        userDao.insertInTx(listUser);

*/



        //db.close();
        //tpdaoSession.clear();


    }

    public List<LuXian> getLuXianData(String searchText) {
        openDataBase();
        QueryBuilder qb=daoSession.getLuXianDao().queryBuilder();
        qb.where(qb.or(LuXianDao.Properties.BianHao.like("%" + searchText + "%"),LuXianDao.Properties.MingCheng.like("%" + searchText + "%")));
        qb.orderAsc(LuXianDao.Properties.BianHao);
        return qb.limit(SystemConfig.PageSizeBridge).list();
    }

    public long getLuXianDataCount() {
        openDataBase();
        QueryBuilder qb=daoSession.getLuXianDao().queryBuilder();
        return qb.count();
    }

    public long getUserDataCount() {
        openDataBase();
        QueryBuilder qb=daoSession.getUserDao().queryBuilder();
        return qb.count();
    }


    public long getDeptDataCount() {
        openDataBase();

        QueryBuilder qb = daoSession.getDanWeiDao().queryBuilder();
        return qb.limit(1).list().size();
    }

    public boolean checkBridgeCount() {
        openDataBase();

        QueryBuilder qb = daoSession.getQiaoLiangDao().queryBuilder();
        if(qb.limit(1).list().size()>0)
            return true;
        else
            return false;
    }

    public List<DanWei> getDeptData(String searchText) {
        openDataBase();

        QueryBuilder qb=daoSession.getDanWeiDao().queryBuilder();

        DanWei danWei=DataSession.getCurrentDanWei();
        String sql="";
        if(!ConvertUtils.isSpace(danWei.getDaiMa())) {
            switch (danWei.getDaiMa().substring(0, 1)) {
                case "1":
                    sql = "substr(DAI_MA,1,1)='2'";
                    break;
                case "2":
                    sql = "substr(DAI_MA,1,1)='3'";
                    break;
                case "3":
                    sql = "substr(DAI_MA,1,1)='4'";
                    break;
            }
            WhereCondition.StringCondition condition=new WhereCondition.StringCondition(sql);
            qb.whereOr(condition,DanWeiDao.Properties.DaiMa.eq(danWei.getDaiMa()));
        }
        if(!searchText.isEmpty())
            qb.where(DanWeiDao.Properties.MingCheng.like("%" + searchText + "%"));

        //qb.where(DanWeiDao.Properties.DaiMa.like("%" + searchText + "%"));
        qb.orderAsc(DanWeiDao.Properties.DaiMa);
        return qb.list();
    }

    public void insertDeptData(List<DanWei> dwList){
        openDataBase();
        daoSession.getDanWeiDao().deleteAll();
        daoSession.getDanWeiDao().insertInTx(dwList);
    }

    public void insertBridgeData(List<QiaoLiang> qlList){
        openDataBase();
        daoSession.getQiaoLiangDao().deleteAll();
        daoSession.getQiaoLiangDao().insertInTx(qlList);
    }

    public void insertConstructData(List<Construct> ctList){
        openDataBase();
        if(ctList.size()>0){
            String qldm=ctList.get(0).getBridgeID();
            daoSession.getConstructDao().deleteInTx(daoSession.getConstructDao().queryBuilder().where(ConstructDao.Properties.BridgeID.eq(qldm)).list());
            daoSession.getConstructDao().insertInTx(ctList);
        }
    }

    public void insertDiseaseData(List<Disease> dList){
        openDataBase();
        if(dList.size()>0){
            String exam=dList.get(0).getExamineID();
            daoSession.getDiseaseDao().deleteInTx(daoSession.getDiseaseDao().queryBuilder().where(DiseaseDao.Properties.ExamineID.eq(exam)).list());
            daoSession.getDiseaseDao().insertInTx(dList);
        }
    }

    public void insertRoadData(List<LuXian> qlList){
        openDataBase();
        daoSession.getLuXianDao().deleteAll();
        daoSession.getLuXianDao().insertInTx(qlList);
    }

    public void insertUserData(List<User> usList){
        openDataBase();
        daoSession.getUserDao().deleteAll();
        daoSession.getUserDao().insertInTx(usList);
    }

    public void insertPatrolListData(List<Patrol> ptList){
        openDataBase();
        //daoSession.getLuXianDao().deleteAll();
        /*
        List<String> keys=new ArrayList<>();
        for(int i=0;i<ptList.size();i++){
            keys.add(ptList.get(i).getExamineID());
        }
        daoSession.getPatrolDao().deleteByKeyInTx(keys);
        */
        //daoSession.getPatrolDao().deleteInTx(daoSession.getPatrolDao().queryBuilder().where(PatrolDao.Properties.BHistory.eq(false)).list());
        daoSession.getPatrolDao().deleteAll();
        daoSession.getPatrolDao().insertInTx(ptList);
    }

    public void insertPatrolTempListData(List<PatrolTemp> ptList){
        openDataBase();

        daoSession.getPatrolTempDao().deleteInTx(daoSession.getPatrolTempDao().queryBuilder().where(
                PatrolTempDao.Properties.BridgeCode.eq(ptList.get(0).getBridgeCode())).list());
        //daoSession.getPatrolDao().deleteAll();

        daoSession.getPatrolTempDao().insertInTx(ptList);
    }

    public void insertCheckTempListData(List<CheckTemp> ptList){
        openDataBase();

        daoSession.getCheckTempDao().deleteInTx(daoSession.getCheckTempDao().queryBuilder().where(
                CheckTempDao.Properties.BridgeID.eq(ptList.get(0).getBridgeID())).list());
        //daoSession.getPatrolDao().deleteAll();

        daoSession.getCheckTempDao().insertInTx(ptList);
    }

    public void insertCheckListData(List<Check> ptList,Boolean bHistory){
        openDataBase();
        //daoSession.getCheckDao().deleteInTx(daoSession.getCheckDao().queryBuilder().where(CheckDao.Properties.BHistory.eq(false)).list());
        daoSession.getCheckDao().deleteAll();
        daoSession.getCheckDao().insertInTx(ptList);
    }

    public void insertYearTestListData(List<YearTest> ptList){
        openDataBase();
        //daoSession.getYearTestDao().deleteInTx(daoSession.getYearTestDao().queryBuilder().where(YearTestDao.Properties.BHistory.eq(false)).list());
        daoSession.getYearTestDao().deleteAll();
        daoSession.getYearTestDao().insertInTx(ptList);
    }

    public void insertChartData(List<BridgeChart> chartList,String type){
        openDataBase();
        daoSession.getBridgeChartDao().queryBuilder().where(BridgeChartDao.Properties.Type.eq(type)).buildDelete().executeDeleteWithoutDetachingEntities();
        for(int i=0;i<chartList.size();i++){
            BridgeChart chart=chartList.get(i);
            chart.setType(type);
            if(ConvertUtils.isSpace(chart.getName()))
                chart.setName(chart.getInfo());
        }
        daoSession.getBridgeChartDao().insertInTx(chartList);
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
                    join.whereOr(wc1,wc2,orArray);
                }
                else
                    join.whereOr(wc1,wc2);
            }
            else
                join.where(orList.get(0));
        }
        return join;
    }

    public Long getBridgeNum(){
        if(bridgeQB==null)
            return 0l;
        return bridgeQB.count();
    }

    public QiaoLiang getBridge(String tpqldm){
        return daoSession.getQiaoLiangDao().queryBuilder().where(QiaoLiangDao.Properties.DaiMa.eq(tpqldm)).list().get(0);
    }

    public List<QiaoLiang> getBridgePageData(int pageIndex){
        if(bridgeQB==null)
            return null;
        return bridgeQB.offset(pageIndex * SystemConfig.PageSizeBridge).limit(SystemConfig.PageSizeBridge).list();
    }

    //m1.1┬1┼m1.2┬01
    public void setBridgeSearchData(String searchRes,String orderKey,boolean bOrderUp) {
        HashMap<String,HashMap<String,List<WhereCondition>>> advSearchCondition=new HashMap<>();
        String[] searchList=searchRes.split("┼");

        bridgeQB=daoSession.getQiaoLiangDao().queryBuilder();

        for(int i=0;i<searchList.length;i++) {
            String[] tplist = searchList[i].split("┬");
            if (tplist.length > 1) {
                String dict=tplist[0];
                String res = tplist[1];
                SearchData sdata=searchDictList.get(dict);
                WhereCondition wc=null;
                if(res.equals("NOTNULL"))
                    wc=sdata.property.isNotNull();
                else {
                    switch (dict) {
                        case "m0.1"://桥梁代码
                        case "m0.2"://桥梁名称
                            dict="m0.0";
                            wc = sdata.property.like("%" + tplist[1] + "%");
                            break;
                        case "m0.3":
                            if(tplist[1].substring(0,1).equals("2")){
                                wc=new WhereCondition.StringCondition("substr(DAN_WEI_ID,2,5)='"+tplist[1].substring(1,6)+"'");
                            }
                            else if(tplist[1].substring(0,1).equals("3")){
                                wc=new WhereCondition.StringCondition("substr(DAN_WEI_ID,2,7)='"+tplist[1].substring(1,8)+"'");
                            }
                            else
                                wc=sdata.property.isNotNull();
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

                    List<WhereCondition> orList = andMap.get(dict);
                    if (orList == null) {
                        orList = new ArrayList<>();
                        andMap.put(dict, orList);
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
                case "JCSB": {
                    Join join = bridgeQB.join(QiaoLiangDao.Properties.DaiMa, BridgeJCSB.class,BridgeJCSBDao.Properties.QiaoLiangDaiMa);
                    dealWhereConditionList(join, advSearchCondition.get(key));
                }
                    break;
                case "Patrol": {
                    Join join = bridgeQB.join(QiaoLiangDao.Properties.PatrolID, Patrol.class, PatrolDao.Properties.ExamineID);
                    dealWhereConditionList(join, advSearchCondition.get(key));
                }
                case "LuXian": {
                    Join join = bridgeQB.join(QiaoLiangDao.Properties.LuXianID, LuXian.class, LuXianDao.Properties.BianHao);
                    dealWhereConditionList(join, advSearchCondition.get(key));
                }
                    break;
            }
        }

        //QueryBuilder<QiaoLiang> qb=new QueryBuilder<>();
        Property[] propertys;
        if(ConvertUtils.isSpace(orderKey)){
            propertys=new Property[]{QiaoLiangDao.Properties.DaiMa};
        }
        else if(orderKey.equals("m0.4")){
            propertys=new Property[]{QiaoLiangDao.Properties.LuXianID,QiaoLiangDao.Properties.ZhuangHao};
        }
        else
            propertys=new Property[]{searchDictList.get(orderKey).property};

        if(bOrderUp)
            bridgeQB.orderAsc(propertys);
        else
            bridgeQB.orderDesc(propertys);
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

    public PatrolTemp getPatrolTempData(String sExamineID) {
        openDataBase();
        return daoSession.getPatrolTempDao().load(sExamineID);
    }

    public CheckTemp getCheckTempData(String sExamineID) {
        openDataBase();
        return daoSession.getCheckTempDao().load(sExamineID);
    }


    public Patrol getPatrolDataFromBridge(String sBridgeID) {
        openDataBase();
        return daoSession.getPatrolDao().load(sBridgeID);
    }

    public List<PatrolTemp> getPatrolTempList(String qldm,int page,int type) {
        openDataBase();
        if(patrolTempQB==null)
            patrolTempQB=daoSession.getPatrolTempDao().queryBuilder();
        patrolTempQB.where(PatrolTempDao.Properties.BridgeCode.eq(qldm),PatrolTempDao.Properties.Type.eq(type));
        patrolTempQB.orderDesc(PatrolTempDao.Properties.Date);
        List<PatrolTemp> tplist= patrolTempQB.offset(page * SystemConfig.PageSizeBridge).limit(SystemConfig.PageSizeBridge).list();
        return tplist;
    }

    public List<CheckTemp> getCheckTempList(String qldm,int page,int type) {
        openDataBase();
        if(checkTempQB==null)
            checkTempQB=daoSession.getCheckTempDao().queryBuilder();
        checkTempQB.where(CheckTempDao.Properties.Type.eq(type));
        List<CheckTemp> tplist= checkTempQB.offset(page * SystemConfig.PageSizeBridge).limit(SystemConfig.PageSizeBridge).list();
        return tplist;
    }

    public List<Check> getCheckDataList(String qldm,int page) {
        openDataBase();
        //return checkQB.offset(page * SystemConfig.PageSizeBridge).limit(SystemConfig.PageSizeBridge).list();
        return null;
    }


    public List<YearTest> getYearTestDataList(String qldm,int page) {
        openDataBase();
        //return yearTestQB.offset(page * SystemConfig.PageSizeBridge).limit(SystemConfig.PageSizeBridge).list();
        return null;
    }
    /*

    public long getPatrolListNum(){
            return patrolQB.count();
    }

    public long getCheckListNum(){
        return checkQB.count();
    }

    public long getYearTestListNum(){
        return yearTestQB.count();
    }

    public void setPatrolSearchData(int searchType){
        patrolQB=daoSession.getPatrolDao().queryBuilder();
        switch (searchType) {
            case 0:
                patrolQB.where(PatrolDao.Properties.ExamineID.isNotNull());
                break;
            case 1:
                patrolQB.where(PatrolDao.Properties.NextDate.gt(new Date()));
                break;
            case 2:
                patrolQB.where(PatrolDao.Properties.NextDate.le(new Date()));
                break;
            case 3:
                patrolQB.whereOr(PatrolDao.Properties.PuZhuangSSF.notEq("0"),
                        PatrolDao.Properties.XianXing.notEq("0"),
                        PatrolDao.Properties.LanGan.notEq("0"),
                        PatrolDao.Properties.BiaoZhi.notEq("0"),
                        PatrolDao.Properties.QiaoLuLianJie.notEq("0"));
                break;
        }
        //patrolQB.where(PatrolDao.Properties.BHistory.eq(false));
        patrolQB.orderAsc(PatrolDao.Properties.Date);
    }

    public void setCheckSearchData(int searchType){
        checkQB=daoSession.getCheckDao().queryBuilder();
        switch (searchType) {
            case 0:
                checkQB.where(CheckDao.Properties.ExamineID.isNotNull());
                break;
            case 1:
                checkQB.where(CheckDao.Properties.NextDate.gt(new Date()));
                break;
            case 2:
                checkQB.where(CheckDao.Properties.NextDate.le(new Date()));
                break;
            case 3:
                checkQB.or(CheckDao.Properties.DunTai.notEq("0"),
                        CheckDao.Properties.GuanCeDian.notEq("0"),
                        CheckDao.Properties.JiaoTongSheShi.notEq("0"),
                        CheckDao.Properties.LanGanHuLan.notEq("0"),
                        CheckDao.Properties.PaiShuiSheShi.notEq("0"),
                        CheckDao.Properties.DunTai.notEq("0"),
                        CheckDao.Properties.QiaoLiangJieGou.notEq("0"),
                        CheckDao.Properties.QiaoLiangWaiGuan.notEq("0"),
                        CheckDao.Properties.QiaoMianPuZhuang.notEq("0"),
                        CheckDao.Properties.RenXingDao.notEq("0"),
                        CheckDao.Properties.ShenSuoFeng.notEq("0"),
                        CheckDao.Properties.XieLaSuo.notEq("0"),
                        CheckDao.Properties.YiQiangHuPo.notEq("0"),
                        CheckDao.Properties.ZhuLiang.notEq("0"));
                break;
        }
        checkQB.where(CheckDao.Properties.BHistory.eq(false));
        checkQB.orderAsc(CheckDao.Properties.Date);
    }

    public void setYearTestSearchData(int searchType){
        yearTestQB=daoSession.getYearTestDao().queryBuilder();
        switch (searchType) {
            case 0:
                yearTestQB.where(YearTestDao.Properties.ExamineID.isNotNull());
                break;
            case 1:
                yearTestQB.where(YearTestDao.Properties.PingJia.gt(0));
                break;
            case 2:
                yearTestQB.where(YearTestDao.Properties.PingJia.le(0));
                break;
            case 3:
                yearTestQB.where(YearTestDao.Properties.PingFen.le(100));
                break;
        }
        yearTestQB.where(YearTestDao.Properties.BHistory.eq(false));
        yearTestQB.orderAsc(YearTestDao.Properties.Date);
    }
    */

    public Long setExamineSearchData(int examType,int searchType){
        bridgeQB=daoSession.getQiaoLiangDao().queryBuilder();
        Join join=null;
        if(examType==SystemConfig.ExamineStyle_Patrol){
            join=bridgeQB.join(QiaoLiangDao.Properties.DaiMa, Patrol.class, PatrolDao.Properties.BridgeCode);
            switch (searchType) {
                case 1:
                    join.where(PatrolDao.Properties.NextDate.gt(new Date()));
                    break;
                case 2:
                    join.where(PatrolDao.Properties.NextDate.le(new Date()));
                    break;
                case 3:
                    join.where(join.or(PatrolDao.Properties.PuZhuangSSF.notEq("0"),
                            PatrolDao.Properties.XianXing.notEq("0"),
                            PatrolDao.Properties.LanGan.notEq("0"),
                            PatrolDao.Properties.BiaoZhi.notEq("0"),
                            PatrolDao.Properties.QiaoLuLianJie.notEq("0")));
                    break;
            }
        }
        else if(examType==SystemConfig.ExamineStyle_Check) {
            join=bridgeQB.join(QiaoLiangDao.Properties.DaiMa, Check.class, CheckDao.Properties.BridgeID);
            switch (searchType) {
                case 1:
                    join.where(CheckDao.Properties.NextDate.gt(new Date()));
                    break;
                case 2:
                    join.where(CheckDao.Properties.NextDate.le(new Date()));
                    break;
                case 3:
                    join.where(join.or(CheckDao.Properties.DunTai.notEq("0"),
                            CheckDao.Properties.GuanCeDian.notEq("0"),
                            CheckDao.Properties.JiaoTongSheShi.notEq("0"),
                            CheckDao.Properties.LanGanHuLan.notEq("0"),
                            CheckDao.Properties.PaiShuiSheShi.notEq("0"),
                            CheckDao.Properties.DunTai.notEq("0"),
                            CheckDao.Properties.QiaoLiangJieGou.notEq("0"),
                            CheckDao.Properties.QiaoLiangWaiGuan.notEq("0"),
                            CheckDao.Properties.QiaoMianPuZhuang.notEq("0"),
                            CheckDao.Properties.RenXingDao.notEq("0"),
                            CheckDao.Properties.ShenSuoFeng.notEq("0"),
                            CheckDao.Properties.XieLaSuo.notEq("0"),
                            CheckDao.Properties.YiQiangHuPo.notEq("0"),
                            CheckDao.Properties.ZhuLiang.notEq("0")));
                    break;
            }
        }
        else if(examType==SystemConfig.ExamineStyle_Test){
                join=bridgeQB.join(QiaoLiangDao.Properties.DaiMa, YearTest.class,YearTestDao.Properties.BridgeID);
                switch (searchType) {
                    case 1:
                        join.where(YearTestDao.Properties.NextDate.gt(new Date()));
                        //join.where(QiaoLiangDao.Properties.YearTestID.isNotNull());
                        break;
                    case 2:
                        join.where(YearTestDao.Properties.NextDate.le(new Date()));
                        break;
                    case 3:
                        //join=bridgeQB.join(QiaoLiangDao.Properties.DaiMa, YearTest.class,YearTestDao.Properties.BridgeID);
                        join.where(YearTestDao.Properties.PingFen.le(100));
                        break;
                }
            }
        bridgeQB.orderAsc(QiaoLiangDao.Properties.DaiMa);
        return bridgeQB.count();
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

    public Check getCheckDataFromBridge(String sBridgeID) {
        openDataBase();
        QueryBuilder qb=daoSession.getCheckDao().queryBuilder();
        qb.where(CheckDao.Properties.BridgeID.eq(sBridgeID));
        List<Check> list=qb.limit(1).list();
        if(list.size()>0)
            return list.get(0);
        else
            return null;
    }


    public YearTest getYearTestData(String sExamineID) {
        openDataBase();
        QueryBuilder qb=daoSession.getYearTestDao().queryBuilder();
        qb.where(YearTestDao.Properties.ExamineID.eq(sExamineID));
        List<YearTest> list=qb.limit(1).list();
        if(list.size()>0)
            return list.get(0);
        else
            return null;
    }

    public YearTest getYearTestDataFromBridge(String sBridgeID) {
        openDataBase();
        QueryBuilder qb=daoSession.getYearTestDao().queryBuilder();
        qb.where(YearTestDao.Properties.BridgeID.eq(sBridgeID));
        List<YearTest> list=qb.limit(1).list();
        if(list.size()>0)
            return list.get(0);
        else
            return null;
    }

    public List<Disease> getDiseaseData(String examineID)
    {
        openDataBase();
        QueryBuilder qb=daoSession.getDiseaseDao().queryBuilder();
        qb.where(DiseaseDao.Properties.ExamineID.eq(examineID));
        return qb.list();
    }

    public BridgeChart getChartData(String type,String code)
    {
        openDataBase();
        QueryBuilder qb=daoSession.getBridgeChartDao().queryBuilder();
        qb.where(qb.and(BridgeChartDao.Properties.Type.eq(type),
                BridgeChartDao.Properties.Name.eq(code)));
        List<BridgeChart> blist=qb.limit(1).list();
        if(blist.size()>0)
            return blist.get(0);
        else
            return null;
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

    public void insertOrReplacePatrolTempData(PatrolTemp tpPatrol)
    {
        openDataBase();
        daoSession.getPatrolTempDao().insertOrReplace(tpPatrol);
    }

    public void insertOrReplaceCheckTempData(CheckTemp tpCheck)
    {
        openDataBase();
        daoSession.getCheckTempDao().insertOrReplace(tpCheck);
    }

    public void insertOrReplaceCheckData(Check check)
    {
        openDataBase();
        daoSession.getCheckDao().insertOrReplace(check);
    }

    public void insertOrReplaceYearTestData(YearTest test)
    {
        openDataBase();
        daoSession.getYearTestDao().insertOrReplace(test);
    }

    public void insertOrReplaceMaintainData(BridgeMaintain maintain){
        openDataBase();
        daoSession.getBridgeMaintainDao().insertOrReplace(maintain);
    }

    public List<QiaoLiang> getBridgeGisData(){
        openDataBase();
        if(null==bridgeQB)
            bridgeQB=daoSession.getQiaoLiangDao().queryBuilder();
        bridgeQB.join(QiaoLiangDao.Properties.DaiMa, QiaoLiang.class,QiaoLiangDao.Properties.DaiMa)
                .where(QiaoLiangDao.Properties.Lat.gt(1),QiaoLiangDao.Properties.Lng.gt(1));
        return bridgeQB.limit(200).list();
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

    public List<User> getLeaderUser(){
        openDataBase();
        QueryBuilder qb=daoSession.getUserDao().queryBuilder();
        qb.where(UserDao.Properties.RoleID.eq(1));
        return qb.list();
    }

    public User getUser(String id){
        openDataBase();
        return daoSession.getUserDao().load(id);
    }

    public DanWei getDepartment(String id){
        openDataBase();
        return daoSession.getDanWeiDao().load(id);
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
        qb.orderAsc(ConstructDao.Properties.XuHao);
        return qb.list();
    }

    public List<Construct> getConstructData(String tpql,int pos){
        openDataBase();
        QueryBuilder qb=daoSession.getConstructDao().queryBuilder();
        qb.where(ConstructDao.Properties.BridgeID.eq(tpql),ConstructDao.Properties.XuHao.eq(pos));
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

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
