package cn.com.lxsoft.bdasphone.entity;

import android.content.Context;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParserException;

import java.util.HashMap;
import java.io.IOException;

import cn.com.lxsoft.bdasphone.R;

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

    public static void loadMold(String mold){
        if(instance!=null)
            instance.loadMold(mold);
    }

    public String getDictData(String mold,String code){
        HashMap<String,String> hashCode=hashMold.get(mold);
        if(hashCode==null)
            hashCode=loadMoldData(mold);
        String res=hashCode.get(code);
        if(res==null)
            return "";
        else
            return res;
    }

    public HashMap<String,String> loadMoldData(String mCode)
    {
        HashMap<String,String> hashData=new HashMap<>();
        hashMold.put(mCode,hashData);
        String xCode="d".concat(mCode);
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
                        } else if (tpName.trim().equals("it"))
                            hashData.put(xmlParser.getAttributeValue(0), xmlParser.getAttributeValue(1));
                    }
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
}
