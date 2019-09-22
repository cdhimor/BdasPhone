package cn.com.lxsoft.bdasphone.app;

import android.content.Context;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import cn.com.lxsoft.bdasphone.entity.DataDict;

public final class SystemConfig {
    private static volatile SystemConfig instance=null;

    public static final String DBName = "BdasPhoneDatabase";
    public static final int PageSizeBridge=100;

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

    public static final short ContentPager_Type_QRCODE=27;

    public static final short ActivityResult_QrCode=223;

    public static final int[][] ExamineLevel={{1,2,2,2},{ 1,2,2,2},{1,2,2,2},{1,2,3,3},{1,2,3,3}};

    public static final String BASE_URL="http://171.217.92.131:18008/";

    //public static final String BASE_URL="http://172.22.1.34:80/"; //湖南

    //public static final String APK_URL="http://171.217.92.131:18008/apk/hulan";

    public static final String Chart_Left_Qlfl="Qlfl";
    public static final String Chart_Left_qljspj="qljspj";
    public static final String Chart_Left_sjhz="sjhz";
    public static final String Chart_Left_jqny="jqny";

    public static final String Chart_Right_Gydw="Gydw";
    public static final String Chart_Right_Lx="Lx";

    public static final short Patrol_NetData_Insert=161;
    public static final short Patrol_NetData_Update=162;

    public static final int DATA_MAX_NUM=500000;

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

    public static Map<String,String> parseNetPicData(String tpres){
        if(tpres==null || tpres.isEmpty())
            return null;
        HashMap<String,String> map=new HashMap<>();
        String[] tplist=tpres.substring(1).split("[┆┄]");
        for(int i=0;i<tplist.length;i+=2){
            map.put(tplist[i+1],tplist[i]);
        }
        return map;
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

    public static int getPingJiColor(int pingJi){
        int color=0xFF00CC00;
        switch (pingJi) {
            case 2:
                color=0xff0000CC;
                break;
            case 3:
                color=0xffFFFF00;
                break;
            case 4:
                color=0xffff6600;
                break;
            case 5:
                color=0xffFF3300;
                break;
        }
        return color;
    }

}
