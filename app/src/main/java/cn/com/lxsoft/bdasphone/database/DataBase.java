package cn.com.lxsoft.bdasphone.database;

import android.content.Context;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import rx.Observable;

public interface DataBase {
    void init(Context context);

    void initTestData();

    Observable<List<LuXian>> getLuXianData(String searchText);

    Observable<List<QiaoLiang>> getQiaoLiangData(WhereCondition whereCondition, Property property,boolean bUp,int pageIndex);
}
