package cn.com.lxsoft.bdasphone.database.greendao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import cn.com.lxsoft.bdasphone.entity.Check;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.entity.Patrol;
import cn.com.lxsoft.bdasphone.entity.User;
import cn.com.lxsoft.bdasphone.entity.YearTest;

import cn.com.lxsoft.bdasphone.entity.QiaoLiang;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "QIAO_LIANG".
*/
public class QiaoLiangDao extends AbstractDao<QiaoLiang, String> {

    public static final String TABLENAME = "QIAO_LIANG";

    /**
     * Properties of entity QiaoLiang.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property DaiMa = new Property(0, String.class, "daiMa", true, "DAI_MA");
        public final static Property MingCheng = new Property(1, String.class, "mingCheng", false, "MING_CHENG");
        public final static Property LuXianID = new Property(2, String.class, "luXianID", false, "LU_XIAN_ID");
        public final static Property DanWeiID = new Property(3, String.class, "danWeiID", false, "DAN_WEI_ID");
        public final static Property LeiXing = new Property(4, String.class, "leiXing", false, "LEI_XING");
        public final static Property JieGou = new Property(5, String.class, "jieGou", false, "JIE_GOU");
        public final static Property ZhuangHao = new Property(6, float.class, "zhuangHao", false, "ZHUANG_HAO");
        public final static Property QiaoChang = new Property(7, float.class, "qiaoChang", false, "QIAO_CHANG");
        public final static Property QiaoKuan = new Property(8, float.class, "qiaoKuan", false, "QIAO_KUAN");
        public final static Property QiaoGao = new Property(9, float.class, "qiaoGao", false, "QIAO_GAO");
        public final static Property PingJi = new Property(10, int.class, "pingJi", false, "PING_JI");
        public final static Property WorkerID = new Property(11, String.class, "workerID", false, "WORKER_ID");
        public final static Property PatrolID = new Property(12, String.class, "patrolID", false, "PATROL_ID");
        public final static Property YearTestID = new Property(13, String.class, "yearTestID", false, "YEAR_TEST_ID");
        public final static Property CheckID = new Property(14, String.class, "checkID", false, "CHECK_ID");
        public final static Property Lat = new Property(15, double.class, "lat", false, "LAT");
        public final static Property Lng = new Property(16, double.class, "lng", false, "LNG");
        public final static Property JianQiaoNianYue = new Property(17, java.util.Date.class, "jianQiaoNianYue", false, "JIAN_QIAO_NIAN_YUE");
        public final static Property JiaoTongLiuLiang = new Property(18, float.class, "jiaoTongLiuLiang", false, "JIAO_TONG_LIU_LIANG");
        public final static Property HeZaiDengJi = new Property(19, String.class, "heZaiDengJi", false, "HE_ZAI_DENG_JI");
        public final static Property KuaYueDiWu = new Property(20, String.class, "kuaYueDiWu", false, "KUA_YUE_DI_WU");
        public final static Property YongTu = new Property(21, String.class, "yongTu", false, "YONG_TU");
    }

    private DaoSession daoSession;


    public QiaoLiangDao(DaoConfig config) {
        super(config);
    }
    
    public QiaoLiangDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"QIAO_LIANG\" (" + //
                "\"DAI_MA\" TEXT PRIMARY KEY NOT NULL ," + // 0: daiMa
                "\"MING_CHENG\" TEXT," + // 1: mingCheng
                "\"LU_XIAN_ID\" TEXT," + // 2: luXianID
                "\"DAN_WEI_ID\" TEXT," + // 3: danWeiID
                "\"LEI_XING\" TEXT," + // 4: leiXing
                "\"JIE_GOU\" TEXT," + // 5: jieGou
                "\"ZHUANG_HAO\" REAL NOT NULL ," + // 6: zhuangHao
                "\"QIAO_CHANG\" REAL NOT NULL ," + // 7: qiaoChang
                "\"QIAO_KUAN\" REAL NOT NULL ," + // 8: qiaoKuan
                "\"QIAO_GAO\" REAL NOT NULL ," + // 9: qiaoGao
                "\"PING_JI\" INTEGER NOT NULL ," + // 10: pingJi
                "\"WORKER_ID\" TEXT," + // 11: workerID
                "\"PATROL_ID\" TEXT," + // 12: patrolID
                "\"YEAR_TEST_ID\" TEXT," + // 13: yearTestID
                "\"CHECK_ID\" TEXT," + // 14: checkID
                "\"LAT\" REAL NOT NULL ," + // 15: lat
                "\"LNG\" REAL NOT NULL ," + // 16: lng
                "\"JIAN_QIAO_NIAN_YUE\" INTEGER," + // 17: jianQiaoNianYue
                "\"JIAO_TONG_LIU_LIANG\" REAL NOT NULL ," + // 18: jiaoTongLiuLiang
                "\"HE_ZAI_DENG_JI\" TEXT," + // 19: heZaiDengJi
                "\"KUA_YUE_DI_WU\" TEXT," + // 20: kuaYueDiWu
                "\"YONG_TU\" TEXT);"); // 21: yongTu
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"QIAO_LIANG\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, QiaoLiang entity) {
        stmt.clearBindings();
 
        String daiMa = entity.getDaiMa();
        if (daiMa != null) {
            stmt.bindString(1, daiMa);
        }
 
        String mingCheng = entity.getMingCheng();
        if (mingCheng != null) {
            stmt.bindString(2, mingCheng);
        }
 
        String luXianID = entity.getLuXianID();
        if (luXianID != null) {
            stmt.bindString(3, luXianID);
        }
 
        String danWeiID = entity.getDanWeiID();
        if (danWeiID != null) {
            stmt.bindString(4, danWeiID);
        }
 
        String leiXing = entity.getLeiXing();
        if (leiXing != null) {
            stmt.bindString(5, leiXing);
        }
 
        String jieGou = entity.getJieGou();
        if (jieGou != null) {
            stmt.bindString(6, jieGou);
        }
        stmt.bindDouble(7, entity.getZhuangHao());
        stmt.bindDouble(8, entity.getQiaoChang());
        stmt.bindDouble(9, entity.getQiaoKuan());
        stmt.bindDouble(10, entity.getQiaoGao());
        stmt.bindLong(11, entity.getPingJi());
 
        String workerID = entity.getWorkerID();
        if (workerID != null) {
            stmt.bindString(12, workerID);
        }
 
        String patrolID = entity.getPatrolID();
        if (patrolID != null) {
            stmt.bindString(13, patrolID);
        }
 
        String yearTestID = entity.getYearTestID();
        if (yearTestID != null) {
            stmt.bindString(14, yearTestID);
        }
 
        String checkID = entity.getCheckID();
        if (checkID != null) {
            stmt.bindString(15, checkID);
        }
        stmt.bindDouble(16, entity.getLat());
        stmt.bindDouble(17, entity.getLng());
 
        java.util.Date jianQiaoNianYue = entity.getJianQiaoNianYue();
        if (jianQiaoNianYue != null) {
            stmt.bindLong(18, jianQiaoNianYue.getTime());
        }
        stmt.bindDouble(19, entity.getJiaoTongLiuLiang());
 
        String heZaiDengJi = entity.getHeZaiDengJi();
        if (heZaiDengJi != null) {
            stmt.bindString(20, heZaiDengJi);
        }
 
        String kuaYueDiWu = entity.getKuaYueDiWu();
        if (kuaYueDiWu != null) {
            stmt.bindString(21, kuaYueDiWu);
        }
 
        String yongTu = entity.getYongTu();
        if (yongTu != null) {
            stmt.bindString(22, yongTu);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, QiaoLiang entity) {
        stmt.clearBindings();
 
        String daiMa = entity.getDaiMa();
        if (daiMa != null) {
            stmt.bindString(1, daiMa);
        }
 
        String mingCheng = entity.getMingCheng();
        if (mingCheng != null) {
            stmt.bindString(2, mingCheng);
        }
 
        String luXianID = entity.getLuXianID();
        if (luXianID != null) {
            stmt.bindString(3, luXianID);
        }
 
        String danWeiID = entity.getDanWeiID();
        if (danWeiID != null) {
            stmt.bindString(4, danWeiID);
        }
 
        String leiXing = entity.getLeiXing();
        if (leiXing != null) {
            stmt.bindString(5, leiXing);
        }
 
        String jieGou = entity.getJieGou();
        if (jieGou != null) {
            stmt.bindString(6, jieGou);
        }
        stmt.bindDouble(7, entity.getZhuangHao());
        stmt.bindDouble(8, entity.getQiaoChang());
        stmt.bindDouble(9, entity.getQiaoKuan());
        stmt.bindDouble(10, entity.getQiaoGao());
        stmt.bindLong(11, entity.getPingJi());
 
        String workerID = entity.getWorkerID();
        if (workerID != null) {
            stmt.bindString(12, workerID);
        }
 
        String patrolID = entity.getPatrolID();
        if (patrolID != null) {
            stmt.bindString(13, patrolID);
        }
 
        String yearTestID = entity.getYearTestID();
        if (yearTestID != null) {
            stmt.bindString(14, yearTestID);
        }
 
        String checkID = entity.getCheckID();
        if (checkID != null) {
            stmt.bindString(15, checkID);
        }
        stmt.bindDouble(16, entity.getLat());
        stmt.bindDouble(17, entity.getLng());
 
        java.util.Date jianQiaoNianYue = entity.getJianQiaoNianYue();
        if (jianQiaoNianYue != null) {
            stmt.bindLong(18, jianQiaoNianYue.getTime());
        }
        stmt.bindDouble(19, entity.getJiaoTongLiuLiang());
 
        String heZaiDengJi = entity.getHeZaiDengJi();
        if (heZaiDengJi != null) {
            stmt.bindString(20, heZaiDengJi);
        }
 
        String kuaYueDiWu = entity.getKuaYueDiWu();
        if (kuaYueDiWu != null) {
            stmt.bindString(21, kuaYueDiWu);
        }
 
        String yongTu = entity.getYongTu();
        if (yongTu != null) {
            stmt.bindString(22, yongTu);
        }
    }

    @Override
    protected final void attachEntity(QiaoLiang entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public QiaoLiang readEntity(Cursor cursor, int offset) {
        QiaoLiang entity = new QiaoLiang( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // daiMa
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // mingCheng
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // luXianID
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // danWeiID
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // leiXing
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // jieGou
            cursor.getFloat(offset + 6), // zhuangHao
            cursor.getFloat(offset + 7), // qiaoChang
            cursor.getFloat(offset + 8), // qiaoKuan
            cursor.getFloat(offset + 9), // qiaoGao
            cursor.getInt(offset + 10), // pingJi
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // workerID
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // patrolID
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // yearTestID
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // checkID
            cursor.getDouble(offset + 15), // lat
            cursor.getDouble(offset + 16), // lng
            cursor.isNull(offset + 17) ? null : new java.util.Date(cursor.getLong(offset + 17)), // jianQiaoNianYue
            cursor.getFloat(offset + 18), // jiaoTongLiuLiang
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // heZaiDengJi
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // kuaYueDiWu
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21) // yongTu
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, QiaoLiang entity, int offset) {
        entity.setDaiMa(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setMingCheng(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLuXianID(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDanWeiID(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setLeiXing(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setJieGou(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setZhuangHao(cursor.getFloat(offset + 6));
        entity.setQiaoChang(cursor.getFloat(offset + 7));
        entity.setQiaoKuan(cursor.getFloat(offset + 8));
        entity.setQiaoGao(cursor.getFloat(offset + 9));
        entity.setPingJi(cursor.getInt(offset + 10));
        entity.setWorkerID(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setPatrolID(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setYearTestID(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setCheckID(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setLat(cursor.getDouble(offset + 15));
        entity.setLng(cursor.getDouble(offset + 16));
        entity.setJianQiaoNianYue(cursor.isNull(offset + 17) ? null : new java.util.Date(cursor.getLong(offset + 17)));
        entity.setJiaoTongLiuLiang(cursor.getFloat(offset + 18));
        entity.setHeZaiDengJi(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setKuaYueDiWu(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setYongTu(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
     }
    
    @Override
    protected final String updateKeyAfterInsert(QiaoLiang entity, long rowId) {
        return entity.getDaiMa();
    }
    
    @Override
    public String getKey(QiaoLiang entity) {
        if(entity != null) {
            return entity.getDaiMa();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(QiaoLiang entity) {
        return entity.getDaiMa() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getLuXianDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getDanWeiDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T2", daoSession.getUserDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T3", daoSession.getPatrolDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T4", daoSession.getYearTestDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T5", daoSession.getCheckDao().getAllColumns());
            builder.append(" FROM QIAO_LIANG T");
            builder.append(" LEFT JOIN LU_XIAN T0 ON T.\"LU_XIAN_ID\"=T0.\"BIAN_HAO\"");
            builder.append(" LEFT JOIN DAN_WEI T1 ON T.\"DAN_WEI_ID\"=T1.\"DAI_MA\"");
            builder.append(" LEFT JOIN USER T2 ON T.\"WORKER_ID\"=T2.\"LOGIN_NAME\"");
            builder.append(" LEFT JOIN PATROL T3 ON T.\"PATROL_ID\"=T3.\"BRIDGE_CODE\"");
            builder.append(" LEFT JOIN YEAR_TEST T4 ON T.\"YEAR_TEST_ID\"=T4.\"BRIDGE_ID\"");
            builder.append(" LEFT JOIN WEEKCHECK T5 ON T.\"CHECK_ID\"=T5.\"EXAMINE_ID\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected QiaoLiang loadCurrentDeep(Cursor cursor, boolean lock) {
        QiaoLiang entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        LuXian luXian = loadCurrentOther(daoSession.getLuXianDao(), cursor, offset);
        entity.setLuXian(luXian);
        offset += daoSession.getLuXianDao().getAllColumns().length;

        DanWei danWei = loadCurrentOther(daoSession.getDanWeiDao(), cursor, offset);
        entity.setDanWei(danWei);
        offset += daoSession.getDanWeiDao().getAllColumns().length;

        User worker = loadCurrentOther(daoSession.getUserDao(), cursor, offset);
        entity.setWorker(worker);
        offset += daoSession.getUserDao().getAllColumns().length;

        Patrol patrol = loadCurrentOther(daoSession.getPatrolDao(), cursor, offset);
        entity.setPatrol(patrol);
        offset += daoSession.getPatrolDao().getAllColumns().length;

        YearTest yearTest = loadCurrentOther(daoSession.getYearTestDao(), cursor, offset);
        entity.setYearTest(yearTest);
        offset += daoSession.getYearTestDao().getAllColumns().length;

        Check check = loadCurrentOther(daoSession.getCheckDao(), cursor, offset);
        entity.setCheck(check);

        return entity;    
    }

    public QiaoLiang loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<QiaoLiang> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<QiaoLiang> list = new ArrayList<QiaoLiang>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<QiaoLiang> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<QiaoLiang> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
