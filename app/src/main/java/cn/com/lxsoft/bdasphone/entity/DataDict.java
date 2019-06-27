package cn.com.lxsoft.bdasphone.entity;

import android.content.Context;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;

/**
 * Created by Administrator on 2018/12/21.
 */

public class DataDict {
    private static volatile DataDict instance=null;

    private HashMap<String,HashMap> hashMold;
    private XmlResourceParser xmlParser;
    Context context;

    public DataDict(Context ct){
        context=ct;
        hashMold=new HashMap<>();
    }

    public static class DictItem implements Serializable {
        public String code;
        public String name;

        public DictItem(String c,String n)
        {
            code=c;
            name=n;
        }

        @Override
        public String toString() {
            //重写该方法，作为选择器显示的名称
            return name;
        }
    }

    public static void init(Context context){
        if(instance==null){
            synchronized(DataDict .class){
                if(instance==null){
                    instance=new DataDict (context);
                }
            }
        }
    }

    public static String getDict(String mold,String code){
        if(instance!=null)
           return instance.getDictData(mold,code);
        else
            return "";
    }

    public static HashMap<String,String> getDictMap(String mold){
        if(instance!=null)
            return instance.getDictMold(mold);
        else
            return null;
    }

    public static List<DictItem> getDictList(String mold){
        List<DictItem> list=new ArrayList<>();
        HashMap<String,String> hash=getDictMap(mold);
        for(String key:hash.keySet())
        {
            list.add(new DictItem(key,hash.get(key)));
        }
        return list;
    }

    public HashMap<String,String> getDictMold(String mold)
    {
        HashMap<String,String> hashCode=hashMold.get(mold);
        if(hashCode==null)
            hashCode=loadMoldData(mold);
        return hashCode;
    }

    public String getDictData(String mold,String code){
        String res=getDictMold(mold).get(code);
        if(res==null)
            return "";
        else
            return res;
    }

