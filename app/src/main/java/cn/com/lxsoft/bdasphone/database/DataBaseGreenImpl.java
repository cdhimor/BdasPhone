package cn.com.lxsoft.bdasphone.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.greendao.DanWeiDao;
import cn.com.lxsoft.bdasphone.database.greendao.DaoMaster;
import cn.com.lxsoft.bdasphone.database.greendao.DaoSession;
import cn.com.lxsoft.bdasphone.database.greendao.LuXianDao;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import rx.Observable;


public class DataBaseGreenImpl implements DataBase {
    private static volatile DataBaseGreenImpl instance=null;

    DaoMaster.OpenHelper helper;
    SQLiteDatabase db=null;
    DaoMaster daoMaster=null;
    DaoSession daoSession=null;
    Context context;

    @Override
    public void init(Context ct) {
        helper = new DaoMaster.DevOpenHelper(ct, SystemConfig.DBName, null);
        context=ct;
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

        db.close();
        tpdaoSession.clear();
    }

    public Observable<List<LuXian>> getLuXianData(String searchText) {
        openDataBase();
        QueryBuilder qb=daoSession.getLuXianDao().queryBuilder();
        qb.where(qb.or(LuXianDao.Properties.BianHao.like("%" + searchText + "%"),LuXianDao.Properties.MingCheng.like("%" + searchText + "%")));
        qb.orderAsc(LuXianDao.Properties.BianHao);
        return qb.rx().list();
    }

    public Observable<List<QiaoLiang>> getQiaoLiangData(WhereCondition whereCondition, Property property,boolean bUp,int pageIndex) {
        openDataBase();
        QueryBuilder qb=daoSession.getQiaoLiangDao().queryBuilder();
        qb.where((whereCondition==null)?QiaoLiangDao.Properties.Id.isNotNull():whereCondition);
        if(bUp)
            qb.orderAsc(property==null?QiaoLiangDao.Properties.DaiMa:property);
        else
            qb.orderDesc(property==null?QiaoLiangDao.Properties.DaiMa:property);
        return qb.offset(pageIndex * SystemConfig.PageSizeQiaoLiang).limit(SystemConfig.PageSizeQiaoLiang)
            .rx()
            .list();
    }

    private void openDataBase(){
        if(daoSession==null) {
            db = helper.getReadableDatabase();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
        }
    }
}
