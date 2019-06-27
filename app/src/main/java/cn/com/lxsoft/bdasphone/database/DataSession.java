package cn.com.lxsoft.bdasphone.database;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.entity.BridgeJCSB;
import cn.com.lxsoft.bdasphone.entity.BridgeJGSJ;
import cn.com.lxsoft.bdasphone.entity.BridgeJJZB;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.entity.Patrol;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.entity.User;

public class DataSession {
    private static volatile DataSession instance=null;

    private DanWei currentDanWei;
    private User currentUser;
    private QiaoLiang currentQiaoLiang;
    private String currentPatrolID;
    private List<BridgeJCSB> testJCSB;
    private List<BridgeJJZB> testJJZB;
    private List<BridgeJGSJ> testJGSJ;
    private HashMap<String,String> simpleSearchInfo;
    //private String searchInfo="";


    private List<Patrol>    tempPatrol;

    Context context;

    public DataSession(Context ct){
        context=ct;
    }

    public static void init(Context ct){
        if(instance==null){
            synchronized(DataDict.class){
                if(instance==null){
                    instance=new DataSession (ct);
                }
            }
        }
    }


    public static QiaoLiang getCurrentQiaoLiang() {
        return instance.currentQiaoLiang;
    }

    public static void  setCurrentQiaoLiang(QiaoLiang currentQiaoLiang) {
        instance.currentQiaoLiang = currentQiaoLiang;
    }


    public static List<BridgeJCSB> getTestJCSB(){
        if(instance.testJCSB==null){
            InputStream stream;
            BufferedReader reader;
            String jsonStr="",line="";

            stream=instance.context.getResources().openRawResource(R.raw.jcsb);
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
            Gson gson = new Gson();
            instance.testJCSB=gson.fromJson(jsonStr, new TypeToken<List<BridgeJCSB>>() {}.getType());
        }
        return instance.testJCSB;
    }

    public static List<BridgeJGSJ> getTestJGSJ(){
        if(instance.testJGSJ==null){
            InputStream stream;
            BufferedReader reader;
            String jsonStr="",line="";

            stream=instance.context.getResources().openRawResource(R.raw.jgsj);
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
            Gson gson = new Gson();
            instance.testJGSJ=gson.fromJson(jsonStr, new TypeToken<List<BridgeJGSJ>>() {}.getType());
        }
        return instance.testJGSJ;
    }

    public static List<BridgeJJZB> getTestJJZB(){
        if(instance.testJJZB==null){
            InputStream stream;
            BufferedReader reader;
            String jsonStr="",line="";

            stream=instance.context.getResources().openRawResource(R.raw.jjzb);
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
            Gson gson = new Gson();
            instance.testJJZB=gson.fromJson(jsonStr, new TypeToken<List<BridgeJJZB>>() {}.getType());
        }
        return instance.testJJZB;
    }

    public List<Patrol> getTempPatrol() {
        return tempPatrol;
    }

    public void setTempPatrol(List<Patrol> tempPatrol) {
        this.tempPatrol = tempPatrol;
    }

    public static DanWei getCurrentDanWei() {
        return instance.currentDanWei;
    }

    public static void  setCurrentDanWei(DanWei currentDanWei) {
        instance.currentDanWei = currentDanWei;
    }

    public static User getCurrentUser() {
        return instance.currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        instance.currentUser = currentUser;
    }

    public String getCurrentPatrolID() {
        return currentPatrolID;
    }

    public void setCurrentPatrolID(String currentPatrolID) {
        this.currentPatrolID = currentPatrolID;
    }

    public static HashMap<String, String> getSimpleSearchInfo() {
        return instance.simpleSearchInfo;
    }

    public static void setSimpleSearchInfo(HashMap<String, String> simpleSearchInfo) {
        instance.simpleSearchInfo = simpleSearchInfo;
    }
}