    private HashMap<String,String> loadMoldData(String mCode)
    {
        HashMap<String,String> hashData=new HashMap<>();
        hashMold.put(mCode,hashData);
        String xCode="m".concat(mCode);
        String tpName = null;
        boolean bFind=false;
        try {
            xmlParser=context.getResources().getXml(R.xml.datadict);
            while (xmlParser.getEventType() != XmlResourceParser.END_DOCUMENT) {  //XML开始解析
                tpName = xmlParser.getName();//标签的名字
                if (xmlParser.getEventType() == XmlResourceParser.START_TAG) {      //标签开头
                    if (bFind == false) {
                        if (tpName.trim().equals(xCode)) {  //找到字典
                            bFind = true;
                        }
                    }
                    else if (tpName.trim().equals("it"))
                        hashData.put(xmlParser.getAttributeValue(0), xmlParser.getAttributeValue(1));
                }else if (xmlParser.getEventType() == XmlResourceParser.END_TAG  && tpName.trim().equals(xCode)){
                    xmlParser.close();
                        break;

                }
                xmlParser.next();//读取下一个标签
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return hashData;
    }

    public static LinkedHashMap<String,String> getSearchList(String sSearch){
        if(instance!=null)
            return instance.getSearchData(sSearch);
        else
            return null;
    }

    public static LinkedHashMap<String,HashMap<String,DiseaseInfo>> getDiseaseList(String buJian,String caiZhi){
        if(instance!=null)
            return instance.getBHData(buJian,caiZhi);
        else
            return null;
    }

    public static DiseaseInfo getBHInfo(String diseaseCode){
        if(instance!=null)
            return instance.getBHData(diseaseCode);
        else
            return null;
    }

    public static String getBHName(String buJian,String caiZhi){
        if(instance!=null)
            return instance.getBHName(buJian,caiZhi);
        else
            return null;
    }


    protected LinkedHashMap<String,HashMap<String,DiseaseInfo>> getBHData(String buJian,String caiZhi)
    {
        LinkedHashMap<String,HashMap<String,DiseaseInfo>> hashData=new LinkedHashMap<>();
        LinkedHashMap<String,DiseaseInfo> listData=null;
        List<String> bhNameList=new ArrayList<>();
        boolean bFindStart=false,bFindEnd=false,bBHStart=false;
        String tpName,dName="",dNameCode="";
        try {
            xmlParser=context.getResources().getXml(R.xml.datadict);
            while (xmlParser.getEventType() != XmlResourceParser.END_DOCUMENT) {  //XML开始解析
                tpName = xmlParser.getName();//标签的名字
                if (xmlParser.getEventType() == XmlResourceParser.START_TAG) {      //标签开头
                    if(bFindEnd==false) {
                        if (tpName.substring(0, 1).equals("s")) {
                            if (xmlParser.getAttributeValue(null, "bj").indexOf(buJian) >= 0){
                                if(caiZhi==null || caiZhi.isEmpty())
                                    bFindStart = true;
                                else if(xmlParser.getAttributeValue(null, "mt").replaceFirst("/", "").equals(caiZhi))
                                    bFindStart = true;
                            }
                        }
                    }
                    else
                    {
                        if(bhNameList.size()==0)
                            break;
                        if(bhNameList.contains(tpName)) {
                            dName=xmlParser.getAttributeValue(null,"n");
                            dNameCode=xmlParser.getAttributeValue(null,"c");
                            bBHStart=true;
                        }
                    }
                    if(bBHStart==true && tpName.equals("di")){
                        if(listData==null)
                             listData=new LinkedHashMap<>();
                        DiseaseInfo di=new DiseaseInfo(dNameCode,xmlParser);
                        listData.put(di.getBianMa(),di);
                    }
                    if(bFindStart==true){
                        if(tpName.trim().equals("bh"))
                            bhNameList.add("b"+xmlParser.nextText());
                    }
                }
                if (xmlParser.getEventType() == XmlResourceParser.END_TAG) {
                    if (bFindStart == true) {
                            if (tpName.substring(0, 1).equals("s")) {
                                bFindEnd=true;
                                bFindStart=false;
                            }
                    }
                    if(bBHStart==true && !tpName.equals("di")) {
                        hashData.put(dName, listData);
                        listData = null;
                        bhNameList.remove(tpName);
                        bBHStart=false;
                    }
                }
                xmlParser.next();//读取下一个标签
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return hashData;
    }


    protected DiseaseInfo getBHData(String diseaseCode)
    {
        if(diseaseCode==null || diseaseCode.isEmpty())
            return null;
        String mold="d"+diseaseCode.substring(0,8);
        if(hashMold.containsKey(mold)) {
            HashMap<String, DiseaseInfo> hashList = hashMold.get(mold);
            return hashList.get(diseaseCode);
        }

        HashMap<String,DiseaseInfo> listData=new HashMap<>();
        boolean bBHList=false,bBHFindList=false;
        String tpName,bhCode="",testCode="";
        try {
            xmlParser=context.getResources().getXml(R.xml.datadict);
            while (xmlParser.getEventType() != XmlResourceParser.END_DOCUMENT) {  //XML开始解析
                tpName = xmlParser.getName();//标签的名字
                if (xmlParser.getEventType() == XmlResourceParser.START_TAG) {      //标签开头
                    if (tpName.equals("m7"))
                        bBHList = true;
                    if (bBHList && bBHFindList==false) {
                        if (tpName.substring(0, 1).equals("b")) {
                            bhCode = xmlParser.getAttributeValue(null, "c");
                            if (bhCode.equals(diseaseCode.substring(0, 8)))
                                bBHFindList = true;
                        }
                    }
                    if (bBHFindList) {
                        if (tpName.equals("di")) {
                            DiseaseInfo di = new DiseaseInfo(bhCode, xmlParser);
                            listData.put(di.getBianMa(), di);
                        }
                    }
                }
                if (xmlParser.getEventType() == XmlResourceParser.END_TAG) {
                    if (bBHList && bBHFindList) {
                        if (tpName.substring(0, 1).equals("b")) {
                            break;
                        }
                    }
                }
                xmlParser.next();//读取下一个标签
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        hashMold.put(mold,listData);
        return listData.get(diseaseCode);
    }

    public static HashMap<String,Float> getConstructWeight(String bridgeType){
        if(instance!=null)
            return instance.getCtWeight(bridgeType);
        else
            return null;
    }

    protected HashMap<String,Float> getCtWeight(String bridgeType)
    {
        String tpName;
        HashMap<String,Float> resMap=new HashMap<>();
        boolean bFindList=false;
        try {
            xmlParser=context.getResources().getXml(R.xml.datadict);
            while (xmlParser.getEventType() != XmlResourceParser.END_DOCUMENT) {  //XML开始解析
                tpName = xmlParser.getName();//标签的名字
                if (xmlParser.getEventType() == XmlResourceParser.START_TAG) {      //标签开头
                    if (tpName.equals("m6.1")) {
                        bFindList = true;
                        //continue;
                    }
                    else if(bFindList){
                        if (xmlParser.getAttributeValue(null, "c").equals(bridgeType)){
                            String[] names=xmlParser.getAttributeValue(null, "up")
                                    .concat(","+xmlParser.getAttributeValue(null, "dw"))
                                    .concat(","+xmlParser.getAttributeValue(null, "sf"))
                                    .concat(","+xmlParser.getAttributeValue(null, "uds"))
                                    .split(",");
                            String[] wts=xmlParser.getAttributeValue(null, "upw")
                                    .concat(","+xmlParser.getAttributeValue(null, "dww"))
                                    .concat(","+xmlParser.getAttributeValue(null, "sfw"))
                                    .concat(","+xmlParser.getAttributeValue(null, "udsw"))
                                    .split(",");
                            for(int a=0;a<names.length;a++){
                                resMap.put(names[a],Float.parseFloat(wts[a]));
                            }
                            break;
                        }
                    }
                }
                xmlParser.next();//读取下一个标签
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return resMap;
    }

    //name:桥梁类型 是 大桥,key:m1.1┬1
    private LinkedHashMap<String,String> getSearchData(String sSearch)
    {
        LinkedHashMap<String,String> hashData=new LinkedHashMap<>();
        String tpName,parentName="",parentCode="";
        try {
            xmlParser=context.getResources().getXml(R.xml.datadict);
            while (xmlParser.getEventType() != XmlResourceParser.END_DOCUMENT) {  //XML开始解析
                tpName = xmlParser.getName();//标签的名字
                if (xmlParser.getEventType() == XmlResourceParser.START_TAG) {      //标签开头
                    if (tpName.substring(0,1).equals("m")){
                        parentName=xmlParser.getAttributeValue(0);
                        parentCode=tpName;
                    }
                    else if (tpName.trim().equals("it") && xmlParser.getAttributeValue(1).indexOf(sSearch)>=0) {//找到
                        hashData.put(SystemConfig.BuildSearchData(parentCode,xmlParser.getAttributeValue(0)),parentName+" 是 "+xmlParser.getAttributeValue(1));
                    }
                }
                xmlParser.next();//读取下一个标签
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return hashData;
    }
}
