package cn.com.lxsoft.bdasphone.app;

import android.content.Context;
import android.os.Bundle;

import cn.com.lxsoft.bdasphone.database.DataSession;
import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.entity.Patrol;

public final class SystemConfig {
    private static volatile SystemConfig instance=null;

    public static final String DBName = "BdasPhoneDatabase";
    public static final int PageSizeBridge=30;

    public static final int PageSizeExamine=30;

    public static final int MATISSE_REQUEST_CODE_CHOOSE=123;

    public static final short ExamineStyle_Patrol =1;

    public static final short ExamineStyle_Check =2;

    public static final short ExamineStyle_Test =3;

    public static final short SlideList_Type_Path=11;

    public static final short SlideList_Type_Dept=12;

    public static final short ContentPager_Type_JCSB=21;

    public static final short ContentPager_Type_JGSJ=22;

    public static final short ContentPager_Type_JJZB=23;

    public static final short ContentPager_Type_DLXX=26;

    Context context;

    public SystemConfig(Context ct){
        context=ct;
    }

    public static void init(Context ct){
        if(instance==null){
            synchronized(DataDict.class){
                if(instance==null){
                    instance=new SystemConfig(ct);
                }
            }
        }
    }

    public static final String Bundle_SearchData="SearchHistory";

    public static Bundle buildBundleSearchData(String name, String res){
        Bundle mBundle = new Bundle();
        mBundle.putString(SystemConfig.Bundle_SearchData,name+"╳"+res);
        return mBundle;
    }

    public static String[] parseBundleSearchData(Bundle mBundle){
        String[] res=null;
        if (mBundle != null) {
            String data = mBundle.getString(SystemConfig.Bundle_SearchData);
            res=data.split("╳");
        }
        return res;
    }

    //m1.1┬1┼m1.2┬01
    public static String BuildSearchData(String key,String code)
    {
        return key+"┬"+code;
    }

    public static String[] parseSearchData(String res){
        return res.split("┬");
    }

    public static String BuildMutipleSearchData(String s1,String s2)
    {
        return s1+"┼"+s2;
    }

    public static String[] parseMutipleSearchData(String res)
    {
        return res.split("┼");
    }

}
